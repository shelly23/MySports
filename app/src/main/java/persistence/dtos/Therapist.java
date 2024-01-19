package persistence.dtos;

import java.io.Serializable;

public class Therapist implements Serializable {

    private String forename;
    private String surname;
    private String username;
    private String password;

    private long id;

    public Therapist() {

    }

    public Therapist(String forename, String surname, String username, String password, long id) {
        this.forename = forename;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.id = id;
    }

    public Therapist(String forename, String surname, String username, String password) {
        this.forename = forename;
        this.surname = surname;
        this.username = username;
        this.password = password;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}