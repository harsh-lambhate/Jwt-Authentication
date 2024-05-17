package com.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.entity.AuthRequest;
import com.security.entity.AuthResponse;
import com.security.entity.User;
import com.security.jwt.JwtHelper;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/home")
@Slf4j
public class SecurityController {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	public AuthenticationManager authenticationManager;

	@Autowired
	private JwtHelper jwtHelper;

	@GetMapping("/welcome")
	public ResponseEntity<String> welcome() {
		return ResponseEntity.ok("Welcome !!");
	}

	@PreAuthorize("hasRole('NORMAL')")
	@GetMapping("/normal")
	public ResponseEntity<String> normal() {
		return ResponseEntity.ok("NORMAL !!");
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin")
	public ResponseEntity<String> admin() {
		return ResponseEntity.ok("ADMIN !!");
	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping("user")
	public ResponseEntity<String> user() {
		return ResponseEntity.ok("USER !!");
	}

	@PostMapping("/token")
	public ResponseEntity<AuthResponse> loginUserSession(@RequestBody AuthRequest user)  throws Exception{
	
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
		} catch (BadCredentialsException e) {
			log.info("Bad credential Exception");
			throw new Exception("Bad credential Exception"+e);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
		final String token = jwtHelper.generateToken(userDetails);
		 
		return ResponseEntity.ok(new AuthResponse(token));
	}
	
	@GetMapping("/tokentest")
	public ResponseEntity<String> tokentest() {
		System.out.println("this is running");
		return ResponseEntity.ok("tokentest !!");
	}

	}
