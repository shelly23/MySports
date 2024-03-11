package persistence.dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Day implements Serializable {

    private int steps;

    private int steps_start;
    private Date current_date;
    private boolean active;

    private long activity_duration;
    private long step_count;
    private long activity_count;
    private boolean attack;
    private boolean pause;
    private long id;
    private long user_id;

    private boolean checked;

    public Day() {
    }

    public Day(int steps, int steps_start, Date current_date, boolean active, long activity_duration, long step_count, long activity_count, boolean attack, boolean pause, long id, long user_id, boolean checked) {
        this.steps = steps;
        this.steps_start = steps_start;
        this.current_date = current_date;
        this.active = active;
        this.activity_duration = activity_duration;
        this.step_count = step_count;
        this.activity_count = activity_count;
        this.attack = attack;
        this.pause = pause;
        this.id = id;
        this.user_id = user_id;
        this.checked = checked;
    }

    public Day(int steps, int steps_start, Date current_date, boolean active, boolean pause, boolean attack, long user_id) {
        this.steps = steps;
        this.steps_start = steps_start;
        this.current_date = current_date;
        this.active = active;
        this.attack = attack;
        this.pause = pause;
        this.user_id = user_id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("steps", this.steps);
        result.put("steps_start", this.steps_start);
        result.put("current_date", this.current_date);
        result.put("active", this.active);
        result.put("activity_duration", this.activity_duration);
        result.put("step_count", this.step_count);
        result.put("activity_count", this.activity_count);
        result.put("attack", this.attack);
        result.put("pause", this.pause);
        result.put("checked", this.checked);
        return result;
    }

    public long getActivity_duration() {
        return activity_duration;
    }

    public void setActivity_duration(long activityDuration) {
        this.activity_duration = activityDuration;
    }

    public long getStep_count() {
        return step_count;
    }

    public void setStep_count(long stepCount) {
        this.step_count = stepCount;
    }

    public long getActivity_count() {
        return activity_count;
    }

    public void setActivity_count(long activityCount) {
        this.activity_count = activityCount;
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

    public int getSteps_start() {
        return steps_start;
    }

    public void setSteps_start(int steps_start) {
        this.steps_start = steps_start;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }
}
