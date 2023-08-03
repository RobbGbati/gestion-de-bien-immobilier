package com.gracetech.gestionimmoback.service;

import java.util.List;

import com.gracetech.gestionimmoback.dto.AppRoleDto;

public interface IAppRoleService {

	AppRoleDto save(AppRoleDto role);
	
	AppRoleDto getAppRole(Long id);
	
	void delete(Long id);
	
	List<AppRoleDto> findAll();

}
