package com.dextest.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@Configuration
@PropertySource("application.properties")
@EnableWebSecurity
@Order(99)


public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	


@Override
	protected void configure(HttpSecurity http) throws Exception {
		
	http
    .csrf()
        .disable()
    .antMatcher("/**")
    .authorizeRequests()
    .antMatchers("/account/**").permitAll()
	.antMatchers("/login").permitAll()
	.antMatchers("/oauth/token/revokeById/**").permitAll()
	.antMatchers("/tokens/**").permitAll()	
	.antMatchers("/google").permitAll()
	.anyRequest().authenticated()
	.and().formLogin().permitAll();
	
}
	/*http		
	.anonymous().and().authorizeRequests()	
	.antMatchers("/account/**").permitAll()
	.antMatchers("/login").permitAll()
	.antMatchers("/oauth/token/revokeById/**").permitAll()
	.antMatchers("/tokens/**").permitAll()	
	.antMatchers("/user").permitAll()
	.anyRequest().authenticated()
	.and().formLogin().permitAll()
	.and().csrf().disable();
		// @formatter:on        
	
	}*/

	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/v2/api-docs", "/configuration/**","/console/**", "/swagger-resources/**",  "/swagger-ui.html", "/webjars/**", "/api-docs/**");
	}
	@Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	
	
	
	
}




