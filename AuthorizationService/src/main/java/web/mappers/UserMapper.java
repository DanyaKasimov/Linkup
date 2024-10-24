package web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;
import web.dto.request.SignUpDto;
import web.model.Email;
import web.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "userRole",  constant = "USER")
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(userDto.getPassword()))")
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "id", ignore = true)
    User toEntity(SignUpDto userDto, PasswordEncoder passwordEncoder, Email email);
}