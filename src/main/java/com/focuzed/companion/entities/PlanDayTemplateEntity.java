package com.focuzed.companion.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "Plan_Day_Templates",
        uniqueConstraints = @UniqueConstraint(name = "uk_plan_day", columnNames = {"plan_id", "day"}))
@EntityListeners(AuditingEntityListener.class)
public class PlanDayTemplateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private TrainingPlanEntity trainingPlan;

    // TODO: Add constraint in DTO CHECK (day BETWEEN 1 AND 7)
    @Column(nullable = false)
    private Integer day;

    private String description;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
