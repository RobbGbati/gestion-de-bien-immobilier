package com.gracetech.gestionimmoback.controller;

import com.gracetech.gestionimmoback.AbstractGestionImmoTest;
import com.gracetech.gestionimmoback.dto.BienDto;
import com.gracetech.gestionimmoback.dto.ClientDto;
import com.gracetech.gestionimmoback.enums.BienStatus;
import com.gracetech.gestionimmoback.enums.Offre;
import com.gracetech.gestionimmoback.exception.ElementNotFoundException;
import com.gracetech.gestionimmoback.mapper.BienMapper;
import com.gracetech.gestionimmoback.model.Bien;
import com.gracetech.gestionimmoback.model.Client;
import com.gracetech.gestionimmoback.service.IBienService;
import com.gracetech.gestionimmoback.service.IClientService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BienControllerTest extends AbstractGestionImmoTest {

    private static final String url = "/api/biens";

    @Autowired
    private IBienService bienService;

    @Autowired
    private IClientService clientService;

    private ClientDto dto;
    private List<BienDto> biens = new ArrayList<>();

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
        initBiens();
    }

    private void initBiens() {
        for (int i = 1; i < 10; i++) {
            Bien bien = new Bien();
            bien.setAddress("adress_" + i);
            bien.setCity("city_" + i);
            bien.setDurabilite("month"+i);
            bien.setAmount(10 * i);
            bien.setDescription("description_" + i);
            bien.setStatus(BienStatus.DISPONIBLE);
            bien.setOffre(Offre.LOCATION);
            biens.add(bienService.create(BienMapper.INSTANCE.toDto(bien)));
        }


    }
    @Test
    public void createBien_success() throws Exception {
        Bien bien = new Bien();
        bien.setAddress("Test");
        bien.setCity("Lille");
        bien.setDurabilite("2 month");
        bien.setAmount(1600);
        bien.setDescription("Bien for test");
        bien.setStatus(BienStatus.DISPONIBLE);
        bien.setOffre(Offre.LOCATION);

        mockMvc.perform(post(url).header(AUTHORIZATION, "Bearer "+token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bien)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()));
    }

    @Test
    public void createBien_with_bad_amount() throws Exception {
        Bien bien = new Bien();
        bien.setAddress("Test");
        bien.setCity("Lille");
        bien.setDurabilite("2 month");
        bien.setAmount(0);
        bien.setDescription("Bien for test");
        bien.setStatus(BienStatus.DISPONIBLE);
        bien.setOffre(Offre.LOCATION);

        mockMvc.perform(post(url).header(AUTHORIZATION, "Bearer "+token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(bien)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status", is("INTERNAL_SERVER_ERROR")));
    }

    @Test
    public void deleteBien_with_bad_id() throws Exception {
        mockMvc.perform(delete(url + "/0").header(AUTHORIZATION, "Bearer "+token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ElementNotFoundException));
    }

    @Test
    public void deleteBien_success() throws Exception {
        mockMvc.perform(delete(url + "/1").header(AUTHORIZATION, "Bearer "+token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void searchByDesc() throws Exception {
        mockMvc.perform(get(url + "/searchByDescriptionOrCity")
                .header(AUTHORIZATION, "Bearer " + token)
                .param("query", "description"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(9)));
    }

}
