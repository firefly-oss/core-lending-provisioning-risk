package com.catalis.core.lending.provisioning.core.services.provisioning.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.filters.FilterUtils;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.lending.provisioning.core.mappers.provisioning.v1.ProvisioningStageHistoryMapper;
import com.catalis.core.lending.provisioning.interfaces.dtos.provisioning.v1.ProvisioningStageHistoryDTO;
import com.catalis.core.lending.provisioning.models.entities.provisioning.v1.ProvisioningStageHistory;
import com.catalis.core.lending.provisioning.models.repositories.provisioning.v1.ProvisioningStageHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProvisioningStageHistoryServiceImpl implements ProvisioningStageHistoryService {

    @Autowired
    private ProvisioningStageHistoryRepository repository;

    @Autowired
    private ProvisioningStageHistoryMapper mapper;

    @Override
    public Mono<PaginationResponse<ProvisioningStageHistoryDTO>> findAll(Long provisioningCaseId, FilterRequest<ProvisioningStageHistoryDTO> filterRequest) {
        filterRequest.getFilters().setProvisioningCaseId(provisioningCaseId);
        return FilterUtils.createFilter(
                ProvisioningStageHistory.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<ProvisioningStageHistoryDTO> create(Long provisioningCaseId, ProvisioningStageHistoryDTO dto) {
        dto.setProvisioningCaseId(provisioningCaseId);
        ProvisioningStageHistory entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProvisioningStageHistoryDTO> getById(Long provisioningCaseId, Long provisioningStageHistoryId) {
        return repository.findById(provisioningStageHistoryId)
                .filter(entity -> entity.getProvisioningCaseId().equals(provisioningCaseId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProvisioningStageHistoryDTO> update(Long provisioningCaseId, Long provisioningStageHistoryId, ProvisioningStageHistoryDTO dto) {
        return repository.findById(provisioningStageHistoryId)
                .filter(entity -> entity.getProvisioningCaseId().equals(provisioningCaseId))
                .flatMap(entity -> {
                    dto.setProvisioningStageHistoryId(provisioningStageHistoryId);
                    dto.setProvisioningCaseId(provisioningCaseId);
                    ProvisioningStageHistory updatedEntity = mapper.toEntity(dto);
                    return repository.save(updatedEntity);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> delete(Long provisioningCaseId, Long provisioningStageHistoryId) {
        return repository.findById(provisioningStageHistoryId)
                .filter(entity -> entity.getProvisioningCaseId().equals(provisioningCaseId))
                .flatMap(repository::delete);
    }
}