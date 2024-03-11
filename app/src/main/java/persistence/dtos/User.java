package persistence.dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {

    private String prename;
    private String surname;
    private String username;
    private String password;

    private double edss;

    private Date birthdate;

    private long id;

    private Badge badge;

    public User() {

    }

    public User(String prename, String surname, String username, String password, double edss, Date birthdate, long id) {
        this.prename = prename;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.edss = edss;
        this.birthdate = birthdate;
        this.id = id;
    }

    public User(String prename, String surname, String username, String password, double edss, Date birthdate) {
        this.prename = prename;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.edss = edss;
        this.birthdate = birthdate;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("prename", this.prename);
        result.put("surname", this.surname);
        result.put("username", this.username);
        result.put("password", this.password);
        result.put("edss", this.edss);
        result.put("birthdate", this.birthdate);
        result.put("badge", this.badge);
        return result;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public double getEdss() {
        return edss;
    }

    public void setEdss(double edss) {
        this.edss = edss;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
}
