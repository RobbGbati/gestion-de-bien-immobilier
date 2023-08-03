package com.gracetech.gestionimmoback.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.gracetech.gestionimmoback.dto.ClientDto;
import com.gracetech.gestionimmoback.model.Client;
import com.gracetech.gestionimmoback.security.jwt.JwtUser;


@Mapper(componentModel = "spring", uses = {AppRoleMapper.class})
public interface ClientMapper {
	
	ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

	JwtUser toJwtDto(Client client);
	
	@Mapping(source = "appRole.id", target = "roleId")
	ClientDto toDto(Client client);
	
	Client toEntity(ClientDto dto);

	List<ClientDto> toDto(List<Client> clients);
	
	List<Client> toEntity(List<ClientDto> clientsDto);
}
