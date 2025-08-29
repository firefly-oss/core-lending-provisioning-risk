package com.firefly.core.lending.provisioning.interfaces.dtos.assessment.v1;

import com.firefly.core.lending.provisioning.interfaces.enums.assessment.v1.ScenarioCodeEnum;
import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskAssessmentDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long riskAssessmentId;

    @FilterableId
    private Long provisioningCaseId;  // Ties to ProvisioningCase

    private BigDecimal pdValue;       // Probability of Default
    private BigDecimal lgdValue;      // Loss Given Default
    private BigDecimal eadValue;      // Exposure At Default
    private String modelVersion;      // E.g. 'IFRS9_Model_v3'
    private ScenarioCodeEnum scenarioCode; // BASE, ADVERSE, etc.
    private LocalDateTime assessmentDate;
    private String details;           // JSON or extended info
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}