package com.gracetech.gestionimmoback.controller;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gracetech.gestionimmoback.constant.Constants;
import com.gracetech.gestionimmoback.dto.ClientDto;
import com.gracetech.gestionimmoback.dto.GestionImmoResponse;
import com.gracetech.gestionimmoback.dto.SignupRequest;
import com.gracetech.gestionimmoback.exception.CustomFunctionalException;
import com.gracetech.gestionimmoback.exception.ElementNotFoundException;
import com.gracetech.gestionimmoback.mapper.ClientMapper;
import com.gracetech.gestionimmoback.model.AppRole;
import com.gracetech.gestionimmoback.model.Client;
import com.gracetech.gestionimmoback.model.ConfirmToken;
import com.gracetech.gestionimmoback.model.PasswordResetToken;
import com.gracetech.gestionimmoback.repository.AppRoleRepository;
import com.gracetech.gestionimmoback.repository.ConfirmTokenRepository;
import com.gracetech.gestionimmoback.repository.PasswordResetTokenRepository;
import com.gracetech.gestionimmoback.security.jwt.JwtAuthenticationRequest;
import com.gracetech.gestionimmoback.security.jwt.JwtAuthenticationResponse;
import com.gracetech.gestionimmoback.security.jwt.JwtTokenUtils;
import com.gracetech.gestionimmoback.security.jwt.JwtUser;
import com.gracetech.gestionimmoback.service.IClientService;
import com.gracetech.gestionimmoback.service.JwtUserDetailsService;
import com.gracetech.gestionimmoback.service.impl.EmailService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(Constants.View.AUTH)
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authentication")
public class AuthController {
	
	private final JwtUserDetailsService jwtUserDetailsService;
	private final JwtTokenUtils jwtTokenUtil;
	private final IClientService clientService;
	private final PasswordResetTokenRepository resetTokenRepository;
	private final ConfirmTokenRepository confirmTokenRepository;
	private final AppRoleRepository roleRepository;
	private final PasswordEncoder encoder;
	private final EmailService emailService;
	private final AuthenticationManager authenticationManager;
	
	@Value("${app.mail}")
	String appMail;
	

