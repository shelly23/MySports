package persistence.daos;

import static persistence.daos.DBUtils.getTableM;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import persistence.FirebaseHandler;
import persistence.dtos.Month;
import persistence.dtos.Type;

public class FBMonthDAO implements MonthDAO {

    DatabaseReference database;

    public FBMonthDAO() {
        database = FirebaseHandler.getDatabase();
    }

    @Override
    public void createMonth(long user, String month) {

        Month monthObj = new Month();
        monthObj.setActive(0);
        monthObj.setEndurance(0);
        monthObj.setStrength(0);
        monthObj.setRelaxation(0);

        long mo = Long.parseLong(month.substring(0, 2));
        long ye = Long.parseLong(month.substring(2));

        if (mo == 1 || mo == 3 || mo == 5 || mo == 7 || mo == 8 || mo == 10 || mo == 12) {
            monthObj.setTotal(31);
        } else if (mo == 4 || mo == 6 || mo == 9 || mo == 11) {
            monthObj.setTotal(30);
        } else if (mo == 2 && ye % 4 == 0) {
            monthObj.setTotal(29);
        } else {
            monthObj.setTotal(28);
        }

        database.child(getTableM(user)).child(month).setValue(monthObj.toMap());
    }

    @Override
    public boolean existsMonth(long user_id, String month) throws InterruptedException {
        Task<DataSnapshot> task = database.child(getTableM(user_id)).get();
        while (!task.isComplete()) {
            Thread.sleep(10);
        }

        if (task.isSuccessful()) {
            for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                if (dataSnapshot.getKey() != null && dataSnapshot.getKey().equals(month)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void updateMonth(long user, String month, Type type) throws InterruptedException {
        Task<DataSnapshot> task = database.child(getTableM(user)).get();
        while (!task.isComplete()) {
            Thread.sleep(10);
        }

        if (task.isSuccessful()) {
            for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                if (dataSnapshot.getKey() != null && dataSnapshot.getKey().equals(month)) {
                    Month monthObj = dataSnapshot.getValue(Month.class);
                    monthObj.setActive(monthObj.getActive() + 1);
                    switch (type) {
                        case strength:
                            monthObj.setStrength(monthObj.getStrength() + 1);
                            break;
                        case endurance:
                            monthObj.setEndurance(monthObj.getEndurance() + 1);
                            break;
                        case relaxation:
                            monthObj.setRelaxation(monthObj.getRelaxation() + 1);
                            break;
                        default:
                            break;
                    }
                    database.child(getTableM(user)).child(month).updateChildren(monthObj.toMap());
                }
            }
        }
    }

    public Month getMonth(long user, String month) throws InterruptedException {
        Task<DataSnapshot> task = database.child(getTableM(user)).get();
        while ((!task.isComplete())) {
            Thread.sleep(10);
        }

        if (task.isSuccessful()) {
            for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                if (dataSnapshot.getKey() != null && dataSnapshot.getKey().equals(month)) {
                    return dataSnapshot.getValue(Month.class);
                }
            }
        }
        return null;
    }
}
