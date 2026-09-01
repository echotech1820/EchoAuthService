package com.echotech.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {
	
	private String status;
	
	private String message;
	
	private Object data;

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
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ResponseDto(String status, String message, Object data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public ResponseDto() {
		super();
	}

}
