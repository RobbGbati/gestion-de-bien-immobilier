package com.gracetech.gestionimmoback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.gracetech.gestionimmoback.security.jwt.JwtAuthenticationEntryPoint;
import com.gracetech.gestionimmoback.security.jwt.JwtAuthorizationTokenFilter;
import com.gracetech.gestionimmoback.service.JwtUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final JwtAuthenticationEntryPoint authenticationEntryPoint;
	private final JwtUserDetailsService jwtService;
	private final JwtAuthorizationTokenFilter tokenFilter;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public AuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(jwtService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}
	
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
        		// disable CSRF because our token is invulnerable
                .csrf(csrf -> csrf.disable())
                .cors( cors -> cors.disable())
                .exceptionHandling(exp -> exp
                		.authenticationEntryPoint(authenticationEntryPoint))
                
                // do not create session
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                        		new AntPathRequestMatcher("/auth/**"),
								new AntPathRequestMatcher("/api/biens/**"),
								new AntPathRequestMatcher("/api-docs/**"),
								new AntPathRequestMatcher("/v2/api-docs"),
								new AntPathRequestMatcher("/v3/api-docs"),
								new AntPathRequestMatcher("/v3/api-docs/**"),
								new AntPathRequestMatcher("/swagger-resources/**"),
								new AntPathRequestMatcher("/configuration/ui"),
								new AntPathRequestMatcher("/configuration/security"),
								new AntPathRequestMatcher("/swagger-ui/**"),
								new AntPathRequestMatcher("/webjars/**"),
								new AntPathRequestMatcher("/swagger-ui.html"),
                        		new AntPathRequestMatcher("/actuator")).permitAll()
                        .anyRequest().authenticated())
				.authenticationProvider(authenticationProvider())
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
