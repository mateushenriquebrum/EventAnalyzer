package brum.mateus.event;

import brum.mateus.event.domain.EventAnalyzer;
import brum.mateus.event.domain.Partial;
import brum.mateus.event.application.HsqldbEvents;
import brum.mateus.event.application.JsonParser;
import brum.mateus.event.application.PartialParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

public class Main {
    static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        LocalDateTime start = LocalDateTime.now();
        String filePath = args[0];
        String dataBaseFile = args.length >= 2 ? args[1] : "hsqldb/events";
        LOG.info("File path for import events : "+filePath);
        LOG.info("Data Base file path : "+dataBaseFile);
        Path file = Paths.get(filePath);
        PartialParser partialParser = new PartialParser(new JsonParser());
        Stream<Partial> partials = partialParser.from(file);
        HsqldbEvents hsqldbEvents = new HsqldbEvents(dataBaseFile);
        hsqldbEvents.dropTable();
        hsqldbEvents.createTable();
        long events = new EventAnalyzer(hsqldbEvents)
                .execute(partials)
                .count();
        long total = start.until(LocalDateTime.now(), ChronoUnit.SECONDS);
        LOG.info("Imported "+events+" Events in "+total+" (s)");
    }
}
