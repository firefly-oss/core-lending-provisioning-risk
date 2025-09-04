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