package com.gracetech.gestionimmoback.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gracetech.gestionimmoback.constant.Constants;
import com.gracetech.gestionimmoback.dto.GestionImmoResponse;
import com.gracetech.gestionimmoback.dto.SignupRequest;
import com.gracetech.gestionimmoback.exception.CustomFunctionalException;
import com.gracetech.gestionimmoback.model.AppRole;
import com.gracetech.gestionimmoback.model.Client;
import com.gracetech.gestionimmoback.model.ConfirmToken;
import com.gracetech.gestionimmoback.repository.AppRoleRepository;
import com.gracetech.gestionimmoback.repository.ConfirmTokenRepository;
import com.gracetech.gestionimmoback.repository.PasswordResetTokenRepository;
import com.gracetech.gestionimmoback.security.jwt.JwtTokenUtils;
import com.gracetech.gestionimmoback.service.IClientService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(Constants.View.AUTH)
@RequiredArgsConstructor
@Slf4j
public class AuthController {
	
	private final JwtTokenUtils jwtTokenUtil;
	private final IClientService clientService;
	private final PasswordResetTokenRepository resetTokenRepository;
	private final ConfirmTokenRepository confirmTokenRepository;
	private final AppRoleRepository roleRepository;
	private final PasswordEncoder encoder;
	
	
	@PostMapping("/signup")
	public ResponseEntity<GestionImmoResponse> signupClient(@Valid @RequestBody SignupRequest req) {
		if (clientService.existByEmail(req.getEmail())) {
			throw new CustomFunctionalException(HttpStatus.BAD_REQUEST, Constants.Errors.EMAIL_TAKEN);
		}
		log.info("Creating client with email {}", req.getEmail());
		
		Client client = new Client();
		client.setFirstname(req.getFirstname());
		client.setLastname(req.getLastname());
		client.setPassword(encoder.encode(req.getPassword()));
		client.setEmail(req.getEmail());
		client.setUsername(req.getUsername());
		
		// affect the default role
		AppRole role = roleRepository.findByDescriptionIgnoreCase("DEFAULT");
		client.setAppRole(role);
		clientService.save(client);
		
		ConfirmToken confirmToken = new ConfirmToken(client);
		confirmTokenRepository.save(confirmToken);
		
		// send email notification
		
		return ResponseEntity.ok().body(new GestionImmoResponse("Client is registered successfuly", null));
	}
	

}
