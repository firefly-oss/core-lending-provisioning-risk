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


package com.firefly.core.lending.provisioning.interfaces.dtos.provisioning.v1;

import com.firefly.core.lending.provisioning.interfaces.enums.provisioning.v1.CalcMethodEnum;
import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProvisioningCalculationDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID provisioningCalculationId;

    @FilterableId
    @NotNull(message = "Provisioning case ID is required")
    private UUID provisioningCaseId;    // Ties to ProvisioningCase

    @FilterableId
    @NotNull(message = "Risk assessment ID is required")
    private UUID riskAssessmentId;      // Ties to RiskAssessment

    @NotNull(message = "Final ECL is required")
    @PositiveOrZero(message = "Final ECL must be positive or zero")
    private BigDecimal finalEcl;        // Calculated ECL from PD/LGD/EAD

    @NotNull(message = "Calculation method is required")
    private CalcMethodEnum calcMethod;  // E.g. TWELVE_MONTH_ECL, LIFETIME_ECL

    @NotNull(message = "Calculation timestamp is required")
    private LocalDateTime calcTimestamp;

    @Size(max = 1000, message = "Notes cannot exceed 1000 characters")
    private String notes;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;
}