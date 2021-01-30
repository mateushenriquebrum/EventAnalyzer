package brum.mateus.event.application;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.Files.lines;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class SortFileLinesTest {
    @Test
    public void shouldSortLinesById(){
        try {
            Pattern id = Pattern.compile("\"id\":\"(?<id>.*?)\"");
            Path in = Paths.get("src", "test", "resources", "small.txt");
            Path out = Paths.get("src", "test", "resources", "sorted_small.txt");
            new SortFileLines(in, out).sortById();
            List<String> ids = lines(out).map(s -> {
                Matcher ms = id.matcher(s);
                ms.find();
                return ms.group("id");
            }).collect(toList());
            assertIterableEquals(
                    asList("scsmbstgra", "scsmbstgra", "scsmbstgrb", "scsmbstgrb", "scsmbstgrc", "scsmbstgrc"),
                    ids
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
