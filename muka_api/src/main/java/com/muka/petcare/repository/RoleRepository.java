package com.muka.petcare.repository;

import com.muka.petcare.entity.Permission;
import com.muka.petcare.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(String roleName);

    @Query("SELECT p FROM Role r JOIN r.permissions p WHERE r.roleName = :roleName")
    List<Permission> findPermissionsByRoleName(@Param("roleName") String roleName);
}
