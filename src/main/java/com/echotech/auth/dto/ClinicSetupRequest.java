package com.echotech.auth.dto;

public class ClinicSetupRequest {

	private String clnName;
	private String clnPhoneNumber;
	private String clnAddressLine1;
	private String clnArea;
	private String clnCity;
	private Integer userId;
	private String roleCode;

	public String getClnName() {
		return clnName;
	}

	public void setClnName(String clnName) {
		this.clnName = clnName;
	}

	public String getClnPhoneNumber() {
		return clnPhoneNumber;
	}

	public void setClnPhoneNumber(String clnPhoneNumber) {
		this.clnPhoneNumber = clnPhoneNumber;
	}

	public String getClnAddressLine1() {
		return clnAddressLine1;
	}

	public void setClnAddressLine1(String clnAddressLine1) {
		this.clnAddressLine1 = clnAddressLine1;
	}

	public String getClnArea() {
		return clnArea;
	}

	public void setClnArea(String clnArea) {
		this.clnArea = clnArea;
	}

	public String getClnCity() {
		return clnCity;
	}

	public void setClnCity(String clnCity) {
		this.clnCity = clnCity;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

}
