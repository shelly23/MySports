package persistence.dtos;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Settings implements Serializable {

    private long id;

    private long user;

    private long step_goal;

    private long activity_duration;

    private long training_count;

    private boolean chat_activated;
    private boolean game_activated;
    private String messages_from;
    private String messages_to;

    public Settings() {
    }

    public Settings(long id, long user, long step_goal, long activity_duration, long training_count, boolean chat_activated, boolean game_activated, String messages_from, String messages_to) {
        this.id = id;
        this.user = user;
        this.step_goal = step_goal;
        this.activity_duration = activity_duration;
        this.training_count = training_count;
        this.chat_activated = chat_activated;
        this.game_activated = game_activated;
        this.messages_from = messages_from;
        this.messages_to = messages_to;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("user", this.user);
        result.put("step_goal", this.step_goal);
        result.put("activity_duration", this.activity_duration);
        result.put("training_count", this.training_count);
        result.put("chat_activated", this.chat_activated);
        result.put("game_activated", this.game_activated);
        result.put("messages_from", this.messages_from);
        result.put("messages_to", this.messages_to);
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

    public long getStep_goal() {
        return step_goal;
    }

    public void setStep_goal(long step_goal) {
        this.step_goal = step_goal;
    }

    public long getActivity_duration() {
        return activity_duration;
    }

    public void setActivity_duration(long activity_duration) {
        this.activity_duration = activity_duration;
    }

    public long getTraining_count() {
        return training_count;
    }

    public void setTraining_count(long training_count) {
        this.training_count = training_count;
    }

    public boolean isChat_activated() {
        return chat_activated;
    }

    public void setChat_activated(boolean chat_activated) {
        this.chat_activated = chat_activated;
    }

    public boolean isGame_activated() {
        return game_activated;
    }

    public void setGame_activated(boolean game_activated) {
        this.game_activated = game_activated;
    }

    public String getMessages_from() {
        return messages_from;
    }

    public void setMessages_from(String messages_from) {
        this.messages_from = messages_from;
    }

    public String getMessages_to() {
        return messages_to;
    }

    public void setMessages_to(String messages_to) {
        this.messages_to = messages_to;
    }
}
