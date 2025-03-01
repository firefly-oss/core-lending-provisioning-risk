package com.catalis.core.lending.provisioning.core.mappers.provisioning.v1;

import com.catalis.core.lending.provisioning.interfaces.dtos.provisioning.v1.ProvisioningCalculationDTO;
import com.catalis.core.lending.provisioning.models.entities.provisioning.v1.ProvisioningCalculation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProvisioningCalculationMapper {
    ProvisioningCalculationDTO toDTO(ProvisioningCalculation entity);
    ProvisioningCalculation toEntity(ProvisioningCalculationDTO dto);
}