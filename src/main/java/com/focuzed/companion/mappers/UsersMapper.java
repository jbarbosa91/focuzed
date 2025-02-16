package com.focuzed.companion.mappers;

import com.focuzed.companion.dto.UserDto;
import com.focuzed.companion.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsersMapper {
    UserDto toDto(UserEntity users);

    UserEntity toEntity(UserDto userDto);
}
