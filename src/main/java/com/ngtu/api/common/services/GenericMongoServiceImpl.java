package com.ngtu.api.common.services;


import com.ngtu.api.common.entities.BaseEntity;
import com.ngtu.api.common.dto.QueryBuilder;
import com.ngtu.api.common.repositories.GenericRepository;
import com.ngtu.api.common.utils.PredicateFactoryBuilder;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public class GenericMongoServiceImpl<T extends BaseEntity, ID> extends PredicateFactoryBuilder implements GenericService<T, ID> {
    private GenericRepository<T, ID> genericRepository;
    private EntityPath query;
    public GenericMongoServiceImpl(GenericRepository<T, ID> genericRepository, EntityPath query) {
        this.genericRepository = genericRepository;
        this.query = query;
    }


    @Override
    public T create(T t) {
        return genericRepository.save(t);
    }

    @Override
    public Boolean delete(ID id) {
        Optional<T> optionalT = genericRepository.findById(id);
        if (optionalT.isPresent()){
            genericRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<T> query(QueryBuilder queryBuilder){
        Predicate predicate = getPredicate(queryBuilder, query);
        Page<T> pageData = genericRepository.findAll(predicate, PageRequest.of(0, 20));
        return pageData.stream().toList();
    }
}
