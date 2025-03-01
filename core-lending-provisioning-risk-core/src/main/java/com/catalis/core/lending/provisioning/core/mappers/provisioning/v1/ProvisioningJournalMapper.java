package com.catalis.core.lending.provisioning.core.mappers.provisioning.v1;

import com.catalis.core.lending.provisioning.interfaces.dtos.provisioning.v1.ProvisioningJournalDTO;
import com.catalis.core.lending.provisioning.models.entities.provisioning.v1.ProvisioningJournal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProvisioningJournalMapper {
    ProvisioningJournalDTO toDTO(ProvisioningJournal entity);
    ProvisioningJournal toEntity(ProvisioningJournalDTO dto);
}
