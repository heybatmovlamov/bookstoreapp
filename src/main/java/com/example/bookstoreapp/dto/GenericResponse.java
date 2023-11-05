package com.example.bookstoreapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse<R> {
    @JsonFormat(pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSXXX")
    private OffsetDateTime timestamp;
    private Integer status;
    private String key;
    private String message;
    private R data;
    private List<ValidationError> errors = new ArrayList<>();

    public static <R> GenericResponse<R> success(R data, String key) {
        return new GenericResponseBuilder<R>()
                .status(HttpStatus.OK.value())
                .key(key)
                .data(data)
                .build();
    }

    public static <R> GenericResponse<R> success(GenericResponse<?> data) {
        return new GenericResponseBuilder<R>()
                .status(HttpStatus.OK.value())
                .key("SUCCESS")
                .data((R) data)
                .build();
    }

    public static GenericResponse<Void> success(String key) {
        return new GenericResponseBuilder<Void>()
                .status(HttpStatus.OK.value())
                .key(key)
                .build();
    }

    public static GenericResponse<Void> failure(int httpStatusCode, String key, String message) {
        return new GenericResponseBuilder<Void>()
                .timestamp(OffsetDateTime.now())
                .status(httpStatusCode)
                .key(key)
                .message(message)
                .build();
    }

    public static <R> GenericResponse<R> failure(int httpStatusCode, String key, R data) {
        return new GenericResponseBuilder<R>()
                .timestamp(OffsetDateTime.now())
                .status(httpStatusCode)
                .key(key)
                .data(data)
                .build();
    }

    public static GenericResponse<Void> failure(int httpStatusCode, String key) {
        return new GenericResponseBuilder<Void>()
                .timestamp(OffsetDateTime.now())
                .status(httpStatusCode)
                .key(key)
                .build();
    }

    public void addValidationError(String field, String message) {
        if (Objects.isNull(errors)) errors = new ArrayList<>();
        errors.add(new ValidationError(field, message));
    }

}