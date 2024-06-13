package com.tecbank.account.cmd.api.controllers;

import com.tecbank.account.cmd.api.commands.DepositFundsCommand;
import com.tecbank.account.common.dto.BaseResponse;
import com.tecbank.cqrs.core.Infrastructure.CommandDispatcher;
import com.tecbank.cqrs.core.exceptions.AggregateNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/depositFunds")
public class DepositFundsController {
    private final Logger logger = Logger.getLogger(OpenAccountController.class.getName());
    @Autowired
    private CommandDispatcher commandDispatcher;
    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable(value = "id") String id,
    @RequestBody DepositFundsCommand command){

        try {
            command.setId(id);
            commandDispatcher.send(command);
            return new ResponseEntity<>(new BaseResponse("Deposit funds request completed successfully"), HttpStatus.OK);

        }catch (IllegalStateException | AggregateNotFoundException e) {
            logger.log(Level.WARNING, MessageFormat.format("Client made a bad request - (0).", e.toString()));
            return  new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            var safeErrorMessage = MessageFormat.format("Error while processing request to deposit funds to bank account with id - (0).", id);
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return  new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
