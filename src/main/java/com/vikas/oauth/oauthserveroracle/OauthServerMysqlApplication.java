package com.vikas.oauth.oauthserveroracle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableDiscoveryClient
@SpringBootApplication
@EnableAuthorizationServer
public class OauthServerMysqlApplication extends ServletInitializer{

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				System.out.println("In HGJGKJGK,GKGKG");
				registry.addMapping("/oauth/token")
				.allowedOrigins("http://localhost:8093");
			}
		};
	}
	
	public static void main(String[] args) {
		
		SpringApplication.run(OauthServerMysqlApplication.class, args);
	}
	
	 
}
