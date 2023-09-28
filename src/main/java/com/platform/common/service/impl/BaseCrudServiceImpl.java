package com.platform.common.service.impl;

import com.platform.common.dto.BaseDTO;
import com.platform.common.dto.request.Filter;
import com.platform.common.dto.request.PageableRequest;
import com.platform.common.dto.response.PageResponse;
import com.platform.common.entities.BaseEntity;
import com.platform.common.mapper.BaseMapper;
import com.platform.common.repository.BaseRepository;
import com.platform.common.service.BaseCrudService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Transactional
public abstract class BaseCrudServiceImpl<T extends BaseEntity, QT extends EntityPathBase<T>, DTO extends BaseDTO, C> implements BaseCrudService<T, DTO, C> {

    protected BaseRepository<T> repository;
    protected BaseMapper<T, DTO, C> mapper;

    private Class<T> clazz;
    private PathBuilder<?> path;
    public BaseCrudServiceImpl(BaseRepository<T> repository, BaseMapper<T, DTO, C> mapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.clazz = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        this.path = new PathBuilder<Object>(clazz, clazz.getSimpleName().toLowerCase());
    }

    @Override
    public DTO findById(Long id) {
        log.info("Request find {} by id : {} ", clazz.getSimpleName(), id);

        Optional<T> optionalT = repository.findById(id);
        if(optionalT.isEmpty()){
            return null;
        }

        return mapper.entityToDTO(optionalT.get());

    }

    @Override
    public DTO save(C createDTO) {
        log.info("Request create {} : {} ", clazz.getSimpleName(), createDTO);

        // Abstract execute sql relational logic
        T savedEntity = executeSqlRelationalLogic(createDTO);

        // Mapping return DTO
        DTO result = mapper.entityToDTO(savedEntity);

        log.info("Create {} successfully with new id: {} ", clazz.getName(), result.getId());
        // Return
        return result;
    }

    @Override
    public PageResponse<DTO> paginate(PageableRequest pageableRequest) {
        // Init result
        PageResponse<DTO> result = new PageResponse<>();

        // Build predicate
        Predicate predicate = new BooleanBuilder();;
        if(pageableRequest.getFilter() != null){
            try {
                predicate = handle(pageableRequest.getFilter());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        // exec pagination
        Page<T> page = repository.findAll(predicate, PageRequest.of(pageableRequest.getPage()-1, pageableRequest.getSize(),  Sort.by("id")));

        // mapping result
        result.setPage(pageableRequest.getPage());
        result.setSize(pageableRequest.getSize());
        result.setData(page.getContent().stream().map(entity -> mapper.entityToDTO(entity)).collect(Collectors.toList()));
        result.setTotal(page.getTotalElements());

        // Return
        return result;
    }

    protected abstract T executeSqlRelationalLogic(C createDTO);

    private Predicate createPredicate(Filter filter) throws Exception {
        BooleanBuilder predicate = new BooleanBuilder();


        Class<?> classField = clazz.getDeclaredField(filter.getField()).getType();

        switch (classField.getSimpleName()){
            case "String":
                predicate.and(getPredicateFromString(filter));
                break;
            case "Long":
                predicate.and(getPredicateFromNumber(filter));
                break;
            case "LocalDateTime":
                predicate.and(getPredicateFromDate(filter));
                break;
            default:
                break;
        }

        return predicate;
    }

    private Predicate handle(Filter filter) throws Exception {
        BooleanBuilder predicate = new BooleanBuilder();

        if(filter.getGroup() == null){
            return predicate.and(createPredicate(filter));
        }

        for(Filter subFilter: filter.getRules()){
            if(filter.getGroup() == "AND") {
                predicate.and(handle(subFilter));
            } else {
                predicate.or(handle(subFilter));
            }
        }

        return predicate;
    }

    private Predicate getPredicateFromString(Filter filter){
        BooleanBuilder predicate = new BooleanBuilder();
        StringPath stringPath = path.getString(filter.getField());

        switch (filter.getOperator()){
            case "eq":
                predicate.and(stringPath.eq(filter.getValues().get(0)));
                break;
            case "in":
                predicate.and(stringPath.in(filter.getValues()));
                break;
            case "notIn":
                predicate.and(stringPath.notIn(filter.getValues()));
                break;
            case "like":
                predicate.and(stringPath.like(filter.getValues().get(0)));
                break;
            default: break;
        }

        return predicate;
    }
    private Predicate getPredicateFromNumber(Filter filter){
        BooleanBuilder predicate = new BooleanBuilder();
        NumberPath numberPath = path.getNumber(filter.getField(), Long.class);

        List<Long> numbers = filter.getValues().stream().map(item -> Long.valueOf(item)).collect(Collectors.toList());
        
        switch (filter.getOperator()){
            case "eq":
                predicate.and(numberPath.eq(numbers.get(0)));
                break;
            case "lt":
                predicate.and(numberPath.lt(numbers.get(0)));
                break;
            case "lte":
                predicate.and(numberPath.loe(numbers.get(0)));
                break;
            case "gt":
                predicate.and(numberPath.gt(numbers.get(0)));
                break;
            case "gte":
                predicate.and(numberPath.goe(numbers.get(0)));
                break;
            case "in":
                predicate.and(numberPath.in(numbers));
                break;
            case "notIn":
                predicate.and(numberPath.notIn(numbers));
                break;
            default: break;
        }

        return predicate;
    }
    private Predicate getPredicateFromDate(Filter filter){
        BooleanBuilder predicate = new BooleanBuilder();
        DateTimePath dateTimePath = path.getDateTime(filter.getField(), LocalDateTime.class);

//        LocalDateTime.parse( arg0.getSchedule() )
        switch (filter.getOperator()){
            case "eq":
                predicate.and(dateTimePath.eq(filter.getValues().get(0)));
                break;
            case "lt":
                predicate.and(dateTimePath.lt(filter.getValues().get(0)));
                break;
            case "lte":
                predicate.and(dateTimePath.loe(filter.getValues().get(0)));
                break;
            case "gt":
                predicate.and(dateTimePath.gt(filter.getValues().get(0)));
                break;
            case "gte":
                predicate.and(dateTimePath.goe(filter.getValues().get(0)));
                break;
            default: break;
        }

        return predicate;
    }

}
