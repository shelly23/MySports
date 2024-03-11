package persistence.daos;

import static persistence.daos.DBUtils.TABLE_DAYS;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import persistence.FirebaseHandler;
import persistence.dtos.Day;
import persistence.dtos.Month;
import persistence.exceptions.PersistenceException;

public class FBDayDAO implements DayDAO {

    DatabaseReference database;


    public FBDayDAO() {
        database = FirebaseHandler.getDatabase();
    }


    @Override
    public Day create(Day day) throws PersistenceException, InterruptedException {
        long id = DBUtils.getNextId(getTable(day));
        day.setId(id);

        database.child(getTable(day)).child(String.valueOf(id)).setValue(day);

        return day;
    }


    @Override
    public List<Day> read(long user) throws PersistenceException, InterruptedException {
        List<Day> days = new ArrayList<>();

        Task<DataSnapshot> task = database.child(getTable(user)).get();

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
        dayToBeUpdated.setStep_count(day.getStep_count());
        dayToBeUpdated.setActivity_count(day.getActivity_count());
        dayToBeUpdated.setActivity_duration(day.getActivity_duration());
        dayToBeUpdated.setChecked(day.isChecked());

        Map<String, Object> dayValues = dayToBeUpdated.toMap();

        database.child(getTable(day)).child(String.valueOf(day.getId())).updateChildren(dayValues);
    }

    @Override
    public Day getDay(long user_id, Date date) throws PersistenceException, InterruptedException {

        List<Day> days = read(user_id);

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
    public boolean exists(long user_id, Date date) throws PersistenceException, InterruptedException {
        return getDay(user_id, date) != null;
    }


    @Override
    public List<Entry> getActiveDays(int year, long user) throws InterruptedException {

        Task<DataSnapshot> task = database.child(DBUtils.getTableM(user)).get();
        List<Entry> entries = new ArrayList<>();

        while (!task.isComplete()) {
            Thread.sleep(10);
        }

        if (task.isSuccessful()) {
            for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                if (dataSnapshot.getKey() != null && dataSnapshot.getKey().endsWith(year + "")) {
                    float month = Float.parseFloat(dataSnapshot.getKey().substring(0, 2));
                    month--;
                    Month monthObj = dataSnapshot.getValue(Month.class);
                    long total = monthObj.getTotal();
                    long active = monthObj.getActive();
                    entries.add(new Entry(month, (long) (((float) active / (float) total) * 100)));

                }
            }
        }

        return entries;
    }

    @Override
    public List<BarEntry> getTrainings(int year, long user) throws InterruptedException {
        Task<DataSnapshot> task = database.child(DBUtils.getTableM(user)).get();
        List<BarEntry> entries = new ArrayList<>();

        while (!task.isComplete()) {
            Thread.sleep(10);
        }

        if (task.isSuccessful()) {
            for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                if (dataSnapshot.getKey() != null && dataSnapshot.getKey().endsWith(year + "")) {
                    float month = Float.parseFloat(dataSnapshot.getKey().substring(0, 2));
                    month--;
                    Month monthObj = dataSnapshot.getValue(Month.class);
                    long total = monthObj.getActive();
                    long strength = monthObj.getStrength();
                    long endurance = monthObj.getEndurance();
                    long relaxation = monthObj.getRelaxation();
                    entries.add(new BarEntry(month, new float[]{
                            (long) (((float) strength / (float) total) * 100),
                            (long) (((float) endurance / (float) total) * 100),
                            (long) (((float) relaxation / (float) total) * 100)}));
                }
            }
        }
        return entries;
    }

    private String getTable(Day day) {
        Date date = day.getCurrent_date();
        /*Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);*/
        //return day.getUser_id() + "/" + month + year + "/" + TABLE_DAYS;
        return day.getUser_id() + "/" + TABLE_DAYS;
    }

    private String getTable(long user) {
        return user + "/" + TABLE_DAYS;
    }
}
