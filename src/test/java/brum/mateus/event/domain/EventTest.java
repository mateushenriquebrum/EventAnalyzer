package brum.mateus.event.domain;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static brum.mateus.event.domain.Partial.STATUS.FINISHED;
import static brum.mateus.event.domain.Partial.STATUS.STARTED;
import static org.junit.jupiter.api.Assertions.*;

public class EventTest {

    private Event createNowEvent() {
        Instant now = Instant.now();
        return new Event("a", now, now, "", "");
    }

    private Partial createStartedPartial(String id) {
        return new Partial(id, STARTED, Instant.now(), "", "");
    }

    private Partial createFinishedPartial(String id) {
        return new Partial(id, FINISHED, Instant.now(), "", "");
    }

    @Test
    public void shouldCreateEventFromParties() {
        Event event = Event.fromParties(createStartedPartial("a"), createFinishedPartial("a"));
        assertEquals(event.id(), "a");
        assertNotNull(event.duration());
        assertNotNull(event.type());
        assertNotNull(event.host());
    }

    @Test
    public void shouldThrowExceptionWhenPartIsMissingOrNotMatch() {
        assertThrows(IllegalArgumentException.class,
                () -> Event.fromParties(createStartedPartial("a"), null));

        assertThrows(IllegalArgumentException.class,
                () -> Event.fromParties(null, createStartedPartial("a")));

        assertThrows(IllegalArgumentException.class,
                () -> Event.fromParties(createStartedPartial("a"), createFinishedPartial("b")));

        assertThrows(IllegalArgumentException.class,
                () -> Event.fromParties(createStartedPartial("a"), createStartedPartial("b")));
    }

    @Test
    public void shouldCalculateDuration() {
        Instant now = Instant.now();
        Instant future = now.plus(Duration.ofMillis(200));
        Event event = Event.fromParties(new Partial("a", STARTED, now, "", ""), new Partial("a", FINISHED, future, "" ,""));
        assertEquals(Duration.ofMillis(200), event.duration());
    }

    @Test
    public void shouldDecideIfDurationIsAlarming() {
        Instant now = Instant.now();
        Instant longer = now.plus(Duration.ofMillis(6));
        Instant limit = now.plus(Duration.ofMillis(4));
        Instant shorter = now.plus(Duration.ofMillis(2));
        assertFalse(
                Event.fromParties(new Partial("a", STARTED, now, "", ""), new Partial("a", FINISHED, shorter, "", ""))
                        .alarming());
        assertFalse(
                Event.fromParties(new Partial("a", STARTED, now, "", ""), new Partial("a", FINISHED, limit, "", ""))
                        .alarming());
        assertTrue(
                Event.fromParties(new Partial("a", STARTED, now, "", ""), new Partial("a", FINISHED, longer, "", ""))
                        .alarming());
    }
}
