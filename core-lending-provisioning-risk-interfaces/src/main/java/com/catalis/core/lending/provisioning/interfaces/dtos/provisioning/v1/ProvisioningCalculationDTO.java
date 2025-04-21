package com.catalis.core.lending.provisioning.interfaces.dtos.provisioning.v1;

import com.catalis.core.lending.provisioning.interfaces.enums.provisioning.v1.CalcMethodEnum;
import com.catalis.core.utils.annotations.FilterableId;
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
public class ProvisioningCalculationDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long provisioningCalculationId;

    @FilterableId
    private Long provisioningCaseId;    // Ties to ProvisioningCase

    @FilterableId
    private Long riskAssessmentId;      // Ties to RiskAssessment
    private BigDecimal finalEcl;        // Calculated ECL from PD/LGD/EAD
    private CalcMethodEnum calcMethod;  // E.g. TWELVE_MONTH_ECL, LIFETIME_ECL
    private LocalDateTime calcTimestamp;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}