package brum.mateus.event.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static brum.mateus.event.domain.Partial.STATUS.STARTED;

/**
 * Aggregator
 */
public class EventComposer {
    final Map<String, Partial> started = new Hashtable<>(100);
    final Map<String, Partial> finished = new Hashtable<>(100);
    static final Logger LOG = LoggerFactory.getLogger(EventComposer.class);

    public Stream<Event> compose(Stream<Partial> partials) {
        return partials
                .map(partial -> {
                    Event event = null;
                    LOG.debug("Parties in memory now "+ (started.size() + finished.size()));
                    if (partial.status().equals(STARTED)) {
                        Partial another = finished.get(partial.id());
                        if (another != null) {
                            finished.remove(another.id());
                            LOG.debug(partial +" with match");
                            event = Event.fromParties(partial, another);
                            LOG.debug("Removing another partial "+partial);
                        } else {
                            LOG.debug(partial +" without match yet");
                            started.put(partial.id(), partial);
                        }
                    } else {
                        Partial another = started.get(partial.id());
                        if (another != null) {
                            started.remove(another.id());
                            LOG.debug(partial +" with match");
                            event = Event.fromParties(another, partial);
                            LOG.debug("Removing another partial "+partial);
                        } else {
                            LOG.debug(partial +" without match yet");
                            finished.put(partial.id(), partial);
                        }
                    }
                    return event; // can be null
                })
                .filter(Objects::nonNull);
    }
}
