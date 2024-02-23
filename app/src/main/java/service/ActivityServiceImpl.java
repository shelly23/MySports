package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import persistence.daos.ActivityDAO;
import persistence.dtos.Activity;

public class ActivityServiceImpl implements ActivityService {

    private static final Logger LOG = LoggerFactory.getLogger(ActivityServiceImpl.class);

    private final ActivityDAO FBpersistenceActivity;

    public ActivityServiceImpl(ActivityDAO FBpersistenceActivity) {
        this.FBpersistenceActivity = FBpersistenceActivity;
    }


    @Override
    public List<Activity> getUsersActivities(long userId) throws InterruptedException {
        return null;
    }

    @Override
    public void setActivity(Activity activity) throws InterruptedException {
        LOG.debug("Set new activity for user " + activity.getUser());
        FBpersistenceActivity.setActivity(activity);
    }

    @Override
    public void delete(long id) throws InterruptedException {

    }
}
