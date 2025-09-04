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
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.provisioning.interfaces.dtos.provisioning.v1.ProvisioningCaseDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProvisioningCaseService {
    /**
     * Retrieves a paginated list of ProvisioningCaseDTO objects based on the provided filter criteria.
     *
     * @param filterRequest the filter criteria used to retrieve the data, encapsulated in a FilterRequest object
     * @return a Mono wrapper containing a PaginationResponse of ProvisioningCaseDTO representing the result set
     */
    Mono<PaginationResponse<ProvisioningCaseDTO>> findAll(FilterRequest<ProvisioningCaseDTO> filterRequest);

    /**
     * Creates a new provisioning case.
     *
     * @param dto the {@link ProvisioningCaseDTO} containing the data for the new provisioning case
     * @return a {@link Mono} emitting the created {@link ProvisioningCaseDTO}
     */
    Mono<ProvisioningCaseDTO> create(ProvisioningCaseDTO dto);

    /**
     * Retrieves a provisioning case by its unique identifier.
     *
     * @param provisioningCaseId the unique identifier of the provisioning case to retrieve
     * @return a {@code Mono} emitting the {@code ProvisioningCaseDTO} if found, or empty if not found
     */
    Mono<ProvisioningCaseDTO> getById(UUID provisioningCaseId);

    /**
     * Updates an existing provisioning case with the given details.
     *
     * @param provisioningCaseId the ID of the provisioning case to be updated
     * @param dto the data transfer object containing the updated details of the provisioning case
     * @return a Mono emitting the updated ProvisioningCaseDTO
     */
    Mono<ProvisioningCaseDTO> update(UUID provisioningCaseId, ProvisioningCaseDTO dto);

    /**
     * Deletes the provisioning case identified by the provided ID.
     *
     * @param provisioningCaseId the ID of the provisioning case to delete
     * @return a Mono that completes when the deletion is successful
     */
    Mono<Void> delete(UUID provisioningCaseId);
}