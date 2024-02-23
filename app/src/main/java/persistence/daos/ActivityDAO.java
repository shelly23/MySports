package persistence.daos;

import java.util.List;

import persistence.dtos.Activity;
import persistence.exceptions.PersistenceException;

public interface ActivityDAO {
    List<Activity> read() throws PersistenceException, InterruptedException;

    Activity getActivity(long id) throws PersistenceException, InterruptedException;

    void setActivity(Activity activity) throws InterruptedException;

    void update(Activity activity) throws PersistenceException;

    List<Activity> getForUser(long userId) throws InterruptedException;

    void delete(long id) throws InterruptedException;
}
