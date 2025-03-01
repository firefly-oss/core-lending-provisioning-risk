package com.catalis.core.lending.provisioning.core.services.provisioning.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.lending.provisioning.core.mappers.provisioning.v1.ProvisioningCalculationMapper;
import com.catalis.core.lending.provisioning.interfaces.dtos.provisioning.v1.ProvisioningCalculationDTO;
import com.catalis.core.lending.provisioning.models.entities.provisioning.v1.ProvisioningCalculation;
import com.catalis.core.lending.provisioning.models.repositories.provisioning.v1.ProvisioningCalculationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@Transactional
public class ProvisioningCalculationServiceImpl implements ProvisioningCalculationService {

    @Autowired
    private ProvisioningCalculationRepository repository;

    @Autowired
    private ProvisioningCalculationMapper mapper;

    @Override
    public Mono<PaginationResponse<ProvisioningCalculationDTO>> findAll(Long provisioningCaseId, FilterRequest<ProvisioningCalculationDTO> filterRequest) {
        filterRequest.getFilters().setProvisioningCaseId(provisioningCaseId);
        return FilterUtils.createFilter(
                ProvisioningCalculation.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<ProvisioningCalculationDTO> create(Long provisioningCaseId, ProvisioningCalculationDTO dto) {
        dto.setProvisioningCaseId(provisioningCaseId);
        ProvisioningCalculation entity = mapper.toEntity(dto);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProvisioningCalculationDTO> getById(Long provisioningCaseId, Long provisioningCalculationId) {
        return repository.findById(provisioningCalculationId)
                .filter(entity -> entity.getProvisioningCaseId().equals(provisioningCaseId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProvisioningCalculationDTO> update(Long provisioningCaseId, Long provisioningCalculationId, ProvisioningCalculationDTO dto) {
        return repository.findById(provisioningCalculationId)
                .filter(entity -> entity.getProvisioningCaseId().equals(provisioningCaseId))
                .flatMap(entity -> {
                    entity.setFinalEcl(dto.getFinalEcl());
                    entity.setCalcMethod(dto.getCalcMethod());
                    entity.setCalcTimestamp(dto.getCalcTimestamp());
                    entity.setNotes(dto.getNotes());
                    entity.setUpdatedAt(LocalDateTime.now());
                    return repository.save(entity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(Long provisioningCaseId, Long provisioningCalculationId) {
        return repository.findById(provisioningCalculationId)
                .filter(entity -> entity.getProvisioningCaseId().equals(provisioningCaseId))
                .flatMap(repository::delete);
    }
}