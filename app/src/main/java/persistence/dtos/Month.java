package persistence.dtos;

import java.util.HashMap;
import java.util.Map;

public class Month {

    private long active;

    private long total;

    private long strength;

    private long endurance;

    private long relaxation;

    public Month() {

    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("active", this.active);
        result.put("total", this.total);
        result.put("strength", this.strength);
        result.put("endurance", this.endurance);
        result.put("releaxation", this.relaxation);
        return result;
    }


    public long getActive() {
        return active;
    }

    public void setActive(long active) {
        this.active = active;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getStrength() {
        return strength;
    }

    public void setStrength(long strength) {
        this.strength = strength;
    }

    public long getEndurance() {
        return endurance;
    }

    public void setEndurance(long endurance) {
        this.endurance = endurance;
    }

    public long getRelaxation() {
        return relaxation;
    }

    public void setRelaxation(long relaxation) {
        this.relaxation = relaxation;
    }
}
