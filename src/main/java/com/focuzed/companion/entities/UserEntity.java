package com.focuzed.companion.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "Users")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private UUID id;

    @Column(length = 100, unique = true, nullable = false)
    private String email;

    @Column(length = 100, unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String role;

    @CreatedDate
    private LocalDateTime createdAt;
}
