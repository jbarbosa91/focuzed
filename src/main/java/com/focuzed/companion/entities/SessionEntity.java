package com.focuzed.companion.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "sessions")
@EntityListeners(AuditingEntityListener.class)
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_session_id")
    private SessionEntity referenceSession;

    private Boolean isPlan;

    @CreatedDate
    private LocalDateTime date;

    @Column(name = "completed")
    private Boolean isCompleted;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExerciseSessionEntity> exercises = new ArrayList<>();

    public void addExercise(ExerciseSessionEntity exercise) {
        exercise.setSession(this); // Garantir que a referência está correta
        this.exercises.add(exercise);
    }
}
