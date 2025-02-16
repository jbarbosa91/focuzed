package com.focuzed.companion.services;

import com.focuzed.companion.dto.UserDto;
import com.focuzed.companion.entities.UserEntity;
import com.focuzed.companion.mappers.UsersMapper;
import com.focuzed.companion.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UsersMapper usersMapper;

    public UUID save(UserDto usersDto) {
        log.info("Saving user: {}", usersDto);

        UserEntity userEntity = usersMapper.toEntity(usersDto);

        repository.save(userEntity);
        return userEntity.getId();
    }

    public UserDto getUserById(String id) {
        var userId = UUID.fromString(id);

        var user = repository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return usersMapper.toDto(user);
    }

    public void delete(String id) {
        log.info("Deleting user with ID: {} ", id);
        var userId = UUID.fromString(id);

        var user = repository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        repository.delete(user);
    }

    public UserDto update(String id, UserDto dto) {
        var userId = UUID.fromString(id);

        var user = repository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.setEmail(dto.email());
        user.setName(dto.name());
        user.setPassword(dto.password());
        user.setRole(dto.role());

        repository.save(user);

        return usersMapper.toDto(user);
    }
}
