package com.firefly.core.lending.provisioning.core.services.provisioning.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.provisioning.core.mappers.provisioning.v1.ProvisioningJournalMapper;
import com.firefly.core.lending.provisioning.interfaces.dtos.provisioning.v1.ProvisioningJournalDTO;
import com.firefly.core.lending.provisioning.models.entities.provisioning.v1.ProvisioningJournal;
import com.firefly.core.lending.provisioning.models.repositories.provisioning.v1.ProvisioningJournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class ProvisioningJournalServiceImpl implements ProvisioningJournalService {

    @Autowired
    private ProvisioningJournalRepository repository;

    @Autowired
    private ProvisioningJournalMapper mapper;

    @Override
    public Mono<PaginationResponse<ProvisioningJournalDTO>> findAll(UUID provisioningCaseId, UUID provisioningCalculationId, FilterRequest<ProvisioningJournalDTO> filterRequest) {
        filterRequest.getFilters().setProvisioningCalculationId(provisioningCalculationId);
        return FilterUtils.createFilter(
                ProvisioningJournal.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<ProvisioningJournalDTO> create(UUID provisioningCaseId, UUID provisioningCalculationId, ProvisioningJournalDTO dto) {
        dto.setProvisioningCalculationId(provisioningCalculationId);
        ProvisioningJournal entity = mapper.toEntity(dto);
        return Mono.just(entity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProvisioningJournalDTO> getById(UUID provisioningCaseId, UUID provisioningCalculationId, UUID provisioningJournalId) {
        return repository.findById(provisioningJournalId)
                .filter(entity -> provisioningCalculationId.equals(entity.getProvisioningCalculationId()))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProvisioningJournalDTO> update(UUID provisioningCaseId, UUID provisioningCalculationId, UUID provisioningJournalId, ProvisioningJournalDTO dto) {
        return repository.findById(provisioningJournalId)
                .filter(entity -> provisioningCalculationId.equals(entity.getProvisioningCalculationId()))
                .flatMap(existingEntity -> {
                    ProvisioningJournal updatedEntity = mapper.toEntity(dto);
                    updatedEntity.setProvisioningJournalId(provisioningJournalId);
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID provisioningCaseId, UUID provisioningCalculationId, UUID provisioningJournalId) {
        return repository.findById(provisioningJournalId)
                .filter(entity -> provisioningCalculationId.equals(entity.getProvisioningCalculationId()))
                .flatMap(repository::delete);
    }
}