package persistence.dtos;

import java.util.Date;

public class Training_Video {

    private long id;

    private long duration;

    private long connection;

    private long level;

    private String notes;

    private String path;

    private Type type;

    private Date lastActivity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getConnection() {
        return connection;
    }

    public void setConnection(long connection) {
        this.connection = connection;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
