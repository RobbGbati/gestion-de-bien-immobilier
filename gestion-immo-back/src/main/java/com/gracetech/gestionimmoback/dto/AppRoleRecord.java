package com.gracetech.gestionimmoback.dto;

import java.time.Instant;

public record AppRoleRecord(
		Long id,
		String description,
		String createdBy,
		String lastModifiedBy,
		Instant createdDate,
		Instant lastModifiedDate	
) {}
