package com.gracetech.gestionimmoback.mapper;

import org.mapstruct.Mapper;

import com.gracetech.gestionimmoback.dto.AppRoleDto;
import com.gracetech.gestionimmoback.model.AppRole;

@Mapper(componentModel = "string")
public interface AppRoleMapper extends EntityMapper<AppRoleDto, AppRole> {

	default AppRole fromId(Long id) {
		if (id == null) {
			return null;
		}
		AppRole role = new AppRole();
		role.setId(id);
		return role;
	}

}
