package com.echotech.auth.serviceImpl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.echotech.auth.dto.ClinicSetupRequest;
import com.echotech.auth.dto.ClinicSetupResponse;
import com.echotech.auth.exception.BadRequestException;
import com.echotech.auth.exception.ResourceNotFoundException;
import com.echotech.auth.model.Clinic;
import com.echotech.auth.model.ClinicUser;
import com.echotech.auth.model.ClinicUserRole;
import com.echotech.auth.model.Role;
import com.echotech.auth.repository.AppUserRepository;
import com.echotech.auth.repository.ClinicRepository;
import com.echotech.auth.repository.ClinicUserRepository;
import com.echotech.auth.repository.ClinicUserRoleRepository;
import com.echotech.auth.repository.RoleRepository;
import com.echotech.auth.service.ClinicService;

@Service
public class ClinicServiceImpl implements ClinicService {

	@Autowired
	private ClinicRepository clinicRepo;

	@Autowired
	private ClinicUserRepository clinicUserRepo;

	@Autowired
	private ClinicUserRoleRepository clnUserRoleRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private AppUserRepository appUserRepo;

	@Override
	@Transactional
	public ClinicSetupResponse setupClinic(ClinicSetupRequest clinicRequest) {
		validateRequest(clinicRequest);

		if (!appUserRepo.existsById(clinicRequest.getUserId())) {
			throw new ResourceNotFoundException("USER NOT FOUND FOR THE GIVEN USER ID");
		}

		Role role = roleRepo.findByRoleCode(clinicRequest.getRoleCode())
				.orElseThrow(() -> new ResourceNotFoundException(
						"ROLE NOT FOUND FOR THE GIVEN ROLE CODE"));

		if (clinicUserRepo.findByClurUserSysId(clinicRequest.getUserId()).isPresent()) {
			throw new BadRequestException("THE GIVEN USER IS ALREADY MAPPED TO A CLINIC");
		}

		if (clinicRepo.findByClnName(clinicRequest.getClnName()).isPresent()) {
			throw new BadRequestException("A CLINIC WITH THE GIVEN NAME ALREADY EXISTS");
		}

		try {
			Clinic clinic = new Clinic();
			clinic.setClnName(clinicRequest.getClnName().trim());
			clinic.setClnPhoneNumber(clinicRequest.getClnPhoneNumber().trim());
			clinic.setClnAddressLine1(clinicRequest.getClnAddressLine1().trim());
			clinic.setClnArea(clinicRequest.getClnArea().trim());
			clinic.setClnCity(clinicRequest.getClnCity().trim());
			clinic.setClnCreatedAt(LocalDateTime.now());
			clinic = clinicRepo.save(clinic);

			ClinicUser clinicUser = new ClinicUser();
			clinicUser.setClurClnSysId(clinic.getClnSysId());
			clinicUser.setClurUserSysId(clinicRequest.getUserId());
			clinicUser.setClurStatus("Y");
			clinicUser.setClurCreatedAt(LocalDateTime.now());
			clinicUser = clinicUserRepo.save(clinicUser);

			ClinicUserRole clnUserRole = new ClinicUserRole();
			clnUserRole.setCluleClurSysId(clinicUser.getClurSysId());
			clnUserRole.setCluleCreatedAt(LocalDateTime.now());
			clnUserRole.setCluleStatus("Y");
			clnUserRole.setCluleRoleSysId(role.getRoleSysId());
			clnUserRoleRepo.save(clnUserRole);

			ClinicSetupResponse response = new ClinicSetupResponse();
			response.setStatusCode("SUCCESS");
			response.setStatusMessage("CLINIC SETUP SUCCESSFUL");
			response.setUniqueId(clinic.getClnSysId());
			return response;
		} catch (DataAccessException ex) {
			throw new BadRequestException("CLINIC SETUP FAILED DUE TO A DATA ERROR");
		}
	}

	private void validateRequest(ClinicSetupRequest clinicRequest) {
		if (clinicRequest == null) {
			throw new BadRequestException("THE GIVEN REQUEST IS INVALID");
		}
		if (isBlank(clinicRequest.getClnName())) {
			throw new BadRequestException("CLINIC NAME IS REQUIRED");
		}
		if (isBlank(clinicRequest.getClnPhoneNumber())) {
			throw new BadRequestException("CLINIC PHONE NUMBER IS REQUIRED");
		}
		if (isBlank(clinicRequest.getClnAddressLine1())) {
			throw new BadRequestException("CLINIC ADDRESS LINE 1 IS REQUIRED");
		}
		if (isBlank(clinicRequest.getClnArea())) {
			throw new BadRequestException("CLINIC AREA IS REQUIRED");
		}
		if (isBlank(clinicRequest.getClnCity())) {
			throw new BadRequestException("CLINIC CITY IS REQUIRED");
		}
		if (clinicRequest.getUserId() == null) {
			throw new BadRequestException("USER ID IS REQUIRED");
		}
		if (isBlank(clinicRequest.getRoleCode())) {
			throw new BadRequestException("ROLE CODE IS REQUIRED");
		}
	}

	private boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}

}
