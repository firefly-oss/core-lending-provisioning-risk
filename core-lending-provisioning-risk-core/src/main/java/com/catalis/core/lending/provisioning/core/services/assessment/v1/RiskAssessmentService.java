package com.catalis.core.lending.provisioning.core.services.assessment.v1;

import com.catalis.common.core.filters.FilterRequest;
import com.catalis.common.core.queries.PaginationResponse;
import com.catalis.core.lending.provisioning.interfaces.dtos.assessment.v1.RiskAssessmentDTO;
import reactor.core.publisher.Mono;

public interface RiskAssessmentService {

    /**
     * Retrieves a paginated list of RiskAssessmentDTO objects associated with a specific provisioning case.
     *
     * @param provisioningCaseId the unique identifier of the provisioning case to retrieve risk assessment records for
     * @param filterRequest an object containing filtering and pagination criteria for the query
     * @return a Mono containing a PaginationResponse of RiskAssessmentDTO representing the filtered and paginated list of risk assessment records
     */
    Mono<PaginationResponse<RiskAssessmentDTO>> findAll(Long provisioningCaseId,
                                                        FilterRequest<RiskAssessmentDTO> filterRequest);

    /**
     * Creates a new risk assessment associated with a specific provisioning case.
     *
     * @param provisioningCaseId the unique identifier of the provisioning case to which the risk assessment is linked
     * @param dto the RiskAssessmentDTO containing the details of the risk assessment to be created
     * @return a Mono emitting the created RiskAssessmentDTO
     */
    Mono<RiskAssessmentDTO> create(Long provisioningCaseId, RiskAssessmentDTO dto);

    /**
     * Retrieves a specific risk assessment associated with a given provisioning case.
     *
     * @param provisioningCaseId the unique identifier of the provisioning case the risk assessment belongs to
     * @param riskAssessmentId the unique identifier of the risk assessment to retrieve
     * @return a Mono emitting the RiskAssessmentDTO if found, or empty if not found
     */
    Mono<RiskAssessmentDTO> getById(Long provisioningCaseId, Long riskAssessmentId);

    /**
     * Updates an existing risk assessment for a specified provisioning case with the provided details.
     *
     * @param provisioningCaseId the unique identifier of the provisioning case to which the risk assessment belongs
     * @param riskAssessmentId the unique identifier of the risk assessment to be updated
     * @param dto the data transfer object containing the updated details of the risk assessment
     * @return a Mono emitting the updated RiskAssessmentDTO
     */
    Mono<RiskAssessmentDTO> update(Long provisioningCaseId, Long riskAssessmentId, RiskAssessmentDTO dto);

    /**
     * Deletes a risk assessment associated with the specified provisioning case
     * and risk assessment identifiers.
     *
     * @param provisioningCaseId the unique identifier of the provisioning case
     * @param riskAssessmentId the unique identifier of the risk assessment to delete
     * @return a Mono that completes when the deletion process is successful
     */
    Mono<Void> delete(Long provisioningCaseId, Long riskAssessmentId);
}
