package com.gracetech.gestionimmoback.controller;

import com.gracetech.gestionimmoback.AbstractGestionImmoTest;
import com.gracetech.gestionimmoback.dto.ClientDto;
import com.gracetech.gestionimmoback.exception.CustomFunctionalException;
import com.gracetech.gestionimmoback.exception.ElementNotFoundException;
import com.gracetech.gestionimmoback.model.Client;
import com.gracetech.gestionimmoback.service.IClientService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientControllerTest extends AbstractGestionImmoTest {

    private static final String url = "/api/clients";
    @Autowired
    private IClientService clientService;
    private ClientDto dto;

    @BeforeAll
    public void setup() {
        Client client = new Client();
        client.setId(1L);
        client.setFirstname("test");
        client.setLastname("test");
        client.setPassword("$2a$10$zxx8oOIwKb3rzaLQF7hp3O1gTg0kr9K4vJY62T9.NIbNnom7deLKy");
        client.setEmail("test@immo.com");
        client.setUsername("test");
        client.setActive(true);
        dto = clientService.save(client);
        initJwtToken(client.getEmail());

        client = new Client();
        client.setFirstname("test");
        client.setLastname("test");
        client.setPassword("$2a$10$zxx8oOIwKb3rzaLQF7hp3O1gTg0kr9K4vJY62T9.NIbNnom7deLKy");
        client.setEmail("newEmail@immo.com");
        client.setUsername("new");
        client.setActive(true);
        clientService.save(client);
    }

    @Test
    public void clientExistByEmail() throws Exception {
        mockMvc.perform(get(url+ "/existByEmail?email=" + dto.getEmail())
                        .header(AUTHORIZATION, "Bearer " + token))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", is(true)));
    }


    @Test
    @Order(1)
    public void getClient_by_id() throws Exception {
        mockMvc.perform(get(url+ "/1")
                        .header(AUTHORIZATION, "Bearer " + token))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.email", is(dto.getEmail())))
                .andExpect(jsonPath("$.data.username", is(dto.getUsername())))
                .andExpect(jsonPath("$.data.active", is(dto.getActive())))
                .andExpect(jsonPath("$.data.password", is(dto.getPassword())));
    }

    @Test
    public void getClient_with_bad_id() throws Exception {
        mockMvc.perform(get(url+ "/0")
                        .header(AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ElementNotFoundException));
    }

    @Test
    public void client_does_not_exist_by_email() throws Exception {
        mockMvc.perform(get(url+ "/existByEmail?email=fake@immo.com")
                        .header(AUTHORIZATION, "Bearer " + token))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", is(false)));
    }

    @Test
    public void getAll_clients() throws Exception {
        mockMvc.perform(get(url+ "/all")
                        .header(AUTHORIZATION, "Bearer " + token))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)));
    }

    @Test
    public void deleteClient_with_bad_id() throws Exception {
        mockMvc.perform(delete(url+ "/0")
                        .header(AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ElementNotFoundException));
    }

    @Test
    public void deleteClient_with_existing_id() throws Exception {
        mockMvc.perform(delete(url+ "/1")
                        .header(AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    public void create_client_with_non_existing_email() throws Exception {
        dto.setEmail("new@immo.com");
        mockMvc.perform(post(url)
                    .header(AUTHORIZATION, "Bearer " + token)
                    .contentType(APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsBytes(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()));
    }

    @Test
    public void create_client_with_duplicate_email() throws Exception {
        mockMvc.perform(post(url)
                        .header(AUTHORIZATION, "Bearer " + token)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsBytes(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(
                        result.getResolvedException() instanceof CustomFunctionalException
                ));
    }

    @Test
    public void updateClient_with_valid_data() throws Exception {
        dto.setUsername("newUsername");
        dto.setFirstname("Firstname");
        mockMvc.perform(put(url)
                        .header(AUTHORIZATION, "Bearer " + token)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsBytes(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()));
    }

    @Test
    public void updateClient_with_bad_data() throws Exception {
        dto.setEmail("newEmail@immo.com");
        dto.setUsername("newUsername");
        mockMvc.perform(put(url)
                        .header(AUTHORIZATION, "Bearer " + token)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsBytes(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CustomFunctionalException));
    }
}
