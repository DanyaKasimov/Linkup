package web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import web.dto.response.UserDto;
import web.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User entity);
}