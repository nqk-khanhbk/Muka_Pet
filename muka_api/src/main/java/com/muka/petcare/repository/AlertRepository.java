package com.muka.petcare.repository;

import com.muka.petcare.entity.Alert;
import com.muka.petcare.entity.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Integer> {
    @Query("SELECT a FROM Alert a WHERE a.user = :user ORDER BY CASE WHEN a.status = 'UNREAD' THEN 0 ELSE 1 END, a.timestamp DESC")
    List<Alert> findAllByUserOrderByStatusAndTimestamp(@Param("user") User user);

    @NonNull
    Optional<Alert> findById(@NonNull Integer id);
}
