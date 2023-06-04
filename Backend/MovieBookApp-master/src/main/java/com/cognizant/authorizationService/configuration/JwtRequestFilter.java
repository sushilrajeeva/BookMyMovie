package com.cognizant.authorizationService.configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cognizant.authorizationService.service.JwtService;
import com.cognizant.authorizationService.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	
	public static String CURR_USER="";
	
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
    private JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		 final String requestTokenHeader = request.getHeader("Authorization");

	        String username = null;
	        String jwtToken = null;
	        

	        HttpServletRequest httpReq = (HttpServletRequest) request;
	        HttpServletResponse httpRes = (HttpServletResponse) response;

	        // Print out the request URL, Method and Headers
	        System.out.println("Request URL: " + httpReq.getRequestURL());
	        System.out.println("Request Method: " + httpReq.getMethod());
	        System.out.println("Request Body: ");
//	        StringBuilder builder = new StringBuilder();
//	        try (BufferedReader reader = httpReq.getReader()) {
//	            String line;
//	            while ((line = reader.readLine()) != null) {
//	                builder.append(line);
//	            }
//	        }
//
//	        String body = builder.toString();
//	        System.out.println("Request Body: " + body);
//	        System.out.println("Request Headers: ");
//	        Enumeration<String> headerNames = httpReq.getHeaderNames();
//	        while (headerNames.hasMoreElements()) {
//	            String headerName = headerNames.nextElement();
//	            System.out.println(headerName + ": " + httpReq.getHeader(headerName));
//	        }
	        String requestURI = request.getRequestURI();
	     // Skip the JWT extraction process if the request is for the /authenticate endpoint
	        if (requestURI.endsWith("/authenticate")) {
	            filterChain.doFilter(request, response);
	            
	        }else {
	        	if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
		            jwtToken = requestTokenHeader.substring(7);
		            try {
		                username = jwtUtil.getUsernameFromToken(jwtToken);
		                CURR_USER=username;
		            } catch (IllegalArgumentException e) {
		                System.out.println("Unable to get JWT Token");
		            } catch (ExpiredJwtException e) {
		                System.out.println("JWT Token has expired");
		            }
		        } else {
		            System.out.println("JWT token does not start with Bearer");
		        }
		        
		        
		        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

		            UserDetails userDetails = jwtService.loadUserByUsername(username);

		            if (jwtUtil.validateToken(jwtToken, userDetails)) {

		                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		            }
		        }
		        filterChain.doFilter(request, response);
	        }

	        

		
	}

}
