package com.catalis.core.lending.provisioning.models.entities.provisioning.v1;

import com.catalis.core.lending.provisioning.interfaces.enums.provisioning.v1.CalcMethodEnum;
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
@Table("provisioning_calculation")
public class ProvisioningCalculation {

    @Id
    @Column("provisioning_calculation_id")
    private Long provisioningCalculationId;

    @Column("provisioning_case_id")
    private Long provisioningCaseId; // FK to ProvisioningCase

    @Column("risk_assessment_id")
    private Long riskAssessmentId;   // FK to RiskAssessment

    @Column("final_ecl")
    private BigDecimal finalEcl;

    @Column("calc_method")
    private CalcMethodEnum calcMethod;

    @Column("calc_timestamp")
    private LocalDateTime calcTimestamp;

    @Column("notes")
    private String notes;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}