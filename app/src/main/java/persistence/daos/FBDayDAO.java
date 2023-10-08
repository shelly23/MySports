package persistence.daos;

import static persistence.daos.DBUtils.TABLE_DAYS;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import persistence.FirebaseHandler;
import persistence.dtos.Day;
import persistence.exceptions.PersistenceException;

public class FBDayDAO implements DayDAO {

    DatabaseReference database;


    public FBDayDAO() {
        database = FirebaseHandler.getDatabase();
    }


    @Override
    public Day create(Day day) throws PersistenceException, InterruptedException {
        long id = DBUtils.getNextId(TABLE_DAYS);
        day.setId(id);

        database.child(TABLE_DAYS).child(String.valueOf(id)).setValue(day);

        return day;
    }

    @Override
    public List<Day> read() throws PersistenceException, InterruptedException {
        List<Day> days = new ArrayList<>();

        Task<DataSnapshot> task = database.child(TABLE_DAYS).get();

        while (!task.isComplete()) {
            Thread.sleep(10);
        }

        if (task.isSuccessful()) {
            for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                Day temp = dataSnapshot.getValue(Day.class);
                days.add(temp);
            }
        }

        return days;
    }

    @Override
    public void delete(List<Day> days) throws PersistenceException {

    }

    @Override
    public void update(Day day) throws PersistenceException, InterruptedException {

        Day dayToBeUpdated = getDay(day.getUser_id(), day.getCurrent_date());

        dayToBeUpdated.setPause(day.isPause());
        dayToBeUpdated.setActive(day.isActive());
        dayToBeUpdated.setAttack(day.isAttack());
        dayToBeUpdated.setSteps(day.getSteps());
        dayToBeUpdated.setSteps_start(day.getSteps_start());

        Map<String, Object> dayValues = dayToBeUpdated.toMap();

        database.child(TABLE_DAYS).child(String.valueOf(day.getId())).updateChildren(dayValues);
    }

    @Override
    public Day getDay(long user_id, Date date) throws PersistenceException, InterruptedException {

        List<Day> days = read();

        for (Day d : days) {
            if (d.getUser_id() == user_id &&
                    d.getCurrent_date().getDate() == date.getDate() &&
                    d.getCurrent_date().getMonth() == date.getMonth() &&
                    d.getCurrent_date().getYear() == date.getYear()) {
                return d;
            }
        }

        return null;
    }

    @Override
    public boolean exists(long user_id, Date date) throws PersistenceException {
        return false;
    }
}
