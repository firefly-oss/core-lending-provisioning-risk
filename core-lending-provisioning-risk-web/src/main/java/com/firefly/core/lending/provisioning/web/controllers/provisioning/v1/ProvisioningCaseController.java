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
import com.firefly.core.lending.provisioning.core.services.provisioning.v1.ProvisioningCaseService;
import com.firefly.core.lending.provisioning.interfaces.dtos.provisioning.v1.ProvisioningCaseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import java.util.UUID;

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
    public Mono<ResponseEntity<ProvisioningCaseDTO>> create(@Valid @RequestBody ProvisioningCaseDTO dto) {
        return service.create(dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{provisioningCaseId}")
    @Operation(summary = "Get a provisioning case by ID")
    public Mono<ResponseEntity<ProvisioningCaseDTO>> getById(
            @PathVariable UUID provisioningCaseId) {

        return service.getById(provisioningCaseId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{provisioningCaseId}")
    @Operation(summary = "Update a provisioning case")
    public Mono<ResponseEntity<ProvisioningCaseDTO>> update(
            @PathVariable UUID provisioningCaseId,
            @Valid @RequestBody ProvisioningCaseDTO dto) {

        return service.update(provisioningCaseId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{provisioningCaseId}")
    @Operation(summary = "Delete a provisioning case")
    public Mono<ResponseEntity<Void>> delete(
            @PathVariable UUID provisioningCaseId) {

        return service.delete(provisioningCaseId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}