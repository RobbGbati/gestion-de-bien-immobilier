package com.gracetech.gestionimmoback.dto;

import java.io.Serializable;

import com.gracetech.gestionimmoback.constant.ValidationMsg;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String username;
	
    @Size(min=6, message = ValidationMsg.PASSWORD_LENGTH)
	private String password;
	
	private String lastname;
	
	private String firstname;
	
    @NotBlank(message = ValidationMsg.EMAIL_MANDATORY)
	@Email(message = ValidationMsg.EMAIL_VALID)
	private String email;
	
	private String createdBy;
}
