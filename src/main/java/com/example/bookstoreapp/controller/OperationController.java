package com.example.bookstoreapp.controller;

import com.example.bookstoreapp.config.JwtUtil;
import com.example.bookstoreapp.dto.BookRequestDto;
import com.example.bookstoreapp.dto.GenericResponse;
import com.example.bookstoreapp.dto.ReaderResponseDto;
import com.example.bookstoreapp.dto.SubscribeRequestDto;
import com.example.bookstoreapp.entity.Author;
import com.example.bookstoreapp.entity.Book;
import com.example.bookstoreapp.entity.Student;
import com.example.bookstoreapp.entity.User;
import com.example.bookstoreapp.enums.RoleEnum;
import com.example.bookstoreapp.mapstruct.UserMapper;
import com.example.bookstoreapp.repository.AuthorRepository;
import com.example.bookstoreapp.repository.BookRepository;
import com.example.bookstoreapp.repository.StudentRepository;
import com.example.bookstoreapp.repository.UserRepository;
import com.example.bookstoreapp.service.AuthorService;
import com.example.bookstoreapp.service.BookService;
import com.example.bookstoreapp.service.StudentService;
import com.example.bookstoreapp.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class OperationController {
    private final AuthorService authorService;
    private final BookService bookService;
    private final StudentService studentService;
    private final MessageUtils messageUtils;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    AuthorRepository authorRepository;
    StudentRepository studentRepository;
    @Value("${my.message.body}")
    private String messageBody;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @PostMapping(value = "/create-book")
    public HttpStatus createBook(@RequestBody BookRequestDto requestDto){
        authorService.createBook(requestDto);
        return HttpStatus.CREATED;
    }
    @GetMapping(value = "/read-book")
    public ResponseEntity<List<Book>> readBook(){
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PostMapping(value = "/books{bookId}/readers")
    public GenericResponse<Void> addReader(@PathVariable Long bookId, HttpServletRequest request){
        String token = jwtUtil.getJWTFromRequest(request);
        String username = jwtUtil.getUsernameFromJwtToken(token);
        User user = userRepository.findUserByEmail(username).get();
        Book book= bookRepository.findById(bookId).get();
        if (user.getRole()== RoleEnum.AUTHOR){
            book.setAuthor(book.getAuthor());
        }else {
            book.setStudent(book.getStudent());
        }
        bookRepository.save(book);
        return GenericResponse.success("SUCCESSFULLY ADDED READER");
    }
    @GetMapping(value = "/books/{bookId}/readers")
    public ResponseEntity<ReaderResponseDto>getReaderListBySpecificBookId(@PathVariable(value = "bookId")Long bookId){
        ReaderResponseDto readerResponseDto=new ReaderResponseDto();
        Book book = bookService.getBookById(bookId);
        ReaderResponseDto readerResponseDto1 = userMapper.userToReaderDto(0,book.getUsers());
        readerResponseDto.setReaders(readerResponseDto1.getReaders());
        return ResponseEntity.ok(readerResponseDto);
    }
    @GetMapping(value = "/currently-reading")
    public ResponseEntity<List<Book>>getListOfBooksThatTheyAreCurrentlyReading(){
        return ResponseEntity.ok(studentService.getListOfBooksThatCurrentlyReading());
    }
    @DeleteMapping(value = "/delete-book{bookId}")
    public HttpStatus deleteBook(@PathVariable Long bookId){
        authorService.deleteBook(bookId);
        return HttpStatus.OK;
    }

    @PostMapping(value = "/subscribe-to-spesific-author")
    public HttpStatus subscribeToSpecificAuthor(@RequestBody SubscribeRequestDto requestDto){
        Author author = authorRepository.findById(requestDto.getAuthorId()).get();
        Student student = studentRepository.findById(requestDto.getStudentId()).get();
        author.setStudents(Collections.singleton(student));
        Author savedAuthor = authorRepository.save(author);
        messageUtils.sendAsync(savedAuthor.getEmail(),student.getName()+" subscribe you.",messageBody);
        return HttpStatus.OK;
    }
}
