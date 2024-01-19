package persistence.daos;

import static persistence.daos.DBUtils.TABLE_TRAINING_VIDEOS;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import persistence.FirebaseHandler;
import persistence.dtos.Connection;
import persistence.dtos.Training_Video;
import persistence.dtos.Type;

public class FBTraining_VideoDAO implements Training_VideoDAO {

    DatabaseReference database;
    StorageReference storage;

    public FBTraining_VideoDAO() {
        database = FirebaseHandler.getDatabase();
        storage = FirebaseHandler.getStorage();
    }

    @Override
    public List<Training_Video> read() throws InterruptedException {
        List<Training_Video> trainingVideos = new ArrayList<>();

        Task<DataSnapshot> task = database.child(TABLE_TRAINING_VIDEOS).get();

        while (!task.isComplete()) {
            Thread.sleep(10);
        }

        if (task.isSuccessful()) {
            for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                Training_Video temp = dataSnapshot.getValue(Training_Video.class);
                trainingVideos.add(temp);
            }
        }

        return trainingVideos;
    }

    @Override
    public Training_Video getTraining_Video(Connection connection, Type type) throws InterruptedException {
        List<Training_Video> trainingVideos = read();

        for (Training_Video t : trainingVideos) {
            if (t.getConnection() == connection.getId() && t.getType().equals(type) && DBUtils.isInRecommendedDuration(t.getDuration(), connection.getRecommendedDuration())) {
                return t;
            }
        }

        return null;
    }

    public Uri getUrl(Training_Video trainingVideo) throws InterruptedException {
        Task<Uri> task = storage.child(trainingVideo.getPath()).getDownloadUrl();

        while (!task.isComplete()) {
            Thread.sleep(10);
        }

        if (task.isSuccessful()) {
            return task.getResult();
        }

        return null;
    }
}
