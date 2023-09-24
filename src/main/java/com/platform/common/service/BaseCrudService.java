package com.platform.common.service;

import com.platform.common.dto.response.BaseDTO;
import com.platform.common.entities.BaseEntity;

public interface BaseCrudService<T extends BaseEntity, DTO extends BaseDTO, C> {

    DTO findById(Long id);
    DTO save(C createDTO);
}
