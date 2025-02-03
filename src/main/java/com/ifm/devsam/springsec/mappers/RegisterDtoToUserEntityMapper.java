package com.ifm.devsam.springsec.mappers;

import com.ifm.devsam.springsec.domain.dto.UserRegistrationDto;
import com.ifm.devsam.springsec.domain.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RegisterDtoToUserEntityMapper {

    RegisterDtoToUserEntityMapper INSTANCE = Mappers.getMapper(RegisterDtoToUserEntityMapper.class);

    UserEntity map(UserRegistrationDto userRegistrationDto);
}
