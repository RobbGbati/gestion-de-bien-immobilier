package com.gracetech.gestionimmoback.security.jwt;

import java.time.Instant;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gracetech.gestionimmoback.dto.AppRoleRecord;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Robbile
 * hold an authenticated user
 */

@Getter
@Setter
public class JwtUser implements UserDetails {
	
    private Long id;
    
    private String username;
    
    private String lastname;
    
    private String firstname;
    
    private String email;
    
    private String photo;
    
    private String description;
    
    private String password;
    
	private String tel;
    
    private Instant lastPwdResetDate;
    
    private Boolean active;
    
    private AppRoleRecord appRoleRecord;
    

	@JsonIgnore
	private Collection<? extends GrantedAuthority> authorities;

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.active;
	}


}
