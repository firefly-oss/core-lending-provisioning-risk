package com.firefly.core.lending.provisioning.web.controllers.assessment.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.provisioning.core.services.assessment.v1.RiskAssessmentService;
import com.firefly.core.lending.provisioning.interfaces.dtos.assessment.v1.RiskAssessmentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/provisioning-cases/{provisioningCaseId}/risk-assessments")
@Tag(name = "RiskAssessment", description = "Risk assessment operations under a provisioning case")
@RequiredArgsConstructor
public class RiskAssessmentController {

    private final RiskAssessmentService service;

    @GetMapping
    @Operation(summary = "List or search risk assessments for a provisioning case")
    public Mono<ResponseEntity<PaginationResponse<RiskAssessmentDTO>>> findAll(
            @PathVariable UUID provisioningCaseId,
            @ModelAttribute FilterRequest<RiskAssessmentDTO> filterRequest) {

        return service.findAll(provisioningCaseId, filterRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a new risk assessment")
    public Mono<ResponseEntity<RiskAssessmentDTO>> create(
            @PathVariable UUID provisioningCaseId,
            @Valid @RequestBody RiskAssessmentDTO dto) {

        return service.create(provisioningCaseId, dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{riskAssessmentId}")
    @Operation(summary = "Get a risk assessment by ID")
    public Mono<ResponseEntity<RiskAssessmentDTO>> getById(
            @PathVariable UUID provisioningCaseId,
            @PathVariable UUID riskAssessmentId) {

        return service.getById(provisioningCaseId, riskAssessmentId)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{riskAssessmentId}")
    @Operation(summary = "Update a risk assessment")
    public Mono<ResponseEntity<RiskAssessmentDTO>> update(
            @PathVariable UUID provisioningCaseId,
            @PathVariable UUID riskAssessmentId,
            @Valid @RequestBody RiskAssessmentDTO dto) {

        return service.update(provisioningCaseId, riskAssessmentId, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{riskAssessmentId}")
    @Operation(summary = "Delete a risk assessment")
    public Mono<ResponseEntity<Void>> delete(
            @PathVariable UUID provisioningCaseId,
            @PathVariable UUID riskAssessmentId) {

        return service.delete(provisioningCaseId, riskAssessmentId)
                .thenReturn(ResponseEntity.noContent().build());
    }
}
