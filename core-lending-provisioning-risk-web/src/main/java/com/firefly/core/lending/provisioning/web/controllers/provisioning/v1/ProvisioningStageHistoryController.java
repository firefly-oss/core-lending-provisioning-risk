package com.firefly.core.lending.provisioning.web.controllers.provisioning.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.provisioning.core.services.provisioning.v1.ProvisioningStageHistoryService;
import com.firefly.core.lending.provisioning.interfaces.dtos.provisioning.v1.ProvisioningStageHistoryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/provisioning-cases/{provisioningCaseId}/stage-history")
@Tag(name = "ProvisioningStageHistory", description = "Stage history operations under a provisioning case")
@RequiredArgsConstructor
public class ProvisioningStageHistoryController {

    private final ProvisioningStageHistoryService service;

    @GetMapping
    @Operation(summary = "List or search stage history for a provisioning case")
    public Mono<ResponseEntity<PaginationResponse<ProvisioningStageHistoryDTO>>> findAll(
            @PathVariable Long provisioningCaseId,
            @ModelAttribute FilterRequest<ProvisioningStageHistoryDTO> filterRequest) {

        return service.findAll(provisioningCaseId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a new stage history entry")
    public Mono<ResponseEntity<ProvisioningStageHistoryDTO>> create(
            @PathVariable Long provisioningCaseId,
            @RequestBody ProvisioningStageHistoryDTO dto) {

        return service.create(provisioningCaseId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{provisioningStageHistoryId}")
    @Operation(summary = "Get a stage history entry by ID")
    public Mono<ResponseEntity<ProvisioningStageHistoryDTO>> getById(
            @PathVariable Long provisioningCaseId,
            @PathVariable Long provisioningStageHistoryId) {

        return service.getById(provisioningCaseId, provisioningStageHistoryId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{provisioningStageHistoryId}")
    @Operation(summary = "Update a stage history entry")
    public Mono<ResponseEntity<ProvisioningStageHistoryDTO>> update(
            @PathVariable Long provisioningCaseId,
            @PathVariable Long provisioningStageHistoryId,
            @RequestBody ProvisioningStageHistoryDTO dto) {

        return service.update(provisioningCaseId, provisioningStageHistoryId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{provisioningStageHistoryId}")
    @Operation(summary = "Delete a stage history entry")
    public Mono<ResponseEntity<Void>> delete(
            @PathVariable Long provisioningCaseId,
            @PathVariable Long provisioningStageHistoryId) {

        return service.delete(provisioningCaseId, provisioningStageHistoryId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}