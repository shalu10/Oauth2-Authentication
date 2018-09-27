package com.dextest.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dextest.api.dto.RegisterDto;
import com.dextest.api.model.User;

import com.dextest.api.service.UserService;
@RestController
@RequestMapping("/account")
public class AccountController {
	@Autowired
	private UserService userService;
	
	
	
	@GetMapping("/")
	public String index() {
		return "Connecting";	
	}
	
	
	@RequestMapping("/user")
    public User user(@AuthenticationPrincipal User principal) {
        return principal;
    }

		
	
	@PostMapping("/register")
	public ResponseEntity register(@RequestBody final RegisterDto dto){
		if(userService.findByEmail(dto.getContact())!=null || userService.findByMobile(dto.getContact())!=null || userService.findByUsername(dto.getContact())!=null)
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body("Username/Mail-id/Mobile-no Already Exists");
		
		userService.registerUser(dto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body("User Created Successfully");
	}
		
	
}
	
	

