package com.focuzed.companion.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "exercise_session")
public class ExerciseSessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private ExerciseEntity exerciseEntity;

    // TODO: Substituir UUID por entidade Session
    private UUID sessionId;

    private Boolean completed;
}
