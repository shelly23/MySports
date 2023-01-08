package persistence.dtos;

import java.io.Serializable;
import java.sql.Date;

public class Day implements Serializable {

    private int steps;
    private Date current_date;
    private boolean active;
    private boolean attack;
    private long id;
    private long user_id;

    public Day(int steps, Date current_date, boolean active, boolean attack, long id, long user_id) {
        this.steps = steps;
        this.current_date = current_date;
        this.active = active;
        this.attack = attack;
        this.id = id;
        this.user_id = user_id;
    }

    public Day(int steps, Date current_date, boolean active, boolean attack, long user_id) {
        this.steps = steps;
        this.current_date = current_date;
        this.active = active;
        this.attack = attack;
        this.user_id = user_id;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public Date getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(Date current_date) {
        this.current_date = current_date;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isAttack() {
        return attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
}
