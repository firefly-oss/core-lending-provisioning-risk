package com.catalis.core.lending.provisioning.core.mappers.provisioning.v1;

import com.catalis.core.lending.provisioning.interfaces.dtos.provisioning.v1.ProvisioningStageHistoryDTO;
import com.catalis.core.lending.provisioning.models.entities.provisioning.v1.ProvisioningStageHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProvisioningStageHistoryMapper {
    ProvisioningStageHistoryDTO toDTO(ProvisioningStageHistory entity);
    ProvisioningStageHistory toEntity(ProvisioningStageHistoryDTO dto);
}
