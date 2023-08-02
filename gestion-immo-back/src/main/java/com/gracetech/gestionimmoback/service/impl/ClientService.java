package com.gracetech.gestionimmoback.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gracetech.gestionimmoback.dto.ClientDto;
import com.gracetech.gestionimmoback.exception.ElementNotFoundException;
import com.gracetech.gestionimmoback.mapper.ClientMapper;
import com.gracetech.gestionimmoback.model.Client;
import com.gracetech.gestionimmoback.repository.ClientRepository;
import com.gracetech.gestionimmoback.service.IClientService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService implements IClientService {
	
	private final ClientRepository repository;
	private final ClientMapper mapper;

	@Override
	public ClientDto save(Client client) {
		if (client.getId() == null) {
			client.setActive(false);
			client.setDeleted(false);
		}
		
		return mapper.toDto(repository.save(client));
	}

	@Override
	@Transactional(readOnly = true)
	public ClientDto getClient(Long id) {
		Optional<Client> client = repository.findById(id);
		if (client.isPresent()) {
			return mapper.toDto(client.get());
		} else {
			throw new ElementNotFoundException(Client.class, id);
		}
	}

	@Override
	public ClientDto getByUsername(String username) {
		Client client = repository.findByUsername(username);
		if (client != null) {
			return mapper.toDto(client);
		} else {
			throw new ElementNotFoundException(Client.class, username);
		}
	}

	@Override
	public ClientDto getByEmail(String email) {
		Client client = repository.findByEmail(email);
		if (client != null) {
			return mapper.toDto(client);
		} else {
			throw new ElementNotFoundException(Client.class, email);
		}
	}

	@Override
	public void delete(Long id) {
		Optional<Client> client = repository.findById(id);
		if (client.isPresent()) {
			repository.deleteById(id);
		} else {
			throw new ElementNotFoundException(Client.class, id);
		}
		
	}

	@Override
	public List<ClientDto> findAll() {
		return mapper.toDto(repository.findAll());
	}

	@Override
	public void activateAccountByMail(String mail) {
		if( repository.existsByEmail(mail)) {
			repository.activateAccountByEmail(mail);
		} else {
			throw new ElementNotFoundException(Client.class, mail);
		}
		
	}

	@Override
	public void updateLastConnextionDate(Long id) {
		Optional<Client> client = repository.findById(id);
		if (client.isPresent()) {
			Client entity = client.get();
			entity.setLastConnexionDate(Instant.now());
			repository.save(entity);
		} else {
			throw new ElementNotFoundException(Client.class, id);
		}
		
	}

}
