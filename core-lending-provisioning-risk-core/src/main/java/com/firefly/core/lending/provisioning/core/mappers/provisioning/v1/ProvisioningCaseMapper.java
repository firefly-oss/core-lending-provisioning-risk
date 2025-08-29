package com.firefly.core.lending.provisioning.core.mappers.provisioning.v1;

import com.firefly.core.lending.provisioning.interfaces.dtos.provisioning.v1.ProvisioningCaseDTO;
import com.firefly.core.lending.provisioning.models.entities.provisioning.v1.ProvisioningCase;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProvisioningCaseMapper {
    ProvisioningCaseDTO toDTO(ProvisioningCase entity);
    ProvisioningCase toEntity(ProvisioningCaseDTO dto);
}
