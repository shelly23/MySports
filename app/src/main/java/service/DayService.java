package service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import persistence.dtos.Day;
import persistence.exceptions.InvalidValueException;
import persistence.exceptions.MandatoryValueException;
import persistence.exceptions.PersistenceException;

public interface DayService {

    Day saveDay(Day day) throws PersistenceException, InvalidValueException, MandatoryValueException, IOException, NoSuchAlgorithmException, InterruptedException;

    void delete(List<Day> days) throws PersistenceException;

    List<Day> getAllDays() throws PersistenceException;

    boolean update(Day day) throws PersistenceException, InvalidValueException, MandatoryValueException, IOException, InterruptedException;

    Day getDay(long user_id, Date date) throws PersistenceException, InterruptedException;

    void markDays(long user_id, Calendar dateFrom, Calendar dateTo, Boolean schub, Boolean pause, Boolean active) throws PersistenceException, InvalidValueException, MandatoryValueException, InterruptedException;
}
