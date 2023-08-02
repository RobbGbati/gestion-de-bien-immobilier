package com.gracetech.gestionimmoback.dto;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;

@Data
public class ClientDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

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
    
	private String createdBy;
	
	private Instant createdDate;
	
	private String lastModifiedBy;
	
	private Instant lastModifiedDate;
	
	private Instant lastConnexionDate;
	
	private Long roleId;
}
