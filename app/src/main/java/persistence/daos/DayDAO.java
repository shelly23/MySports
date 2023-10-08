package persistence.daos;

import java.util.Date;
import java.util.List;

import persistence.dtos.Day;
import persistence.exceptions.PersistenceException;

public interface DayDAO {

    Day create(Day day) throws PersistenceException, InterruptedException;

    List<Day> read() throws PersistenceException, InterruptedException;

    void delete(List<Day> days) throws PersistenceException;

    void update(Day day) throws PersistenceException, InterruptedException;

    Day getDay(long user_id, Date date) throws PersistenceException, InterruptedException;

    boolean exists(long user_id, Date date) throws PersistenceException;

}
