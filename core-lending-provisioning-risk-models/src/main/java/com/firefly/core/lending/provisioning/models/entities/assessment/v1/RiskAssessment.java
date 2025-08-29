package com.firefly.core.lending.provisioning.models.entities.assessment.v1;

import com.firefly.core.lending.provisioning.interfaces.enums.assessment.v1.ScenarioCodeEnum;
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
@Table("risk_assessment")
public class RiskAssessment {

    @Id
    @Column("risk_assessment_id")
    private Long riskAssessmentId;

    @Column("provisioning_case_id")
    private Long provisioningCaseId; // FK to ProvisioningCase

    @Column("pd_value")
    private BigDecimal pdValue;      // Probability of Default

    @Column("lgd_value")
    private BigDecimal lgdValue;     // Loss Given Default

    @Column("ead_value")
    private BigDecimal eadValue;     // Exposure At Default

    @Column("model_version")
    private String modelVersion;     // E.g. 'IFRS9_Model_v3'

    @Column("scenario_code")
    private ScenarioCodeEnum scenarioCode; // BASE, ADVERSE, etc.

    @Column("assessment_date")
    private LocalDateTime assessmentDate;

    @Column("details")
    private String details;          // JSON or extended info

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}