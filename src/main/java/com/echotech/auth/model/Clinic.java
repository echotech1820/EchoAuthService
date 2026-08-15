package com.echotech.auth.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clinic")
public class Clinic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cln_sys_id")
	private Integer clnSysId;

	@Column(name = "cln_name")
	private String clnName;

	@Column(name = "cln_phone_number")
	private String clnPhoneNumber;

	@Column(name = "cln_address_line1")
	private String clnAddressLine1;

	@Column(name = "cln_area")
	private String clnArea;

	@Column(name = "cln_city")
	private String clnCity;

	@Column(name = "cln_created_at")
	private LocalDateTime clnCreatedAt;

	public Integer getClnSysId() {
		return clnSysId;
	}

	public void setClnSysId(Integer clnSysId) {
		this.clnSysId = clnSysId;
	}

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

	public LocalDateTime getClnCreatedAt() {
		return clnCreatedAt;
	}

	public void setClnCreatedAt(LocalDateTime clnCreatedAt) {
		this.clnCreatedAt = clnCreatedAt;
	}

}
