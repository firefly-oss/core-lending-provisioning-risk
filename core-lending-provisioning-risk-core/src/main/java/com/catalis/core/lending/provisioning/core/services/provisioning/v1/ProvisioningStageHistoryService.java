package com.catalis.core.lending.provisioning.core.services.provisioning.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.lending.provisioning.interfaces.dtos.provisioning.v1.ProvisioningStageHistoryDTO;
import reactor.core.publisher.Mono;

public interface ProvisioningStageHistoryService {

    /**
     * Retrieves a paginated list of ProvisioningStageHistoryDTO objects associated with a specific provisioning case
     * based on the provided filtering criteria.
     *
     * @param provisioningCaseId the unique identifier of the provisioning case to retrieve records for
     * @param filterRequest an object containing filtering and pagination criteria for the query
     * @return a Mono that emits a PaginationResponse containing the filtered and paginated list of ProvisioningStageHistoryDTO objects
     */
    Mono<PaginationResponse<ProvisioningStageHistoryDTO>> findAll(Long provisioningCaseId,
                                                                  FilterRequest<ProvisioningStageHistoryDTO> filterRequest);

    /**
     * Creates a new ProvisioningStageHistory record linked to the specified provisioning case.
     *
     * @param provisioningCaseId the unique identifier of the provisioning case to which the record is associated
     * @param dto the {@link ProvisioningStageHistoryDTO} containing the data for the new stage history entry
     * @return a {@link Mono} emitting the created {@link ProvisioningStageHistoryDTO}
     */
    Mono<ProvisioningStageHistoryDTO> create(Long provisioningCaseId, ProvisioningStageHistoryDTO dto);

    /**
     * Retrieves the provisioning stage history entry for the given provisioning case and stage history IDs.
     *
     * @param provisioningCaseId the unique identifier of the provisioning case
     * @param provisioningStageHistoryId the unique identifier of the provisioning stage history
     * @return a Mono emitting the ProvisioningStageHistoryDTO if found, or empty if not found
     */
    Mono<ProvisioningStageHistoryDTO> getById(Long provisioningCaseId, Long provisioningStageHistoryId);

    /**
     * Updates an existing provisioning stage history record for a specific provisioning case.
     *
     * @param provisioningCaseId the ID of the provisioning case the history record belongs to
     * @param provisioningStageHistoryId the ID of the provisioning stage history record to update
     * @param dto the data transfer object containing the updated details of the provisioning stage history
     * @return a Mono emitting the updated ProvisioningStageHistoryDTO
     */
    Mono<ProvisioningStageHistoryDTO> update(Long provisioningCaseId, Long provisioningStageHistoryId, ProvisioningStageHistoryDTO dto);

    /**
     * Deletes a provisioning stage history record associated with the specified provisioning case ID and provisioning stage history ID.
     *
     * @param provisioningCaseId the unique identifier of the provisioning case to which the stage history belongs
     * @param provisioningStageHistoryId the unique identifier of the provisioning stage history to delete
     * @return a Mono that completes when the deletion process is successful
     */
    Mono<Void> delete(Long provisioningCaseId, Long provisioningStageHistoryId);
}
