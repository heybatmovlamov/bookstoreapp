package com.example.bookstoreapp.mapstruct;


import com.example.bookstoreapp.dto.ReaderResponseDto;
import com.example.bookstoreapp.dto.VerifyResponseDto;
import com.example.bookstoreapp.entity.User;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        typeConversionPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class UserMapper {

    public abstract VerifyResponseDto mapToVerifyDto(User user);

//    @Mapping(target = "readerId", source = "id")
//    @Mapping(target = "bookName", source = "book.name")
//    @Mapping(target = "role", source = "role")
//    public abstract ReaderResponseDto.ReaderDto userToReaderDto(User user);

    @Mapping(target = "readers",source = "users")
    public abstract ReaderResponseDto userToReaderDto(Integer dummy,Set<User> users);

}
