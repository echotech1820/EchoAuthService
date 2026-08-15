package com.echotech.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echotech.auth.model.ClinicUser;

@Repository
public interface ClinicUserRepository extends JpaRepository<ClinicUser, Integer> {

	Optional<ClinicUser> findByClurUserSysId(Integer clurUserSysId);

}
