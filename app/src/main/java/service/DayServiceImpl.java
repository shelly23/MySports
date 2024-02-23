package service;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import persistence.daos.DayDAO;
import persistence.daos.MonthDAO;
import persistence.dtos.Day;
import persistence.dtos.Type;
import persistence.exceptions.InvalidValueException;
import persistence.exceptions.MandatoryValueException;
import persistence.exceptions.PersistenceException;
import persistence.validators.TextValidator;

public class DayServiceImpl implements DayService {

    private static final Logger LOG = LoggerFactory.getLogger(DayServiceImpl.class);

    private final DayDAO FBpersistenceDay;

    private final MonthDAO FBpersistenceMonth;

    private final TextValidator textValidator;

    public DayServiceImpl(DayDAO FBpersistenceDay, MonthDAO FBpersistenceMonth, TextValidator textValidator) {
        this.FBpersistenceDay = FBpersistenceDay;
        this.FBpersistenceMonth = FBpersistenceMonth;
        this.textValidator = textValidator;
    }

    @Override
    public Day saveDay(Day day) throws PersistenceException, InvalidValueException, MandatoryValueException, IOException, NoSuchAlgorithmException, InterruptedException {
        Day result = null;
        String month = getMonth(day);
        if (checkDay(day)) {
            if (!this.FBpersistenceDay.exists(day.getUser_id(), day.getCurrent_date())) {
                result = this.FBpersistenceDay.create(day);
                LOG.debug("Saved day {} for user {}", day.getCurrent_date(), day.getUser_id());
                if (!this.FBpersistenceMonth.existsMonth(day.getUser_id(), month)) {
                    this.FBpersistenceMonth.createMonth(day.getUser_id(), month);
                }
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
    public boolean update(Day day, boolean setActive, Type type) throws PersistenceException, InvalidValueException, MandatoryValueException, IOException, InterruptedException {
        if (checkDay(day)) {
            Day dayToBeUpdated = this.FBpersistenceDay.getDay(day.getUser_id(), day.getCurrent_date());
            dayToBeUpdated.setSteps(day.getSteps());
            dayToBeUpdated.setSteps_start(day.getSteps_start());
            dayToBeUpdated.setActive(day.isActive());
            dayToBeUpdated.setAttack(day.isAttack());
            dayToBeUpdated.setActivity_count(day.getActivity_count());
            dayToBeUpdated.setActivity_duration(day.getActivity_duration());
            dayToBeUpdated.setStep_count(day.getStep_count());
            this.FBpersistenceDay.update(dayToBeUpdated);

            if (setActive) {
                String month = getMonth(day);
                this.FBpersistenceMonth.updateMonth(day.getUser_id(), month, type);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Day getDay(long user_id, Date date) throws PersistenceException, InterruptedException {
        if (date != null) {
            return this.FBpersistenceDay.getDay(user_id, date);
        }
        return null;
    }

    @Override
    public void markDays(long user_id, Calendar dateFrom, Calendar dateTo, Boolean schub, Boolean pause, Boolean active) throws PersistenceException, InvalidValueException, MandatoryValueException, InterruptedException {

        if (checkDays(dateFrom, dateTo)) {

            while (dateFrom.compareTo(dateTo) <= 0) {

                Date date = new Date(dateFrom.getTime().getTime());

                Day dayToBeMarked = this.FBpersistenceDay.getDay(user_id, date);

                if (dayToBeMarked == null) {
                    dayToBeMarked = this.FBpersistenceDay.create(new Day(0, -1, new Date(dateFrom.getTime().getTime()), Boolean.TRUE.equals(active), 0, 0, 0, Boolean.TRUE.equals(pause), Boolean.TRUE.equals(schub), 0, user_id));
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

                    this.FBpersistenceDay.update(dayToBeMarked);
                }

                LOG.debug("Day {} successfully marked: Attack: {} and Active: {} and Pause: {}", date, schub != null ? schub.toString() : "-", active != null ? active.toString() : "-", pause != null ? pause.toString() : "-");

                dateFrom.add(Calendar.DATE, 1);
            }

        }

    }

    public List<Entry> getActiveDays(int year, long user) throws InterruptedException {
        return FBpersistenceDay.getActiveDays(year, user);
    }

    public List<BarEntry> getTrainings(int year, long user) throws InterruptedException {
        return FBpersistenceDay.getTrainings(year, user);
    }

    private String getMonth(Day day) {
        String month = "";
        long mo = day.getCurrent_date().getMonth() + 1;
        long ye = day.getCurrent_date().getYear();
        String yearStr = ye + "";
        yearStr = yearStr.substring(1);
        String moStr = mo + "";
        if (mo < 10) {
            moStr = "0" + moStr;
        }
        month = moStr + yearStr;
        return month;
    }

}
