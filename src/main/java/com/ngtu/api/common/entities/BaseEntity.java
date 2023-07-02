package com.ngtu.api.common.entities;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BaseEntity<ID> {
    private ID id;
}
