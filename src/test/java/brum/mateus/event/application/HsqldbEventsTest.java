package brum.mateus.event.application;

import brum.mateus.event.domain.Event;
import org.junit.jupiter.api.*;

import java.time.Instant;

public class HsqldbEventsTest {
    private static HsqldbEvents events;

    @BeforeAll
    public static void all() {
        events = new HsqldbEvents("/opt/test");
    }

    @BeforeEach
    public void before() {
        events.dropTable();
        events.createTable();
    }

    @Test
    public void shouldSaveEvent() {
        Event event = new Event("id", Instant.now(), Instant.now(), "" ,"");
        events.save(event);
        Assertions.assertEquals(1, events.count());
    }
}
