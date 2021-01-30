package brum.mateus.event.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

import static brum.mateus.event.domain.Partial.STATUS.FINISHED;
import static brum.mateus.event.domain.Partial.STATUS.STARTED;

/**
 * Value Object
 */
public class Event {
    private final String id;
    private final Instant started;
    private final Instant finished;
    private final String type;
    private final String host;
    static final Logger LOG = LoggerFactory.getLogger(Event.class);

    public Event(String id, Instant started, Instant finished, String type, String host) {
        this.id = id;
        this.started = started;
        this.finished = finished;
        this.type = type;
        this.host = host;
    }

    public static Event fromParties(Partial start, Partial finish) {
        if (start == null
                || finish == null
                || !start.status().equals(STARTED)
                || !finish.status().equals(FINISHED)
                || !start.id().equals(finish.id())) {
            LOG.debug("Invalid arguments to create Event : "+start+", "+finish);
            throw new IllegalArgumentException();
        }
        Event e = new Event(start.id(), start.moment(), finish.moment(), start.type(), start.host());
        LOG.debug("Event created : "+e);
        return e;
    }

    public String id() {
        return id;
    }

    public Duration duration() {
        return Duration.between(started, finished);
    }

    public boolean alarming() {
        int LIMIT = 4;
        return duration().toMillis() > LIMIT;
    }

    public String type() {
        return type;
    }

    public String host() {
        return host;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) && Objects.equals(started, event.started) && Objects.equals(finished, event.finished) && Objects.equals(type, event.type) && Objects.equals(host, event.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, started, finished, type, host);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", started=" + started +
                ", finished=" + finished +
                ", type='" + type + '\'' +
                ", host='" + host + '\'' +
                '}';
    }
}
