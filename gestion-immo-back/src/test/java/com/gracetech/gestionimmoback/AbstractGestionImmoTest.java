package com.gracetech.gestionimmoback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.gracetech.gestionimmoback.security.jwt.JwtTokenUtils;
import com.gracetech.gestionimmoback.security.jwt.JwtUser;
import com.gracetech.gestionimmoback.service.JwtUserDetailsService;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractGestionImmoTest {
	
    protected static final String AUTHORIZATION = "Authorization";
    protected static final String CONTENT_TYPE = "Content-Type";
    private static MockHttpServletRequest request;
    
    @Autowired
    protected MockMvc mockMvc;
    
    @Autowired
    protected ObjectMapper objectMapper;
    
    
    @Autowired
    protected JwtTokenUtils jwtTokenUtils;
    
    @Autowired
    protected JwtUserDetailsService userDetailsService;
    
    protected String token;
    
    
    protected void initJwtToken(String email) {
    	final JwtUser userDetail = (JwtUser) userDetailsService.loadUserByUsername(email);
    	token = jwtTokenUtils.generateToken(userDetail);
    }
    
    
    public String asJsonString(final Object obj) {
        try {
        	objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            final String jsonContent = objectMapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
