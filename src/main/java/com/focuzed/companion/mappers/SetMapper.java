package com.focuzed.companion.mappers;

import com.focuzed.companion.dto.SetDto;
import com.focuzed.companion.entities.SetEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SetMapper {
    SetDto toDto(SetEntity setEntity);

    SetEntity toEntity(SetDto setDto);
}
