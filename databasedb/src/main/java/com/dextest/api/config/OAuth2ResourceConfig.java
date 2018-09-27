package com.dextest.api.config;

import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@EnableResourceServer
public class OAuth2ResourceConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        TokenStore tokenStore = new JdbcTokenStore(dataSource);
        resources.resourceId("dexter-api-services")
                .tokenStore(tokenStore);   	
    }
 

    @Override
    public void configure(HttpSecurity http) throws Exception {
		http
		.anonymous().and().authorizeRequests()
		.antMatchers("/account/**").permitAll()
		.antMatchers("/login").permitAll()
		.antMatchers("/oauth/token/revokeById/**").permitAll()
		.antMatchers("/tokens/**").permitAll()		
		.anyRequest().authenticated()
		.and().formLogin().permitAll()
		.and().csrf().disable() ;
               
    }
}