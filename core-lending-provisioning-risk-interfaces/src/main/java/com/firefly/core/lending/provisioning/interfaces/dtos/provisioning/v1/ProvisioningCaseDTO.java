package com.firefly.core.lending.provisioning.interfaces.dtos.provisioning.v1;

import com.firefly.core.lending.provisioning.interfaces.enums.provisioning.v1.ProvisioningStatusEnum;
import com.firefly.core.lending.provisioning.interfaces.enums.provisioning.v1.RiskGradeEnum;
import com.firefly.core.lending.provisioning.interfaces.enums.assessment.v1.StageCodeEnum;
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
public class ProvisioningCaseDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long provisioningCaseId;

    @FilterableId
    private Long loanServicingCaseId;  // External reference to Servicing

    private StageCodeEnum stageCode;   // STAGE_1, STAGE_2, STAGE_3, POCI
    private BigDecimal eclAmount;      // Current ECL amount
    private RiskGradeEnum riskGrade;   // Risk grade (AAA, BBB, etc.)
    private LocalDateTime lastCalculatedAt;
    private ProvisioningStatusEnum provisioningStatus; // ACTIVE, RELEASED, etc.
    private String remarks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}