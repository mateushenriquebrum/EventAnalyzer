package brum.mateus.event.domain;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

import static brum.mateus.event.domain.Partial.STATUS.FINISHED;
import static brum.mateus.event.domain.Partial.STATUS.STARTED;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventComposerTest {

    Partial createPartialEvent(String id, Partial.STATUS status) {
        return new Partial(id, status, Instant.now(), "", "");
    }

    private Event createNowEvent(String id) {
        Instant now = Instant.now();
        return new Event(id, now, now, "", "");
    }

    @Test
    void shouldComposePartialEventsByIdAndState() {

        EventComposer analyzer = new EventComposer();

        Stream<Partial> partials = Stream.of(
                createPartialEvent("a", STARTED),
                createPartialEvent("b", STARTED),
                createPartialEvent("a", FINISHED),
                createPartialEvent("b", FINISHED)
        );

        List<Event> events = analyzer.compose(partials).collect(toList());
        List<Event> expected = asList(createNowEvent("a"), createNowEvent("b"));
        assertEquals(2, events.size());
        assertEquals(expected.get(0).id(), events.get(0).id());
        assertEquals(expected.get(1).id(), events.get(1).id());
    }
}
