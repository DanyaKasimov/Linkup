package web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import web.dto.response.AvatarDto;
import web.model.Avatar;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AvatarMapper {

    AvatarMapper INSTANCE = Mappers.getMapper(AvatarMapper.class);

    AvatarDto toDto(Avatar entity);

    List<AvatarDto> toDtoList(List<Avatar> entity);
}