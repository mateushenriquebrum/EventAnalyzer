package brum.mateus.event.application;

import com.google.gson.Gson;
import brum.mateus.event.domain.Partial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

public class JsonParser implements PartialParser.Parser {

    static final Logger LOG = LoggerFactory.getLogger(JsonParser.class);

    @Override
    public Partial toPartial(String string) {
        // domain object should never ever have any relation with external world.
        // external world need to adapt to create the proper DO, not the opposite.
        Gson gson = new Gson();
        JsonParser.Intermediary intermediary = gson.fromJson(string, JsonParser.Intermediary.class);
        String id = intermediary.id;
        Partial.STATUS status = Partial.STATUS.valueOf(intermediary.state.toUpperCase());
        String type = intermediary.type;
        String host = intermediary.host;
        Instant moment = Instant.ofEpochMilli(intermediary.timestamp);
        return new Partial(id, status, moment, type, host);
    }

    private static class Intermediary {
        public String id;
        public String state;
        public String type;
        public String host;
        public long timestamp;
    }
}
