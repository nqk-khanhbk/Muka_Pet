package com.muka.petcare.repository;

import com.muka.petcare.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName);
    Optional<User> findByUserEmail(String userEmail);
    Optional<User> findByUserPhone(String userPhone);
}
