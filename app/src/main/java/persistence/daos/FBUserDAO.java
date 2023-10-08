package persistence.daos;

import static persistence.daos.DBUtils.TABLE_USERS;
import static persistence.daos.DBUtils.hashPassword;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import persistence.FirebaseHandler;
import persistence.dtos.User;
import persistence.exceptions.PersistenceException;

public class FBUserDAO implements UserDAO {

    public volatile Boolean canLogin = null;
    DatabaseReference database;

    public FBUserDAO() {
        database = FirebaseHandler.getDatabase();
    }

    @Override
    public void create(User user) throws PersistenceException, NoSuchAlgorithmException, InterruptedException {

        user.setPassword(hashPassword(user.getPassword()));
        long id = DBUtils.getNextId(TABLE_USERS);
        user.setId(id);

        database.child(TABLE_USERS).child(String.valueOf(id)).setValue(user);

    }

    @Override
    public List<User> read() throws PersistenceException, InterruptedException {

        List<User> users = new ArrayList<>();

        Task<DataSnapshot> task = database.child(TABLE_USERS).get();

        while (!task.isComplete()) {
            Thread.sleep(10);
        }

        if (task.isSuccessful()) {
            for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                User temp = dataSnapshot.getValue(User.class);
                users.add(temp);
            }
        }

        return users;
    }

    @Override
    public void delete(List<User> vehicles) throws PersistenceException {

    }

    @Override
    public void update(User vehicle) throws PersistenceException {

    }

    @Override
    public User getUser(String username) throws PersistenceException, InterruptedException {

        List<User> users = read();

        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }

        return null;
    }

    @Override
    public boolean canLogin(String username, String password) throws NoSuchAlgorithmException, InterruptedException {
        canLogin = false;

        String passwordHashed = hashPassword(password);

        Task<DataSnapshot> task = database.child(TABLE_USERS).get();

        while (!task.isComplete()) {
            Thread.sleep(10);
        }

        if (task.isSuccessful()) {
            for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                User temp = dataSnapshot.getValue(User.class);
                assert temp != null;
                if (temp.getUsername().equals(username) && temp.getPassword().equals(passwordHashed)) {
                    canLogin = true;
                }
            }
        }
        return canLogin != null && canLogin;
    }
}
