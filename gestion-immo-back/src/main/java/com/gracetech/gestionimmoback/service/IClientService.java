package com.gracetech.gestionimmoback.service;

import java.util.List;

import com.gracetech.gestionimmoback.dto.ClientDto;
import com.gracetech.gestionimmoback.model.Client;

public interface IClientService {
	
	ClientDto save(ClientDto client);
	
	ClientDto save(Client client);
	
	ClientDto getClient(Long id);
	
	ClientDto getByUsername(String username);
	
	ClientDto getByEmail(String email);
	
	void delete(Long id);
	
	List<ClientDto> findAll();
	
	void activateAccountByMail(String mail);
	
	void updateLastConnextionDate(Long id);
	
	boolean existByEmail(String mail);

}
