package com.ifm.devsam.springsec.mappers;

import com.ifm.devsam.springsec.domain.dto.UserLoginDto;
import com.ifm.devsam.springsec.domain.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserLoginDtoToUserEntityMapper {

    UserLoginDtoToUserEntityMapper INSTANCE = Mappers.getMapper(UserLoginDtoToUserEntityMapper.class);

    UserEntity map(UserLoginDto userLoginDto);
}
