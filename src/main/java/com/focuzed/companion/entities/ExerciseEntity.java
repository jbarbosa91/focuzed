
package com.focuzed.companion.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "Exercise")
@EntityListeners(AuditingEntityListener.class)
public class ExerciseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private UUID id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private String url;

    @CreatedDate
    private LocalDateTime createdAt;
}
