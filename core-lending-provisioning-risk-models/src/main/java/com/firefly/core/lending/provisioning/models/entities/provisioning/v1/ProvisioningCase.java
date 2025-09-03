package com.firefly.core.lending.provisioning.models.entities.provisioning.v1;

import com.firefly.core.lending.provisioning.interfaces.enums.provisioning.v1.ProvisioningStatusEnum;
import com.firefly.core.lending.provisioning.interfaces.enums.provisioning.v1.RiskGradeEnum;
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
@Table("provisioning_case")
public class ProvisioningCase {

    @Id
    @Column("provisioning_case_id")
    private UUID provisioningCaseId;

    @Column("loan_servicing_case_id")
    private UUID loanServicingCaseId; // External reference (no direct FK)

    @Column("stage_code")
    private StageCodeEnum stageCode;  // STAGE_1, STAGE_2, STAGE_3, POCI

    @Column("ecl_amount")
    private BigDecimal eclAmount;

    @Column("risk_grade")
    private RiskGradeEnum riskGrade;

    @Column("last_calculated_at")
    private LocalDateTime lastCalculatedAt;

    @Column("provisioning_status")
    private ProvisioningStatusEnum provisioningStatus;

    @Column("remarks")
    private String remarks;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}