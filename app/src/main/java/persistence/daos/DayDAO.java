package persistence.daos;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.util.Date;
import java.util.List;

import persistence.dtos.Day;
import persistence.exceptions.PersistenceException;

public interface DayDAO {

    Day create(Day day) throws PersistenceException, InterruptedException;

    List<Day> read(long user) throws PersistenceException, InterruptedException;

    void delete(List<Day> days) throws PersistenceException;

    void update(Day day) throws PersistenceException, InterruptedException;

    Day getDay(long user_id, Date date) throws PersistenceException, InterruptedException;

    boolean exists(long user_id, Date date) throws PersistenceException, InterruptedException;

    List<Entry> getActiveDays(int year, long user) throws InterruptedException;

    List<BarEntry> getTrainings(int year, long user) throws InterruptedException;

}
