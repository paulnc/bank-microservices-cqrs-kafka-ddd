package com.tecbank.cqrs.core.Infrastructure;

import com.tecbank.cqrs.core.events.BaseEvent;

import java.util.List;

public interface EventStore {
    void  saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion);
    List<BaseEvent> getEvents(String aggregateId);
    List<String> getAggregeIds();

}
