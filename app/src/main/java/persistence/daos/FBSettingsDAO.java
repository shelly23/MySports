package persistence.daos;

import static persistence.daos.DBUtils.TABLE_SETTINGS;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import persistence.FirebaseHandler;
import persistence.dtos.Settings;
import persistence.exceptions.PersistenceException;

public class FBSettingsDAO implements SettingsDAO {

    DatabaseReference database;

    public FBSettingsDAO() {
        database = FirebaseHandler.getDatabase();
    }

    public void create(long user) throws InterruptedException {
        long id = DBUtils.getNextId(TABLE_SETTINGS);
        Settings settings = new Settings(id, user, -1, -1, -1, true, true, "00:00", "00:00");

        database.child(TABLE_SETTINGS).child(String.valueOf(id)).setValue(settings);
    }

    @Override
    public List<Settings> read() throws InterruptedException {
        List<Settings> settings = new ArrayList<>();

        Task<DataSnapshot> task = database.child(TABLE_SETTINGS).get();

        while (!task.isComplete()) {
            Thread.sleep(10);
        }

        if (task.isSuccessful()) {
            for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                Settings temp = dataSnapshot.getValue(Settings.class);
                settings.add(temp);
            }
        }

        return settings;
    }

    @Override
    public Settings getSettings(long id) throws InterruptedException {
        List<Settings> settings = read();

        for (Settings s : settings) {
            if (s.getId() == id) {
                return s;
            }
        }

        return null;
    }

    @Override
    public void update(Settings settings) throws PersistenceException {

    }

    @Override
    public Settings getForUser(long userId) throws InterruptedException {

        List<Settings> settings = read();

        for (Settings s : settings) {
            if (s.getUser() == userId) {
                return s;
            }
        }

        return null;
    }

    public void delete(long id) throws InterruptedException {
        database.child(TABLE_SETTINGS).child(String.valueOf(id)).removeValue();
    }
}
