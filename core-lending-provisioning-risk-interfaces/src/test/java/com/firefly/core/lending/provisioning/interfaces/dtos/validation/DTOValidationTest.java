package com.firefly.core.lending.provisioning.interfaces.dtos.validation;

import com.firefly.core.lending.provisioning.interfaces.dtos.assessment.v1.RiskAssessmentDTO;
import com.firefly.core.lending.provisioning.interfaces.dtos.provisioning.v1.*;
import com.firefly.core.lending.provisioning.interfaces.enums.assessment.v1.ScenarioCodeEnum;
import com.firefly.core.lending.provisioning.interfaces.enums.assessment.v1.StageCodeEnum;
import com.firefly.core.lending.provisioning.interfaces.enums.provisioning.v1.CalcMethodEnum;
import com.firefly.core.lending.provisioning.interfaces.enums.provisioning.v1.ProvisioningStatusEnum;
import com.firefly.core.lending.provisioning.interfaces.enums.provisioning.v1.RiskGradeEnum;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive validation tests for all DTOs to ensure validation annotations work correctly.
 */
public class DTOValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testProvisioningCaseDTO_ValidData_NoViolations() {
        ProvisioningCaseDTO dto = ProvisioningCaseDTO.builder()
                .loanServicingCaseId(UUID.randomUUID())
                .stageCode(StageCodeEnum.STAGE_1)
                .eclAmount(BigDecimal.valueOf(1000.00))
                .riskGrade(RiskGradeEnum.BBB)
                .provisioningStatus(ProvisioningStatusEnum.ACTIVE)
                .remarks("Valid remarks")
                .build();

