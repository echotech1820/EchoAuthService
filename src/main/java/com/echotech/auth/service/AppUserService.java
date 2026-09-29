package com.echotech.auth.service;

import com.echotech.auth.dto.ResponseDto;
import com.echotech.auth.dto.UserRequest;

public interface AppUserService {

	ResponseDto createUser(UserRequest userRequest);

}
