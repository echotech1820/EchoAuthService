package com.echotech.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echotech.auth.dto.ResponseDto;
import com.echotech.auth.dto.UserRequest;
import com.echotech.auth.service.AppUserService;

@RestController
@RequestMapping("/api/v1/user")
public class AppUserController {
	
	@Autowired
	private AppUserService appUserService;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createUser(@RequestBody UserRequest userRequest){
		ResponseDto response = appUserService.createUser(userRequest);
		
		return ResponseEntity.ok(response);
	}

}
