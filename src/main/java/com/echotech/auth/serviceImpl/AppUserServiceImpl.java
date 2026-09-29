package com.echotech.auth.serviceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.echotech.auth.dto.ResponseDto;
import com.echotech.auth.dto.UserRequest;
import com.echotech.auth.model.AppUser;
import com.echotech.auth.repository.AppUserRepository;
import com.echotech.auth.service.AppUserService;
import com.echotech.auth.util.UtilityClass;

@Service
public class AppUserServiceImpl implements AppUserService {

	@Autowired
	private AppUserRepository appUserRepo;

	private final UtilityClass utilityClass = new UtilityClass();

	@Override
	public ResponseDto createUser(UserRequest userRequest) {
		System.out.println("REQUEST CAME");
		ResponseDto response = new ResponseDto();

		Optional<AppUser> optAppUser = appUserRepo.findByUserPhoneNumber(userRequest.getMobileNumber());

		if (optAppUser.isPresent()) {
			System.out.println("ALREADY EXISTS");
			response.setStatus(utilityClass.successCode);
			response.setMessage("User already exists");
			response.setData(optAppUser.get().getUserSysId());
			return response;
		}

		AppUser user = new AppUser();
		user.setUserPhoneNumber(userRequest.getMobileNumber());
		user.setUserFullName(userRequest.getFullName());
		user.setUserFirstLogin("Y");
		user.setUserCreatedAt(LocalDateTime.now());

		user = appUserRepo.save(user);
		
		System.out.println(user.getUserSysId());

		response.setStatus(utilityClass.successCode);
		response.setMessage("User created successfully");
		response.setData(user.getUserSysId());

		return response;
	}

}
