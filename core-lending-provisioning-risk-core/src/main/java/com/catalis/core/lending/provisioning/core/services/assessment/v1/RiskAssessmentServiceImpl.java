package com.catalis.core.lending.provisioning.core.services.assessment.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.lending.provisioning.core.mappers.assessment.v1.RiskAssessmentMapper;
import com.catalis.core.lending.provisioning.interfaces.dtos.assessment.v1.RiskAssessmentDTO;
import com.catalis.core.lending.provisioning.models.entities.assessment.v1.RiskAssessment;
import com.catalis.core.lending.provisioning.models.repositories.assessment.v1.RiskAssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class RiskAssessmentServiceImpl implements RiskAssessmentService {

    @Autowired
    private RiskAssessmentRepository repository;

    @Autowired
    private RiskAssessmentMapper mapper;

    @Override
    public Mono<PaginationResponse<RiskAssessmentDTO>> findAll(Long provisioningCaseId, FilterRequest<RiskAssessmentDTO> filterRequest) {
        filterRequest.getFilters().setProvisioningCaseId(provisioningCaseId);
        return FilterUtils.createFilter(
                RiskAssessment.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<RiskAssessmentDTO> create(Long provisioningCaseId, RiskAssessmentDTO dto) {
        dto.setProvisioningCaseId(provisioningCaseId);
        return repository.save(mapper.toEntity(dto))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<RiskAssessmentDTO> getById(Long provisioningCaseId, Long riskAssessmentId) {
        return repository.findById(riskAssessmentId)
                .filter(entity -> entity.getProvisioningCaseId().equals(provisioningCaseId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<RiskAssessmentDTO> update(Long provisioningCaseId, Long riskAssessmentId, RiskAssessmentDTO dto) {
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
    public Mono<Void> delete(Long provisioningCaseId, Long riskAssessmentId) {
        return repository.findById(riskAssessmentId)
                .filter(entity -> entity.getProvisioningCaseId().equals(provisioningCaseId))
                .flatMap(repository::delete);
    }
}