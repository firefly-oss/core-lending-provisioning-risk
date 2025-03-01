package com.catalis.core.lending.provisioning.models.entities.provisioning.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("provisioning_journal")
public class ProvisioningJournal {

    @Id
    @Column("provisioning_journal_id")
    private Long provisioningJournalId;

    @Column("provisioning_calculation_id")
    private Long provisioningCalculationId; // FK to ProvisioningCalculation

    @Column("accounting_journal_entry_id")
    private Long accountingJournalEntryId;  // External reference (no direct FK)

    @Column("provision_change_amount")
    private BigDecimal provisionChangeAmount;

    @Column("posted_at")
    private LocalDateTime postedAt;

    @Column("posting_description")
    private String postingDescription;

    @Column("is_reversal")
    private Boolean isReversal;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}