        Set<ConstraintViolation<ProvisioningCaseDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Valid DTO should have no violations");
    }

    @Test
    void testProvisioningCaseDTO_InvalidData_HasViolations() {
        ProvisioningCaseDTO dto = ProvisioningCaseDTO.builder()
                .loanServicingCaseId(null) // Required field
                .stageCode(null) // Required field
                .eclAmount(BigDecimal.valueOf(-100.00)) // Must be positive or zero
                .riskGrade(null) // Required field
                .provisioningStatus(null) // Required field
                .remarks("A".repeat(1001)) // Exceeds max length
                .build();

        Set<ConstraintViolation<ProvisioningCaseDTO>> violations = validator.validate(dto);
        assertEquals(6, violations.size(), "Should have 6 validation violations");
    }

    @Test
    void testRiskAssessmentDTO_ValidData_NoViolations() {
        RiskAssessmentDTO dto = RiskAssessmentDTO.builder()
                .provisioningCaseId(UUID.randomUUID())
                .pdValue(BigDecimal.valueOf(0.05))
                .lgdValue(BigDecimal.valueOf(0.45))
                .eadValue(BigDecimal.valueOf(10000.00))
                .modelVersion("IFRS9_Model_v3")
                .scenarioCode(ScenarioCodeEnum.BASE)
                .assessmentDate(LocalDateTime.now())
                .details("Valid assessment details")
                .build();

        Set<ConstraintViolation<RiskAssessmentDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Valid DTO should have no violations");
    }

    @Test
    void testRiskAssessmentDTO_InvalidProbabilityValues_HasViolations() {
        RiskAssessmentDTO dto = RiskAssessmentDTO.builder()
                .provisioningCaseId(UUID.randomUUID())
                .pdValue(BigDecimal.valueOf(1.5)) // Must be <= 1.0
                .lgdValue(BigDecimal.valueOf(-0.1)) // Must be >= 0.0
                .eadValue(BigDecimal.valueOf(-1000.00)) // Must be positive or zero
                .modelVersion("") // Cannot be blank
                .scenarioCode(ScenarioCodeEnum.BASE)
                .assessmentDate(LocalDateTime.now())
                .build();

        Set<ConstraintViolation<RiskAssessmentDTO>> violations = validator.validate(dto);
        assertEquals(4, violations.size(), "Should have 4 validation violations");
    }

    @Test
    void testProvisioningCalculationDTO_ValidData_NoViolations() {
        ProvisioningCalculationDTO dto = ProvisioningCalculationDTO.builder()
                .provisioningCaseId(UUID.randomUUID())
                .riskAssessmentId(UUID.randomUUID())
                .finalEcl(BigDecimal.valueOf(500.00))
                .calcMethod(CalcMethodEnum.TWELVE_MONTH_ECL)
                .calcTimestamp(LocalDateTime.now())
                .notes("Valid calculation notes")
                .build();

        Set<ConstraintViolation<ProvisioningCalculationDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Valid DTO should have no violations");
    }

    @Test
    void testProvisioningCalculationDTO_InvalidData_HasViolations() {
        ProvisioningCalculationDTO dto = ProvisioningCalculationDTO.builder()
                .provisioningCaseId(null) // Required field
                .riskAssessmentId(null) // Required field
                .finalEcl(null) // Required field
                .calcMethod(null) // Required field
                .calcTimestamp(null) // Required field
                .notes("A".repeat(1001)) // Exceeds max length
                .build();

        Set<ConstraintViolation<ProvisioningCalculationDTO>> violations = validator.validate(dto);
        assertEquals(6, violations.size(), "Should have 6 validation violations");
    }

    @Test
    void testProvisioningJournalDTO_ValidData_NoViolations() {
        ProvisioningJournalDTO dto = ProvisioningJournalDTO.builder()
                .provisioningCalculationId(UUID.randomUUID())
                .accountingJournalEntryId(UUID.randomUUID())
                .provisionChangeAmount(BigDecimal.valueOf(250.00))
                .postedAt(LocalDateTime.now())
                .postingDescription("Valid posting description")
                .isReversal(false)
                .build();

        Set<ConstraintViolation<ProvisioningJournalDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Valid DTO should have no violations");
    }

    @Test
    void testProvisioningJournalDTO_InvalidData_HasViolations() {
        ProvisioningJournalDTO dto = ProvisioningJournalDTO.builder()
                .provisioningCalculationId(null) // Required field
                .accountingJournalEntryId(null) // Required field
                .provisionChangeAmount(null) // Required field
                .postedAt(null) // Required field
                .postingDescription("") // Cannot be blank
                .isReversal(null) // Required field
                .build();

        Set<ConstraintViolation<ProvisioningJournalDTO>> violations = validator.validate(dto);
        assertEquals(6, violations.size(), "Should have 6 validation violations");
    }

    @Test
    void testProvisioningStageHistoryDTO_ValidData_NoViolations() {
        ProvisioningStageHistoryDTO dto = ProvisioningStageHistoryDTO.builder()
                .provisioningCaseId(UUID.randomUUID())
                .oldStageCode(StageCodeEnum.STAGE_1)
                .newStageCode(StageCodeEnum.STAGE_2)
                .eclAmountAtChange(BigDecimal.valueOf(1500.00))
                .changedAt(LocalDateTime.now())
                .changedBy("system")
                .reason("Stage transition due to payment delay")
                .build();

        Set<ConstraintViolation<ProvisioningStageHistoryDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Valid DTO should have no violations");
    }

    @Test
    void testProvisioningStageHistoryDTO_InvalidData_HasViolations() {
        ProvisioningStageHistoryDTO dto = ProvisioningStageHistoryDTO.builder()
                .provisioningCaseId(null) // Required field
                .oldStageCode(null) // Required field
                .newStageCode(null) // Required field
                .eclAmountAtChange(BigDecimal.valueOf(-100.00)) // Must be positive or zero
                .changedAt(null) // Required field
                .changedBy("") // Cannot be blank
                .reason("") // Cannot be blank
                .build();

        Set<ConstraintViolation<ProvisioningStageHistoryDTO>> violations = validator.validate(dto);
        assertEquals(7, violations.size(), "Should have 7 validation violations");
    }

    @Test
    void testProvisioningStageHistoryDTO_ExceedsMaxLength_HasViolations() {
        ProvisioningStageHistoryDTO dto = ProvisioningStageHistoryDTO.builder()
                .provisioningCaseId(UUID.randomUUID())
                .oldStageCode(StageCodeEnum.STAGE_1)
                .newStageCode(StageCodeEnum.STAGE_2)
                .eclAmountAtChange(BigDecimal.valueOf(1500.00))
                .changedAt(LocalDateTime.now())
                .changedBy("A".repeat(101)) // Exceeds max length
                .reason("A".repeat(501)) // Exceeds max length
                .build();

        Set<ConstraintViolation<ProvisioningStageHistoryDTO>> violations = validator.validate(dto);
        assertEquals(2, violations.size(), "Should have 2 validation violations for max length");
    }

    @Test
    void testRiskAssessmentDTO_BoundaryValues_NoViolations() {
        // Test boundary values for PD and LGD (0.0 and 1.0)
        RiskAssessmentDTO dto = RiskAssessmentDTO.builder()
                .provisioningCaseId(UUID.randomUUID())
                .pdValue(BigDecimal.valueOf(0.0)) // Minimum valid value
                .lgdValue(BigDecimal.valueOf(1.0)) // Maximum valid value
                .eadValue(BigDecimal.valueOf(0.0)) // Minimum valid value
                .modelVersion("Model")
                .scenarioCode(ScenarioCodeEnum.BASE)
                .assessmentDate(LocalDateTime.now())
                .build();

        Set<ConstraintViolation<RiskAssessmentDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Boundary values should be valid");
    }

    @Test
    void testProvisioningCaseDTO_ZeroEclAmount_NoViolations() {
        ProvisioningCaseDTO dto = ProvisioningCaseDTO.builder()
                .loanServicingCaseId(UUID.randomUUID())
                .stageCode(StageCodeEnum.STAGE_1)
                .eclAmount(BigDecimal.ZERO) // Zero should be valid
                .riskGrade(RiskGradeEnum.AAA)
                .provisioningStatus(ProvisioningStatusEnum.ACTIVE)
                .build();

        Set<ConstraintViolation<ProvisioningCaseDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Zero ECL amount should be valid");
    }
}
