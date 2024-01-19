package persistence.dtos;

import java.io.Serializable;
import java.sql.Date;

public class Activity implements Serializable {

    private long id;
    private long user;
    private long date_id;
    private Date date;
    private long content;
    private long duration;
    private Feedback feedback_total;
    private Feedback feedback_breaks;
    private long points_rec;
    private long points_chosen;
    private Type type;

    public Activity() {
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

    public long getDate_id() {
        return date_id;
    }

    public void setDate_id(long date_id) {
        this.date_id = date_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getContent() {
        return content;
    }

    public void setContent(long content) {
        this.content = content;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Feedback getFeedback_total() {
        return feedback_total;
    }

    public void setFeedback_total(Feedback feedback_total) {
        this.feedback_total = feedback_total;
    }

    public Feedback getFeedback_breaks() {
        return feedback_breaks;
    }

    public void setFeedback_breaks(Feedback feedback_breaks) {
        this.feedback_breaks = feedback_breaks;
    }

    public long getPoints_rec() {
        return points_rec;
    }

    public void setPoints_rec(long points_rec) {
        this.points_rec = points_rec;
    }

    public long getPoints_chosen() {
        return points_chosen;
    }

    public void setPoints_chosen(long points_chosen) {
        this.points_chosen = points_chosen;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
