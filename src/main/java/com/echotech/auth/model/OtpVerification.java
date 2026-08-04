package com.echotech.auth.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "otp_verfn")
public class OtpVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "otpvn_sys_id")
    private Integer otpvnSysId;

    @Column(name = "otpvn_phone_number")
    private String otpvnPhoneNumber;

    @Column(name = "otpvn_otp")
    private String otpvnOtp;

    @Column(name = "otpvn_exp_time")
    private LocalDateTime otpvnExpTime;

    @Column(name = "otp_used")
    private String otpUsed;

    @Column(name = "otpvn_created_dt")
    private LocalDateTime otpvnCreatedDt;

	public Integer getOtpvnSysId() {
		return otpvnSysId;
	}

	public void setOtpvnSysId(Integer otpvnSysId) {
		this.otpvnSysId = otpvnSysId;
	}

	public String getOtpvnPhoneNumber() {
		return otpvnPhoneNumber;
	}

	public void setOtpvnPhoneNumber(String otpvnPhoneNumber) {
		this.otpvnPhoneNumber = otpvnPhoneNumber;
	}

	public String getOtpvnOtp() {
		return otpvnOtp;
	}

	public void setOtpvnOtp(String otpvnOtp) {
		this.otpvnOtp = otpvnOtp;
	}

	public LocalDateTime getOtpvnExpTime() {
		return otpvnExpTime;
	}

	public void setOtpvnExpTime(LocalDateTime otpvnExpTime) {
		this.otpvnExpTime = otpvnExpTime;
	}

	public String getOtpUsed() {
		return otpUsed;
	}

	public void setOtpUsed(String otpUsed) {
		this.otpUsed = otpUsed;
	}

	public LocalDateTime getOtpvnCreatedDt() {
		return otpvnCreatedDt;
	}

	public void setOtpvnCreatedDt(LocalDateTime otpvnCreatedDt) {
		this.otpvnCreatedDt = otpvnCreatedDt;
	}

}
