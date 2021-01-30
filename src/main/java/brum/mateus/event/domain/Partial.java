package brum.mateus.event.domain;

import java.time.Instant;
import java.util.Objects;

/**
 * Data Structure
 */
public class Partial {
    private final String id;
    private final STATUS status;
    private final Instant moment;
    private final String type;
    private final String host;

    public Partial(String id, STATUS status, Instant moment, String type, String host) {
        this.id = id;
        this.status = status;
        this.moment = moment;
        this.type = type;
        this.host = host;
    }

    public Instant moment() {
        return this.moment;
    }

    public String id() {
        return id;
    }

    public STATUS status() {
        return status;
    }

    public String type() {return  type;}

    public String host() {return host;}

    public enum STATUS {
        STARTED,
        FINISHED
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partial partial = (Partial) o;
        return Objects.equals(id, partial.id) && status == partial.status && Objects.equals(moment, partial.moment) && Objects.equals(type, partial.type) && Objects.equals(host, partial.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, moment, type, host);
    }

    @Override
    public String toString() {
        return "Partial{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", moment=" + moment +
                ", type='" + type + '\'' +
                ", host='" + host + '\'' +
                '}';
    }
}
