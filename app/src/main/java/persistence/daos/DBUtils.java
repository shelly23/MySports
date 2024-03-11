package persistence.daos;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import persistence.FirebaseHandler;
import persistence.dtos.Activity;
import persistence.dtos.Day;
import persistence.dtos.Month;
import persistence.dtos.Type;
import persistence.dtos.User;

public class DBUtils {

    public static final String TABLE_USERS = "users";
    public static final String TABLE_THERAPISTS = "therapists";
    public static final String TABLE_TRAINING_VIDEOS = "contents";
    public static final String TABLE_DAYS = "days";
    public static final String TABLE_MONTHS = "months";
    public static final String TABLE_ACTIVITIES = "activities";

    public static final String TABLE_CONNECTIONS = "connections";
    public static final String TABLE_SETTINGS = "settings";
    public static final String TABLE_GAME_PROGRESS = "game_progress";
    public static final String TABLE_REWARDS = "rewards";

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

        if (table.contains(TABLE_DAYS)) {
            table = TABLE_DAYS;
        }

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
                    case TABLE_ACTIVITIES:
                        Activity tempActivity = dataSnapshot.getValue(Activity.class);
                        assert tempActivity != null;
                        id = Math.max(tempActivity.getId(), id);
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

    public static Type whichTypeToChoose(long user, String month, String recommendendedRatio) {
        recommendendedRatio = recommendendedRatio.replace(" ", "");
        long ratStrength;
        long ratEndurance;
        if (!recommendendedRatio.isEmpty()) {
            String[] values = recommendendedRatio.split(":");
            ratStrength = Long.parseLong(values[0]);
            ratEndurance = Long.parseLong(values[1]);
        } else {
            ratStrength = 50;
            ratEndurance = 50;
        }
        MonthDAO monthDAO = new FBMonthDAO();
        Month monthObj;
        try {
            monthObj = monthDAO.getMonth(user, month);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long ratStr = (long) (((float) monthObj.getStrength() / (float) monthObj.getActive()) * 100);
        long ratEnd = (long) (((float) monthObj.getEndurance() / (float) monthObj.getActive()) * 100);

        long diffStr = ratStrength - ratStr;
        long diffEnd = ratEndurance - ratEnd;

        return diffStr > 0 ? Type.strength : diffEnd > 0 ? Type.endurance : null;

    }

    static String getTableM(long user) {
        return user + "/" + TABLE_MONTHS;
    }

}