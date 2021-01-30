package brum.mateus.event.application;

import com.google.code.externalsorting.ExternalSort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SortFileLines {
    private final Path input;
    private final Path output;
    static final Logger LOG = LoggerFactory.getLogger(SortFileLines.class);


    public SortFileLines(Path input, Path output) {

        this.input = input;
        this.output = output;
    }

    public void sortById() {
        try{
            LOG.info("Sort event file started, input: "+input.toAbsolutePath()+", output: "+output.toAbsolutePath());
            Pattern id = Pattern.compile("\"id\":\"(?<id>.*?)\"");
            ExternalSort
                    .mergeSortedFiles(
                            ExternalSort.sortInBatch(input.toFile()), output.toFile(),
                            (o1, o2) -> {
                                Matcher mo1 = id.matcher(o1);
                                Matcher mo2 = id.matcher(o2);
                                boolean m1f = mo1.find();
                                boolean m2f = mo2.find();
                                if (!m1f) LOG.debug("Id not fount for line "+ false);
                                if (!m2f) LOG.debug("Id not fount for line "+ false);
                                String o1id = mo1.group("id");
                                String o2id = mo2.group("id");
                                return o1id.compareTo(o2id);
                            }
                    );
            LOG.info("Sorted event file created : "+output.toAbsolutePath());
        } catch (Exception e) {
            LOG.error("Sorted event file not created", e);
        }
    }
}
