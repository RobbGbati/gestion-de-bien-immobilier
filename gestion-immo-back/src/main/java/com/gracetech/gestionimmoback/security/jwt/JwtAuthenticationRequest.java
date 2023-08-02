package com.gracetech.gestionimmoback.security.jwt;

import java.io.Serializable;

public record JwtAuthenticationRequest(String email, String password) implements Serializable {
}
