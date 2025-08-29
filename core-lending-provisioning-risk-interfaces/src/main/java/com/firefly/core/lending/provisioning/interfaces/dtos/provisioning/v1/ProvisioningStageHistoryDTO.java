package com.firefly.core.lending.provisioning.interfaces.dtos.provisioning.v1;

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
public class ProvisioningStageHistoryDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long provisioningStageHistoryId;

    @FilterableId
    private Long provisioningCaseId;   // Ties to ProvisioningCase

    private StageCodeEnum oldStageCode;
    private StageCodeEnum newStageCode;
    private BigDecimal eclAmountAtChange;
    private LocalDateTime changedAt;
    private String changedBy;
    private String reason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}