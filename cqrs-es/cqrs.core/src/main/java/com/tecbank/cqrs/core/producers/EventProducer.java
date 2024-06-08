package com.tecbank.cqrs.core.producers;

import com.tecbank.cqrs.core.events.BaseEvent;

public interface EventProducer {
    void produce(String topic, BaseEvent event);
}
