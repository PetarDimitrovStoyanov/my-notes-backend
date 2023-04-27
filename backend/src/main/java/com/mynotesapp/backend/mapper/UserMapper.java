package com.mynotesapp.backend.mapper;

import com.mynotesapp.backend.domain.entity.UserEntity;
import com.mynotesapp.backend.dto.user.RegisterUserDto;
import com.mynotesapp.backend.dto.user.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Objects;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", expression = "java(getId(user))")
    UserDto toDto(UserEntity user);

    UserEntity toEntity(RegisterUserDto userDto);

    @Named("getId")
    default String getId(UserEntity user) {
        return Objects.nonNull(user.getId()) ? user.getId().toString() : null;
    }
}
