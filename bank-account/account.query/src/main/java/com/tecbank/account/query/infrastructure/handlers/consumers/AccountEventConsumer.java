package com.tecbank.account.query.infrastructure.handlers.consumers;

import com.tecbank.account.common.events.AccountClosedEvent;
import com.tecbank.account.common.events.AccountOpenedEvent;
import com.tecbank.account.common.events.FundsDepositedEvent;
import com.tecbank.account.common.events.FundsWithdrawnEvent;
import com.tecbank.account.query.infrastructure.handlers.EventHandoer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

public class AccountEventConsumer implements EventConsumer {
    @Autowired
    private EventHandoer eventHandoer;

    @KafkaListener(topics = "AccountOpenedEvent", groupId = "$(spring.kafka.consumer.group-is)")
    @Override
    public void consume(AccountOpenedEvent event, Acknowledgment ack) {
        eventHandoer.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "FundsDepositedEvent", groupId = "$(spring.kafka.consumer.group-is)")

    @Override
    public void consume(FundsDepositedEvent event, Acknowledgment ack) {
        eventHandoer.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "FundsWithdrawnEvent", groupId = "$(spring.kafka.consumer.group-is)")

    @Override
    public void consume(FundsWithdrawnEvent event, Acknowledgment ack) {
        eventHandoer.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "AccountClosedEvent", groupId = "$(spring.kafka.consumer.group-is)")

    @Override
    public void consume(AccountClosedEvent event, Acknowledgment ack) {
        eventHandoer.on(event);
        ack.acknowledge();
    }
}
