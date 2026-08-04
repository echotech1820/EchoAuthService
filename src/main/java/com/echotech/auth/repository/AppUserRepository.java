package com.echotech.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echotech.auth.model.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
	
    Optional<AppUser> findByUserPhoneNumber(String userPhoneNumber);


}