	@Operation(
			summary = "Signup a new client",
			responses = {
					@ApiResponse(
							description = "Client is registered successfuly",
							responseCode = "200"
					),
					@ApiResponse(
							description = "Email is already taken ",
							responseCode = "400"
					)
			}
	)
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
		// envoyer un mail de confirmation
		String subject = "Complete registration";
		String url = appMail + "auth/confirm-account?token=";
		String text = "To confirm your account, please follorw this link: " +
				url + confirmToken.getToken();
		emailService.sendSimpleMessage(client.getEmail(), subject, text);
		
		
		return ResponseEntity.ok().body(new GestionImmoResponse("Client is registered successfuly", null));
	}


	@Operation(
			summary = "Confirm-account to activate it",
			responses = {
					@ApiResponse(
							description = "Account verified !",
							responseCode = "200"
					),
					@ApiResponse(
							description = "The link is invalid or broken !",
							responseCode = "400"
					)
			}
	)
	@GetMapping("/confirm-account")
	public ResponseEntity<GestionImmoResponse> confirmClientAccount(@RequestParam String token) {
		ConfirmToken cfToken = confirmTokenRepository.findByToken(token);
		
		if (cfToken != null) {
			clientService.activateAccountByMail(cfToken.getClient().getEmail());
			return ResponseEntity.ok().body(new GestionImmoResponse("Account verified !", null));
		}
		return ResponseEntity.badRequest().body(new GestionImmoResponse("The link is invalid or broken !", null));
	}


	@Operation(
			summary = "Login a user",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200"
					),
					@ApiResponse(
							description = "Element not found",
							responseCode = "404"
					)
			}
	)
	@PostMapping("/login")
	public ResponseEntity<GestionImmoResponse> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authReq) {
		// if the user does not exist
		if (authReq.email().isEmpty() || !clientService.existByEmail(authReq.email())) {
			throw new ElementNotFoundException(Client.class, authReq.email());
		}

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authReq.email(), authReq.password()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// creating token
		JwtUser userDetails = (JwtUser) authentication.getPrincipal();
		final String token = jwtTokenUtil.generateToken(userDetails);
		
		return ResponseEntity.ok().body(new GestionImmoResponse(
				new JwtAuthenticationResponse(token, userDetails)));
	}


	@Operation(
			summary = "Refresh a token",
			responses = {
					@ApiResponse(
							description = "Token refreshed!",
							responseCode = "200"
					)
			}
	)
	@GetMapping("/refresh")
	public ResponseEntity<GestionImmoResponse> refreshAndGetAuthToken(final HttpServletRequest req) {
		String authToken = req.getHeader("Authorization");
		String token = authToken.substring(7);
		String email = jwtTokenUtil.getEmailFromToken(token);
		JwtUser userDetails = (JwtUser) jwtUserDetailsService.loadUserByUsername(email);
		Boolean canRefresh = jwtTokenUtil.canTokenBeRefreshed(token, Date.from(userDetails.getLastPwdResetDate()));
		if (Boolean.TRUE.equals(canRefresh)) {
			String refreshToken = jwtTokenUtil.refreshToken(token);
			return ResponseEntity.ok().body(new GestionImmoResponse(
					new JwtAuthenticationResponse(refreshToken, userDetails)));
		} else {
			return ResponseEntity.ok().body(new GestionImmoResponse("Not refreshed!", null));
		}
	}


	@Operation(
			summary = "Send email when forget the password",
			responses = {
					@ApiResponse(
							description = "You receive an email to reset your password.",
							responseCode = "200"
					),
					@ApiResponse(
							description = "Internal error",
							responseCode = "500"
					)
			}
	)
	@PostMapping("/forget-password")
	public ResponseEntity<GestionImmoResponse> forgerPassword(@RequestParam String email) {
		if (!clientService.existByEmail(email)) {
			throw new ElementNotFoundException(Client.class, email);
		}
		
		String verificationToken  = UUID.randomUUID().toString();
		ClientDto clientDto = clientService.getByEmail(email);
		
		PasswordResetToken pwdResetToken = new PasswordResetToken(
				verificationToken,
				ClientMapper.INSTANCE.toEntity(clientDto));
		resetTokenRepository.save(pwdResetToken);
		String redirectionUrl = appMail + "auth/reset-password?token=";
		String subject = "Complete password reset";
		String text = "To reset your password, please follow this link; " + 
				redirectionUrl + pwdResetToken.getPwdToken();
		
		emailService.sendSimpleMessage(email, subject, text);
		
		return ResponseEntity.ok().body(new GestionImmoResponse("You receive an email to reset your password.", null));
	}


	@Operation(
			summary = "Reset the password of user",
			responses = {
					@ApiResponse(
							description = "Change password successfully!",
							responseCode = "200"
					),
					@ApiResponse(
							description = "Session has expired! / Unable to reset your password",
							responseCode = "404"
					)
			}
	)
	@PostMapping("/reset-password")
	public ResponseEntity<GestionImmoResponse> resetPassword(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword) {
		PasswordResetToken pwdResetToken = resetTokenRepository.findByPwdToken(token);
		if (pwdResetToken != null) {
			if (pwdResetToken.getExpirationDate().isBefore(Instant.now())) {
				return ResponseEntity.badRequest().body( new GestionImmoResponse("Session has expired!", null));
			}
			ClientDto dto = clientService.getByEmail(pwdResetToken.getClient().getEmail());
			Client client = ClientMapper.INSTANCE.toEntity(dto);
			client.setLastPwdResetDate(Instant.now());
			client.setPassword(newPassword);
			clientService.save(client);
		} else {
			return ResponseEntity.badRequest().body(new GestionImmoResponse("Unable to reset your password", null));
		}
		
		return ResponseEntity.ok().body(new GestionImmoResponse("Change password successfully!", null));
	}
	
	
	

}
