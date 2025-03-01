package com.catalis.core.lending.provisioning.web.controllers.provisioning.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.lending.provisioning.core.services.provisioning.v1.ProvisioningCaseService;
import com.catalis.core.lending.provisioning.interfaces.dtos.provisioning.v1.ProvisioningCaseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/provisioning-cases")
@Tag(name = "ProvisioningCase", description = "Operations on Provisioning Cases")
@RequiredArgsConstructor
public class ProvisioningCaseController {

    private final ProvisioningCaseService service;

    @GetMapping
    @Operation(summary = "List or search provisioning cases")
    public Mono<ResponseEntity<PaginationResponse<ProvisioningCaseDTO>>> findAll(
            @ModelAttribute FilterRequest<ProvisioningCaseDTO> filterRequest) {

        return service.findAll(filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a new provisioning case")
    public Mono<ResponseEntity<ProvisioningCaseDTO>> create(@RequestBody ProvisioningCaseDTO dto) {
        return service.create(dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{provisioningCaseId}")
    @Operation(summary = "Get a provisioning case by ID")
    public Mono<ResponseEntity<ProvisioningCaseDTO>> getById(
            @PathVariable Long provisioningCaseId) {

        return service.getById(provisioningCaseId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{provisioningCaseId}")
    @Operation(summary = "Update a provisioning case")
    public Mono<ResponseEntity<ProvisioningCaseDTO>> update(
            @PathVariable Long provisioningCaseId,
            @RequestBody ProvisioningCaseDTO dto) {

        return service.update(provisioningCaseId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{provisioningCaseId}")
    @Operation(summary = "Delete a provisioning case")
    public Mono<ResponseEntity<Void>> delete(
            @PathVariable Long provisioningCaseId) {

        return service.delete(provisioningCaseId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}