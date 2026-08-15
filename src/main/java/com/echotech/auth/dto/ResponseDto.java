package com.echotech.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {
	
	private String status;
	
	private String message;
	
	private Object Data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return Data;
	}

	public void setData(Object data) {
		Data = data;
	}

	public ResponseDto(String status, String message, Object data) {
		super();
		this.status = status;
		this.message = message;
		Data = data;
	}

	public ResponseDto() {
		super();
	}

}
