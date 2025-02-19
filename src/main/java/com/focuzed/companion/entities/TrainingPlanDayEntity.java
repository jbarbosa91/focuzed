package com.focuzed.companion.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "training_plan_day")
public class TrainingPlanDayEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_plan_id")
    private TrainingPlanEntity trainingPlan;

    private Integer day;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_plan_session_id")
    private SessionEntity session;

}
