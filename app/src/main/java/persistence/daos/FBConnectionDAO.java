package persistence.daos;

import static persistence.daos.DBUtils.TABLE_CONNECTIONS;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import persistence.FirebaseHandler;
import persistence.dtos.Connection;
import persistence.exceptions.PersistenceException;

public class FBConnectionDAO implements ConnectionDAO {

    DatabaseReference database;

    public FBConnectionDAO() {
        database = FirebaseHandler.getDatabase();
    }

    @Override
    public List<Connection> read() throws InterruptedException {
        List<Connection> connections = new ArrayList<>();

        Task<DataSnapshot> task = database.child(TABLE_CONNECTIONS).get();

        while (!task.isComplete()) {
            Thread.sleep(10);
        }

        if (task.isSuccessful()) {
            for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                Connection temp = dataSnapshot.getValue(Connection.class);
                connections.add(temp);
            }
        }

        return connections;
    }

    @Override
    public Connection getConnection(long id) throws InterruptedException {
        List<Connection> connections = read();

        for (Connection c : connections) {
            if (c.getId() == id) {
                return c;
            }
        }

        return null;
    }

    @Override
    public void update(Connection connection) throws PersistenceException {

    }

    @Override
    public List<Connection> getForUser(long userId, boolean pending) throws InterruptedException {

        List<Connection> matchingConnections = new ArrayList<>();

        List<Connection> connections = read();

        for (Connection c : connections) {
            if (c.getUser() == userId && c.isPending() == pending) {
                matchingConnections.add(c);
            }
        }

        return matchingConnections;
    }

    public void set(long id, boolean accept) throws InterruptedException {
        Connection toBeSet = getConnection(id);
        toBeSet.setPending(!accept);

        Map<String, Object> connectionValues = toBeSet.toMap();

        database.child(TABLE_CONNECTIONS).child(String.valueOf(id)).updateChildren(connectionValues);
    }

    public void delete(long id) throws InterruptedException {
        database.child(TABLE_CONNECTIONS).child(String.valueOf(id)).removeValue();
    }
}
