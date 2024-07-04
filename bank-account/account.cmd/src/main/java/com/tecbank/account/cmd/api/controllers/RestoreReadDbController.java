/*
 * Copyright (c) 2024. Paul Nwabudike
 * Since: June 2024
 * Author: Paul Nwabudike
 * Name: RestoreReadDbController
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at   http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 *
 */

package com.tecbank.account.cmd.api.controllers;


import com.tecbank.account.cmd.api.commands.RestoreReadDbCommand;
import com.tecbank.account.common.dto.BaseResponse;
import com.tecbank.cqrs.core.Infrastructure.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
@RequestMapping(path = "/api/v1/restoreReadDb")
public class RestoreReadDbController {
    private final Logger logger = Logger.getLogger(RestoreReadDbController.class.getName());
    @Autowired
    private CommandDispatcher commandDispatcher;

    @PostMapping
    public ResponseEntity<BaseResponse> restoreReadDb() {
        try {
            commandDispatcher.send(new RestoreReadDbCommand());
            return  new ResponseEntity<>(new BaseResponse("Read database request completed successfully!"), HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            logger.log(Level.WARNING, MessageFormat.format("Client made a bad request - (0).", e.toString()));
            return  new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrorMessage = ("Error while processing request to restore read database");
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return  new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
