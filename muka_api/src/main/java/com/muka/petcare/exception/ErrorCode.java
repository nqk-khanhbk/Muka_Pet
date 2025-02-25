package com.muka.petcare.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
	// các ErrorCode mặc định
	USER_NAME_EXISTED(1, "username existed", HttpStatus.BAD_REQUEST),
	USER_EMAIL_EXISTED(2, "useremail existed", HttpStatus.BAD_REQUEST),
	USER_PHONE_EXISTED(3, "userphone existed", HttpStatus.BAD_REQUEST),
	ROLE_NOT_FOUND(4, "role not found", HttpStatus.NOT_FOUND),

	USER_NAME_NOT_EXISTED(5, "username not exists", HttpStatus.NOT_FOUND),
	EMAIL_NAME_NOT_EXISTED(6, "useremail not exists", HttpStatus.NOT_FOUND),
	UNAUTHENTICATED(1000, "unauthenticated", HttpStatus.UNAUTHORIZED),

	PET_NAME_EXISTED(7, "petname existed", HttpStatus.BAD_REQUEST),
	PET_NAME_NOT_EXISTED(8, "petname not exists", HttpStatus.NOT_FOUND),
	PET_NOT_OWNER(9, "you are not the owner of this pet", HttpStatus.FORBIDDEN),

	ALERT_NOT_EXISTED(8, "alert not exists", HttpStatus.NOT_FOUND),
	ALERT_NOT_OWNER(9, "you are not the owner of this alert", HttpStatus.FORBIDDEN),

	FILE_UPLOAD_FAILED(10, "upload image failed", HttpStatus.BAD_REQUEST);
	;

	private int code;
	private String message;
	private HttpStatusCode statusCode;

	ErrorCode(int code, String message, HttpStatusCode statusCode) {
		this.code = code;
		this.message = message;
		this.statusCode = statusCode;
	}
}
