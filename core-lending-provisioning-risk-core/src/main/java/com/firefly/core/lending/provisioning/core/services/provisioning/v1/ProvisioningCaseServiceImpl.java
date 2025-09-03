package com.firefly.core.lending.provisioning.core.services.provisioning.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.provisioning.core.mappers.provisioning.v1.ProvisioningCaseMapper;
import com.firefly.core.lending.provisioning.interfaces.dtos.provisioning.v1.ProvisioningCaseDTO;
import com.firefly.core.lending.provisioning.models.entities.provisioning.v1.ProvisioningCase;
import com.firefly.core.lending.provisioning.models.repositories.provisioning.v1.ProvisioningCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class ProvisioningCaseServiceImpl implements ProvisioningCaseService {

    @Autowired
    private ProvisioningCaseRepository repository;

    @Autowired
    private ProvisioningCaseMapper mapper;

    @Override
    public Mono<PaginationResponse<ProvisioningCaseDTO>> findAll(FilterRequest<ProvisioningCaseDTO> filterRequest) {
        return FilterUtils.createFilter(
                ProvisioningCase.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<ProvisioningCaseDTO> create(ProvisioningCaseDTO dto) {
        ProvisioningCase entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProvisioningCaseDTO> getById(UUID provisioningCaseId) {
        return repository.findById(provisioningCaseId)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProvisioningCaseDTO> update(UUID provisioningCaseId, ProvisioningCaseDTO dto) {
        return repository.findById(provisioningCaseId)
                .flatMap(existing -> {
                    mapper.toEntity(dto).setProvisioningCaseId(provisioningCaseId);
                    ProvisioningCase updatedEntity = mapper.toEntity(dto);
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(UUID provisioningCaseId) {
        return repository.findById(provisioningCaseId)
                .flatMap(repository::delete);
    }
}
