package com.gracetech.gestionimmoback.controller;

import com.gracetech.gestionimmoback.AbstractGestionImmoTest;
import com.gracetech.gestionimmoback.dto.ClientDto;
import com.gracetech.gestionimmoback.dto.SignupRequest;
import com.gracetech.gestionimmoback.model.Client;
import com.gracetech.gestionimmoback.model.ConfirmToken;
import com.gracetech.gestionimmoback.repository.ConfirmTokenRepository;
import com.gracetech.gestionimmoback.security.jwt.JwtAuthenticationRequest;
import com.gracetech.gestionimmoback.service.impl.ClientService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
public class AuthenticationControllerTest extends AbstractGestionImmoTest {
	
	final static String AUTH_ENDPOINT = "/auth";

	@Autowired
	private ClientService clientService;

	@Autowired
	private ConfirmTokenRepository confirmTokenRepository;

	private ClientDto clientDto;
	private ClientDto unActivatedClient;
	private ConfirmToken confirmToken;

	@BeforeEach
	public void setup() {
		Client client = new Client();
		client.setId(1L);
		client.setFirstname("test");
		client.setLastname("test");
		client.setPassword("$2a$10$zxx8oOIwKb3rzaLQF7hp3O1gTg0kr9K4vJY62T9.NIbNnom7deLKy");
		client.setEmail("test@immo.com");
		client.setUsername("test");
		client.setActive(true);
		clientDto = clientService.save(client);

		Client client1 = new Client();
		client1.setId(2L);
		client1.setFirstname("test1");
		client1.setLastname("test1");
		client1.setPassword("$2a$10$zxx8oOIwKb3rzaLQF7hp3O1gTg0kr9K4vJY62T9.NIbNnom7deLKy");
		client1.setEmail("unactivated@immo.com");
		client1.setUsername("test165");
		unActivatedClient = clientService.save(client1);

		initJwtToken("test@immo.com");
		ConfirmToken confToken = new ConfirmToken(client1);
		confirmToken = confirmTokenRepository.save(confToken);

	}


	@Test
	public void signupClient_and_error_sending_mail() throws Exception {
		SignupRequest req = new SignupRequest();
		req.setUsername("user");
		req.setFirstname("user");
		req.setLastname("user");
		req.setEmail("user@immo.com");
		req.setPassword("pwd1234");

		mockMvc.perform(post(AUTH_ENDPOINT + "/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(req)))
				.andExpect(status().isInternalServerError())
				.andReturn();
	}

	@Test
	public void signupClient_with_existing_mail() throws Exception {
		SignupRequest req = new SignupRequest();
		req.setUsername("userT");
		req.setFirstname("userT");
		req.setLastname("userT");
		req.setEmail("test@immo.com");
		req.setPassword("pwd1234");

		mockMvc.perform(post(AUTH_ENDPOINT + "/signup")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsBytes(req)))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
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
		JwtAuthenticationRequest authReq = new JwtAuthenticationRequest(
				clientDto.getEmail(), "pwd1234");
		ClientDto clientss = clientService.getByEmail(clientDto.getEmail());
		System.out.println(clientss.toString());
		mockMvc.perform(post(AUTH_ENDPOINT + "/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(authReq)))
				.andExpect( status().isOk())
				.andExpect(jsonPath("$.data", notNullValue()));

	}

	@Test()
	public void login_existing_user_with_bad_password_in_db() throws Exception {
		JwtAuthenticationRequest authReq = new JwtAuthenticationRequest(
				clientDto.getEmail(), "badpwd");

		mockMvc.perform(post(AUTH_ENDPOINT + "/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsBytes(authReq)))
				.andExpect( status().is4xxClientError())
				.andReturn();
	}

	@Test
	public void login_unactivated_user() throws Exception {
		JwtAuthenticationRequest authReq = new JwtAuthenticationRequest(
				unActivatedClient.getEmail(), "badpwd");

		mockMvc.perform(post(AUTH_ENDPOINT + "/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsBytes(authReq)))
				.andExpect( status().is5xxServerError())
				.andReturn();
	}
	
	@Test
	public void confirm_account_with_bad_token() throws Exception {
		String token = "okennnn";
		
		mockMvc.perform(get(AUTH_ENDPOINT + "/confirm-account?token="+token))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect( status().isBadRequest())
				.andExpect(jsonPath("$.message", is("The link is invalid or broken !")));
	}


	@Test
	public void confirm_account_with_good_token() throws Exception {
		ConfirmToken cfToken = confirmTokenRepository.findByToken(confirmToken.getToken());
		System.out.println(cfToken.getToken());

		ClientDto dto = clientService.getByEmail(cfToken.getClient().getEmail());
		System.out.println(dto.toString());

		mockMvc.perform(get(AUTH_ENDPOINT + "/confirm-account?token="+cfToken.getToken())
						.contentType(MediaType.APPLICATION_JSON)
						.header(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
						.andExpect( status().isOk())
						.andExpect(jsonPath("$.message", is("Account verified !")));
	}
	@AfterEach
	public void setupAfterTransaction() {
		this.clientService.deleteAll();
		this.confirmTokenRepository.deleteAll();
	}

}
