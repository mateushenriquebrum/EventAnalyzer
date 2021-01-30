package brum.mateus.event.application;

import brum.mateus.event.domain.Partial;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;

import static brum.mateus.event.domain.Partial.STATUS.STARTED;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PartialParseTest {
    @Test
    public void shouldCreatePartialFromJson() {
        String line = "{\"id\":\"scsmbstgra\",\"state\":\"STARTED\",\"type\":\"APPLICATION_LOG\",\"host\":\"12345\",\"timestamp\":1491377495212}";
        Partial partial = new JsonParser().toPartial(line);
        Partial expected = new Partial("scsmbstgra", STARTED, Instant.ofEpochMilli(1491377495212L), "APPLICATION_LOG", "12345");
        Assertions.assertEquals(expected, partial);
    }

    @Test
    public void shouldCreatePartialFromJsonTemplateFile() {
        Path small = Paths.get("src", "test", "resources", "small.txt");
        List<Partial> partials = new PartialParser(new JsonParser())
                .from(small)
                .collect(toList());
        assertEquals(6, partials.size());
        partials.forEach(partial -> {
            assertNotNull(partial.id());
            assertNotNull(partial.moment());
        });
    }
}
