package com.gracetech.gestionimmoback.dto;

import java.io.Serializable;
import java.time.Instant;

import com.gracetech.gestionimmoback.constant.ValidationMsg;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private Long id;
    
    private String username;
    
    @NotBlank(message = ValidationMsg.LASTNAME_MANDATORY)
    private String lastname;
    
    @NotBlank(message = ValidationMsg.FIRSTNAME_MANDATORY)
    private String firstname;
    
    @NotBlank(message = ValidationMsg.EMAIL_MANDATORY)
	@Email(message = ValidationMsg.EMAIL_VALID)
    private String email;
    
    private String photo;
    
    private String description;
    
    @Size(min=6, message = ValidationMsg.PASSWORD_LENGTH)
    private String password;
    
	private String tel;
    
    private Instant lastPwdResetDate;
    
    private Boolean active;
    
    private Boolean deleted;
    
	private String createdBy;
	
	private Instant createdDate;
	
	private String lastModifiedBy;
	
	private Instant lastModifiedDate;
	
	private Instant lastConnexionDate;
	
	private Long roleId;
}
