package com.stationary.controller;


public enum GoMessageType {
	// Failure
	// Common
	INVALID_TOKEN(101), 
	MISSING_TOKEN(102), 
	EXPIRED_TOKEN(103), 
	PARAM_REQUIRED(104), 
	EXCEPTION_OCCURED(105), 
	PARAM_TYPE_MISMATCH(106),
	NOT_FOUND(107),
	UNAUTHORIZED(108),
	

	// Login
	INVALID_USERNAME(201), 
	INVALID_PASSWORD(202),
	LOGIN_ERROR(203),

	// Issue

	// Success Message
	// Common
	ADD_SUCCESS(1001), 
	UPDATE_SUCCESS(1002),
	APPROVE_SUCCESS(1003),
	DELETE_SUCCESS(1004),
	DESCARD_SUCCESS(1005),
	EXECUTE_SUCCESS(1006),
	MAIL_SENT_SUCCESS(1007),
	COMMON_SUCCESS(1008),

	// Failure
	ISSUE_ALREADY_APPROVED(1051),
	ADD_FAILURE(1052),
	UPDATE_FAILURE(1053),
	APPROVE_FAILURE(1054),
	APPROVE_UNAUTHORIZED(1055),
	APPROVAL_ALREADY_APPROVED(1056),
	DELETE_FAILURE(1057),
	
	
	// File Upload and Download
	// Failure
	FILE_UPLOAD_NO_FILES(2051),
	FILE_DELETE_UNAUTHORIZED(2052),
	 EXECUTE_SUCCSESS(200);
	
	
	int code;

	GoMessageType(int code) {
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}
}
