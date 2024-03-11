package service;

public class SingletonUser {

    private static final SingletonUser ourInstance = new SingletonUser();
    private long userId;

    public static SingletonUser getInstance() {
        return ourInstance;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
