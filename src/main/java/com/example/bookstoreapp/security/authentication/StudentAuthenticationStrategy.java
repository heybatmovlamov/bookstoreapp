package com.example.bookstoreapp.security.authentication;

import com.example.bookstoreapp.config.JwtUtil;
import com.example.bookstoreapp.dto.*;
import com.example.bookstoreapp.entity.Student;
import com.example.bookstoreapp.entity.User;
import com.example.bookstoreapp.enums.RoleEnum;
import com.example.bookstoreapp.enums.TokenClaims;
import com.example.bookstoreapp.exception.*;
import com.example.bookstoreapp.mapstruct.UserMapper;
import com.example.bookstoreapp.repository.StudentRepository;
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
public class StudentAuthenticationStrategy implements AuthenticationStrategy{
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    private final Long accessTokenValidtiy = 60000L;
    @Override
    public GenericResponse<Void> register(Object request) {
        RegisterUserRequestDto requestDto=(RegisterUserRequestDto) request;
        boolean isPresent = userRepository.findUserByEmail(requestDto.getEmail()).isPresent();
        if (isPresent){
            throw new EmailAlreadyExistException();
        }
        if (!requestDto.getPassword().equals(requestDto.getConfirmedPassword())){
            throw new PasswordRepeatNotSameException();
        }
        Student student=new Student();
        student.setName(requestDto.getName());
        student.setAge(requestDto.getAge());
        student.setEmail(requestDto.getEmail());
        student.setRole(RoleEnum.STUDENT);
        student.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        studentRepository.save(student);
        return GenericResponse.success("USER SUCCESSFULLY REGISTERED"); //ela duzdur
    }

    @Override
    public GenericResponse<AuthenticationResponseDto> login(Object requestDto) {
        LoginRequestDto loginRequestDto=(LoginRequestDto)requestDto;
        User user = userRepository.findUserByEmail(loginRequestDto.getEmail()).orElseThrow(() -> new UsernameNotFoundException("username not found"));//bunu ozun duzeldersen
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
            user = userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("can not find"));//bunu ozun duzeldersen
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
            String username = claims.get(TokenClaims.EMAIL.getValue(), String.class);
            User user = userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("can not find"));//bunu ozun duzeldersen
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
