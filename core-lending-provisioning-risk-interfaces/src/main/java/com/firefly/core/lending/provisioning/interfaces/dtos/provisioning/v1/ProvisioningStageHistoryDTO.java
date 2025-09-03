package com.firefly.core.lending.provisioning.interfaces.dtos.provisioning.v1;

import com.firefly.core.lending.provisioning.interfaces.enums.assessment.v1.StageCodeEnum;
import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
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
public class ProvisioningStageHistoryDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID provisioningStageHistoryId;

    @FilterableId
    @NotNull(message = "Provisioning case ID is required")
    private UUID provisioningCaseId;   // Ties to ProvisioningCase

    @NotNull(message = "Old stage code is required")
    private StageCodeEnum oldStageCode;

    @NotNull(message = "New stage code is required")
    private StageCodeEnum newStageCode;

    @NotNull(message = "ECL amount at change is required")
    @PositiveOrZero(message = "ECL amount at change must be positive or zero")
    private BigDecimal eclAmountAtChange;

    @NotNull(message = "Changed at timestamp is required")
    private LocalDateTime changedAt;

    @NotBlank(message = "Changed by is required")
    @Size(max = 100, message = "Changed by cannot exceed 100 characters")
    private String changedBy;

    @NotBlank(message = "Reason is required")
    @Size(max = 500, message = "Reason cannot exceed 500 characters")
    private String reason;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;
}