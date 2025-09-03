package com.firefly.core.lending.provisioning.interfaces.dtos.provisioning.v1;

import com.firefly.core.utils.annotations.FilterableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProvisioningJournalDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID provisioningJournalId;

    @FilterableId
    @NotNull(message = "Provisioning calculation ID is required")
    private UUID provisioningCalculationId;  // Ties to ProvisioningCalculation

    @FilterableId
    @NotNull(message = "Accounting journal entry ID is required")
    private UUID accountingJournalEntryId;   // External reference (no direct FK)

    @NotNull(message = "Provision change amount is required")
    private BigDecimal provisionChangeAmount;

    @NotNull(message = "Posted at timestamp is required")
    private LocalDateTime postedAt;

    @NotBlank(message = "Posting description is required")
    @Size(max = 500, message = "Posting description cannot exceed 500 characters")
    private String postingDescription;

    @NotNull(message = "Is reversal flag is required")
    private Boolean isReversal;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;
}