package com.tecbank.cqrs.core.queries;

import com.tecbank.cqrs.core.domain.BaseEntity;

import java.util.List;

@FunctionalInterface
public interface QueryHandlerMethod<T extends BaseQuery> {
   List<BaseEntity> handle(T query);

}
