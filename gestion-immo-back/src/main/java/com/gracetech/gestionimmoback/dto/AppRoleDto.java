package com.gracetech.gestionimmoback.dto;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;

@Data
public class AppRoleDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	Long id;
	String description;
	String createdBy;
	String lastModifiedBy;
	Instant createdDate;
	Instant lastModifiedDate;

}
