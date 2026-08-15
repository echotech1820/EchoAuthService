package com.echotech.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echotech.auth.model.Clinic;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Integer> {

	Optional<Clinic> findByClnName(String clnName);

}
