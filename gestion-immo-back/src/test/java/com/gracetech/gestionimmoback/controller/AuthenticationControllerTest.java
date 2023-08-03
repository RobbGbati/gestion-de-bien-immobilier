package com.gracetech.gestionimmoback.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.gracetech.gestionimmoback.AbstractGestionImmoTest;
import com.gracetech.gestionimmoback.security.jwt.JwtAuthenticationRequest;

public class AuthenticationControllerTest extends AbstractGestionImmoTest {
	
	final static String AUTH_ENDPOINT = "/auth";
	
	@Test
	public void auhtenticate_non_existing_user_in_db() throws Exception {
		String email = "fake@test.com";
		String password = "pwd1234";
		JwtAuthenticationRequest authReq = new JwtAuthenticationRequest(email, password);
		
		mockMvc.perform(post(AUTH_ENDPOINT + "/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(authReq)))
				.andExpect( status().isNotFound())
				.andReturn();
	}
	
	@Test
	public void auhtenticate_existing_user_in_db() throws Exception {
		String email = "test@test.com";
		String password = "pwd1234";
		JwtAuthenticationRequest authReq = new JwtAuthenticationRequest(email, password);
		
		mockMvc.perform(post(AUTH_ENDPOINT + "/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(authReq)))
				.andExpect( status().isOk())
				.andReturn();
	}
	
	@Test
	public void confirm_account_with_bad_token() throws Exception {
		String token = "okennnn";
		
		mockMvc.perform(get(AUTH_ENDPOINT + "/confirm-token?token="+token)
				.contentType(MediaType.APPLICATION_JSON)
				.header(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
				.andExpect( status().isNotFound())
				.andReturn();
	}

}
