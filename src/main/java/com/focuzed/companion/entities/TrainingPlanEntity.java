package com.focuzed.companion.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "Training_Plans")
@EntityListeners(AuditingEntityListener.class)
public class TrainingPlanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @Column(length = 100, nullable = false)
    private  String name;

    private LocalDate endDate;

    // TODO: Add constraint in DTO CHECK (day BETWEEN 1 AND 7)
    @Column(nullable = false)
    private Integer daysPerWeek;

    private Boolean isActive;

    @OneToMany(mappedBy = "trainingPlan")
    private List<PlanDayTemplateEntity> planDayTemplateEntities;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
