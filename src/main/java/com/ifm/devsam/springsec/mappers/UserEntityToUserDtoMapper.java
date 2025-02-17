package com.ifm.devsam.springsec.mappers;

import com.ifm.devsam.springsec.domain.dto.UserDto;
import com.ifm.devsam.springsec.domain.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserEntityToUserDtoMapper {

    UserEntityToUserDtoMapper INSTANCE = Mappers.getMapper(UserEntityToUserDtoMapper.class);

    @Mapping(source = "depot", target = "depotDto")
    UserDto map(UserEntity userEntity);
}
