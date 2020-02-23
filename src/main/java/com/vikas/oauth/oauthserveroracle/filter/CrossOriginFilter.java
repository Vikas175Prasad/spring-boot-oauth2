package com.vikas.oauth.oauthserveroracle.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class CrossOriginFilter implements Filter{
	 private static final Logger log = LoggerFactory.getLogger(CrossOriginFilter.class);

	    @Override
	    public void init(FilterConfig filterConfig) throws ServletException {

	        // Called by the web container to indicate to a filter that it is being
	        // placed into service.
	        // We do not want to do anything here.
	    }

	    @Override
	    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
	            throws IOException, ServletException {

	        log.info("Applying CORS filter");
	        HttpServletResponse response = (HttpServletResponse) resp;
	        HttpServletRequest request = (HttpServletRequest) req;
	        response.setHeader("Access-Control-Allow-Origin", "*");
	        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
	        response.setHeader("Access-Control-Max-Age", "0");
	        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization, x-auth-token, origin, content-type, accept");

	        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
	            response.setStatus(HttpServletResponse.SC_OK);
	        } else {
	            chain.doFilter(request, response);
	        }
	    }

	    @Override
	    public void destroy() {

	        // Called by the web container to indicate to a filter that it is being
	        // taken out of service.
	        // We do not want to do anything here.
	    }
}
