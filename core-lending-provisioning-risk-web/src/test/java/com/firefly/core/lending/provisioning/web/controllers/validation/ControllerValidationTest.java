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


package com.firefly.core.lending.provisioning.web.controllers.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firefly.core.lending.provisioning.core.services.provisioning.v1.ProvisioningCaseService;
import com.firefly.core.lending.provisioning.interfaces.dtos.provisioning.v1.ProvisioningCaseDTO;
import com.firefly.core.lending.provisioning.interfaces.enums.assessment.v1.StageCodeEnum;
import com.firefly.core.lending.provisioning.interfaces.enums.provisioning.v1.ProvisioningStatusEnum;
import com.firefly.core.lending.provisioning.interfaces.enums.provisioning.v1.RiskGradeEnum;
import com.firefly.core.lending.provisioning.web.controllers.provisioning.v1.ProvisioningCaseController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Integration tests to verify that validation annotations work correctly in controllers.
 */
@WebFluxTest(ProvisioningCaseController.class)
public class ControllerValidationTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProvisioningCaseService provisioningCaseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateProvisioningCase_ValidData_Success() throws Exception {
        // Arrange
        ProvisioningCaseDTO validDto = ProvisioningCaseDTO.builder()
                .loanServicingCaseId(UUID.randomUUID())
                .stageCode(StageCodeEnum.STAGE_1)
                .eclAmount(BigDecimal.valueOf(1000.00))
                .riskGrade(RiskGradeEnum.BBB)
                .provisioningStatus(ProvisioningStatusEnum.ACTIVE)
                .remarks("Valid remarks")
                .build();

        ProvisioningCaseDTO responseDto = ProvisioningCaseDTO.builder()
                .provisioningCaseId(UUID.randomUUID())
                .loanServicingCaseId(validDto.getLoanServicingCaseId())
                .stageCode(validDto.getStageCode())
                .eclAmount(validDto.getEclAmount())
                .riskGrade(validDto.getRiskGrade())
                .provisioningStatus(validDto.getProvisioningStatus())
                .remarks(validDto.getRemarks())
                .build();

        when(provisioningCaseService.create(any(ProvisioningCaseDTO.class)))
                .thenReturn(Mono.just(responseDto));

        // Act & Assert
        webTestClient.post()
                .uri("/api/v1/provisioning-cases")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(validDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProvisioningCaseDTO.class);

