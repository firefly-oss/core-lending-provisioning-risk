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
import com.firefly.core.lending.provisioning.core.services.provisioning.v1.ProvisioningJournalService;
import com.firefly.core.lending.provisioning.interfaces.dtos.provisioning.v1.ProvisioningJournalDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/provisioning-cases/{provisioningCaseId}/calculations/{provisioningCalculationId}/journal")
@Tag(name = "ProvisioningJournal", description = "Provisioning journal entries for a specific calculation")
@RequiredArgsConstructor
public class ProvisioningJournalController {

    private final ProvisioningJournalService service;

    @GetMapping
    @Operation(summary = "List or search provisioning journal entries for a calculation")
    public Mono<ResponseEntity<PaginationResponse<ProvisioningJournalDTO>>> findAll(
            @PathVariable UUID provisioningCaseId,
            @PathVariable UUID provisioningCalculationId,
            @ModelAttribute FilterRequest<ProvisioningJournalDTO> filterRequest) {

        return service.findAll(provisioningCaseId, provisioningCalculationId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a new provisioning journal entry")
    public Mono<ResponseEntity<ProvisioningJournalDTO>> create(
            @PathVariable UUID provisioningCaseId,
            @PathVariable UUID provisioningCalculationId,
            @Valid @RequestBody ProvisioningJournalDTO dto) {

        return service.create(provisioningCaseId, provisioningCalculationId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{provisioningJournalId}")
    @Operation(summary = "Get a provisioning journal entry by ID")
    public Mono<ResponseEntity<ProvisioningJournalDTO>> getById(
            @PathVariable UUID provisioningCaseId,
            @PathVariable UUID provisioningCalculationId,
            @PathVariable UUID provisioningJournalId) {

        return service.getById(provisioningCaseId, provisioningCalculationId, provisioningJournalId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{provisioningJournalId}")
    @Operation(summary = "Update a provisioning journal entry")
    public Mono<ResponseEntity<ProvisioningJournalDTO>> update(
            @PathVariable UUID provisioningCaseId,
            @PathVariable UUID provisioningCalculationId,
            @PathVariable UUID provisioningJournalId,
            @Valid @RequestBody ProvisioningJournalDTO dto) {

        return service.update(provisioningCaseId, provisioningCalculationId, provisioningJournalId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{provisioningJournalId}")
    @Operation(summary = "Delete a provisioning journal entry")
    public Mono<ResponseEntity<Void>> delete(
            @PathVariable UUID provisioningCaseId,
            @PathVariable UUID provisioningCalculationId,
            @PathVariable UUID provisioningJournalId) {

        return service.delete(provisioningCaseId, provisioningCalculationId, provisioningJournalId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}
