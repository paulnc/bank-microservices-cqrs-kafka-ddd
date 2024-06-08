package com.tecbank.account.cmd.infrastructure;

import com.tecbank.account.cmd.domain.AccountAggregate;
import com.tecbank.account.cmd.domain.EventStoreRepository;
import com.tecbank.cqrs.core.Infrastructure.EventStore;
import com.tecbank.cqrs.core.events.BaseEvent;
import com.tecbank.cqrs.core.events.EventModel;
import com.tecbank.cqrs.core.exceptions.AggregateNotFoundException;
import com.tecbank.cqrs.core.exceptions.ConcurrencyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountEventStore implements EventStore {

    @Autowired
    private EventStoreRepository eventStoreRepository;

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
            throw new ConcurrencyException();
        }
        var version = expectedVersion;
        for (var event: events){
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .timestamp(new Date())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();
            var persistedEvent = eventStoreRepository.save(eventModel);
            if (persistedEvent != null) {
                //TODO Produce event to Kafka
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (eventStream == null || eventStream.isEmpty()) {
            throw new AggregateNotFoundException("Incorrect Account ID Provided");
        }
        return eventStream.stream().map(x -> x.getEventData()).collect(Collectors.toList());
    }
}
