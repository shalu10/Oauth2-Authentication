
package com.dextest.api.controller;

import java.util.ArrayList;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.dextest.api.dto.UserDto;
import com.dextest.api.model.User;
import com.dextest.api.service.UserService;
import com.dextest.api.util.ContactValidator;



@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String index() {
		return "U r now Authenticated and you scope is authorized to access this resource";	
	}
	
	 
	

	@GetMapping("/findByEmail")
	public String findbyEmail(@RequestParam("email") String email) {
		if(userService.findByEmail(email)!=null)
			return "User Found";
		else
			return "User Not Found";				
	}
	
	@GetMapping("/findByMobile")
	public String findByMobile(@RequestParam("mobile") String mobile) {
		if(userService.findByMobile(mobile)!=null)
			return "User Found";
		else
			return "User Not Found";
				
	}
	
	@GetMapping("/find/{contact}")
	@ResponseBody
	public UserDto findByContact(@PathVariable String contact) {
		UserDto user=null;
		if(ContactValidator.isValidEmailAddress(contact)) {
			user=userService.findByEmail(contact);
		}else if(ContactValidator.isValidMobileNumber(contact)) {
			user=userService.findByMobile(contact);
		}else{
			user=userService.findByUsername(contact);
		}
		return user;
	}
	
}