        verify(provisioningCaseService).create(any(ProvisioningCaseDTO.class));
    }

    @Test
    void testCreateProvisioningCase_InvalidData_BadRequest() throws Exception {
        // Arrange
        ProvisioningCaseDTO invalidDto = ProvisioningCaseDTO.builder()
                .loanServicingCaseId(null) // Required field missing
                .stageCode(null) // Required field missing
                .eclAmount(BigDecimal.valueOf(-100.00)) // Invalid negative value
                .riskGrade(null) // Required field missing
                .provisioningStatus(null) // Required field missing
                .remarks("A".repeat(1001)) // Exceeds max length
                .build();

        // Act & Assert
        webTestClient.post()
                .uri("/api/v1/provisioning-cases")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidDto)
                .exchange()
                .expectStatus().isBadRequest();

        // Verify service method was never called due to validation failure
        verify(provisioningCaseService, never()).create(any(ProvisioningCaseDTO.class));
    }

    @Test
    void testCreateProvisioningCase_NullLoanServicingCaseId_BadRequest() throws Exception {
        // Arrange
        ProvisioningCaseDTO dtoWithNullId = ProvisioningCaseDTO.builder()
                .loanServicingCaseId(null) // Required field
                .stageCode(StageCodeEnum.STAGE_1)
                .eclAmount(BigDecimal.valueOf(1000.00))
                .riskGrade(RiskGradeEnum.BBB)
                .provisioningStatus(ProvisioningStatusEnum.ACTIVE)
                .build();

        // Act & Assert
        webTestClient.post()
                .uri("/api/v1/provisioning-cases")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dtoWithNullId)
                .exchange()
                .expectStatus().isBadRequest();

        verify(provisioningCaseService, never()).create(any(ProvisioningCaseDTO.class));
    }

    @Test
    void testCreateProvisioningCase_NegativeEclAmount_BadRequest() throws Exception {
        // Arrange
        ProvisioningCaseDTO dtoWithNegativeAmount = ProvisioningCaseDTO.builder()
                .loanServicingCaseId(UUID.randomUUID())
                .stageCode(StageCodeEnum.STAGE_1)
                .eclAmount(BigDecimal.valueOf(-500.00)) // Invalid negative value
                .riskGrade(RiskGradeEnum.BBB)
                .provisioningStatus(ProvisioningStatusEnum.ACTIVE)
                .build();

        // Act & Assert
        webTestClient.post()
                .uri("/api/v1/provisioning-cases")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dtoWithNegativeAmount)
                .exchange()
                .expectStatus().isBadRequest();

        verify(provisioningCaseService, never()).create(any(ProvisioningCaseDTO.class));
    }

    @Test
    void testCreateProvisioningCase_ExcessiveRemarksLength_BadRequest() throws Exception {
        // Arrange
        ProvisioningCaseDTO dtoWithLongRemarks = ProvisioningCaseDTO.builder()
                .loanServicingCaseId(UUID.randomUUID())
                .stageCode(StageCodeEnum.STAGE_1)
                .eclAmount(BigDecimal.valueOf(1000.00))
                .riskGrade(RiskGradeEnum.BBB)
                .provisioningStatus(ProvisioningStatusEnum.ACTIVE)
                .remarks("A".repeat(1001)) // Exceeds max length of 1000
                .build();

        // Act & Assert
        webTestClient.post()
                .uri("/api/v1/provisioning-cases")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dtoWithLongRemarks)
                .exchange()
                .expectStatus().isBadRequest();

        verify(provisioningCaseService, never()).create(any(ProvisioningCaseDTO.class));
    }

    @Test
    void testUpdateProvisioningCase_ValidData_Success() throws Exception {
        // Arrange
        UUID provisioningCaseId = UUID.randomUUID();
        ProvisioningCaseDTO validDto = ProvisioningCaseDTO.builder()
                .loanServicingCaseId(UUID.randomUUID())
                .stageCode(StageCodeEnum.STAGE_2)
                .eclAmount(BigDecimal.valueOf(1500.00))
                .riskGrade(RiskGradeEnum.BB)
                .provisioningStatus(ProvisioningStatusEnum.ACTIVE)
                .remarks("Updated remarks")
                .build();

        ProvisioningCaseDTO responseDto = ProvisioningCaseDTO.builder()
                .provisioningCaseId(provisioningCaseId)
                .loanServicingCaseId(validDto.getLoanServicingCaseId())
                .stageCode(validDto.getStageCode())
                .eclAmount(validDto.getEclAmount())
                .riskGrade(validDto.getRiskGrade())
                .provisioningStatus(validDto.getProvisioningStatus())
                .remarks(validDto.getRemarks())
                .build();

        when(provisioningCaseService.update(any(UUID.class), any(ProvisioningCaseDTO.class)))
                .thenReturn(Mono.just(responseDto));

        // Act & Assert
        webTestClient.put()
                .uri("/api/v1/provisioning-cases/{provisioningCaseId}", provisioningCaseId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(validDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProvisioningCaseDTO.class);

        verify(provisioningCaseService).update(any(UUID.class), any(ProvisioningCaseDTO.class));
    }

    @Test
    void testUpdateProvisioningCase_InvalidData_BadRequest() throws Exception {
        // Arrange
        UUID provisioningCaseId = UUID.randomUUID();
        ProvisioningCaseDTO invalidDto = ProvisioningCaseDTO.builder()
                .loanServicingCaseId(null) // Required field missing
                .stageCode(null) // Required field missing
                .eclAmount(BigDecimal.valueOf(-200.00)) // Invalid negative value
                .riskGrade(null) // Required field missing
                .provisioningStatus(null) // Required field missing
                .build();

        // Act & Assert
        webTestClient.put()
                .uri("/api/v1/provisioning-cases/{provisioningCaseId}", provisioningCaseId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidDto)
                .exchange()
                .expectStatus().isBadRequest();

        verify(provisioningCaseService, never()).update(any(UUID.class), any(ProvisioningCaseDTO.class));
    }

    @Test
    void testCreateProvisioningCase_ZeroEclAmount_Success() throws Exception {
        // Arrange - Zero should be valid for ECL amount
        ProvisioningCaseDTO dtoWithZeroAmount = ProvisioningCaseDTO.builder()
                .loanServicingCaseId(UUID.randomUUID())
                .stageCode(StageCodeEnum.STAGE_1)
                .eclAmount(BigDecimal.ZERO) // Zero should be valid
                .riskGrade(RiskGradeEnum.AAA)
                .provisioningStatus(ProvisioningStatusEnum.ACTIVE)
                .build();

        ProvisioningCaseDTO responseDto = ProvisioningCaseDTO.builder()
                .provisioningCaseId(UUID.randomUUID())
                .loanServicingCaseId(dtoWithZeroAmount.getLoanServicingCaseId())
                .stageCode(dtoWithZeroAmount.getStageCode())
                .eclAmount(dtoWithZeroAmount.getEclAmount())
                .riskGrade(dtoWithZeroAmount.getRiskGrade())
                .provisioningStatus(dtoWithZeroAmount.getProvisioningStatus())
                .build();

        when(provisioningCaseService.create(any(ProvisioningCaseDTO.class)))
                .thenReturn(Mono.just(responseDto));

        // Act & Assert
        webTestClient.post()
                .uri("/api/v1/provisioning-cases")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dtoWithZeroAmount)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProvisioningCaseDTO.class);

        verify(provisioningCaseService).create(any(ProvisioningCaseDTO.class));
    }
}
