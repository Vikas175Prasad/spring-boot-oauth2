package com.vikas.oauth.oauthserveroracle.requestmodels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRequest {

	@JsonProperty("username")
    private String username;
	
	@JsonProperty("password")
    private String password;
	
	@JsonProperty("email")
    private String email;
	
	@JsonProperty("role")
	private String role;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
}
