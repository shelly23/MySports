package persistence.daos;

import static persistence.daos.DBUtils.TABLE_SETTINGS;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        Settings settings = new Settings(id, user, -1, "-1", -1, true, true, "00:00", "00:00");

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
    public Settings update(Settings settings) throws PersistenceException, InterruptedException {
        Settings settingsToBeUpdated = getSettings(settings.getId());

        settingsToBeUpdated.setStep_goal(settings.getStep_goal());
        settingsToBeUpdated.setActivity_duration(settings.getActivity_duration());
        settingsToBeUpdated.setTraining_count(settings.getTraining_count());
        settingsToBeUpdated.setChat_activated(settings.isChat_activated());
        settingsToBeUpdated.setGame_activated(settings.isGame_activated());
        settingsToBeUpdated.setMessages_to(settings.getMessages_to());
        settingsToBeUpdated.setMessages_from(settings.getMessages_from());

        Map<String, Object> settingsValues = settingsToBeUpdated.toMap();

        database.child(TABLE_SETTINGS).child(String.valueOf(settings.getId())).updateChildren(settingsValues);
        return settingsToBeUpdated;
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
