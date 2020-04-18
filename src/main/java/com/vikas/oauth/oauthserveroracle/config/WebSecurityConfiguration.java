package com.vikas.oauth.oauthserveroracle.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManager();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	public void configure(HttpSecurity web) throws Exception {
		web.authorizeRequests()
	    .antMatchers("/oauth/addUser","/**","/swagger-ui.html","/webjars/**","/swagger-resources/**","/v2/api-docs","/swagger.html").permitAll().anyRequest().authenticated();
		web.cors().and().csrf().disable();
	}
	

	
	
	
}
