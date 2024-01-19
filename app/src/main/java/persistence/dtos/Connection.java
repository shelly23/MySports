package persistence.dtos;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Connection implements Serializable {

    private long id;

    private long therapist;

    private long user;

    private String recommendedDuration;

    private String recommendedRatio;

    private boolean pending;

    public Connection() {
    }

    public Connection(long id, long therapist, long user, String recommendedDuration, String recommendedRatio, boolean pending) {
        this.id = id;
        this.therapist = therapist;
        this.user = user;
        this.recommendedDuration = recommendedDuration;
        this.recommendedRatio = recommendedRatio;
        this.pending = pending;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("therapist", this.therapist);
        result.put("user", this.user);
        result.put("recommendedDuration", this.recommendedDuration);
        result.put("recommendedRatio", this.recommendedRatio);
        result.put("pending", this.pending);
        return result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTherapist() {
        return therapist;
    }

    public void setTherapist(long therapist) {
        this.therapist = therapist;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public String getRecommendedDuration() {
        return recommendedDuration;
    }

    public void setRecommendedDuration(String recommendedDuration) {
        this.recommendedDuration = recommendedDuration;
    }

    public String getRecommendedRatio() {
        return recommendedRatio;
    }

    public void setRecommendedRatio(String recommendedRatio) {
        this.recommendedRatio = recommendedRatio;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }
}
