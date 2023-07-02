package com.ngtu.api.common.repositories;

import com.ngtu.api.common.entities.BaseEntity;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<T extends BaseEntity, ID> extends CrudRepository<T,ID>, QuerydslPredicateExecutor<T> {
}
