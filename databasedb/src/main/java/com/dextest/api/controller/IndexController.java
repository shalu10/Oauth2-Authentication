/*package com.dextest.api.controller;

import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dextest.api.model.User;
import com.dextest.api.repository.UserRepository;
import com.dextest.api.service.UserService;
import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;






@RestController
public class IndexController {
	
	@Autowired
	private UserService userService;
	
	@Autowired private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@RequestMapping("/confirm")
	public ModelAndView showPage(ModelAndView modelAndView, User user){
		modelAndView.addObject("user", user);
		modelAndView.setViewName("confirm");
		return modelAndView;
	}	
	
	
	
	
	
	// Process confirmation link
		@RequestMapping(value="/confirm", method = RequestMethod.GET)
		public ResponseEntity confirmRegistration( @RequestParam("token") String token) {
				
			User user = userService.findByConfirmationToken(token);
				
			if (user == null) // No token found in DB
				return ResponseEntity
						.status(HttpStatus.CONFLICT)
						.body("invalidToken, Oops!  This is an invalid confirmation link.");	
				
				user.getConfirmationToken();
				return ResponseEntity
						.status(HttpStatus.CREATED)
						.body("confirmationToken");
			
				
				
		}
	
@RequestMapping(value="/confirm", method = RequestMethod.GET)
	public String confirmRegistration(@RequestParam("token") String token) {
		User user = userService.findByConfirmationToken(token);
		if (user == null)  // No token found in DB		
		{
			return  "This is an invalid confirmation link";	
		}		
		 else // Token found 
		 {
			return "confirmationToken" + user.getConfirmationToken();
		 }
		
}
	@RequestMapping(value="/confirm", method = RequestMethod.GET)
	public ModelAndView confirmRegistration(ModelAndView modelAndView, @RequestParam("token") String token) {
			
		User user = userService.findByConfirmationToken(token);
			
		if (user == null) { // No token found in DB
			modelAndView.addObject("invalidToken", "Oops!  This is an invalid confirmation link.");
		} else { // Token found
			modelAndView.addObject("confirmationToken", user.getConfirmationToken());
		}
			
		modelAndView.setViewName("confirm");
		return modelAndView;		
	}
		
		// Process confirmation link
		@RequestMapping(value="/confirm", method = RequestMethod.POST)
		public ModelAndView confirmRegistration(ModelAndView modelAndView, BindingResult bindingResult, @RequestParam Map<String, String> requestParams, RedirectAttributes redir) {
					
			modelAndView.setViewName("confirm");
			
			Zxcvbn passwordCheck = new Zxcvbn();
			
			Strength strength = passwordCheck.measure(requestParams.get("password"));
			
			if (strength.getScore() < 3) {
				//modelAndView.addObject("errorMessage", "Your password is too weak.  Choose a stronger one.");
				bindingResult.reject("password");
				
				redir.addFlashAttribute("errorMessage", "Your password is too weak.  Choose a stronger one.");

				modelAndView.setViewName("redirect:confirm?token=" + requestParams.get("token"));
				System.out.println(requestParams.get("token"));
				return modelAndView;
			}
		
			// Find the user associated with the reset token
			User user = userService.findByConfirmationToken(requestParams.get("token"));

			// Set new password
			user.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));

			// Set user to enabled
			user.setEnabled(true);
			
			// Save user
			userRepository.save(user);
			
			modelAndView.addObject("successMessage", "Your password has been set!");
			return modelAndView;		
		}
		

}
*/