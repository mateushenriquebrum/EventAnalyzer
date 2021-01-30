package brum.mateus.event.domain;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static java.time.Instant.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EventAnalyzerTest {
    @Test
    public void shouldSaveOnEventAnalyze() {
        Events mockEvents = Mockito.mock(Events.class);
        EventAnalyzer analyzerUseCase = new EventAnalyzer(mockEvents);
        Stream<Partial> partials = Stream.of(
                new Partial("a", Partial.STATUS.STARTED, now(), "", ""),
                new Partial("a", Partial.STATUS.FINISHED, now(), "", "")
        );
        Long saved = analyzerUseCase.execute(partials).count();
        assertEquals(1, saved);
        verify(mockEvents, only()).save(any(Event.class));
    }
}
