package com.gracetech.gestionimmoback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GestionImmoResponse {
	
	private String message = "";
	private Object data;
	
	public GestionImmoResponse(Object data) {
		super();
		this.data = data;
	}
	
	

}
