package com.echotech.auth.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clinic_user")
public class ClinicUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "clur_sys_id")
	private Integer clurSysId;

	@Column(name = "clur_cln_sys_id")
	private Integer clurClnSysId;

	@Column(name = "clur_user_sys_id")
	private Integer clurUserSysId;

	@Column(name = "clur_status")
	private String clurStatus;

	@Column(name = "clur_created_at")
	private LocalDateTime clurCreatedAt;

	public Integer getClurSysId() {
		return clurSysId;
	}

	public void setClurSysId(Integer clurSysId) {
		this.clurSysId = clurSysId;
	}

	public Integer getClurClnSysId() {
		return clurClnSysId;
	}

	public void setClurClnSysId(Integer clurClnSysId) {
		this.clurClnSysId = clurClnSysId;
	}

	public Integer getClurUserSysId() {
		return clurUserSysId;
	}

	public void setClurUserSysId(Integer clurUserSysId) {
		this.clurUserSysId = clurUserSysId;
	}

	public String getClurStatus() {
		return clurStatus;
	}

	public void setClurStatus(String clurStatus) {
		this.clurStatus = clurStatus;
	}

	public LocalDateTime getClurCreatedAt() {
		return clurCreatedAt;
	}

	public void setClurCreatedAt(LocalDateTime clurCreatedAt) {
		this.clurCreatedAt = clurCreatedAt;
	}

}
