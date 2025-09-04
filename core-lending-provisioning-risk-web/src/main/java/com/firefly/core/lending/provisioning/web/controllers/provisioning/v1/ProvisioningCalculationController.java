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
import com.firefly.core.lending.provisioning.core.services.provisioning.v1.ProvisioningCalculationService;
import com.firefly.core.lending.provisioning.interfaces.dtos.provisioning.v1.ProvisioningCalculationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/provisioning-cases/{provisioningCaseId}/calculations")
@Tag(name = "ProvisioningCalculation", description = "Provisioning calculation ops under a provisioning case")
@RequiredArgsConstructor
public class ProvisioningCalculationController {

    private final ProvisioningCalculationService service;

    @GetMapping
    @Operation(summary = "List or search provisioning calculations for a case")
    public Mono<ResponseEntity<PaginationResponse<ProvisioningCalculationDTO>>> findAll(
            @PathVariable UUID provisioningCaseId,
            @ModelAttribute FilterRequest<ProvisioningCalculationDTO> filterRequest) {

        return service.findAll(provisioningCaseId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a new provisioning calculation")
    public Mono<ResponseEntity<ProvisioningCalculationDTO>> create(
            @PathVariable UUID provisioningCaseId,
            @Valid @RequestBody ProvisioningCalculationDTO dto) {

        return service.create(provisioningCaseId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{provisioningCalculationId}")
    @Operation(summary = "Get a provisioning calculation by ID")
    public Mono<ResponseEntity<ProvisioningCalculationDTO>> getById(
            @PathVariable UUID provisioningCaseId,
            @PathVariable UUID provisioningCalculationId) {

        return service.getById(provisioningCaseId, provisioningCalculationId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{provisioningCalculationId}")
    @Operation(summary = "Update a provisioning calculation")
    public Mono<ResponseEntity<ProvisioningCalculationDTO>> update(
            @PathVariable UUID provisioningCaseId,
            @PathVariable UUID provisioningCalculationId,
            @Valid @RequestBody ProvisioningCalculationDTO dto) {

        return service.update(provisioningCaseId, provisioningCalculationId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{provisioningCalculationId}")
    @Operation(summary = "Delete a provisioning calculation")
    public Mono<ResponseEntity<Void>> delete(
            @PathVariable UUID provisioningCaseId,
            @PathVariable UUID provisioningCalculationId) {

        return service.delete(provisioningCaseId, provisioningCalculationId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}