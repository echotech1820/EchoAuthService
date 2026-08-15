package com.echotech.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echotech.auth.dto.AcntSetupDto;
import com.echotech.auth.dto.AcntSetupResponse;
import com.echotech.auth.dto.CreatePasswordRequest;
import com.echotech.auth.dto.CreatePasswordResponse;
import com.echotech.auth.dto.OtpGenerationResponse;
import com.echotech.auth.dto.OtpVerificationRequest;
import com.echotech.auth.dto.OtpVerificationResponse;
import com.echotech.auth.dto.ResponseDto;
import com.echotech.auth.dto.SignInDto;
import com.echotech.auth.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping({"/logIn", "/signIn"})
	public ResponseEntity<ResponseDto> logIn(@RequestBody SignInDto signInDto){
		
		ResponseDto response = authService.logIn(signInDto);
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/newAcnt")
	public ResponseEntity<AcntSetupResponse> newAcntSetup(@RequestBody AcntSetupDto acntSetupDto){
		
		AcntSetupResponse response = authService.acntSetup(acntSetupDto);
		
		return ResponseEntity.ok(response);		
	}
	
	@PostMapping("/otpGeneration")
	public ResponseEntity<OtpGenerationResponse> otpGeneration(@RequestBody AcntSetupDto acntSetupDto){
		
		OtpGenerationResponse response = authService.otpGeneration(acntSetupDto);
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/verifyOtp")
	public ResponseEntity<OtpVerificationResponse> verifyOtp(@RequestBody OtpVerificationRequest otpVerificationRequest){
		
		OtpVerificationResponse response = authService.verifyOtp(otpVerificationRequest);
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/createPassword")
	public ResponseEntity<CreatePasswordResponse> createPassword(@RequestBody CreatePasswordRequest createPasswordRequest){
		
		CreatePasswordResponse response = authService.createPassword(createPasswordRequest);
		
		return ResponseEntity.ok(response);
	}

}
