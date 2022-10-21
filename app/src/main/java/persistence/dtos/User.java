package persistence.dtos;

public class User {

    private String prename;
    private String surname;
    private String username;
    private String password;
    private long id;

    public User(String prename, String surname, String username, String password, long id) {
        this.prename = prename;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.id = id;
    }

    public User(String prename, String surname, String username, String password) {
        this.prename = prename;
        this.surname = surname;
        this.username = username;
        this.password = password;
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
}
