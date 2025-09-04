/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.lending.provisioning.core.services.provisioning.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.filters.FilterUtils;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.provisioning.core.mappers.provisioning.v1.ProvisioningStageHistoryMapper;
import com.firefly.core.lending.provisioning.interfaces.dtos.provisioning.v1.ProvisioningStageHistoryDTO;
import com.firefly.core.lending.provisioning.models.entities.provisioning.v1.ProvisioningStageHistory;
import com.firefly.core.lending.provisioning.models.repositories.provisioning.v1.ProvisioningStageHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
public class ProvisioningStageHistoryServiceImpl implements ProvisioningStageHistoryService {

    @Autowired
    private ProvisioningStageHistoryRepository repository;

    @Autowired
    private ProvisioningStageHistoryMapper mapper;

    @Override
    public Mono<PaginationResponse<ProvisioningStageHistoryDTO>> findAll(UUID provisioningCaseId, FilterRequest<ProvisioningStageHistoryDTO> filterRequest) {
        filterRequest.getFilters().setProvisioningCaseId(provisioningCaseId);
        return FilterUtils.createFilter(
                ProvisioningStageHistory.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<ProvisioningStageHistoryDTO> create(UUID provisioningCaseId, ProvisioningStageHistoryDTO dto) {
        dto.setProvisioningCaseId(provisioningCaseId);
        ProvisioningStageHistory entity = mapper.toEntity(dto);
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProvisioningStageHistoryDTO> getById(UUID provisioningCaseId, UUID provisioningStageHistoryId) {
        return repository.findById(provisioningStageHistoryId)
                .filter(entity -> entity.getProvisioningCaseId().equals(provisioningCaseId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProvisioningStageHistoryDTO> update(UUID provisioningCaseId, UUID provisioningStageHistoryId, ProvisioningStageHistoryDTO dto) {
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
    public Mono<Void> delete(UUID provisioningCaseId, UUID provisioningStageHistoryId) {
        return repository.findById(provisioningStageHistoryId)
                .filter(entity -> entity.getProvisioningCaseId().equals(provisioningCaseId))
                .flatMap(repository::delete);
    }
}