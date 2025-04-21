package com.catalis.core.lending.provisioning.interfaces.dtos.provisioning.v1;

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
public class ProvisioningJournalDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long provisioningJournalId;

    @FilterableId
    private Long provisioningCalculationId;  // Ties to ProvisioningCalculation

    @FilterableId
    private Long accountingJournalEntryId;   // External reference (no direct FK)

    private BigDecimal provisionChangeAmount;
    private LocalDateTime postedAt;
    private String postingDescription;
    private Boolean isReversal;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}