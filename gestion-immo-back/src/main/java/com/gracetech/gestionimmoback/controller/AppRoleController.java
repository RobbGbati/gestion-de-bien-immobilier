package com.gracetech.gestionimmoback.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gracetech.gestionimmoback.constant.Constants;
import com.gracetech.gestionimmoback.dto.AppRoleDto;
import com.gracetech.gestionimmoback.dto.GestionImmoResponse;
import com.gracetech.gestionimmoback.service.IAppRoleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(Constants.View.ROLES)
@RequiredArgsConstructor
@Tag(name = "Role")
public class AppRoleController {

	private final IAppRoleService service;
	
	@Operation(summary = "Create a role")
	@PostMapping("/create")
	public ResponseEntity<GestionImmoResponse> createRole(@RequestParam String description) {
		AppRoleDto toSave = new AppRoleDto();
		toSave.setDescription(description.toUpperCase());
		
		return ResponseEntity.ok().body(new GestionImmoResponse(service.save(toSave)));
	}
	
	@Operation(summary = "Delete a role")
	@DeleteMapping("/{id}")
	public ResponseEntity<GestionImmoResponse> deleteRole(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.ok().body(new GestionImmoResponse());
	}
	
	@Operation(summary = "Get role by its id")
	@GetMapping("/{id}")
	public ResponseEntity<GestionImmoResponse> getRole(@PathVariable Long id) {
		return ResponseEntity.ok().body(new GestionImmoResponse(service.getAppRole(id)));
	}
	
	@Operation(summary = "Get all role")
	@GetMapping("/all")
	public ResponseEntity<GestionImmoResponse> getAll() {
		return ResponseEntity.ok().body(new GestionImmoResponse(service.findAll()));
	}
}