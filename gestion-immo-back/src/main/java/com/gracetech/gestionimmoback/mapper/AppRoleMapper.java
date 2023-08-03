package com.gracetech.gestionimmoback.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.gracetech.gestionimmoback.dto.AppRoleDto;
import com.gracetech.gestionimmoback.model.AppRole;

@Mapper(componentModel = "spring")
public interface AppRoleMapper {

	AppRoleMapper INSTANCE = Mappers.getMapper(AppRoleMapper.class);
	
	AppRole toEntity(AppRoleDto dto);
	
	AppRoleDto toDto(AppRole role);
	
	List<AppRoleDto> toDto(List<AppRole> roles);
	
	List<AppRole> toEntity(List<AppRoleDto> roleDtos);

	
}
