package com.focuzed.companion.controllers;

import com.focuzed.companion.dto.UserDto;
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

    @PostMapping
    public ResponseEntity<Void> saveUser(@RequestBody @Valid UserDto dto) {
        var userId = service.save(dto);
        URI location = generateHeaderLocation(userId);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getById(@PathVariable("id") String id) {
        var userDto = service.getUserById(id);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDto> update(@PathVariable String id, @RequestBody @Valid UserDto dto) {
        var updatedUser = service.update(id, dto);
        return ResponseEntity.ok(updatedUser);
    }
}
