package com.muka.petcare.repository;

import com.muka.petcare.entity.Pet;
import com.muka.petcare.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {
    Optional<Pet> findByPetName(String petName);

    List<Pet> findByUser(User user);
}
