package com.echotech.auth.serviceImpl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.echotech.auth.dto.AcntSetupDto;
import com.echotech.auth.dto.AcntSetupResponse;
import com.echotech.auth.dto.CreatePasswordRequest;
import com.echotech.auth.dto.CreatePasswordResponse;
import com.echotech.auth.dto.OtpGenerationResponse;
import com.echotech.auth.dto.OtpVerificationRequest;
import com.echotech.auth.dto.OtpVerificationResponse;
import com.echotech.auth.dto.ResponseDto;
import com.echotech.auth.dto.SignInDto;
import com.echotech.auth.exception.UserAlreadyExistsException;
import com.echotech.auth.exception.UserDoesNotExistException;
import com.echotech.auth.exception.WrongPasswordException;
import com.echotech.auth.model.AppUser;
import com.echotech.auth.model.OtpVerification;
import com.echotech.auth.repository.AppUserRepository;
import com.echotech.auth.repository.OtpVerificationRepository;
import com.echotech.auth.security.JwtService;
import com.echotech.auth.security.UserInfoService;
import com.echotech.auth.service.AuthService;
import com.echotech.auth.util.UtilityClass;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private AppUserRepository appUserRepo;
	
	@Autowired
	private OtpVerificationRepository otpVerificationRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UtilityClass utilityClass;
    
    private final String dummyOtp = "123456";

	@Override
	public ResponseDto logIn(SignInDto signInDto) {
		ResponseDto response = new ResponseDto();

		Optional<AppUser> optAppUser = appUserRepo.findByUserPhoneNumber(signInDto.getMobileNumber());

		if (optAppUser.isPresent()) {
			AppUser user = optAppUser.get();

			if (user.getUserPassword() == null || user.getUserPassword().isBlank()) {
				throw new WrongPasswordException("Password is not set. Please complete account setup.");
			}

			boolean rightPwd = passwordEncoder.matches(signInDto.getPassword(), user.getUserPassword());

			if (rightPwd) {

				UserDetails userDetails = userInfoService.loadUserByUsername(user.getUserPhoneNumber());

				String jwtToken = jwtService.generateToken(userDetails);
				response.setStatus(utilityClass.successCode);
				response.setData(jwtToken);
			} else {
				throw new WrongPasswordException("Password Does Not Match");
			}
		} else {
			throw new UserDoesNotExistException("User Does Not Exist");
		}

		return response;
	}

	@Override
	public AcntSetupResponse acntSetup(AcntSetupDto acntSetupDto) {
		AcntSetupResponse response = new AcntSetupResponse();
		
		Optional<AppUser> optAppUser = appUserRepo.findByUserPhoneNumber(acntSetupDto.getMobileNumber());
		
		if(!optAppUser.isPresent()) {
			AppUser user = new AppUser();
			
			user.setUserPhoneNumber(acntSetupDto.getMobileNumber());
			user.setUserCreatedAt(LocalDateTime.now());
			user.setUserFirstLogin("Y");
			
			user = appUserRepo.save(user);
			
			response.setStatusCode("SUCCESS");
			response.setMessage("User created successfully");
			response.setUserId(user.getUserSysId());
			
		}else {
			throw new UserAlreadyExistsException("User with the given mobile number already exists");
		}

		return response;
	}

	@Override
	public OtpGenerationResponse otpGeneration(AcntSetupDto acntSetupDto) {
		OtpGenerationResponse response = new OtpGenerationResponse();
		
		Optional<OtpVerification> optOtpVn = otpVerificationRepo.findByOtpvnPhoneNumber(acntSetupDto.getMobileNumber());
		Optional<AppUser> optAppUser = appUserRepo.findByUserPhoneNumber(acntSetupDto.getMobileNumber());
		
		if(optAppUser.isPresent()) {
		if(optOtpVn.isPresent()) {
			OtpVerification verification = optOtpVn.get();
			
			/* otp 
			 * 1. If otp is used and expiry time is before current time
			 * 2. If otp is not used and expiry time is before current time*/
			if((verification.getOtpUsed().equals("Y") && verification.getOtpvnExpTime().isBefore(LocalDateTime.now()))
					|| verification.getOtpUsed().equals("N") && verification.getOtpvnExpTime().isBefore(LocalDateTime.now())) {
				otpVerificationRepo.delete(verification);
				
				verification = new OtpVerification();
				verification.setOtpvnCreatedDt(LocalDateTime.now());
				verification.setOtpvnExpTime(LocalDateTime.now().plusSeconds(90));
				verification.setOtpvnPhoneNumber(acntSetupDto.getMobileNumber());
				verification.setOtpUsed("N");
				verification.setOtpvnOtp(dummyOtp);
				
				verification = otpVerificationRepo.save(verification);
				
				response.setOtp(dummyOtp);
				response.setStatusCode("SUCCESS");
				response.setMessage("OTP Generation Successful");
			}else if(verification.getOtpUsed().equals("N") && !verification.getOtpvnExpTime().isBefore(LocalDateTime.now())) {
				
				long remainingSeconds = Duration.between(
				        LocalDateTime.now(),
				        verification.getOtpvnExpTime()
				).getSeconds();
				
				response.setStatusCode("SUCCESS");
				response.setMessage("Please Wait for " + remainingSeconds + " before requesting for a new Otp");
			}
		}else {
			OtpVerification verification = new OtpVerification();
			verification.setOtpvnCreatedDt(LocalDateTime.now());
			verification.setOtpvnExpTime(LocalDateTime.now().plusSeconds(90));
			verification.setOtpvnPhoneNumber(acntSetupDto.getMobileNumber());
			verification.setOtpUsed("N");
			verification.setOtpvnOtp(dummyOtp);
			
			verification = otpVerificationRepo.save(verification);
			
			response.setOtp(dummyOtp);
			response.setStatusCode("SUCCESS");
			response.setMessage("OTP Generation Successful");
		}
		}else {
			throw new UserAlreadyExistsException("No User Account is created with the given mobile Number");
		}
		
		return response;
	}

	@Override
	public OtpVerificationResponse verifyOtp(OtpVerificationRequest otpVerificationRequest) {
		OtpVerificationResponse response = new OtpVerificationResponse();
		
		Optional<OtpVerification> optOtpVn = otpVerificationRepo.findByOtpvnPhoneNumber(otpVerificationRequest.getMobileNumber());

		if(optOtpVn.isPresent()) {
			OtpVerification verification = optOtpVn.get();
			if(verification.getOtpUsed().equals("N") && !verification.getOtpvnExpTime().isBefore(LocalDateTime.now())
					&& verification.getOtpvnOtp().equals(otpVerificationRequest.getOtp())) {
				
				verification.setOtpUsed("Y");
				
				otpVerificationRepo.save(verification);
				response.setStatusCode("SUCCESS");
				response.setMessage("OTP Verification Successful");
			}else if(verification.getOtpUsed().equals("N") && !verification.getOtpvnExpTime().isBefore(LocalDateTime.now())
					&& !verification.getOtpvnOtp().equals(otpVerificationRequest.getOtp())){
				response.setStatusCode("SUCCESS");
				response.setMessage("The Entered Otp is wrong");
			}
			else {
				response.setStatusCode("SUCCESS");
				response.setMessage("Please Generate a new Otp");
			}
		}else {
			response.setStatusCode("SUCCESS");
			response.setMessage("Please Generate a new Otp");
		}
		
		return response;
	}

	@Override
	public CreatePasswordResponse createPassword(CreatePasswordRequest createPasswordRequest) {
		CreatePasswordResponse response = new CreatePasswordResponse();

		if (createPasswordRequest.getPassword() == null
				|| !createPasswordRequest.getPassword().equals(createPasswordRequest.getConfirmPassword())) {
			throw new WrongPasswordException("Password and confirm password do not match");
		}

		AppUser user = appUserRepo.findByUserPhoneNumber(createPasswordRequest.getMobileNumber())
				.orElseThrow(() -> new UserDoesNotExistException("User Does Not Exist"));

		user.setUserPassword(passwordEncoder.encode(createPasswordRequest.getPassword()));
		user.setUserFirstLogin("N");
		user.setUserUpdatedAt(LocalDateTime.now());
		appUserRepo.save(user);

		response.setStatusCode(utilityClass.successCode);
		response.setMessage("Password created successfully");
		return response;
	}

}
