package com.focuzed.companion.controllers;

import com.focuzed.companion.dto.UserDto;
import com.focuzed.companion.entities.UserEntity;
import com.focuzed.companion.mappers.UsersMapper;
import com.focuzed.companion.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserRestController implements GenericController {

    private final UserService service;
    private final UsersMapper usersMapper;

    @PostMapping
    public ResponseEntity<Void> saveUser(@RequestBody @Valid UserDto dto) {
        log.info("Saving user: {}", dto);

        UserEntity userEntity = usersMapper.toEntity(dto);

        var userId = service.save(userEntity);
        URI location = generateHeaderLocation(userId);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getById(@PathVariable("id") String id) {
        var userEntity = service.getUserById(id);
        var userDto = usersMapper.toDto(userEntity);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        log.info("Deleting user with ID: {} ", id);

        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDto> update(@PathVariable String id, @RequestBody @Valid UserDto dto) {
        var updatedUserEntity = service.update(id, dto);

        var userDto = usersMapper.toDto(updatedUserEntity);
        return ResponseEntity.ok(userDto);
    }
}
