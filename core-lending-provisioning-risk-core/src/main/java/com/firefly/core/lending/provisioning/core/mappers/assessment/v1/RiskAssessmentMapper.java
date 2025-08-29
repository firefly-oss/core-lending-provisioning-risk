package com.firefly.core.lending.provisioning.core.mappers.assessment.v1;

import com.firefly.core.lending.provisioning.interfaces.dtos.assessment.v1.RiskAssessmentDTO;
import com.firefly.core.lending.provisioning.models.entities.assessment.v1.RiskAssessment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RiskAssessmentMapper {
    RiskAssessmentDTO toDTO(RiskAssessment entity);
    RiskAssessment toEntity(RiskAssessmentDTO dto);
}