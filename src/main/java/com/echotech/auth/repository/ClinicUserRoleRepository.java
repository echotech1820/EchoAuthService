package com.echotech.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echotech.auth.model.ClinicUserRole;

@Repository
public interface ClinicUserRoleRepository extends JpaRepository<ClinicUserRole, Integer> {

}
