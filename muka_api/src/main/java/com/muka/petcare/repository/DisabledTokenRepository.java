package com.muka.petcare.repository;

import com.muka.petcare.entity.DisabledToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DisabledTokenRepository extends JpaRepository<DisabledToken, Integer> {
    Optional<DisabledToken> findByTokenId(String tokenId);
}
