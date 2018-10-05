package com.dextest.api.service;

import java.security.Principal;

import java.util.Map;
import java.util.Set;


import java.util.UUID;

import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;

import com.dextest.api.dto.RegisterDto;
import com.dextest.api.dto.UserDto;
import com.dextest.api.model.Contact;
import com.dextest.api.model.User;
import com.dextest.api.repository.UserRepository;


public interface UserService {
	void registerUser(RegisterDto dto);

	UserDto findByEmail(String email);
	
	UserDto findByMobile(String mobile);
	
	UserDto findByUsername(String username);
	
	User findById(String uuid);
	
	User validate(String contact, String password);
	
	User update(User user);

	User findByConfirmationToken(String confirmationToken);

	User findByGenerateOtp(String GenerateOtp);

	

	

	
}
