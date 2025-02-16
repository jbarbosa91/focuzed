package com.focuzed.companion.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String email;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String role;

    @CreatedDate
    private LocalDateTime createdAt;

    // TODO: Quando é alterado o valor deste trainingPlan, a session nao tem um previous
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_plan_id")
    private SessionEntity trainingPlan;
}
