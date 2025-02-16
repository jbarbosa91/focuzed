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
@Table(name = "sets")
@EntityListeners(AuditingEntityListener.class)
public class SetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_session_id")
    private ExerciseSessionEntity exerciseSession;

    private Integer setNumber;

    @Column(precision = 5, scale = 2, nullable = false)
    private BigDecimal weight;

    @Column(precision = 3, nullable = false)
    private BigDecimal reps;

    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
