package com.focuzed.companion.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "set")
@EntityListeners(AuditingEntityListener.class)
public class ExerciseSet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private UUID id;

    @Column(precision = 5, scale = 2, nullable = false)
    private BigDecimal weight;

    @Column(precision = 3, nullable = false)
    private BigDecimal reps;

    @Column(precision = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
