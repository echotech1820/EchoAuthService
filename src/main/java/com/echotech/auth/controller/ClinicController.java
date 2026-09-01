package com.echotech.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echotech.auth.dto.ClinicSetupRequest;
import com.echotech.auth.dto.ClinicSetupResponse;
import com.echotech.auth.service.ClinicService;

@RestController
@RequestMapping("/api/v1/clinic")
public class ClinicController {

	@Autowired
	private ClinicService clinicService;

	@PostMapping("/setup")
	public ResponseEntity<ClinicSetupResponse> setupClinic(@RequestBody ClinicSetupRequest clinicRequest) {
		ClinicSetupResponse response = clinicService.setupClinic(clinicRequest);
		return ResponseEntity.ok(response);
	}

}
