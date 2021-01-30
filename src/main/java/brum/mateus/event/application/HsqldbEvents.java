package brum.mateus.event.application;

import brum.mateus.event.domain.Event;
import brum.mateus.event.domain.Events;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HsqldbEvents implements Events {
    static final Logger LOG = LoggerFactory.getLogger(HsqldbEvents.class);
    private final Jdbi jdbi;
    public HsqldbEvents (String file) {
        String url = "jdbc:hsqldb:file:"+file;
        jdbi = Jdbi.create(url);
        LOG.debug("HSQLDB connected to "+file);
    }
    @Override
    public void save(Event event) {
        LOG.info("Inserting event "+ event.id());
        // for a remote database, using database pool is mandatory as open/close connection is too expensive.
        jdbi.useHandle(handle -> {
            LOG.debug("Inserting into table "+event);
            handle.execute("INSERT INTO Events VALUES (?, ?, ?, ?, ?)", event.id(), event.duration().toMillis(), event.type(), event.host(), event.alarming());
        });
    }

    public void dropTable() {
        LOG.debug("Dropping table");
        String sql = "DROP TABLE IF EXISTS Events";
        jdbi.useHandle(handle -> handle.execute(sql));
    }

    public void createTable() {
        LOG.debug("Creating table");
        String sql = "CREATE TABLE IF NOT EXISTS Events (id VARCHAR(255) PRIMARY KEY, duration INTEGER, type VARCHAR(255), host VARCHAR(255), alert BOOLEAN)";
        jdbi.useHandle(handle -> handle.execute(sql));
    }

    public int count() {
        LOG.debug("Counting events");
        String sql = "SELECT COUNT(*) FROM Events";
        return jdbi.withHandle(handle -> handle.createQuery(sql).mapTo(Integer.class).one());
    }
}
