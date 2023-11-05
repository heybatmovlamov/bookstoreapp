package com.example.bookstoreapp.security.authentication;

import com.example.bookstoreapp.config.JwtUtil;
import com.example.bookstoreapp.dto.*;
import com.example.bookstoreapp.entity.User;
import com.example.bookstoreapp.enums.TokenClaims;
import com.example.bookstoreapp.exception.*;
import com.example.bookstoreapp.mapstruct.UserMapper;
import com.example.bookstoreapp.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthorAuthenticationStrategy implements AuthenticationStrategy{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    private final Long accessTokenValidtiy = 60000L;

    @Override
    public GenericResponse<Void> register(Object request) {
        RegisterUserRequestDto requestDto=(RegisterUserRequestDto) request;
        boolean isPresent = userRepository.findByName(requestDto.getName()).isPresent();
        if (isPresent){
            throw new UsernameAlreadyExistException();
        }
        if (!requestDto.getPassword().equals(requestDto.getConfirmedPassword())){
            throw new PasswordRepeatNotSameException();
        }
        return GenericResponse.success("USER SUCCESSFULLY REGISTERED");
    }

    @Override
    public GenericResponse<AuthenticationResponseDto> login(Object requestDto) {
        LoginRequestDto loginRequestDto=(LoginRequestDto)requestDto;
        User user = userRepository.findByName(loginRequestDto.getUsername()).orElseThrow(() -> new UsernameNotFoundException("username not found"));
        boolean isPasswordCorrect=passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword());
        if (!isPasswordCorrect){
            throw new WrongPasswordException();
        }
        String token=jwtUtil.generateToken(user.getClaims());
        return GenericResponse.success(AuthenticationResponseDto.builder()
                .authenticationToken(token)
                .build(),"SUCCESS");
    }

    @Override
    public GenericResponse<VerifyResponseDto> verifyToken(HttpServletRequest request) {
        isLogin(request);
        String jwtFromRequest=jwtUtil.getJWTFromRequest(request);
        User user=null;
        try {
            String username = jwtUtil.getUsernameFromJwtToken(jwtFromRequest);
            if (username == null) {
                return null;
            }
            user = userRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException("can not find"));
            if (!jwtUtil.validateToken(jwtFromRequest)) {
                throw new TokeNotValidException();
            }
        }catch (JwtException e){
            log.error(e.getMessage());
        }
        return GenericResponse.success(userMapper.mapToVerifyDto(user),"SUCCESS");
    }

    private void isLogin(HttpServletRequest request) {
        String jwtFromRequest = jwtUtil.getJWTFromRequest(request);
        String username = jwtUtil.getUsernameFromJwtToken(jwtFromRequest);
        if (username == null) {
            throw new UserNotFoundException();
        }
    }

    @Override
    public GenericResponse<? extends Object> refreshToken(HttpServletRequest request) {
        String jwt = jwtUtil.getJWTFromRequest(request);
        try {
            jwtUtil.validateAndExtractClaims(jwt);
        } catch (ExpiredJwtException ex) {
            Claims claims = ex.getClaims();
            String username = claims.get(TokenClaims.USERNAME.getValue(), String.class);
            User user = userRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException("can not find"));
            if ((new Date().getTime() - claims.getExpiration().getTime()) < accessTokenValidtiy * 5) {
                String token = jwtUtil.generateToken(user.getClaims());
                RefreshResponseDto refreshResponseDto = new RefreshResponseDto();
                refreshResponseDto.setToken(token);
                return GenericResponse.success(refreshResponseDto, "SUCCESS");
            } else {
                throw new TokeNotValidException();
            }
        } catch (JwtException ex) {
            throw new TokeNotValidException();
        }
        RefreshResponseDto response = new RefreshResponseDto();
        response.setToken(jwt);
        return GenericResponse.success(response, "success");
    }
}
