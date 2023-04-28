package com.example.rs.ftn.ConnectSocialNetworkProject.security;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAthFilter extends OncePerRequestFilter {
	
	private final UserDetailsService userDetailService;
	private final JwtUtil jwtUtils;
	
	public JwtAthFilter(UserDetailsService userDetailService, JwtUtil jwtUtils) {
	    this.userDetailService = userDetailService;
	    this.jwtUtils = jwtUtils;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response,FilterChain filterChain) throws ServletException,IOException {
		SecurityContextHolder.clearContext();

		final String authHeader = request.getHeader(AUTHORIZATION);
		final String userEmail;
		final String jwtToken;

		
		if (authHeader == null || !authHeader.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}
		jwtToken = authHeader.substring(7);
		userEmail = jwtUtils.extractUsername(jwtToken); 
		if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null ) {
			UserDetails userDetails = 	userDetailService.loadUserByUsername(userEmail);
			if (jwtUtils.isTokenValid(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken authToken = 
						new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);


			}
		}

		filterChain.doFilter(request, response);
		
	}

}
