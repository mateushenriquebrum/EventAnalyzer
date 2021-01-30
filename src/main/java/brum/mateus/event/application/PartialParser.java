package brum.mateus.event.application;

import brum.mateus.event.domain.Partial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class PartialParser {
    private final Parser parser;
    static final Logger LOG = LoggerFactory.getLogger(PartialParser.class);

    public PartialParser(Parser parser) {
        this.parser = parser;
    }

    public Stream<Partial> from(Path file) {
        Path tmp = null;
        try {
            LOG.info("Reading file "+file.toAbsolutePath());
            String parent = file.getParent().toString();
            tmp = Paths.get(parent, "sorted.tmp");

            new SortFileLines(file, tmp).sortById();
            LOG.info("Reading sorted file "+tmp.toAbsolutePath());
            return Files.lines(tmp)
                    .parallel()
                    .map(parser::toPartial);
        }catch (Exception e) {
            LOG.error("Error on read file "+file.toAbsolutePath(), e);
            return Stream.empty();
        } finally {
            if(tmp != null){
                try {
                    Files.delete(tmp);
                    LOG.info("Temporary file "+tmp.toAbsolutePath()+" deleted");
                } catch (IOException e) {
                    LOG.error("Error on delete file "+tmp.toAbsolutePath(), e);
                }
            }
        }
    }


    public interface Parser {
        Partial toPartial(String string);
    }
}
