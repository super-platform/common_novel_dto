package com.platform.common.service.impl;

import com.platform.common.dto.response.BaseDTO;
import com.platform.common.entities.BaseEntity;
import com.platform.common.mapper.BaseMapper;
import com.platform.common.repository.BaseRepository;
import com.platform.common.service.BaseCrudService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.ParameterizedType;
import java.util.Optional;

@Slf4j
@Transactional
public abstract class BaseCrudServiceImpl<T extends BaseEntity, DTO extends BaseDTO, C> implements BaseCrudService<T, DTO, C> {

    protected BaseRepository<T> repository;
    protected BaseMapper<T, DTO, C> mapper;

    private Class<T> type;

    public BaseCrudServiceImpl(BaseRepository<T> repository, BaseMapper<T, DTO, C> mapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.type = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public DTO findById(Long id) {
        log.info("Request find {} by id : {} ", type.getSimpleName(), id);

        Optional<T> optionalT = repository.findById(id);
        if(optionalT.isEmpty()){
            return null;
        }

        return mapper.entityToDTO(optionalT.get());

    }

    @Override
    public DTO save(C createDTO) {
        log.info("Request create {} : {} ", type.getSimpleName(), createDTO);

        // Abstract execute sql relational logic
        T savedEntity = executeSqlRelationalLogic(createDTO);

        // Mapping return DTO
        DTO result = mapper.entityToDTO(savedEntity);

        log.info("Create {} successfully with new id: {} ", type.getName(), result.getId());
        // Return
        return result;
    }

    protected abstract T executeSqlRelationalLogic(C createDTO);
}
