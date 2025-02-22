package com.focuzed.companion.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "Training_Sessions")
@EntityListeners(AuditingEntityListener.class)
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private UUID id;

    // TODO: Add new entity from table Training_Plans
    @Column(nullable = false)
    private UUID planId;

    // TODO: Add new entity from table Plan_Day_Templates
    private UUID templateId;

    // TODO: Add constraint in DTO CHECK (day BETWEEN 1 AND 7)
    @Column(nullable = false)
    private Integer day;

    // TODO: Create Enum with ('Pending', 'In Progress', 'Completed')
    @Column(length = 50, nullable = false)
    private String status;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private Boolean isCompleted;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExerciseSessionEntity> exercises = new ArrayList<>();

    public void addExercise(ExerciseSessionEntity exercise) {
        exercise.setSession(this);
        this.exercises.add(exercise);
    }
}
