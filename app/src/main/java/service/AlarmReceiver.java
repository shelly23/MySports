package service;

import static androidx.core.app.NotificationCompat.EXTRA_NOTIFICATION_ID;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.mysports.R;
import com.example.mysports.activities.log_in_page_activity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import persistence.daos.FBDayDAO;
import persistence.daos.FBMonthDAO;
import persistence.daos.FBSettingsDAO;
import persistence.dtos.Day;
import persistence.dtos.Settings;
import persistence.exceptions.PersistenceException;
import persistence.validators.TextValidator;

public class AlarmReceiver extends BroadcastReceiver {

    private AlarmManager alarmManager;

    private PendingIntent pendingIntent;

    private DateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
    private DayService dayService = new DayServiceImpl(new FBDayDAO(), new FBMonthDAO(), new TextValidator());
    private SettingsService settingsService = new SettingsServiceImpl(new FBSettingsDAO());

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, log_in_page_activity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_IMMUTABLE);
        Intent snoozeIntent = new Intent("snooze");
        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        String text = getText();
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(context, 0, snoozeIntent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "push")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Aktivitätserinnerung")
                .setContentText("Hi! Mit nur " + text + " erreichst Du Dein heutiges Aktivitätsziel! Bist Du bereit?")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Hi! Mit nur " + text + " erreichst Du Dein heutiges Aktivitätsziel! Bist Du bereit?"))
                .addAction(R.drawable.check_vector, "AUF GEHT'S", pendingIntent)
                .addAction(R.drawable.clock_vector, "ERINNERN", snoozePendingIntent);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        // Check if notification permission is granted
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManagerCompat.notify(0, builder.build());
        }

        setAlarm(context);

    }

    private String getText() {

        try {

            String result = "";

            Day day;
            Settings settings;
            try {
                day = dayService.getDay(SingletonUser.getInstance().getUserId(), new Date(System.currentTimeMillis()));
                settings = settingsService.getUsersSettings(SingletonUser.getInstance().getUserId());
            } catch (PersistenceException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (!settings.getActivity_duration().equals("-1")) {
                String goal = settings.getActivity_duration();
                String[] vals = goal.split(":");
                long goalNum = 0;
                goalNum += Long.parseLong(vals[0]) * 3600000 + Long.parseLong(vals[1]) * 60000;
                if (day.getActivity_duration() < goalNum) {
                    long diff = goalNum - day.getActivity_duration();
                    result += simpleDateFormat.format(diff) + " an Trainingszeit";
                }
            }
            if (settings.getTraining_count() != -1) {
                long goal = settings.getTraining_count();
                if (day.getActivity_count() < goal) {
                    long diff = goal - day.getActivity_count();
                    if (result.length() > 0) {
                        result += " oder ";
                    }
                    result += diff + " Trainingseinheiten";
                }
            }
            if (settings.getStep_goal() != -1) {
                long goal = settings.getStep_goal();
                if (day.getStep_count() < goal) {
                    long diff = goal - day.getStep_count();
                    if (result.length() > 0) {
                        result += " oder ";
                    }
                    result += diff + " Schritte";
                }
            }
            return result;
        } catch (Exception e) {
            return "ein bisschen Aufwand";
        }

    }

    private void setSnooze(Context context) {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, this.getClass());

        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        Calendar calendarFrom = Calendar.getInstance();
        calendarFrom.set(Calendar.MINUTE, 10);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendarFrom.getTimeInMillis(), pendingIntent);

    }

    private void setAlarm(Context context) {

        Settings settings;
        try {
            settings = settingsService.getUsersSettings(SingletonUser.getInstance().getUserId());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, this.getClass());

        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        Calendar calendarFrom = Calendar.getInstance();
        String timeFrom = settings.getMessages_from();
        String[] vals = timeFrom.split(":");
        calendarFrom.set(Calendar.HOUR_OF_DAY, Integer.parseInt(vals[0]));
        calendarFrom.set(Calendar.MINUTE, Integer.parseInt(vals[1]));

        Calendar calendarTo = Calendar.getInstance();
        String timeTo = settings.getMessages_to();
        String[] vals1 = timeFrom.split(":");
        calendarTo.set(Calendar.HOUR_OF_DAY, Integer.parseInt(vals1[0]));
        calendarTo.set(Calendar.MINUTE, Integer.parseInt(vals1[1]));

        if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= calendarTo.get(Calendar.HOUR_OF_DAY) && Calendar.getInstance().get(Calendar.MINUTE) >= calendarTo.get(Calendar.MINUTE)) {
            calendarFrom.add(Calendar.DATE, 1);
        }

        alarmManager.setWindow(AlarmManager.RTC_WAKEUP, calendarFrom.getTimeInMillis(), calculateWindow(settings.getMessages_from(), settings.getMessages_to()), pendingIntent);

    }

    private long calculateWindow(String from, String to) {
        try {
            java.sql.Time timeFrom = new java.sql.Time(simpleDateFormat.parse(from).getTime());
            java.sql.Time timeTo = new java.sql.Time(simpleDateFormat.parse(to).getTime());
            return Math.abs(timeTo.getTime() - timeFrom.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


}
