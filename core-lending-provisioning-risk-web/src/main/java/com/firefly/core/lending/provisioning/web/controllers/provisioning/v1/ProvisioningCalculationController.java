package com.firefly.core.lending.provisioning.web.controllers.provisioning.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.provisioning.core.services.provisioning.v1.ProvisioningCalculationService;
import com.firefly.core.lending.provisioning.interfaces.dtos.provisioning.v1.ProvisioningCalculationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/provisioning-cases/{provisioningCaseId}/calculations")
@Tag(name = "ProvisioningCalculation", description = "Provisioning calculation ops under a provisioning case")
@RequiredArgsConstructor
public class ProvisioningCalculationController {

    private final ProvisioningCalculationService service;

    @GetMapping
    @Operation(summary = "List or search provisioning calculations for a case")
    public Mono<ResponseEntity<PaginationResponse<ProvisioningCalculationDTO>>> findAll(
            @PathVariable Long provisioningCaseId,
            @ModelAttribute FilterRequest<ProvisioningCalculationDTO> filterRequest) {

        return service.findAll(provisioningCaseId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a new provisioning calculation")
    public Mono<ResponseEntity<ProvisioningCalculationDTO>> create(
            @PathVariable Long provisioningCaseId,
            @RequestBody ProvisioningCalculationDTO dto) {

        return service.create(provisioningCaseId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{provisioningCalculationId}")
    @Operation(summary = "Get a provisioning calculation by ID")
    public Mono<ResponseEntity<ProvisioningCalculationDTO>> getById(
            @PathVariable Long provisioningCaseId,
            @PathVariable Long provisioningCalculationId) {

        return service.getById(provisioningCaseId, provisioningCalculationId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{provisioningCalculationId}")
    @Operation(summary = "Update a provisioning calculation")
    public Mono<ResponseEntity<ProvisioningCalculationDTO>> update(
            @PathVariable Long provisioningCaseId,
            @PathVariable Long provisioningCalculationId,
            @RequestBody ProvisioningCalculationDTO dto) {

        return service.update(provisioningCaseId, provisioningCalculationId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{provisioningCalculationId}")
    @Operation(summary = "Delete a provisioning calculation")
    public Mono<ResponseEntity<Void>> delete(
            @PathVariable Long provisioningCaseId,
            @PathVariable Long provisioningCalculationId) {

        return service.delete(provisioningCaseId, provisioningCalculationId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}