package com.firefly.core.lending.provisioning.core.services.provisioning.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.provisioning.interfaces.dtos.provisioning.v1.ProvisioningJournalDTO;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProvisioningJournalService {

    /**
     * Retrieves a paginated list of ProvisioningJournalDTO objects associated with a specific provisioning case
     * and provisioning calculation based on the provided filter criteria.
     *
     * @param provisioningCaseId the unique identifier of the provisioning case to which the provisioning journals are linked
     * @param provisioningCalculationId the unique identifier of the provisioning calculation to which the provisioning journals are linked
     * @param filterRequest an object containing filtering and pagination criteria for the query
     * @return a Mono containing a PaginationResponse of ProvisioningJournalDTO representing the filtered and paginated list of provisioning journals
     */
    Mono<PaginationResponse<ProvisioningJournalDTO>> findAll(UUID provisioningCaseId, UUID provisioningCalculationId,
                                                             FilterRequest<ProvisioningJournalDTO> filterRequest);

    /**
     * Creates a new provisioning journal entry associated with a specific provisioning case
     * and provisioning calculation.
     *
     * @param provisioningCaseId the unique identifier of the provisioning case to which the journal entry is linked
     * @param provisioningCalculationId the unique identifier of the provisioning calculation to which the journal entry is related
     * @param dto the ProvisioningJournalDTO containing the details of the journal entry to be created
     * @return a Mono emitting the created ProvisioningJournalDTO
     */
    Mono<ProvisioningJournalDTO> create(UUID provisioningCaseId, UUID provisioningCalculationId, ProvisioningJournalDTO dto);

    /**
     * Retrieves a specific ProvisioningJournalDTO by its unique identifiers.
     *
     * @param provisioningCaseId the unique identifier of the provisioning case the journal entry belongs to
     * @param provisioningCalculationId the unique identifier of the provisioning calculation associated with the journal entry
     * @param provisioningJournalId the unique identifier of the provisioning journal entry to retrieve
     * @return a Mono emitting the ProvisioningJournalDTO if found, or empty if not found
     */
    Mono<ProvisioningJournalDTO> getById(UUID provisioningCaseId, UUID provisioningCalculationId, UUID provisioningJournalId);

    /**
     * Updates an existing provisioning journal entry with the specified details.
     *
     * @param provisioningCaseId the unique identifier of the provisioning case to which the journal entry belongs
     * @param provisioningCalculationId the unique identifier of the provisioning calculation associated with the journal entry
     * @param provisioningJournalId the unique identifier of the provisioning journal entry to update
     * @param dto the data transfer object containing the updated details of the provisioning journal entry
     * @return a Mono emitting the updated ProvisioningJournalDTO
     */
    Mono<ProvisioningJournalDTO> update(UUID provisioningCaseId, UUID provisioningCalculationId, UUID provisioningJournalId,
                                        ProvisioningJournalDTO dto);

    /**
     * Deletes a provisioning journal entry associated with the specified provisioning case,
     * provisioning calculation, and journal identifiers.
     *
     * @param provisioningCaseId the unique identifier of the provisioning case
     * @param provisioningCalculationId the unique identifier of the provisioning calculation
     * @param provisioningJournalId the unique identifier of the provisioning journal entry to delete
     * @return a Mono that completes when the deletion process is successful
     */
    Mono<Void> delete(UUID provisioningCaseId, UUID provisioningCalculationId, UUID provisioningJournalId);
}
