-- V2 - CREATE TABLES FOR PROVISIONING & RISK MODULE

-- ========================================================================
-- TABLE: provisioning_case
-- ========================================================================
CREATE TABLE IF NOT EXISTS provisioning_case (
                                                 provisioning_case_id    BIGSERIAL PRIMARY KEY,
                                                 loan_servicing_case_id  BIGINT,               -- External ref (no FK)
                                                 stage_code              stage_code NOT NULL,  -- e.g. STAGE_1|STAGE_2|STAGE_3|POCI
                                                 ecl_amount              DECIMAL(18,2) DEFAULT 0,
    risk_grade              risk_grade NOT NULL,  -- e.g. A, BBB, D, etc.
    last_calculated_at      TIMESTAMP,
    provisioning_status     provisioning_status NOT NULL, -- ACTIVE, RELEASED, etc.
    remarks                 TEXT,
    created_at              TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMP NOT NULL DEFAULT NOW()
    );

-- ========================================================================
-- TABLE: provisioning_stage_history
-- ========================================================================
CREATE TABLE IF NOT EXISTS provisioning_stage_history (
                                                          provisioning_stage_history_id BIGSERIAL PRIMARY KEY,
                                                          provisioning_case_id          BIGINT NOT NULL,
                                                          old_stage_code                stage_code,
                                                          new_stage_code                stage_code NOT NULL,
                                                          ecl_amount_at_change          DECIMAL(18,2),
    changed_at                    TIMESTAMP NOT NULL DEFAULT NOW(),
    changed_by                    VARCHAR(100),
    reason                        TEXT,
    created_at                    TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at                    TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_stage_hist_case
    FOREIGN KEY (provisioning_case_id)
    REFERENCES provisioning_case (provisioning_case_id)
    );

-- ========================================================================
-- TABLE: risk_assessment
-- ========================================================================
CREATE TABLE IF NOT EXISTS risk_assessment (
                                               risk_assessment_id     BIGSERIAL PRIMARY KEY,
                                               provisioning_case_id   BIGINT NOT NULL,
                                               pd_value               DECIMAL(9,4),   -- Probability of Default
    lgd_value              DECIMAL(9,4),   -- Loss Given Default
    ead_value              DECIMAL(15,2),  -- Exposure At Default
    model_version          VARCHAR(50),    -- e.g. 'IFRS9_Model_v3'
    scenario_code          scenario_code NOT NULL, -- e.g. BASE, ADVERSE
    assessment_date        TIMESTAMP NOT NULL DEFAULT NOW(),
    details                TEXT,           -- JSON or extended info
    created_at             TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at             TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_risk_assess_case
    FOREIGN KEY (provisioning_case_id)
    REFERENCES provisioning_case (provisioning_case_id)
    );

-- ========================================================================
-- TABLE: provisioning_calculation
-- ========================================================================
CREATE TABLE IF NOT EXISTS provisioning_calculation (
                                                        provisioning_calculation_id  BIGSERIAL PRIMARY KEY,
                                                        provisioning_case_id         BIGINT NOT NULL,
                                                        risk_assessment_id           BIGINT NOT NULL,
                                                        final_ecl                    DECIMAL(18,2),
    calc_method                  calc_method NOT NULL,
    calc_timestamp               TIMESTAMP NOT NULL DEFAULT NOW(),
    notes                        TEXT,
    created_at                   TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at                   TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_calc_case
    FOREIGN KEY (provisioning_case_id)
    REFERENCES provisioning_case (provisioning_case_id),
    CONSTRAINT fk_calc_risk_assess
    FOREIGN KEY (risk_assessment_id)
    REFERENCES risk_assessment (risk_assessment_id)
    );

-- ========================================================================
-- TABLE: provisioning_journal
-- ========================================================================
CREATE TABLE IF NOT EXISTS provisioning_journal (
                                                    provisioning_journal_id     BIGSERIAL PRIMARY KEY,
                                                    provisioning_calculation_id BIGINT NOT NULL,
                                                    accounting_journal_entry_id BIGINT,   -- External ref to Accounting (no FK)
                                                    provision_change_amount     DECIMAL(18,2),
    posted_at                   TIMESTAMP,
    posting_description         VARCHAR(255),
    is_reversal                 BOOLEAN DEFAULT FALSE,
    created_at                  TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at                  TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_journal_calc
    FOREIGN KEY (provisioning_calculation_id)
    REFERENCES provisioning_calculation (provisioning_calculation_id)
    );
