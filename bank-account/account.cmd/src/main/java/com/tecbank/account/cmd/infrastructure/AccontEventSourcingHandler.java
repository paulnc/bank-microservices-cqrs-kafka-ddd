package com.tecbank.account.cmd.infrastructure;

import com.tecbank.account.cmd.domain.AccountAggregate;
import com.tecbank.cqrs.core.Infrastructure.EventStore;
import com.tecbank.cqrs.core.domain.AggregateRoot;
import com.tecbank.cqrs.core.handlers.EventSourcingHandler;
import com.tecbank.cqrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class AccontEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {
@Autowired
private EventStore eventStore;

@Autowired
private EventProducer eventProducer;

    @Override
    public void save(AggregateRoot aggregate) {
        eventStore.saveEvents(aggregate.getId(), aggregate.grtUncommittedChanges(), aggregate.getVersion());
        aggregate.markChangedAsCommitted();
    }

    @Override
    public AccountAggregate getById(String id) {
        var aggregate = new AccountAggregate();
        var events = eventStore.getEvents(id);
        if (events != null && !events.isEmpty()) {
            aggregate.replayEvents(events);
            var latestVersion = events.stream().map(x -> x.getVersion()).max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }
        return aggregate;
    }

    @Override
    public void republishEvents() {
        var aggregateIds = eventStore.getAggregeIds();
        for (var aggregateId: aggregateIds) {
            var aggregate = getById(aggregateId);
            if (aggregate == null || !aggregate.getActive()) continue;
            var events = eventStore.getEvents(aggregateId);
            for (var event: events) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }

    }
}
