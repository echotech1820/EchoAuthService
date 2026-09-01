package com.echotech.auth.service;

import com.echotech.auth.dto.ClinicSetupRequest;
import com.echotech.auth.dto.ClinicSetupResponse;

public interface ClinicService {

	ClinicSetupResponse setupClinic(ClinicSetupRequest clinicRequest);

}
