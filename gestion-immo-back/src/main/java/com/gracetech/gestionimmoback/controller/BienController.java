package com.gracetech.gestionimmoback.controller;

import com.gracetech.gestionimmoback.constant.Constants;
import com.gracetech.gestionimmoback.dto.BienDto;
import com.gracetech.gestionimmoback.dto.GestionImmoResponse;
import com.gracetech.gestionimmoback.mapper.BienMapper;
import com.gracetech.gestionimmoback.service.IBienService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.View.BIENS)
@RequiredArgsConstructor
@Tag(name = "Bien")
public class BienController {

    private final IBienService bienService;

    @Operation(summary = "Create bien ")
    @PostMapping("")
    public ResponseEntity<GestionImmoResponse> createBien(@Valid @RequestBody BienDto bien) {
        return ResponseEntity.ok().body(
                new GestionImmoResponse(bienService.create(bien))
        );
    }

    @Operation(summary = "Get a bien by id")
    @GetMapping("/{id}")
    public ResponseEntity<GestionImmoResponse> getBienById(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                new GestionImmoResponse(
                    BienMapper.INSTANCE.toDto(bienService.getBienById(id)))
        );
    }

    @Operation(summary = "Delete Bien by id",
        responses = {
            @ApiResponse(
                    responseCode = "404",
                    description = "Bien not found"
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "Bien deleted successfully"
            )
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<GestionImmoResponse> deleteBienById(@PathVariable Long id) {
        bienService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search Bien by description")
    @GetMapping("/searchByDescriptionOrCity")
    public ResponseEntity<GestionImmoResponse> searchBienByDescription(@RequestParam(name = "query") String query) {
        return ResponseEntity.ok().body(
                new GestionImmoResponse(
                    BienMapper.INSTANCE.toDto(bienService.searchBiensByDescription(query)))
        );
    }

    @Operation(summary = "Get all by creator")
    @GetMapping("/createBy/{clientId}")
    public ResponseEntity<GestionImmoResponse> findBienByFirstOwner(@PathVariable Long clientId) {
        return ResponseEntity.ok().body(
                new GestionImmoResponse(
                    BienMapper.INSTANCE.toDto(bienService.findBiensByFirstOwner(clientId)))
        );
    }

    @Operation(summary = "Get all bien owned by a client")
    @GetMapping("/ownedBy/{clientId}")
    public ResponseEntity<GestionImmoResponse> findBienOwnedByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok().body(
                new GestionImmoResponse(
                    BienMapper.INSTANCE.toDto(bienService.findBienOwnedByClient(clientId)))
        );
    }

}
