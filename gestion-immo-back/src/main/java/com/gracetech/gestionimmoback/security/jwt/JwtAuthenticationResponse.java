package com.gracetech.gestionimmoback.security.jwt;

import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;

public record JwtAuthenticationResponse(String token, UserDetails currentUser) implements Serializable {
}
