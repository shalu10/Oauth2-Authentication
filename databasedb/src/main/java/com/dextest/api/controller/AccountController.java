package com.dextest.api.controller;

import java.security.Principal;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dextest.api.dto.RegisterDto;
import com.dextest.api.model.Contact;
import com.dextest.api.model.User;
import com.dextest.api.repository.UserRepository;
import com.dextest.api.service.UserService;
import com.dextest.api.util.ContactValidator;
@RestController
@RequestMapping("/account")
public class AccountController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	
	
	@GetMapping("/")
	public String index() {
		return "Connecting";	
	}
	
	
	/*@RequestMapping("/user")
    public User user(@AuthenticationPrincipal User principal) {
        return principal;
    }*/

		
	
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
	
	
	
	@RequestMapping({"/user","/me"})
	public Map<String, String> usersave(Principal principal) {
		Map<String, Object> details = (Map<String, Object>) ((OAuth2Authentication) principal).getUserAuthentication().getDetails();
		Map<String, String> map = new LinkedHashMap();
	   /* map.put("id",(String) details.get("id"));*/
        map.put("name", (String) details.get("name"));
        map.put("password", (String) details.get("password"));
        map.put("link", (String) details.get("link"));
        map.put("given name", (String) details.get("given_name"));
        map.put("family name", (String) details.get("family_name"));
        map.put("photo", (String) details.get("picture"));
        map.put("enable", (String) details.get("true"));
        /*BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();*/
		User user = new User();
    
        user.setFirstName((String)details.get("given_name"));
        user.setLastName((String)details.get("family_name"));
      /*  user.setLastLogin(LocalDateTime.now());*/
        user.setAvatar((String)details.get("picture"));
      /*  user.setCreated(LocalDateTime.now());*/
        user.setEnabled(true);	
        user.setPrincipalId((String)details.get("id"));
       /* user.setPassword(passwordEncoder.encode((String) details.get("password")));*/
        Contact contact=new Contact();
		if((String)details.get("email")!=null) {
			if(ContactValidator.isValidEmailAddress((String)details.get("email"))) {
				contact.setType("email");
				contact.setData((String)details.get("email"));	
				contact.setUserId(user);			
			}else  {
				return null;		
			}
			if(user.getUsername()==null || user.getUsername()=="") {
				user.setUsername((String)details.get("name"));
			}			
		}
    
		user.getContacts().add(contact);
        
		userRepository.save(user);
    
    return map;


}
	
}
	
	

