package com.ngtu.api.common.utils;
import com.ngtu.api.common.dto.QueryBuilder;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;

public class PredicateFactoryBuilder {

    public Predicate getPredicate(QueryBuilder queryBuilder, EntityPath query){
        BooleanBuilder predicate = new BooleanBuilder();

        if("EQ".equals(queryBuilder.getOperator())){
            StringPath column = Expressions.stringPath(query, queryBuilder.getKey());
            return column.eq((String) queryBuilder.getValues().get(0));
        } else if("AND".equals(queryBuilder.getOperator())){
            for (QueryBuilder sub: queryBuilder.getCombines()){
                predicate.and(getPredicate(sub, query));
            }
            return predicate;
        }

        return predicate;
    }
}
