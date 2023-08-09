package com.gracetech.gestionimmoback.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gracetech.gestionimmoback.constant.Constants;
import com.gracetech.gestionimmoback.dto.ClientDto;
import com.gracetech.gestionimmoback.dto.GestionImmoResponse;
import com.gracetech.gestionimmoback.exception.CustomFunctionalException;
import com.gracetech.gestionimmoback.service.IClientService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constants.View.CLIENTS)
@Tag(name = "Client")
public class ClientController {
	
	private final IClientService clientService;

	@Operation(
			summary = "Check if a client exists by email",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200"
					),
					@ApiResponse(
							description = "Element not found",
							responseCode = "404"
					)
			}
	)
	@GetMapping("/existByEmail")
	public ResponseEntity<GestionImmoResponse> existByEmail(@RequestParam(name = "email") String email) {
		return ResponseEntity.ok().body(new GestionImmoResponse(clientService.existByEmail(email)));
	}

	@Operation(summary = "get all clients")
	@GetMapping("/all")
	public ResponseEntity<GestionImmoResponse> getAllClients() {
		return ResponseEntity.ok().body(new GestionImmoResponse(clientService.findAll()));
	}

	@Operation(
			summary = "Get a client by its id",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200"
					),
					@ApiResponse(
							description = "Client not found",
							responseCode = "404"
					)
			}
	)
	@GetMapping("/{id}")
	public ResponseEntity<GestionImmoResponse> getClientById(@PathVariable Long id) {
		return ResponseEntity.ok().body(new GestionImmoResponse(clientService.getClient(id)));
	}

	@Operation(
			summary = "Delete client by id",
			responses = {
					@ApiResponse(
							description = "Deletion success",
							responseCode = "204"
					),
					@ApiResponse(
							description = "Client not found",
							responseCode = "404"
					)
			}
	)
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
		clientService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(
			summary = "Create a client",
			responses = {
					@ApiResponse(
							description = "Client is created successfuly",
							responseCode = "200"
					),
					@ApiResponse(
							description = "Email is already taken ",
							responseCode = "404"
					)
			}
	)
	@PostMapping("")
	public ResponseEntity<GestionImmoResponse> createClient(@Valid @RequestBody(required = true) ClientDto client) {
		if (clientService.existByEmail(client.getEmail())) {
			throw new CustomFunctionalException(HttpStatus.BAD_REQUEST, Constants.Errors.EMAIL_TAKEN);
		}
		
		ClientDto created = clientService.save(client);
		
		return ResponseEntity.ok().body(new GestionImmoResponse(created));
	}

	@Operation(
			summary = "Update a client",
			responses = {
					@ApiResponse(
							description = "Update successfully ",
							responseCode = "200"
					),
					@ApiResponse(
							description = "Email is already taken ",
							responseCode = "404"
					)
			}
	)
	@PutMapping("")
	@Transactional
	public ResponseEntity<GestionImmoResponse> updateClient(@Valid @RequestBody(required = true) ClientDto client) {
		
		ClientDto dbClient = clientService.getClient(client.getId());
		if (StringUtils.isNotBlank(dbClient.getEmail()) && !dbClient.getEmail().equals(client.getEmail()) 
				&& clientService.existByEmail(client.getEmail())) {
			throw new CustomFunctionalException(HttpStatus.BAD_REQUEST, Constants.Errors.EMAIL_TAKEN);
		}
		
		ClientDto created = clientService.save(client);
		
		return ResponseEntity.ok().body(new GestionImmoResponse(created));
	}

	@Operation(
			summary = "Get all real estates of client"
	)
	@GetMapping("/my-real-estates/{id}")
	public ResponseEntity<GestionImmoResponse> getClientRealEstates(@PathVariable Long id) {


		return ResponseEntity.ok().body(new GestionImmoResponse());
	}

}
