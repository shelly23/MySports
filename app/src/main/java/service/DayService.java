package service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import persistence.dtos.Day;
import persistence.dtos.User;
import persistence.exceptions.InvalidValueException;
import persistence.exceptions.MandatoryValueException;
import persistence.exceptions.PersistenceException;

public interface DayService {

    Day saveDay(Day day) throws PersistenceException, InvalidValueException, MandatoryValueException, IOException, NoSuchAlgorithmException;

    void delete(List<Day> days) throws PersistenceException;

    List<Day> getAllDays() throws PersistenceException;

    boolean update(Day day) throws PersistenceException, InvalidValueException, MandatoryValueException, IOException;

    Day getDay(long user_id, Date date) throws PersistenceException;

    void markDays(long user_id, Calendar dateFrom, Calendar dateTo, Boolean schub, Boolean active) throws PersistenceException, InvalidValueException, MandatoryValueException;
}
