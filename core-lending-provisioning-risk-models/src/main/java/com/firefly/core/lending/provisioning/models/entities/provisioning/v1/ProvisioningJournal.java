/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.lending.provisioning.models.entities.provisioning.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("provisioning_journal")
public class ProvisioningJournal {

    @Id
    @Column("provisioning_journal_id")
    private UUID provisioningJournalId;

    @Column("provisioning_calculation_id")
    private UUID provisioningCalculationId; // FK to ProvisioningCalculation

    @Column("accounting_journal_entry_id")
    private UUID accountingJournalEntryId;  // External reference (no direct FK)

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