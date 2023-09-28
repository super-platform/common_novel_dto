package com.platform.common.service;

import com.platform.common.dto.BaseDTO;
import com.platform.common.dto.request.PageableRequest;
import com.platform.common.dto.response.PageResponse;
import com.platform.common.entities.BaseEntity;

public interface BaseCrudService<T extends BaseEntity, DTO extends BaseDTO, C> {

    DTO findById(Long id);
    DTO save(C createDTO);
    PageResponse<DTO> paginate(PageableRequest pageableRequest);
}
