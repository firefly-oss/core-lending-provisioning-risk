package com.firefly.core.lending.provisioning.core.services.provisioning.v1;

import com.firefly.common.core.filters.FilterRequest;
import com.firefly.common.core.queries.PaginationResponse;
import com.firefly.core.lending.provisioning.interfaces.dtos.provisioning.v1.ProvisioningCalculationDTO;
import reactor.core.publisher.Mono;

public interface ProvisioningCalculationService {

    /**
     * Retrieves a paginated list of ProvisioningCalculationDTO objects for a specific provisioning case
     * based on the provided filtering and pagination criteria.
     *
     * @param provisioningCaseId the unique identifier of the provisioning case for which the calculations are retrieved
     * @param filterRequest an object containing the filtering and pagination criteria for the query
     * @return a Mono containing a PaginationResponse of ProvisioningCalculationDTO representing the filtered and paginated list of calculations
     */
    Mono<PaginationResponse<ProvisioningCalculationDTO>> findAll(Long provisioningCaseId,
                                                                 FilterRequest<ProvisioningCalculationDTO> filterRequest);

    /**
     * Creates a new provisioning calculation for a specific provisioning case.
     *
     * @param provisioningCaseId the unique identifier of the provisioning case to which the calculation is linked
     * @param dto the ProvisioningCalculationDTO containing the details of the calculation to be created
     * @return a Mono emitting the created ProvisioningCalculationDTO
     */
    Mono<ProvisioningCalculationDTO> create(Long provisioningCaseId, ProvisioningCalculationDTO dto);

    /**
     * Retrieves a specific provisioning calculation associated with a given provisioning case.
     *
     * @param provisioningCaseId the unique identifier of the provisioning case the calculation belongs to
     * @param provisioningCalculationId the unique identifier of the provisioning calculation to retrieve
     * @return a Mono emitting the ProvisioningCalculationDTO if found, or empty if not found
     */
    Mono<ProvisioningCalculationDTO> getById(Long provisioningCaseId, Long provisioningCalculationId);

    /**
     * Updates an existing provisioning calculation associated with a specific provisioning case.
     *
     * @param provisioningCaseId the unique identifier of the provisioning case to which the provisioning calculation belongs
     * @param provisioningCalculationId the unique identifier of the provisioning calculation to be updated
     * @param dto the data transfer object containing the updated details of the provisioning calculation
     * @return a Mono emitting the updated ProvisioningCalculationDTO
     */
    Mono<ProvisioningCalculationDTO> update(Long provisioningCaseId, Long provisioningCalculationId,
                                            ProvisioningCalculationDTO dto);

    /**
     * Deletes a provisioning calculation associated with the specified provisioning case and calculation identifiers.
     *
     * @param provisioningCaseId the unique identifier of the provisioning case
     * @param provisioningCalculationId the unique identifier of the provisioning calculation to be deleted
     * @return a Mono that completes when the deletion operation is successful
     */
    Mono<Void> delete(Long provisioningCaseId, Long provisioningCalculationId);
}