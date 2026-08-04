package com.echotech.auth.service;

import com.echotech.auth.dto.AcntSetupDto;
import com.echotech.auth.dto.AcntSetupResponse;
import com.echotech.auth.dto.CreatePasswordRequest;
import com.echotech.auth.dto.CreatePasswordResponse;
import com.echotech.auth.dto.OtpGenerationResponse;
import com.echotech.auth.dto.OtpVerificationRequest;
import com.echotech.auth.dto.OtpVerificationResponse;
import com.echotech.auth.dto.SignInDto;
import com.echotech.auth.dto.SignInDtoResponse;

public interface AuthService {
	
	public SignInDtoResponse logIn(SignInDto signInDto);

	public AcntSetupResponse acntSetup(AcntSetupDto acntSetupDto);

	public OtpGenerationResponse otpGeneration(AcntSetupDto acntSetupDto);

	public OtpVerificationResponse verifyOtp(OtpVerificationRequest otpVerificationRequest);

	public CreatePasswordResponse createPassword(CreatePasswordRequest createPasswordRequest);

}
