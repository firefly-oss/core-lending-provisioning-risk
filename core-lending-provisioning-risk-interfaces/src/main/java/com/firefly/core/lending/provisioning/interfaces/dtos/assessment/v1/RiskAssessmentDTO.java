package com.firefly.core.lending.provisioning.interfaces.dtos.assessment.v1;

import com.firefly.core.lending.provisioning.interfaces.enums.assessment.v1.ScenarioCodeEnum;
import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskAssessmentDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID riskAssessmentId;

    @FilterableId
    @NotNull(message = "Provisioning case ID is required")
    private UUID provisioningCaseId;  // Ties to ProvisioningCase

    @NotNull(message = "PD value is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "PD value must be between 0 and 1")
    @DecimalMax(value = "1.0", inclusive = true, message = "PD value must be between 0 and 1")
    private BigDecimal pdValue;       // Probability of Default

    @NotNull(message = "LGD value is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "LGD value must be between 0 and 1")
    @DecimalMax(value = "1.0", inclusive = true, message = "LGD value must be between 0 and 1")
    private BigDecimal lgdValue;      // Loss Given Default

    @NotNull(message = "EAD value is required")
    @PositiveOrZero(message = "EAD value must be positive or zero")
    private BigDecimal eadValue;      // Exposure At Default

    @NotBlank(message = "Model version is required")
    @Size(max = 100, message = "Model version cannot exceed 100 characters")
    private String modelVersion;      // E.g. 'IFRS9_Model_v3'

    @NotNull(message = "Scenario code is required")
    private ScenarioCodeEnum scenarioCode; // BASE, ADVERSE, etc.

    @NotNull(message = "Assessment date is required")
    private LocalDateTime assessmentDate;

    @Size(max = 2000, message = "Details cannot exceed 2000 characters")
    private String details;           // JSON or extended info

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;
}