package com.echotech.auth.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.echotech.auth.dto.CreatePasswordRequest;
import com.echotech.auth.dto.CreatePasswordResponse;
import com.echotech.auth.dto.ResponseDto;
import com.echotech.auth.dto.SignInDto;
import com.echotech.auth.exception.UserDoesNotExistException;
import com.echotech.auth.exception.WrongPasswordException;
import com.echotech.auth.model.AppUser;
import com.echotech.auth.repository.AppUserRepository;
import com.echotech.auth.repository.OtpVerificationRepository;
import com.echotech.auth.security.JwtService;
import com.echotech.auth.security.UserInfoService;
import com.echotech.auth.util.UtilityClass;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

	@Mock
	private AppUserRepository appUserRepo;

	@Mock
	private OtpVerificationRepository otpVerificationRepo;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private JwtService jwtService;

	@Mock
	private UserInfoService userInfoService;

	@Spy
	private UtilityClass utilityClass = new UtilityClass();

	@InjectMocks
	private AuthServiceImpl authService;

	@Test
	void logInReturnsJwtWhenCredentialsMatch() {
		SignInDto signInDto = new SignInDto();
		signInDto.setMobileNumber("9999999999");
		signInDto.setPassword("secret");

		AppUser user = new AppUser();
		user.setUserPhoneNumber("9999999999");
		user.setUserPassword("encoded");

		User userDetails = new User("9999999999", "encoded", java.util.List.of());

		when(appUserRepo.findByUserPhoneNumber("9999999999")).thenReturn(Optional.of(user));
		when(passwordEncoder.matches("secret", "encoded")).thenReturn(true);
		when(userInfoService.loadUserByUsername("9999999999")).thenReturn(userDetails);
		when(jwtService.generateToken(userDetails)).thenReturn("jwt-token");

		ResponseDto response = authService.logIn(signInDto);

		assertEquals("SUCCESS", response.getStatus());
		assertEquals("jwt-token", response.getData());
	}

	@Test
	void logInThrowsWhenUserDoesNotExist() {
		SignInDto signInDto = new SignInDto();
		signInDto.setMobileNumber("0000000000");
		signInDto.setPassword("secret");

		when(appUserRepo.findByUserPhoneNumber("0000000000")).thenReturn(Optional.empty());

		assertThrows(UserDoesNotExistException.class, () -> authService.logIn(signInDto));
	}

	@Test
	void logInThrowsWhenPasswordDoesNotMatch() {
		SignInDto signInDto = new SignInDto();
		signInDto.setMobileNumber("9999999999");
		signInDto.setPassword("wrong");

		AppUser user = new AppUser();
		user.setUserPhoneNumber("9999999999");
		user.setUserPassword("encoded");

		when(appUserRepo.findByUserPhoneNumber("9999999999")).thenReturn(Optional.of(user));
		when(passwordEncoder.matches("wrong", "encoded")).thenReturn(false);

		assertThrows(WrongPasswordException.class, () -> authService.logIn(signInDto));
	}

	@Test
	void createPasswordEncodesAndSavesPassword() {
		CreatePasswordRequest request = new CreatePasswordRequest();
		request.setMobileNumber("9999999999");
		request.setPassword("secret");
		request.setConfirmPassword("secret");

		AppUser user = new AppUser();
		user.setUserPhoneNumber("9999999999");

		when(appUserRepo.findByUserPhoneNumber("9999999999")).thenReturn(Optional.of(user));
		when(passwordEncoder.encode("secret")).thenReturn("encoded");
		when(appUserRepo.save(user)).thenReturn(user);

		CreatePasswordResponse response = authService.createPassword(request);

		assertEquals("SUCCESS", response.getStatusCode());
		assertEquals("encoded", user.getUserPassword());
		assertEquals("N", user.getUserFirstLogin());
	}
}
