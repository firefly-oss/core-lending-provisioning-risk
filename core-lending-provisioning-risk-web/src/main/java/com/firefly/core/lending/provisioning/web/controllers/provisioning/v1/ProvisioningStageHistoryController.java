/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


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

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/provisioning-cases/{provisioningCaseId}/stage-history")
@Tag(name = "ProvisioningStageHistory", description = "Stage history operations under a provisioning case")
@RequiredArgsConstructor
public class ProvisioningStageHistoryController {

    private final ProvisioningStageHistoryService service;

    @GetMapping
    @Operation(summary = "List or search stage history for a provisioning case")
    public Mono<ResponseEntity<PaginationResponse<ProvisioningStageHistoryDTO>>> findAll(
            @PathVariable UUID provisioningCaseId,
            @ModelAttribute FilterRequest<ProvisioningStageHistoryDTO> filterRequest) {

        return service.findAll(provisioningCaseId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a new stage history entry")
    public Mono<ResponseEntity<ProvisioningStageHistoryDTO>> create(
            @PathVariable UUID provisioningCaseId,
            @Valid @RequestBody ProvisioningStageHistoryDTO dto) {

        return service.create(provisioningCaseId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{provisioningStageHistoryId}")
    @Operation(summary = "Get a stage history entry by ID")
    public Mono<ResponseEntity<ProvisioningStageHistoryDTO>> getById(
            @PathVariable UUID provisioningCaseId,
            @PathVariable UUID provisioningStageHistoryId) {

        return service.getById(provisioningCaseId, provisioningStageHistoryId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{provisioningStageHistoryId}")
    @Operation(summary = "Update a stage history entry")
    public Mono<ResponseEntity<ProvisioningStageHistoryDTO>> update(
            @PathVariable UUID provisioningCaseId,
            @PathVariable UUID provisioningStageHistoryId,
            @Valid @RequestBody ProvisioningStageHistoryDTO dto) {

        return service.update(provisioningCaseId, provisioningStageHistoryId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{provisioningStageHistoryId}")
    @Operation(summary = "Delete a stage history entry")
    public Mono<ResponseEntity<Void>> delete(
            @PathVariable UUID provisioningCaseId,
            @PathVariable UUID provisioningStageHistoryId) {

        return service.delete(provisioningCaseId, provisioningStageHistoryId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}