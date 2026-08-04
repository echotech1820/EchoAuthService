package com.echotech.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echotech.auth.model.OtpVerification;

@Repository
public interface OtpVerificationRepository extends JpaRepository<OtpVerification, Integer> {
	
	 Optional<OtpVerification> findByOtpvnPhoneNumber(String otpvnPhoneNumber);

}
