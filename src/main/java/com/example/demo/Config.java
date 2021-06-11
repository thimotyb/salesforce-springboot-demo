package com.example.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class Config {
 
    private String authEndPoint;
    private String authName;
    private String password;
    private String token;
	public String getAuthEndPoint() {
		return authEndPoint;
	}
	public void setAuthEndPoint(String authEndPoint) {
		this.authEndPoint = authEndPoint;
	}

	public String getAuthName() {
		return authName;
	}
	public void setAuthName(String authName) {
		this.authName = authName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
    
    
    
}