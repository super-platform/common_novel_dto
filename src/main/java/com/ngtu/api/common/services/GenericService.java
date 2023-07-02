package com.ngtu.api.common.services;

import com.ngtu.api.common.entities.BaseEntity;
import com.ngtu.api.common.dto.QueryBuilder;

import java.util.List;

public interface GenericService<T extends BaseEntity, ID> {
    T create(T t);
    Boolean delete(ID id);
    List<T> query(QueryBuilder queryBuilder);
}
