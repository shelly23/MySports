package persistence.dtos;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Game_Progress implements Serializable {

    private long id;
    private long user;
    private long points;

    private long field;

    public Game_Progress() {
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("user", this.user);
        result.put("points", this.points);
        result.put("field", this.field);
        return result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public long getField() {
        return field;
    }

    public void setField(long field) {
        this.field = field;
    }
}
