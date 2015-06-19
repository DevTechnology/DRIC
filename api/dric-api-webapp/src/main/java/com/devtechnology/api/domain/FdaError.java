package com.devtechnology.api.domain;

/**
 * POJO representing FDA JSON object
 * @author jbnimble
 *
 */
public class FdaError {
	private String code;
	private String message;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
