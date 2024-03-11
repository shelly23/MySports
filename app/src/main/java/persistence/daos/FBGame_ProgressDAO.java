package persistence.daos;

import static persistence.daos.DBUtils.TABLE_GAME_PROGRESS;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import persistence.FirebaseHandler;
import persistence.dtos.Game_Progress;

public class FBGame_ProgressDAO implements Game_ProgressDAO {

    DatabaseReference database;


    public FBGame_ProgressDAO() {
        database = FirebaseHandler.getDatabase();
    }

    public List<Game_Progress> read() throws InterruptedException {
        List<Game_Progress> gameProgresses = new ArrayList<>();

        Task<DataSnapshot> task = database.child(TABLE_GAME_PROGRESS).get();

        while (!task.isComplete()) {
            Thread.sleep(10);
        }

        if (task.isSuccessful()) {
            for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                Game_Progress temp = dataSnapshot.getValue(Game_Progress.class);
                gameProgresses.add(temp);
            }
        }

        return gameProgresses;
    }

    @Override
    public Game_Progress getGameProgress(long userId) throws InterruptedException {
        List<Game_Progress> gameProgresses = read();

        for (Game_Progress s : gameProgresses) {
            if (s.getUser() == userId) {
                return s;
            }
        }

        return null;
    }

    @Override
    public void createGameProgress(long UserId) throws InterruptedException {
        long id = DBUtils.getNextId(DBUtils.TABLE_GAME_PROGRESS);
        Game_Progress gameProgress = new Game_Progress();
        gameProgress.setUser(UserId);
        gameProgress.setPoints(0);
        gameProgress.setId(id);

        database.child(DBUtils.TABLE_GAME_PROGRESS).child(String.valueOf(id)).setValue(gameProgress);
    }

    @Override
    public void update(Game_Progress gameProgress) {
        Map<String, Object> gameProgressValues = gameProgress.toMap();

        database.child(TABLE_GAME_PROGRESS).child(String.valueOf(gameProgress.getId())).updateChildren(gameProgressValues);
    }
}
