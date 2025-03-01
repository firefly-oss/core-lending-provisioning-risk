-- V3 - CASTS USING "WITH INOUT AS IMPLICIT" FOR ALL ENUM TYPES

-------------------------
-- stage_code
-------------------------
CREATE CAST (varchar AS stage_code)
    WITH INOUT
    AS IMPLICIT;

-------------------------
-- risk_grade
-------------------------
CREATE CAST (varchar AS risk_grade)
    WITH INOUT
    AS IMPLICIT;

-------------------------
-- provisioning_status
-------------------------
CREATE CAST (varchar AS provisioning_status)
    WITH INOUT
    AS IMPLICIT;

-------------------------
-- scenario_code
-------------------------
CREATE CAST (varchar AS scenario_code)
    WITH INOUT
    AS IMPLICIT;

-------------------------
-- calc_method
-------------------------
CREATE CAST (varchar AS calc_method)
    WITH INOUT
    AS IMPLICIT;
