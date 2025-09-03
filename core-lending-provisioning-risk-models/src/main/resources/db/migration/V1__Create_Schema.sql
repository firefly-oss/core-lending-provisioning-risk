-- V1 - CREATE COMPLETE SCHEMA WITH ENUMS, TABLES (UUID), AND CASTS

-- Enable UUID extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- ========================================================================
-- CREATE ENUMS FOR PROVISIONING & RISK
-- ========================================================================

-- provisioning_case -> stage_code
CREATE TYPE stage_code AS ENUM (
    'STAGE_1',
    'STAGE_2',
    'STAGE_3',
    'POCI'
);

-- provisioning_case -> risk_grade
CREATE TYPE risk_grade AS ENUM (
    'AAA',
    'AA',
    'A',
    'BBB',
    'BB',
    'B',
    'CCC',
    'CC',
    'C',
    'D'
);

-- provisioning_case -> provisioning_status
CREATE TYPE provisioning_status AS ENUM (
    'ACTIVE',
    'RELEASED',
    'WRITTEN_OFF',
    'RECOVERED'
);

-- risk_assessment -> scenario_code
CREATE TYPE scenario_code AS ENUM (
    'BASE',
    'OPTIMISTIC',
    'ADVERSE',
    'SEVERE'
);

-- provisioning_calculation -> calc_method
CREATE TYPE calc_method AS ENUM (
    'TWELVE_MONTH_ECL',
    'LIFETIME_ECL',
    'EXPERT_OVERRIDE'
);

-- ========================================================================
-- CREATE TABLES WITH UUID PRIMARY KEYS
-- ========================================================================

-- TABLE: provisioning_case
CREATE TABLE IF NOT EXISTS provisioning_case (
    provisioning_case_id    UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    loan_servicing_case_id  UUID,               -- External ref (no FK)
    stage_code              stage_code NOT NULL,  -- e.g. STAGE_1|STAGE_2|STAGE_3|POCI
    ecl_amount              DECIMAL(18,2) DEFAULT 0,
    risk_grade              risk_grade NOT NULL,  -- e.g. A, BBB, D, etc.
    last_calculated_at      TIMESTAMP,
    provisioning_status     provisioning_status NOT NULL, -- ACTIVE, RELEASED, etc.
    remarks                 TEXT,
    created_at              TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMP NOT NULL DEFAULT NOW()
);

-- TABLE: provisioning_stage_history
CREATE TABLE IF NOT EXISTS provisioning_stage_history (
    provisioning_stage_history_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    provisioning_case_id          UUID NOT NULL,
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

-- TABLE: risk_assessment
CREATE TABLE IF NOT EXISTS risk_assessment (
    risk_assessment_id     UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    provisioning_case_id   UUID NOT NULL,
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

-- TABLE: provisioning_calculation
CREATE TABLE IF NOT EXISTS provisioning_calculation (
    provisioning_calculation_id  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    provisioning_case_id         UUID NOT NULL,
    risk_assessment_id           UUID NOT NULL,
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

-- TABLE: provisioning_journal
CREATE TABLE IF NOT EXISTS provisioning_journal (
    provisioning_journal_id     UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    provisioning_calculation_id UUID NOT NULL,
    accounting_journal_entry_id UUID,   -- External ref to Accounting (no FK)
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

-- ========================================================================
-- CREATE CASTS USING "WITH INOUT AS IMPLICIT" FOR ALL ENUM TYPES
-- ========================================================================

-- stage_code
CREATE CAST (varchar AS stage_code)
    WITH INOUT
    AS IMPLICIT;

-- risk_grade
CREATE CAST (varchar AS risk_grade)
    WITH INOUT
    AS IMPLICIT;

-- provisioning_status
CREATE CAST (varchar AS provisioning_status)
    WITH INOUT
    AS IMPLICIT;

-- scenario_code
CREATE CAST (varchar AS scenario_code)
    WITH INOUT
    AS IMPLICIT;

-- calc_method
CREATE CAST (varchar AS calc_method)
    WITH INOUT
    AS IMPLICIT;