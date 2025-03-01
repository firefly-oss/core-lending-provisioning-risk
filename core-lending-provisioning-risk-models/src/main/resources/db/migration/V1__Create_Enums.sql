-- V1 - CREATE ENUMS FOR PROVISIONING & RISK

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
