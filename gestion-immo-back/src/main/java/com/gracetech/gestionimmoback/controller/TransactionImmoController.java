package com.gracetech.gestionimmoback.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gracetech.gestionimmoback.constant.Constants;
import com.gracetech.gestionimmoback.dto.GestionImmoResponse;
import com.gracetech.gestionimmoback.dto.TransactionDto;
import com.gracetech.gestionimmoback.service.ITransactionService;
import com.gracetech.gestionimmoback.utils.WebUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(Constants.View.TRANSACTIONS)
@RequiredArgsConstructor
@Tag(name = "Transaction")
public class TransactionImmoController {

    private final ITransactionService service;

    @Operation(
        summary = "Create a transaction",
        responses = {
            @ApiResponse(responseCode = "200", description = "Transaction is created"),
            @ApiResponse(responseCode = "404", description = "Bien does not exists")
        }
    )
    @PostMapping()
    public ResponseEntity<GestionImmoResponse> createTransaction(@RequestBody TransactionDto transaction) {
        final String username = WebUtils.getCurrentUsername();
        service.save(transaction, username);
        return ResponseEntity.ok().body(new GestionImmoResponse("Transaction is created", null));
    }

    @Operation(summary = "Delete a transaction",
        responses = {
            @ApiResponse(responseCode = "204", description = "Transacation deleted with success"),
            @ApiResponse(responseCode = "404", description = "Transaction does not exists")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<GestionImmoResponse> deleteTransaction(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Get all transactions",
        responses = {
            @ApiResponse(responseCode = "200", description = "All transaction")
        }
    )
    @GetMapping("/all")
    public ResponseEntity<GestionImmoResponse> getAllTransaction() {
        return ResponseEntity.ok().body(new GestionImmoResponse(service.getAllTransaction()));
    }
}