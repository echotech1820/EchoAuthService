package com.echotech.auth.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_sys_id")
    private Integer userSysId;

    @Column(name = "user_full_name")
    private String userFullName;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_phone_number")
    private String userPhoneNumber;

    @Column(name = "user_first_name")
    private String userFirstName;

    @Column(name = "user_middle_name")
    private String userMiddleName;

    @Column(name = "user_last_name")
    private String userLastName;

    @Column(name = "user_email_verified")
    private String userEmailVerified;

    @Column(name = "user_phone_verified")
    private String userPhoneVerified;

    @Column(name = "user_first_login", nullable = false)
    private String userFirstLogin;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_active_status")
    private String userActiveStatus;

    @Column(name = "user_created_at")
    private LocalDateTime userCreatedAt;

    @Column(name = "user_created_by")
    private String userCreatedBy;

    @Column(name = "user_updated_at")
    private LocalDateTime userUpdatedAt;

    @Column(name = "user_updated_by")
    private String userUpdatedBy;

	public Integer getUserSysId() {
		return userSysId;
	}

	public void setUserSysId(Integer userSysId) {
		this.userSysId = userSysId;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}

	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserMiddleName() {
		return userMiddleName;
	}

	public void setUserMiddleName(String userMiddleName) {
		this.userMiddleName = userMiddleName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserEmailVerified() {
		return userEmailVerified;
	}

	public void setUserEmailVerified(String userEmailVerified) {
		this.userEmailVerified = userEmailVerified;
	}

	public String getUserPhoneVerified() {
		return userPhoneVerified;
	}

	public void setUserPhoneVerified(String userPhoneVerified) {
		this.userPhoneVerified = userPhoneVerified;
	}

	public String getUserFirstLogin() {
		return userFirstLogin;
	}

	public void setUserFirstLogin(String userFirstLogin) {
		this.userFirstLogin = userFirstLogin;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserActiveStatus() {
		return userActiveStatus;
	}

	public void setUserActiveStatus(String userActiveStatus) {
		this.userActiveStatus = userActiveStatus;
	}

	public LocalDateTime getUserCreatedAt() {
		return userCreatedAt;
	}

	public void setUserCreatedAt(LocalDateTime userCreatedAt) {
		this.userCreatedAt = userCreatedAt;
	}

	public String getUserCreatedBy() {
		return userCreatedBy;
	}

	public void setUserCreatedBy(String userCreatedBy) {
		this.userCreatedBy = userCreatedBy;
	}

	public LocalDateTime getUserUpdatedAt() {
		return userUpdatedAt;
	}

	public void setUserUpdatedAt(LocalDateTime userUpdatedAt) {
		this.userUpdatedAt = userUpdatedAt;
	}

	public String getUserUpdatedBy() {
		return userUpdatedBy;
	}

	public void setUserUpdatedBy(String userUpdatedBy) {
		this.userUpdatedBy = userUpdatedBy;
	}
    
}
