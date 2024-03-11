
	 
	/*
     *	This content is generated from the API File Info.
     *	(Alt+Shift+Ctrl+I).
     *
     *	@desc
     *	@file 		log_in_page
     *	@date 		Saturday 01st of October 2022 02:28:35 PM
     *	@title 		Page 1
     *	@author
     *	@keywords
     *	@generator 	Export Kit v1.3.figma
     *
     */


    package com.example.mysports.activities;

    import android.Manifest;
    import android.app.Activity;
    import android.app.AlarmManager;
    import android.app.PendingIntent;
    import android.content.Context;
    import android.content.Intent;
    import android.content.pm.PackageManager;
    import android.graphics.drawable.Drawable;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.CompoundButton;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.RelativeLayout;
    import android.widget.Switch;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.core.app.ActivityCompat;
    import androidx.core.content.ContextCompat;

    import com.example.mysports.R;

    import java.text.DateFormat;
    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.util.Calendar;
    import java.util.Date;
    import java.util.List;

    import persistence.daos.FBConnectionDAO;
    import persistence.daos.FBDayDAO;
    import persistence.daos.FBMonthDAO;
    import persistence.daos.FBSettingsDAO;
    import persistence.daos.FBTherapistDAO;
    import persistence.dtos.Connection;
    import persistence.dtos.Day;
    import persistence.dtos.Settings;
    import persistence.dtos.Therapist;
    import persistence.dtos.User;
    import persistence.exceptions.PersistenceException;
    import persistence.validators.TextValidator;
    import service.AlarmReceiver;
    import service.ConnectionService;
    import service.ConnectionServiceImpl;
    import service.DayService;
    import service.DayServiceImpl;
    import service.SettingsService;
    import service.SettingsServiceImpl;
    import service.TherapistService;
    import service.TherapistServiceImpl;

    public class settingsscreen_activity extends Activity {

        private TextView nutzerIdFeld;

        private User user;

        private String userId;

        private ImageView home;
        private ImageView activity;
        private ImageView game;
        private ImageView statistik;


        private TextView hinweis;
        private TextView therapeut;

        private Button accept;

        private Button reject;

        private Button save;

        private Switch switch_steps;
        private EditText step_goal;
        private Switch switch_duration;
        private EditText duration_goal;
        private Switch switch_trainings;
        private EditText training_goal;
        private Switch switch_chat;
        private Switch switch_game;

        private EditText timeFrom;
        private EditText timeTo;

        private RelativeLayout speechbubble;

        private ConnectionService connectionService;
        private TherapistService therapistService;
        private SettingsService settingsService;

        private DayService dayService;

        private Connection connection;
        private List<Connection> connections;
        private Settings settings;

        private long window;

        private AlarmManager alarmManager;

        private PendingIntent pendingIntent;

        private DateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.settingsscreen);

            nutzerIdFeld = findViewById(R.id.nutzerid_feld);
            home = findViewById(R.id.vector_ek131);
            activity = findViewById(R.id.vector_ek127);
            game = findViewById(R.id.vector_ek125);
            statistik = findViewById(R.id.vector_ek129);
            hinweis = findViewById(R.id.hinweis);
            speechbubble = findViewById(R.id.chat2);
            therapeut = findViewById(R.id.herrlicher);
            reject = findViewById(R.id.reject);
            accept = findViewById(R.id.accept);

            switch_steps = findViewById(R.id.switch1);
            step_goal = findViewById(R.id.step_goal);
            switch_duration = findViewById(R.id.switch2);
            duration_goal = findViewById(R.id.activity_goal);
            switch_trainings = findViewById(R.id.switch3);
            training_goal = findViewById(R.id.training_goal);
            switch_chat = findViewById(R.id.switch4);
            switch_game = findViewById(R.id.switch5);
            save = findViewById(R.id.save);

            Drawable originalBG = duration_goal.getBackground();

            timeFrom = findViewById(R.id.timeFrom);
            timeTo = findViewById(R.id.timeTo);

            user = (User) getIntent().getSerializableExtra("USER");

            connectionService = new ConnectionServiceImpl(new FBConnectionDAO());
            therapistService = new TherapistServiceImpl(new FBTherapistDAO());
            settingsService = new SettingsServiceImpl(new FBSettingsDAO());
            dayService = new DayServiceImpl(new FBDayDAO(), new FBMonthDAO(), new TextValidator());

            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent nextScreen = new Intent(getApplicationContext(), homescreen_activity.class);
                    nextScreen.putExtra("USER", user);
                    nextScreen.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(nextScreen);
                }
            });

            activity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent nextScreen = new Intent(getApplicationContext(), activityscreen_activity.class);
                    nextScreen.putExtra("USER", user);
                    nextScreen.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(nextScreen);
                }
            });

            statistik.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent nextScreen = new Intent(getApplicationContext(), statistik_activity.class);
                    nextScreen.putExtra("USER", user);
                    nextScreen.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(nextScreen);
                }
            });

            try {
                settings = settingsService.getUsersSettings(user.getId());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (!settings.isGame_activated()) {
                game.setActivated(false);
                game.setImageResource(R.drawable.vector_ek125_disabled);
            } else {
                game.setImageResource(R.drawable.vector_ek125);
                game.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Day day = null;
                        try {
                            day = dayService.getDay(user.getId(), new Date(Calendar.getInstance().getTime().getTime()));
                        } catch (PersistenceException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        Intent nextScreen = new Intent(getApplicationContext(), gamescreen_activity.class);
                        nextScreen.putExtra("USER", user);
                        nextScreen.putExtra("SETTINGS", settings);
                        nextScreen.putExtra("DAY", day);
                        nextScreen.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(nextScreen);
                    }
                });
            }

            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        connectionService.set(connection.getId(), true);
                        connections.remove(connection);
                        setFirstConnection(connections);
                        speechbubble.setVisibility(View.INVISIBLE);
                        hinweis.setVisibility(View.VISIBLE);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        connectionService.delete(connection.getId());
                        connections.remove(connection);
                        setFirstConnection(connections);
                        speechbubble.setVisibility(View.INVISIBLE);
                        hinweis.setVisibility(View.VISIBLE);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            switch_steps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    step_goal.setVisibility(step_goal.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
                }
            });

            switch_duration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    duration_goal.setVisibility(duration_goal.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
                }
            });

            switch_trainings.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    training_goal.setVisibility(training_goal.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
                }
            });

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    step_goal.setBackground(originalBG);
                    duration_goal.setBackground(originalBG);
                    training_goal.setBackground(originalBG);
                    timeTo.setBackground(originalBG);
                    timeFrom.setBackground(originalBG);
                    if (validateFields()) {
                        if (switch_steps.isChecked()) {
                            settings.setStep_goal(Long.parseLong(step_goal.getText().toString()));
                        } else {
                            settings.setStep_goal(-1);
                        }
                        if (switch_duration.isChecked()) {
                            settings.setActivity_duration(duration_goal.getText().toString());
                        } else {
                            settings.setActivity_duration("-1");
                        }
                        if (switch_trainings.isChecked()) {
                            settings.setTraining_count(Long.parseLong(training_goal.getText().toString()));
                        } else {
                            settings.setTraining_count(-1);
                        }
                        settings.setChat_activated(switch_chat.isChecked());
                        settings.setGame_activated(switch_game.isChecked());
                        if (!settings.getMessages_from().equals(timeFrom.getText().toString()) || !settings.getMessages_to().equals(timeTo.getText().toString())) {
                            settings.setMessages_from(timeFrom.getText().toString());
                            settings.setMessages_to(timeTo.getText().toString());
                            setAlarm();
                        }
                        try {
                            settings = settingsService.updateSettings(settings);
                            setFields();
                            if (!settings.isGame_activated()) {
                                game.setActivated(false);
                                game.setImageResource(R.drawable.vector_ek125_disabled);
                            } else {
                                game.setImageResource(R.drawable.vector_ek125);
                                game.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Day day = null;
                                        try {
                                            day = dayService.getDay(user.getId(), new Date(Calendar.getInstance().getTime().getTime()));
                                        } catch (PersistenceException | InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }
                                        Intent nextScreen = new Intent(getApplicationContext(), gamescreen_activity.class);
                                        nextScreen.putExtra("USER", user);
                                        nextScreen.putExtra("SETTINGS", settings);
                                        nextScreen.putExtra("DAY", day);
                                        nextScreen.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                        startActivity(nextScreen);
                                    }
                                });
                            }

                            Toast.makeText(getApplicationContext(), "Einstellungen erfolgreich gespeichert.", Toast.LENGTH_LONG).show();

                            /*
                            //Create a View object yourself through inflater
                            view.getContext();
                            LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                            View popupView = inflater.inflate(R.layout.popupscreen, findViewById(R.id.popup1_root));

                            //Specify the length and width through constants
                            int width = LinearLayout.LayoutParams.MATCH_PARENT;
                            int height = LinearLayout.LayoutParams.MATCH_PARENT;

                            //Make Inactive Items Outside Of PopupWindow
                            boolean focusable = true;
                            //Create a window with our parameters
                            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                            //Set the location of the window on the screen
                            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                            //Initialize the elements of our window, install the handler

                            TextView test2 = popupView.findViewById(R.id.titleText);
                            test2.setText("Einstellungen erfolgreich gespeichert.");

                            Button buttonEdit = popupView.findViewById(R.id.messageButton);
                            buttonEdit.setText("Zurück");
                            buttonEdit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    popupWindow.dismiss();
                                }
                            });*/
                        } catch (InterruptedException | PersistenceException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
            //custom code goes here

        }

        private void setAlarm() {

            // Check if notification permission is granted
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // If not granted, request permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 0);
            }

            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(this, AlarmReceiver.class);

            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

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
            //alarmManager.setWindow(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), 10, pendingIntent);

        }

        @Override
        protected void onResume() {
            super.onResume();
            initialize();
        }

        private void initialize() {
            try {
                connections = connectionService.getUsersConnections(user.getId(), true);
                setFirstConnection(connections);
                settings = settingsService.getUsersSettings(user.getId());
                setFields();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        private void setFields() {
            if (settings.getStep_goal() != -1) {
                switch_steps.setChecked(true);
                step_goal.setVisibility(View.VISIBLE);
                step_goal.setText(String.valueOf(settings.getStep_goal()));
            }
            if (!settings.getActivity_duration().equals("-1")) {
                switch_duration.setChecked(true);
                duration_goal.setVisibility(View.VISIBLE);
                duration_goal.setText(String.valueOf(settings.getActivity_duration()));
            }
            if (settings.getTraining_count() != -1) {
                switch_trainings.setChecked(true);
                training_goal.setVisibility(View.VISIBLE);
                training_goal.setText(String.valueOf(settings.getTraining_count()));
            }
            timeFrom.setText(settings.getMessages_from());
            timeTo.setText(settings.getMessages_to());

            switch_chat.setChecked(settings.isChat_activated());
            switch_game.setChecked(settings.isGame_activated());

            userId = String.valueOf(user.getId());
            int length = userId.length();

            for (int i = 0; i < 8 - length; i++) {
                userId = "0" + userId;
            }

            nutzerIdFeld.setText(userId);
        }

        private void setFirstConnection(List<Connection> connections) throws InterruptedException {
            for (Connection connection : connections) {
                if (connection.isPending()) {
                    this.connection = connection;
                    speechbubble.setVisibility(View.VISIBLE);
                    hinweis.setVisibility(View.INVISIBLE);
                    Therapist therapist = therapistService.getTherapist(connection.getTherapist());
                    therapeut.setText(therapist.getForename() + " " + therapist.getSurname() + " möchte sich verbinden.");
                }
            }
        }

        private boolean validateFields() {
            try {
                Long.parseLong(step_goal.getText().toString());
            } catch (NumberFormatException e) {
                step_goal.setBackgroundColor(getResources().getColor(R.color.rectangle_50_colorOP));
                return false;
            }
            try {
                Long.parseLong(training_goal.getText().toString());
            } catch (NumberFormatException e) {
                training_goal.setBackgroundColor(getResources().getColor(R.color.rectangle_50_colorOP));
                return false;
            }
            if (!validateTime(duration_goal.getText().toString())) {
                duration_goal.setBackgroundColor(getResources().getColor(R.color.rectangle_50_colorOP));
                return false;
            }
            if (!validateTime(timeFrom.getText().toString())) {
                timeFrom.setBackgroundColor(getResources().getColor(R.color.rectangle_50_colorOP));
                return false;
            }
            if (!validateTime(timeTo.getText().toString())) {
                timeTo.setBackgroundColor(getResources().getColor(R.color.rectangle_50_colorOP));
                return false;
            }

            return true;
        }

        private boolean validateTime(String input) {
            try {
                String[] res = input.split(":");
                if (res.length != 2) {
                    return false;
                }
                Long.parseLong(res[0]);
                long minutes = Long.parseLong(res[1]);
                if (minutes >= 60) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
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
	
	