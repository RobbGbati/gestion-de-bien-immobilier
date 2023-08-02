package com.gracetech.gestionimmoback.security.jwt;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {
	
	public static final String AUTHORIZATION_HEADER = "Authorization";
	
	private final JwtTokenUtils jwtTokenUtils;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.debug("processing authentication for '{}'", request.getRequestURL());
		final String token = resolveToken(request);
		
		if (StringUtils.isNotBlank(token)) {
			final String email = jwtTokenUtils.getEmailFromToken(token);
			log.debug("Connected user with email'{}'", email);
			log.debug("Checking token validity for user '{}'", email);
			if (jwtTokenUtils.validateToken(email, token)) {
				Authentication auth = this.jwtTokenUtils.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(auth);
			} else {
				log.warn("Token expired");
			}
		} else {
			log.warn("Couldn't find bearer string, will ignore the header");
		}
		
		filterChain.doFilter(request, response);

	}

	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

}
