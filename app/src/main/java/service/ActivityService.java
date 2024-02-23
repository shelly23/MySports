package service;

import java.util.List;

import persistence.dtos.Activity;

public interface ActivityService {

    List<Activity> getUsersActivities(long userId) throws InterruptedException;

    void setActivity(Activity activity) throws InterruptedException;

    void delete(long id) throws InterruptedException;
}
