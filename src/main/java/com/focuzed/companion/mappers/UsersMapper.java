package com.focuzed.companion.mappers;

import com.focuzed.companion.dto.UserDto;
import com.focuzed.companion.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsersMapper {
    UserDto toDto(User users);

    User toEntity(UserDto userDto);
}
