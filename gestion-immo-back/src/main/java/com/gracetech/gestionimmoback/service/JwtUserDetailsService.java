package com.gracetech.gestionimmoback.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gracetech.gestionimmoback.exception.UserNotActivatedException;
import com.gracetech.gestionimmoback.mapper.ClientMapper;
import com.gracetech.gestionimmoback.model.Client;
import com.gracetech.gestionimmoback.repository.ClientRepository;
import com.gracetech.gestionimmoback.security.UserPrincipal;
import com.gracetech.gestionimmoback.security.jwt.JwtUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
	
	private final ClientRepository repository;
	
	private final ClientMapper mapper;
	
	private final IClientService clientService;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		final Client client = repository.findByEmail(email);
		if (client != null) {
			if (!client.isActive()) {
				throw new UserNotActivatedException("User " + email + " is not activated");
			}
			
			final JwtUser jwtUser = mapper.toJwtDto(client);
			List<GrantedAuthority> authorities = new ArrayList<>();
			
			if (client.getAppRole() != null) {
				authorities.add(new SimpleGrantedAuthority(client.getAppRole().getDescription()));
			}
			
			jwtUser.setAuthorities(authorities);
			// save last connexion date for Auditing
			clientService.updateLastConnextionDate(jwtUser.getId());
			
			return jwtUser;
		}
		
		throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
	}
	
	public static UserPrincipal getConnecteduser() {
		return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
