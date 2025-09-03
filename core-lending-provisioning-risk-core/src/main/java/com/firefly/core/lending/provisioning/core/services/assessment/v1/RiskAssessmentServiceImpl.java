package com.firefly.core.lending.provisioning.core.services.assessment.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.provisioning.core.mappers.assessment.v1.RiskAssessmentMapper;
import com.firefly.core.lending.provisioning.interfaces.dtos.assessment.v1.RiskAssessmentDTO;
import com.firefly.core.lending.provisioning.models.entities.assessment.v1.RiskAssessment;
import com.firefly.core.lending.provisioning.models.repositories.assessment.v1.RiskAssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class RiskAssessmentServiceImpl implements RiskAssessmentService {

    @Autowired
    private RiskAssessmentRepository repository;

    @Autowired
    private RiskAssessmentMapper mapper;

    @Override
    public Mono<PaginationResponse<RiskAssessmentDTO>> findAll(UUID provisioningCaseId, FilterRequest<RiskAssessmentDTO> filterRequest) {
        filterRequest.getFilters().setProvisioningCaseId(provisioningCaseId);
        return FilterUtils.createFilter(
                RiskAssessment.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<RiskAssessmentDTO> create(UUID provisioningCaseId, RiskAssessmentDTO dto) {
        dto.setProvisioningCaseId(provisioningCaseId);
        return repository.save(mapper.toEntity(dto))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<RiskAssessmentDTO> getById(UUID provisioningCaseId, UUID riskAssessmentId) {
        return repository.findById(riskAssessmentId)
                .filter(entity -> entity.getProvisioningCaseId().equals(provisioningCaseId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<RiskAssessmentDTO> update(UUID provisioningCaseId, UUID riskAssessmentId, RiskAssessmentDTO dto) {
        return repository.findById(riskAssessmentId)
                .filter(entity -> entity.getProvisioningCaseId().equals(provisioningCaseId))
                .flatMap(existing -> {
                    RiskAssessment updated = mapper.toEntity(dto);
                    updated.setRiskAssessmentId(riskAssessmentId);
                    updated.setProvisioningCaseId(provisioningCaseId);
                    return repository.save(updated);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID provisioningCaseId, UUID riskAssessmentId) {
        return repository.findById(riskAssessmentId)
                .filter(entity -> entity.getProvisioningCaseId().equals(provisioningCaseId))
                .flatMap(repository::delete);
    }
}