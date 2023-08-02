package com.gracetech.gestionimmoback.mapper;

import org.mapstruct.Mapper;

import com.gracetech.gestionimmoback.dto.ClientDto;
import com.gracetech.gestionimmoback.model.AppRole;
import com.gracetech.gestionimmoback.model.Client;
import com.gracetech.gestionimmoback.security.jwt.JwtUser;


@Mapper(componentModel = "spring", uses = {AppRole.class})
public interface ClientMapper extends EntityMapper<ClientDto, Client> {

	JwtUser toJwtDto(Client client);
	
	ClientDto toDto(Client client);
	
	Client toEntity(ClientDto dto);
	
	default Client fromId(Long id) {
		if (id == null) return null;
		
		Client client =  new Client();
		client.setId(id);
		return client;
	}
}
