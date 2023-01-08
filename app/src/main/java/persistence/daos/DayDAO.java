package persistence.daos;

import java.sql.Date;
import java.util.List;

import persistence.dtos.Day;
import persistence.exceptions.PersistenceException;

public interface DayDAO {

    Day create(Day day) throws PersistenceException;

    List<Day> read() throws PersistenceException;

    void delete(List<Day> days) throws PersistenceException;

    void update(Day day) throws PersistenceException;

    Day getDay(long user_id, Date date) throws PersistenceException;

    boolean exists(long user_id, Date date) throws PersistenceException;

}
