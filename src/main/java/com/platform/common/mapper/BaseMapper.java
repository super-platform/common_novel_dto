package com.platform.common.mapper;

import com.platform.common.dto.BaseDTO;
import com.platform.common.entities.BaseEntity;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 *
 * @param <T> must be a concrete entity class
 * @param <DTO> must be a concrete dto class
 * @param <C> must be a concrete create dto class
 */
public interface BaseMapper<T extends BaseEntity, DTO extends BaseDTO, C> {
    T createDTOToEntity(C createDto);
    T DTOToEntity(DTO dto);
    DTO entityToDTO(T entity);

    @Mapping(target = "id", ignore = true)
    T createDTOToEntity(@MappingTarget T entity, C createDTO);
}
