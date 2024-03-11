package persistence.dtos;

import java.util.HashMap;
import java.util.Map;

public class Reward {

    private long connection;
    private long id;

    private String link;
    private String path;
    private boolean shown;
    private String title;

    private String belohnung;

    public Reward() {
    }

    public long getConnection() {
        return connection;
    }

    public void setConnection(long connection) {
        this.connection = connection;
    }

    public String getBelohnung() {
        return belohnung;
    }

    public void setBelohnung(String belohnung) {
        this.belohnung = belohnung;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("connection", this.connection);
        result.put("link", this.link);
        result.put("path", this.path);
        result.put("shown", this.shown);
        result.put("title", this.title);
        result.put("belohnung", this.belohnung);
        return result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isShown() {
        return shown;
    }

    public void setShown(boolean shown) {
        this.shown = shown;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
