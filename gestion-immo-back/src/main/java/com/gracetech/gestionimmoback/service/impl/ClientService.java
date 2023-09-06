package com.gracetech.gestionimmoback.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.gracetech.gestionimmoback.dto.BienDto;
import com.gracetech.gestionimmoback.repository.BienRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gracetech.gestionimmoback.dto.ClientDto;
import com.gracetech.gestionimmoback.exception.ElementNotFoundException;
import com.gracetech.gestionimmoback.mapper.ClientMapper;
import com.gracetech.gestionimmoback.model.Client;
import com.gracetech.gestionimmoback.repository.AppRoleRepository;
import com.gracetech.gestionimmoback.repository.ClientRepository;
import com.gracetech.gestionimmoback.service.IClientService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService implements IClientService {
	
	private final ClientRepository clientRepository;
	private final AppRoleRepository roleRepository;

	@Override
	public ClientDto save(ClientDto client) {
		
		Client toSave = ClientMapper.INSTANCE.toEntity(client);
		if (toSave.getId() == null) {
			toSave.setActive(false);
			toSave.setDeleted(false);
		}
		if (client.getRoleId() != null) {
			toSave.setAppRole(roleRepository.findById(client.getRoleId()).orElse(null));
		}
		
		return ClientMapper.INSTANCE.toDto(clientRepository.save(toSave));
	}
	
	@Override
	public ClientDto save(Client client) {
		
		if (client.getId() == null) {
			client.setActive(false);
			client.setDeleted(false);
		}
		
		return ClientMapper.INSTANCE.toDto(clientRepository.save(client));
	}

	@Override
	@Transactional(readOnly = true)
	public ClientDto getClient(Long id) {
		Optional<Client> client = clientRepository.findById(id);
		if (client.isPresent()) {
			return ClientMapper.INSTANCE.toDto(client.get());
		} else {
			throw new ElementNotFoundException(Client.class, id);
		}
	}

	@Override
	public ClientDto getByUsername(String username) {
		Client client = clientRepository.findByUsername(username);
		if (client != null) {
			return ClientMapper.INSTANCE.toDto(client);
		} else {
			throw new ElementNotFoundException(Client.class, username);
		}
	}

	@Override
	public ClientDto getByEmail(String email) {
		Client client = clientRepository.findByEmail(email);
		if (client != null) {
			return ClientMapper.INSTANCE.toDto(client);
		} else {
			throw new ElementNotFoundException(Client.class, email);
		}
	}

	@Override
	public void delete(Long id) {
		Optional<Client> client = clientRepository.findById(id);
		if (client.isPresent()) {
			clientRepository.deleteById(id);
		} else {
			throw new ElementNotFoundException(Client.class, id);
		}
		
	}

	@Override
	public List<ClientDto> findAll() {
		return ClientMapper.INSTANCE.toDto(clientRepository.findAll());
	}

	@Override
	public void activateAccountByMail(String mail) {
		if( clientRepository.existsByEmail(mail)) {
			clientRepository.activateAccountByEmail(mail);
		} else {
			throw new ElementNotFoundException(Client.class, mail);
		}
		
	}

	@Override
	public void updateLastConnextionDate(Long id) {
		Optional<Client> client = clientRepository.findById(id);
		if (client.isPresent()) {
			Client entity = client.get();
			entity.setLastConnexionDate(Instant.now());
			clientRepository.save(entity);
		} else {
			throw new ElementNotFoundException(Client.class, id);
		}
		
	}

	@Override
	public boolean existByEmail(String mail) {
		return clientRepository.existsByEmail(mail);
	}

	@Override
	public void deleteAll() {
		this.clientRepository.deleteAll();
	}

	@Override
	public List<BienDto> getAllEstatesForClient(Long id) {
		return null;
	}


}
