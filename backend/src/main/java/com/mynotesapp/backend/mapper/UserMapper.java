package com.mynotesapp.backend.mapper;

import com.mynotesapp.backend.domain.entity.UserEntity;
import com.mynotesapp.backend.dto.user.RegisterUserDto;
import com.mynotesapp.backend.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(UserEntity user);

    UserEntity toEntity(RegisterUserDto userDto);
}
