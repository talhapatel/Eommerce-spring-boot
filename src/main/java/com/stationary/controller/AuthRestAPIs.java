package com.stationary.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stationary.message.request.LoginForm;
import com.stationary.message.request.SignUpForm;
import com.stationary.message.response.JwtResponse;
import com.stationary.message.response.ResponseMessage;
import com.stationary.model.Role;
import com.stationary.model.RoleName;
import com.stationary.model.User;
import com.stationary.repository.RoleRepository;
import com.stationary.repository.UserRepository;
import com.stationary.security.jwt.JwtProvider;
@Scope("request")
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs extends BaseController{

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtProvider jwtProvider;

	@PostMapping("/signin")
	public ApiResponse authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		setData("token", jwt);
		setData("user",userRepository.findByUsername(userDetails.getUsername()));
		addSuccess(GoMessageType.COMMON_SUCCESS);
return renderResponse();
		//return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
	}

	@PostMapping("/signup")
	public ApiResponse registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			/*
			 * return new ResponseEntity<>(new
			 * ResponseMessage("Fail -> Username is already taken!"),
			 * HttpStatus.BAD_REQUEST);
			 */
			addFailure(GoMessageType.ADD_FAILURE,"User Name already exists");
			return renderResponse();
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			/*
			 * return new ResponseEntity<>(new
			 * ResponseMessage("Fail -> Email is already in use!"), HttpStatus.BAD_REQUEST);
			 */
			addFailure(GoMessageType.ADD_FAILURE,"Email already exists");
			return renderResponse();
		}

		// Creating user's account
		User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		strRoles.forEach(role -> {
			switch (role) {
			case "admin":
				Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(adminRole);

				break;
			case "pm":
				Role pmRole = roleRepository.findByName(RoleName.ROLE_PM)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(pmRole);

				break;
			default:
				Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
				roles.add(userRole);
			}
		});

		user.setRoles(roles);
		userRepository.save(user);
		addSuccess(GoMessageType.ADD_SUCCESS,"Registration Successfully");
		return renderResponse();
		//return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
	}
}