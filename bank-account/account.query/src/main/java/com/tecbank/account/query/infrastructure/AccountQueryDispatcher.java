/*
 * Copyright (c) 2024. Paul Nwabudike
 * Since: June 2024
 * Author: Paul Nwabudike
 * Name: AccountQueryDispatcher
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at   http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 *
 */

package com.tecbank.account.query.infrastructure;

import com.tecbank.cqrs.core.Infrastructure.QueryDispatcher;
import com.tecbank.cqrs.core.domain.BaseEntity;
import com.tecbank.cqrs.core.queries.BaseQuery;
import com.tecbank.cqrs.core.queries.QueryHandlerMethod;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;

public class AccountQueryDispatcher implements QueryDispatcher {
    private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler) {
         var handlers = routes.computeIfAbsent(type,c -> new LinkedList<>());
         handlers.add(handler);
    }

    @Override
    public <U extends BaseEntity> List<U> send(BaseQuery query) {
        var handlers = routes.get(query.getClass());
        if (handlers == null || handlers.size() <= 0) {
            throw new RuntimeException("No query handler was registered!");
        }
        if (handlers.size() > 1) {
            throw new RuntimeException("Cannot send query to more than one handler!");

        }

        return handlers.get(0).handle(query);
    }
}
