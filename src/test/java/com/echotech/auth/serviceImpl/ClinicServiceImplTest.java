package com.echotech.auth.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

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

@ExtendWith(MockitoExtension.class)
class ClinicServiceImplTest {

	@Mock
	private ClinicRepository clinicRepo;

	@Mock
	private ClinicUserRepository clinicUserRepo;

	@Mock
	private ClinicUserRoleRepository clnUserRoleRepo;

	@Mock
	private RoleRepository roleRepo;

	@Mock
	private AppUserRepository appUserRepo;

	@InjectMocks
	private ClinicServiceImpl clinicService;

	@Test
	void setupClinic_rejectsNullRequest() {
		assertThrows(BadRequestException.class, () -> clinicService.setupClinic(null));
	}

	@Test
	void setupClinic_rejectsMissingFields() {
		ClinicSetupRequest request = validRequest();
		request.setClnName("  ");
		assertThrows(BadRequestException.class, () -> clinicService.setupClinic(request));
	}

	@Test
	void setupClinic_rejectsUnknownUser() {
		when(appUserRepo.existsById(10)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> clinicService.setupClinic(validRequest()));
		verify(clinicRepo, never()).save(any());
	}

	@Test
	void setupClinic_rejectsUnknownRole() {
		when(appUserRepo.existsById(10)).thenReturn(true);
		when(roleRepo.findByRoleCode("CLINIC_ADMIN")).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> clinicService.setupClinic(validRequest()));
		verify(clinicRepo, never()).save(any());
	}

	@Test
	void setupClinic_rejectsUserAlreadyMapped() {
		when(appUserRepo.existsById(10)).thenReturn(true);
		when(roleRepo.findByRoleCode("CLINIC_ADMIN")).thenReturn(Optional.of(role()));
		when(clinicUserRepo.findByClurUserSysId(10)).thenReturn(Optional.of(new ClinicUser()));

		assertThrows(BadRequestException.class, () -> clinicService.setupClinic(validRequest()));
		verify(clinicRepo, never()).save(any());
	}

	@Test
	void setupClinic_createsClinicUserAndRole() {
		when(appUserRepo.existsById(10)).thenReturn(true);
		when(roleRepo.findByRoleCode("CLINIC_ADMIN")).thenReturn(Optional.of(role()));
		when(clinicUserRepo.findByClurUserSysId(10)).thenReturn(Optional.empty());
		when(clinicRepo.findByClnName("Echo Clinic")).thenReturn(Optional.empty());

		Clinic savedClinic = new Clinic();
		savedClinic.setClnSysId(101);
		when(clinicRepo.save(any(Clinic.class))).thenReturn(savedClinic);

		ClinicUser savedUser = new ClinicUser();
		savedUser.setClurSysId(201);
		when(clinicUserRepo.save(any(ClinicUser.class))).thenReturn(savedUser);
		when(clnUserRoleRepo.save(any(ClinicUserRole.class))).thenAnswer(invocation -> invocation.getArgument(0));

		ClinicSetupResponse response = clinicService.setupClinic(validRequest());

		assertEquals("SUCCESS", response.getStatusCode());
		assertEquals("CLINIC SETUP SUCCESSFUL", response.getStatusMessage());
		assertEquals(101, response.getUniqueId());
	}

	@Test
	void setupClinic_wrapsDataAccessErrors() {
		when(appUserRepo.existsById(10)).thenReturn(true);
		when(roleRepo.findByRoleCode("CLINIC_ADMIN")).thenReturn(Optional.of(role()));
		when(clinicUserRepo.findByClurUserSysId(10)).thenReturn(Optional.empty());
		when(clinicRepo.findByClnName("Echo Clinic")).thenReturn(Optional.empty());
		when(clinicRepo.save(any(Clinic.class))).thenThrow(new DataIntegrityViolationException("constraint"));

		assertThrows(BadRequestException.class, () -> clinicService.setupClinic(validRequest()));
	}

	private ClinicSetupRequest validRequest() {
		ClinicSetupRequest request = new ClinicSetupRequest();
		request.setClnName("Echo Clinic");
		request.setClnPhoneNumber("9999999999");
		request.setClnAddressLine1("12 Main Street");
		request.setClnArea("T Nagar");
		request.setClnCity("Chennai");
		request.setUserId(10);
		request.setRoleCode("CLINIC_ADMIN");
		return request;
	}

	private Role role() {
		Role role = new Role();
		role.setRoleSysId(5);
		role.setRoleCode("CLINIC_ADMIN");
		return role;
	}

}
