package com.tecbank.account.query.infrastructure.handlers;

import com.tecbank.account.common.events.AccountClosedEvent;
import com.tecbank.account.common.events.AccountOpenedEvent;
import com.tecbank.account.common.events.FundsDepositedEvent;
import com.tecbank.account.common.events.FundsWithdrawnEvent;

public interface EventHandoer {
    void on(AccountOpenedEvent event);
    void on(FundsDepositedEvent event);

    void on(FundsWithdrawnEvent event);

    void on(AccountClosedEvent event);

}
