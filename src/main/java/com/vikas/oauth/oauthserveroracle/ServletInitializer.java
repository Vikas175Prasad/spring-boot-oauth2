package com.vikas.oauth.oauthserveroracle;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.vikas.oauth.oauthserveroracle.filter.CrossOriginFilter;

public class ServletInitializer extends SpringBootServletInitializer {
//
//	@Override
//	public void onStartup(ServletContext servletContext) throws ServletException {
//		// TODO Auto-generated method stub
//		
//		System.out.println("In servlet Context");
//		FilterRegistration corsFilterReg = servletContext.addFilter("simpleCORSFilter", CrossOriginFilter.class);
//        corsFilterReg.addMappingForUrlPatterns(null, false, "/*");
//		//super.onStartup(servletContext);
//	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(OauthServerMysqlApplication.class);
	}

}
