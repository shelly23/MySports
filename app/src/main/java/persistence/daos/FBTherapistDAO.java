package persistence.daos;

import static persistence.daos.DBUtils.TABLE_THERAPISTS;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import persistence.FirebaseHandler;
import persistence.dtos.Therapist;

public class FBTherapistDAO implements TherapistDAO {

    DatabaseReference database;

    public FBTherapistDAO() {
        database = FirebaseHandler.getDatabase();
    }

    @Override
    public List<Therapist> read() throws InterruptedException {

        List<Therapist> therapists = new ArrayList<>();

        Task<DataSnapshot> task = database.child(TABLE_THERAPISTS).get();

        while (!task.isComplete()) {
            Thread.sleep(10);
        }

        if (task.isSuccessful()) {
            for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                Therapist temp = dataSnapshot.getValue(Therapist.class);
                therapists.add(temp);
            }
        }

        return therapists;
    }

    @Override
    public Therapist getTherapist(long id) throws InterruptedException {

        List<Therapist> therapists = read();

        for (Therapist t : therapists) {
            if (t.getId() == id) {
                return t;
            }
        }

        return null;
    }

}
