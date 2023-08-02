package com.gracetech.gestionimmoback.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.gracetech.gestionimmoback.security.UserPrincipal;

/**
 * Configuration pour l'auditing, le scheduling
 * @author Robbile
 *
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AppConfig {
	
	@Bean
	AuditorAware<String> auditorProvider() {
		return () -> {
			
			final Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null) {
				if (authentication.getPrincipal() instanceof UserPrincipal) {
					UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
					return Optional.of(user.getUsername());
				} else {
					return Optional.of((String) authentication.getPrincipal());
				}
			}
			return Optional.of("GESTION_IMMO_AGENT");
		};
	}
}
