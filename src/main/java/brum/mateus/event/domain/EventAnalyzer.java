package brum.mateus.event.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public class EventAnalyzer {
    private final Events events;
    static final Logger LOG = LoggerFactory.getLogger(EventAnalyzer.class);

    public EventAnalyzer(Events events) {
        this.events = events;
    }

    public Stream<Event> execute(Stream<Partial> partials) {
            return new EventComposer()
                    .compose(partials)
                    .peek(event -> {
                        LOG.debug("Saving event : "+event);
                        events.save(event);
                    });

    }
}
