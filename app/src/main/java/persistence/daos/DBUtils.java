package persistence.daos;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import persistence.FirebaseHandler;
import persistence.dtos.Day;
import persistence.dtos.User;

public class DBUtils {

    public static final String TABLE_USERS = "users";
    public static final String TABLE_THERAPISTS = "therapists";
    public static final String TABLE_TRAINING_VIDEOS = "contents";
    public static final String TABLE_DAYS = "days";

    public static final String TABLE_CONNECTIONS = "connections";
    public static final String TABLE_SETTINGS = "settings";

    public static String hashPassword(String password) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.reset();
        md.update(password.getBytes());
        byte[] mdArray = md.digest();
        StringBuilder sb = new StringBuilder(mdArray.length * 2);
        for (byte b : mdArray) {
            int v = b & 0xff;
            if (v < 16)
                sb.append('0');
            sb.append(Integer.toHexString(v));
        }

        return sb.toString();
    }

    public static long getNextId(String table) throws InterruptedException {

        DatabaseReference database = FirebaseHandler.getDatabase();

        long id = 0;

        Task<DataSnapshot> task = database.child(table).get();

        while (!task.isComplete()) {
            Thread.sleep(10);
        }

        if (task.isSuccessful()) {
            for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                switch (table) {
                    case TABLE_USERS:
                        User tempUser = dataSnapshot.getValue(User.class);
                        assert tempUser != null;
                        id = Math.max(tempUser.getId(), id);
                        break;
                    case TABLE_DAYS:
                        Day tempDay = dataSnapshot.getValue(Day.class);
                        assert tempDay != null;
                        id = Math.max(tempDay.getId(), id);
                        break;
                    default:
                        break;
                }
            }
        }
        return ++id;
    }

    public static boolean isInRecommendedDuration(long duration, String recommendedDuration) {
        recommendedDuration = recommendedDuration.replace(" ", "");
        String[] values = recommendedDuration.split("-");
        long val1 = Long.parseLong(values[0]);
        long val2 = Long.parseLong(values[1]);
        return Long.min(val1, val2) <= duration && Long.max(val1, val2) >= duration;
    }

}