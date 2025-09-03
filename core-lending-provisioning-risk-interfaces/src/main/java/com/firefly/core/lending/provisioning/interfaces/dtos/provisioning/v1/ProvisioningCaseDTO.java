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
public class ProvisioningCaseDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID provisioningCaseId;

    @FilterableId
    @NotNull(message = "Loan servicing case ID is required")
    private UUID loanServicingCaseId;  // External reference to Servicing

    @NotNull(message = "Stage code is required")
    private StageCodeEnum stageCode;   // STAGE_1, STAGE_2, STAGE_3, POCI

    @PositiveOrZero(message = "ECL amount must be positive or zero")
    private BigDecimal eclAmount;      // Current ECL amount

    @NotNull(message = "Risk grade is required")
    private RiskGradeEnum riskGrade;   // Risk grade (AAA, BBB, etc.)

    private LocalDateTime lastCalculatedAt;

    @NotNull(message = "Provisioning status is required")
    private ProvisioningStatusEnum provisioningStatus; // ACTIVE, RELEASED, etc.

    @Size(max = 1000, message = "Remarks cannot exceed 1000 characters")
    private String remarks;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;
}