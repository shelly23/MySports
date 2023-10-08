package service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import persistence.daos.DayDAO;
import persistence.dtos.Day;
import persistence.exceptions.InvalidValueException;
import persistence.exceptions.MandatoryValueException;
import persistence.exceptions.PersistenceException;
import persistence.validators.TextValidator;

public class DayServiceImpl implements DayService {

    private static final Logger LOG = LoggerFactory.getLogger(DayServiceImpl.class);

    private final DayDAO DBpersistenceDay;
    private final TextValidator textValidator;

    public DayServiceImpl(DayDAO DBpersistenceDay, TextValidator textValidator) {
        this.DBpersistenceDay = DBpersistenceDay;
        this.textValidator = textValidator;
    }

    @Override
    public Day saveDay(Day day) throws PersistenceException, InvalidValueException, MandatoryValueException, IOException, NoSuchAlgorithmException, InterruptedException {
        Day result = null;
        if (checkDay(day)) {
            if (!this.DBpersistenceDay.exists(day.getUser_id(), day.getCurrent_date())) {
                result = this.DBpersistenceDay.create(day);
                LOG.debug("Saved day {} for user {}", day.getCurrent_date(), day.getUser_id());
            } else {
                LOG.debug("Day {} for user {} already exists.", day.getCurrent_date(), day.getUser_id());
            }
        } else {
            LOG.error("Failed to create day {} for user {}, cause: invalid day", day.getCurrent_date(), day.getUser_id());
        }
        return result;
    }

    private boolean checkDay(Day day) throws MandatoryValueException, InvalidValueException, IOException {
        return true;
    }

    private boolean checkDays(Calendar from, Calendar to) throws MandatoryValueException, InvalidValueException {
        Calendar now = Calendar.getInstance();
        if (from == null) {
            throw new MandatoryValueException("dateFrom");
        }
        if (to == null) {
            throw new MandatoryValueException("dateTo");
        }
        if (from.after(to)) {
            throw new InvalidValueException("dateFrom must not be after dateTo");
        }
        if (to.before(from)) {
            throw new InvalidValueException("dateTo must not be before dateFrom");
        }
        if (to.after(now)) {
            throw new InvalidValueException("dateTo must not be after today");
        }
        if (from.after(now)) {
            throw new InvalidValueException("dateFrom must not be after today");
        }
        return true;
    }

    @Override
    public void delete(List<Day> days) throws PersistenceException {

    }

    @Override
    public List<Day> getAllDays() throws PersistenceException {
        return null;
    }

    @Override
    public boolean update(Day day) throws PersistenceException, InvalidValueException, MandatoryValueException, IOException, InterruptedException {
        if (checkDay(day)) {
            Day dayToBeUpdated = this.DBpersistenceDay.getDay(day.getUser_id(), day.getCurrent_date());
            dayToBeUpdated.setSteps(day.getSteps());
            dayToBeUpdated.setSteps_start(day.getSteps_start());
            dayToBeUpdated.setActive(day.isActive());
            dayToBeUpdated.setAttack(day.isAttack());
            this.DBpersistenceDay.update(dayToBeUpdated);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Day getDay(long user_id, Date date) throws PersistenceException, InterruptedException {
        if (date != null) {
            return this.DBpersistenceDay.getDay(user_id, date);
        }
        return null;
    }

    @Override
    public void markDays(long user_id, Calendar dateFrom, Calendar dateTo, Boolean schub, Boolean pause, Boolean active) throws PersistenceException, InvalidValueException, MandatoryValueException, InterruptedException {

        if (checkDays(dateFrom, dateTo)) {

            while (dateFrom.compareTo(dateTo) <= 0) {

                Date date = new Date(dateFrom.getTime().getTime());

                Day dayToBeMarked = this.DBpersistenceDay.getDay(user_id, date);

                if (dayToBeMarked == null) {
                    dayToBeMarked = this.DBpersistenceDay.create(new Day(0, -1, new Date(dateFrom.getTime().getTime()), Boolean.TRUE.equals(active), Boolean.TRUE.equals(pause), Boolean.TRUE.equals(schub), 0, user_id));
                } else {

                    if (schub != null) {
                        dayToBeMarked.setAttack(schub);
                    }
                    if (active != null) {
                        dayToBeMarked.setActive(active);
                    }
                    if (pause != null) {
                        dayToBeMarked.setPause(pause);
                    }

                    this.DBpersistenceDay.update(dayToBeMarked);
                }

                LOG.debug("Day {} successfully marked: Attack: {} and Active: {} and Pause: {}", date, schub != null ? schub.toString() : "-", active != null ? active.toString() : "-", pause != null ? pause.toString() : "-");

                dateFrom.add(Calendar.DATE, 1);
            }

        }

    }


}
