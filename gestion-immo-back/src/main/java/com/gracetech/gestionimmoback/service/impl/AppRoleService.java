package com.gracetech.gestionimmoback.service.impl;


import java.util.List;
import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import com.gracetech.gestionimmoback.dto.AppRoleDto;
import com.gracetech.gestionimmoback.exception.ElementNotFoundException;
import com.gracetech.gestionimmoback.mapper.AppRoleMapper;
import com.gracetech.gestionimmoback.model.AppRole;
import com.gracetech.gestionimmoback.repository.AppRoleRepository;
import com.gracetech.gestionimmoback.service.IAppRoleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppRoleService implements IAppRoleService {
	
	private final AppRoleRepository repository;

	@Override
	public AppRoleDto save(AppRoleDto role) {
		log.debug("Saving approle with id {}", role.getId());
		AppRole toSave = AppRoleMapper.INSTANCE.toEntity(role);
		return AppRoleMapper.INSTANCE.toDto(repository.save(toSave));
	}

	@Override
	public AppRoleDto getAppRole(Long id) {
		log.debug("Get approle with id {}", id);
		Optional<AppRole> obj = repository.findById(id);
		if (obj.isPresent()) {
			return AppRoleMapper.INSTANCE.toDto(obj.get());
		} else {
			throw new ElementNotFoundException(AppRole.class, id);
		}
	}

	@Override
	public void delete(Long id) {
		log.debug("Deleting approle with id {}", id);
		try {
			repository.deleteById(id);			
		} catch(Exception e) {
			throw new ElementNotFoundException(AppRole.class, id);
		}
	}

	@Override
	public List<AppRoleDto> findAll() {
		List<AppRole> roles = repository.findAll();
		return AppRoleMapper.INSTANCE.toDto(roles);
	}
	

}
