package com.dextest.api.controller;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dextest.api.dto.RegisterDto;
import com.dextest.api.model.User;
import com.dextest.api.repository.UserRepository;
import com.dextest.api.service.UserService;



@RestController
@RequestMapping("/api")
public class IndexController {
	
	@Autowired
	private UserService userService;
	
	@Autowired private UserRepository userRepository;
		
	
	@GetMapping("/confirm")
	public ResponseEntity<?> verify(@RequestParam("token") String token,HttpSession session) {		
			User user = userService.findByConfirmationToken(token);
			session.setAttribute("ctoken", token); 
			if (user == null) { 
				return ResponseEntity
						.status(HttpStatus.CONFLICT)
						.body("This is an invalid confirmation link.");			
			}		
			else {
				return ResponseEntity
						.status(HttpStatus.OK)
						.body("Valid confirmation link!!!");			
			}	
	}
	
	@PostMapping("/confirm")	
	public ResponseEntity<?> confirm(@RequestBody final RegisterDto dto, HttpSession session){
				String ctoken =(String)session.getAttribute("ctoken");  
				User user = userService.findByConfirmationToken(ctoken);
				BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
				user.setPassword(passwordEncoder.encode(dto.getPassword()));		
				user.setEnabled(true);
				userRepository.save(user);
				return ResponseEntity
						.status(HttpStatus.OK)
						.body("User Verified Successfully");	
			}	
	
	@GetMapping("/otp")
	public ResponseEntity<?> otp(@RequestParam("token") String token,HttpSession session) {		
			User user = userService.findByGenerateOtp(token);
			session.setAttribute("ctoken", token); 
			if (user == null) { 
				return ResponseEntity
						.status(HttpStatus.CONFLICT)
						.body("This is an invalid Otp Number.");			
			}		
			else {
				return ResponseEntity
						.status(HttpStatus.OK)
						.body("Valid Otp Number!!!");			
			}	
	}			

	@PostMapping("/otp")
	public ResponseEntity<?> otp(@RequestBody final RegisterDto dto, HttpSession session){
				String ctoken =(String)session.getAttribute("ctoken");  
				User user = userService.findByGenerateOtp(ctoken);
					BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
					user.setEnabled(true);
					user.setPassword(passwordEncoder.encode(dto.getPassword()));
					userRepository.save(user);	
					return ResponseEntity
							.status(HttpStatus.OK)
							.body("User Verified Successfully");								

			}	
	
}


 