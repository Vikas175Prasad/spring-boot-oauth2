package com.vikas.oauth.oauthserveroracle.controllers;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vikas.oauth.oauthserveroracle.requestmodels.UserRequest;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/oauth")
public class OauthController {

	
	@ApiOperation(value = "Add an user")
	@RequestMapping(value = "/addUser",method = RequestMethod.POST)
	public String addUser(@RequestBody UserRequest user) {
		
		
		System.out.println(user);
		String hashedPW = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
		System.out.println(hashedPW);
		System.out.println(BCrypt.checkpw(user.getPassword(), hashedPW));

		return null;
	}
	
}
