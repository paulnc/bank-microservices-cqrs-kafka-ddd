/*
 * Copyright (c) 2023. Paul Nwabudike
 * Since: February 2023
 * Author: Paul Nwabudike
 * Name: AccountCommandDispatcher.java
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at   http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 *
 */

package com.tecbank.account.cmd.infrastructure;

import com.tecbank.cqrs.core.Infrastructure.CommandDispatcher;
import com.tecbank.cqrs.core.commands.BaseCommand;
import com.tecbank.cqrs.core.commands.CommandHandlerMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountCommandDispatcher implements CommandDispatcher {

    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
            var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
            handlers.add(handler);
    }

    @Override
    public void send(BaseCommand command) {

        var handlers = routes.get(command.getClass());
        if (handlers == null || handlers.size() == 0){
            throw new RuntimeException(" No command handler was registered");
        }
        if (handlers.size() > 1) {
            throw  new RuntimeException("Cannot send command to more than one handler!");
        }

        handlers.get(0).handle(command);
    }
}
