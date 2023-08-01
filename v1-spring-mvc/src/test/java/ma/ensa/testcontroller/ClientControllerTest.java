package ma.ensa.testcontroller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ma.ensa.controller.ClientController;
import ma.ensa.model.Bien;
import ma.ensa.repository.BienRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientControllerTest {

	@Autowired
	private ClientController clientController;
	
	@Autowired
	private BienRepository bienRepository;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
	}
	
	@Test
	public void contexLoads() throws Exception{
		assertThat(clientController).isNotNull();
	}
	
	
	@Test
	public void testAccueilPage() throws Exception{
		assertEquals(bienRepository.findById((long)1).get().getId(), (long)1);
		//when(bienRepository.findById((long) 1).thenReturn(new Bien());
		this.mockMvc.perform(get("/accueil"))
			.andExpect(status().isOk())
			.andExpect(view().name("accueil"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("biens"))
	
			
			.andDo(MockMvcResultHandlers.print())
			.andReturn();
	}
	
	@Test
	public void testConnexionPage() throws Exception {
		this.mockMvc.perform(get("/connexion"))
			.andExpect(status().isOk())
			.andExpect(view().name("connexion"));
	}
	/*
	@Test
	public void testListBiens() throws Exception{
		this.mockMvc.perform(get("/acceuil"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("biens"))
			.andExpect(view().name("acceuil"))
			.andExpect(status().isOk());
	}*/
}
