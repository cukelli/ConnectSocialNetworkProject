package com.example.rs.ftn.ConnectSocialNetworkProject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity //spring zna da je ovo klasa koja se odnosi na security konfiguraciju
public class SecurityConfig {
	
	private final JwtAthFilter jwtAuthFilter;
	
	public SecurityConfig(JwtAthFilter jwtAthFilter) {
		this.jwtAuthFilter = jwtAthFilter;
	}
	
	// ovo se koristi umesto springove defaultne konfiguracije securityFilterChain-a
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.cors().and().csrf().disable()
		.authorizeRequests().
		antMatchers("/user/login").permitAll()
		.anyRequest().
		authenticated().
		and().
		addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();  //osnovna autentifikacija,username: user,password generated security passwrod
	}
   
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:4200");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
	

}