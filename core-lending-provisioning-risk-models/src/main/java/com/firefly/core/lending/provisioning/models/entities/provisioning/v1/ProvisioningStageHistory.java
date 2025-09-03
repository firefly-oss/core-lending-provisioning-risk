package com.firefly.core.lending.provisioning.models.entities.provisioning.v1;

import com.firefly.core.lending.provisioning.interfaces.enums.assessment.v1.StageCodeEnum;
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
@Table("provisioning_stage_history")
public class ProvisioningStageHistory {

    @Id
    @Column("provisioning_stage_history_id")
    private UUID provisioningStageHistoryId;

    @Column("provisioning_case_id")
    private UUID provisioningCaseId; // FK to ProvisioningCase

    @Column("old_stage_code")
    private StageCodeEnum oldStageCode;

    @Column("new_stage_code")
    private StageCodeEnum newStageCode;

    @Column("ecl_amount_at_change")
    private BigDecimal eclAmountAtChange;

    @Column("changed_at")
    private LocalDateTime changedAt;

    @Column("changed_by")
    private String changedBy;

    @Column("reason")
    private String reason;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}