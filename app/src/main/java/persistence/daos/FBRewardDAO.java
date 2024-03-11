package persistence.daos;

import static persistence.daos.DBUtils.TABLE_REWARDS;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import persistence.FirebaseHandler;
import persistence.dtos.Connection;
import persistence.dtos.Reward;

public class FBRewardDAO implements RewardDAO {

    DatabaseReference database;
    StorageReference storage;

    public FBRewardDAO() {
        database = FirebaseHandler.getDatabase();
        storage = FirebaseHandler.getStorage();
    }

    @Override
    public List<Reward> read() throws InterruptedException {
        List<Reward> rewards = new ArrayList<>();

        Task<DataSnapshot> task = database.child(TABLE_REWARDS).get();

        while (!task.isComplete()) {
            Thread.sleep(10);
        }

        if (task.isSuccessful()) {
            for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                Reward temp = dataSnapshot.getValue(Reward.class);
                rewards.add(temp);
            }
        }

        return rewards;
    }

    @Override
    public Reward getReward(Connection connection) throws InterruptedException {
        List<Reward> rewards = read();
        Collections.shuffle(rewards);

        for (Reward s : rewards) {
            if (s.getConnection() == connection.getId() && !s.isShown()) {
                return s;
            }
        }

        return null;
    }

    @Override
    public Uri getUrl(Reward reward) throws InterruptedException {
        Task<Uri> task = storage.child(reward.getPath()).getDownloadUrl();

        while (!task.isComplete()) {
            Thread.sleep(10);
        }

        if (task.isSuccessful()) {
            return task.getResult();
        }

        return null;
    }
}
