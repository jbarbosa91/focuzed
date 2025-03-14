package com.focuzed.companion.repositories;

import com.focuzed.companion.entities.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<SessionEntity, UUID> {

    @Query("SELECT e FROM SessionEntity e WHERE FUNCTION('DATE', e.createdAt) = :currentDate")
    Optional<SessionEntity> findByCreatedAtCurrentDay(@Param("currentDate") LocalDate currentDate);
}
