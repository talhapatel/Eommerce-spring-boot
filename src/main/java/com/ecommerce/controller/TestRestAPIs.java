package com.ecommerce.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@Scope("request")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController


public class TestRestAPIs extends BaseController {
	
	@GetMapping("/api/test/user")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ApiResponse userAccess() {
	//	clearMessages();
		setData("data", "This is user DashBoard");
		addSuccess(GoMessageType.EXECUTE_SUCCSESS);
		
		return renderResponse();
	}

	@GetMapping("/api/test/pm")
	@PreAuthorize("hasRole('PM')")
	public ApiResponse projectManagementAccess() {
	//	clearMessages();
		setData("data","This is PM DashBoard");
		
		return renderResponse();
	}
	
	@GetMapping("/api/test/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse adminAccess() {
	//	clearMessages();
		setData("data","This is Admin DashBoard");
		addFailure(GoMessageType.ADD_FAILURE);
		return renderResponse();
	}
	
	@PostMapping("api/test/{name}")
	public ApiResponse welcome(@PathVariable String name) {
		setData("name", "welcome "+name);
		return renderResponse();
	}
}