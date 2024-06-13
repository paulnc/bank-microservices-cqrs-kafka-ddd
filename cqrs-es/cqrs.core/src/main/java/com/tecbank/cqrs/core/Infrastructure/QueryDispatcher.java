package com.tecbank.cqrs.core.Infrastructure;

import com.tecbank.cqrs.core.domain.BaseEntity;
import com.tecbank.cqrs.core.queries.BaseQuery;
import com.tecbank.cqrs.core.queries.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {
    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);
    <U extends BaseEntity>List<U> send(BaseQuery query);
}
