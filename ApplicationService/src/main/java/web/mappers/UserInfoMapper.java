package web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import web.dto.request.account.UserUpdateDto;
import web.model.UserInfo;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserInfoMapper {

    UserInfoMapper INSTANCE = Mappers.getMapper(UserInfoMapper.class);

    @Mapping(target = "dto.name", ignore = true)
    @Mapping(target = "dto.secondName", ignore = true)
    @Mapping(target = "id", source = "id")
    UserInfo toEntity(UserUpdateDto dto, UUID id);
}