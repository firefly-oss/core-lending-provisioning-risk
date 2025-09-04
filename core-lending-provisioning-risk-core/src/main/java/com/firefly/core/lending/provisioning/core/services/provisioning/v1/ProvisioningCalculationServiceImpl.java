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
import com.firefly.core.lending.provisioning.core.mappers.provisioning.v1.ProvisioningCalculationMapper;
import com.firefly.core.lending.provisioning.interfaces.dtos.provisioning.v1.ProvisioningCalculationDTO;
import com.firefly.core.lending.provisioning.models.entities.provisioning.v1.ProvisioningCalculation;
import com.firefly.core.lending.provisioning.models.repositories.provisioning.v1.ProvisioningCalculationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class ProvisioningCalculationServiceImpl implements ProvisioningCalculationService {

    @Autowired
    private ProvisioningCalculationRepository repository;

    @Autowired
    private ProvisioningCalculationMapper mapper;

    @Override
    public Mono<PaginationResponse<ProvisioningCalculationDTO>> findAll(UUID provisioningCaseId, FilterRequest<ProvisioningCalculationDTO> filterRequest) {
        filterRequest.getFilters().setProvisioningCaseId(provisioningCaseId);
        return FilterUtils.createFilter(
                ProvisioningCalculation.class,
                mapper::toDTO
        ).filter(filterRequest);
    }

    @Override
    public Mono<ProvisioningCalculationDTO> create(UUID provisioningCaseId, ProvisioningCalculationDTO dto) {
        dto.setProvisioningCaseId(provisioningCaseId);
        ProvisioningCalculation entity = mapper.toEntity(dto);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return repository.save(entity)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProvisioningCalculationDTO> getById(UUID provisioningCaseId, UUID provisioningCalculationId) {
        return repository.findById(provisioningCalculationId)
                .filter(entity -> entity.getProvisioningCaseId().equals(provisioningCaseId))
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ProvisioningCalculationDTO> update(UUID provisioningCaseId, UUID provisioningCalculationId, ProvisioningCalculationDTO dto) {
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
    public Mono<Void> delete(UUID provisioningCaseId, UUID provisioningCalculationId) {
        return repository.findById(provisioningCalculationId)
                .filter(entity -> entity.getProvisioningCaseId().equals(provisioningCaseId))
                .flatMap(repository::delete);
    }
}