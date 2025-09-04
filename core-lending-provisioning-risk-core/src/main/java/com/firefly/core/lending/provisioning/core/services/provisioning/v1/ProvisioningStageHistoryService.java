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
import com.firefly.core.lending.provisioning.interfaces.dtos.provisioning.v1.ProvisioningStageHistoryDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProvisioningStageHistoryService {

    /**
     * Retrieves a paginated list of ProvisioningStageHistoryDTO objects associated with a specific provisioning case
     * based on the provided filtering criteria.
     *
     * @param provisioningCaseId the unique identifier of the provisioning case to retrieve records for
     * @param filterRequest an object containing filtering and pagination criteria for the query
     * @return a Mono that emits a PaginationResponse containing the filtered and paginated list of ProvisioningStageHistoryDTO objects
     */
    Mono<PaginationResponse<ProvisioningStageHistoryDTO>> findAll(UUID provisioningCaseId,
                                                                  FilterRequest<ProvisioningStageHistoryDTO> filterRequest);

    /**
     * Creates a new ProvisioningStageHistory record linked to the specified provisioning case.
     *
     * @param provisioningCaseId the unique identifier of the provisioning case to which the record is associated
     * @param dto the {@link ProvisioningStageHistoryDTO} containing the data for the new stage history entry
     * @return a {@link Mono} emitting the created {@link ProvisioningStageHistoryDTO}
     */
    Mono<ProvisioningStageHistoryDTO> create(UUID provisioningCaseId, ProvisioningStageHistoryDTO dto);

    /**
     * Retrieves the provisioning stage history entry for the given provisioning case and stage history IDs.
     *
     * @param provisioningCaseId the unique identifier of the provisioning case
     * @param provisioningStageHistoryId the unique identifier of the provisioning stage history
     * @return a Mono emitting the ProvisioningStageHistoryDTO if found, or empty if not found
     */
    Mono<ProvisioningStageHistoryDTO> getById(UUID provisioningCaseId, UUID provisioningStageHistoryId);

    /**
     * Updates an existing provisioning stage history record for a specific provisioning case.
     *
     * @param provisioningCaseId the ID of the provisioning case the history record belongs to
     * @param provisioningStageHistoryId the ID of the provisioning stage history record to update
     * @param dto the data transfer object containing the updated details of the provisioning stage history
     * @return a Mono emitting the updated ProvisioningStageHistoryDTO
     */
    Mono<ProvisioningStageHistoryDTO> update(UUID provisioningCaseId, UUID provisioningStageHistoryId, ProvisioningStageHistoryDTO dto);

    /**
     * Deletes a provisioning stage history record associated with the specified provisioning case ID and provisioning stage history ID.
     *
     * @param provisioningCaseId the unique identifier of the provisioning case to which the stage history belongs
     * @param provisioningStageHistoryId the unique identifier of the provisioning stage history to delete
     * @return a Mono that completes when the deletion process is successful
     */
    Mono<Void> delete(UUID provisioningCaseId, UUID provisioningStageHistoryId);
}
