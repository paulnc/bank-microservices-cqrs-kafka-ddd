package com.tecbank.cqrs.core.queries;

import java.util.List;

@FunctionalInterface
public interface QueryHandlerMethod<T extends BaseQuery> {
   List<BaseQuery> handle(T query);

}
