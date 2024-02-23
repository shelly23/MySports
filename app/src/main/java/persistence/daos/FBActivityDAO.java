package persistence.daos;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import persistence.FirebaseHandler;
import persistence.dtos.Activity;
import persistence.exceptions.PersistenceException;

public class FBActivityDAO implements ActivityDAO {

    DatabaseReference database;
    StorageReference storage;

    public FBActivityDAO() {
        database = FirebaseHandler.getDatabase();
        storage = FirebaseHandler.getStorage();
    }

    @Override
    public List<Activity> read() throws PersistenceException, InterruptedException {
        return null;
    }

    @Override
    public Activity getActivity(long id) throws PersistenceException, InterruptedException {
        return null;
    }

    @Override
    public void setActivity(Activity activity) throws InterruptedException {
        long id = DBUtils.getNextId(DBUtils.TABLE_ACTIVITIES);
        activity.setId(id);

        database.child(DBUtils.TABLE_ACTIVITIES).child(String.valueOf(id)).setValue(activity);
    }

    @Override
    public void update(Activity activity) throws PersistenceException {

    }

    @Override
    public List<Activity> getForUser(long userId) throws InterruptedException {
        return null;
    }

    @Override
    public void delete(long id) throws InterruptedException {

    }
}
