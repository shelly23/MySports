
	 
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

    import android.app.Activity;
    import android.content.Intent;
    import android.graphics.drawable.Drawable;
    import android.os.Bundle;
    import android.view.Gravity;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.widget.Button;
    import android.widget.CompoundButton;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.LinearLayout;
    import android.widget.PopupWindow;
    import android.widget.RelativeLayout;
    import android.widget.Switch;
    import android.widget.TextView;

    import com.example.mysports.R;

    import java.util.List;

    import persistence.daos.FBConnectionDAO;
    import persistence.daos.FBSettingsDAO;
    import persistence.daos.FBTherapistDAO;
    import persistence.dtos.Connection;
    import persistence.dtos.Settings;
    import persistence.dtos.Therapist;
    import persistence.dtos.User;
    import persistence.exceptions.PersistenceException;
    import service.ConnectionService;
    import service.ConnectionServiceImpl;
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

        private Connection connection;
        private List<Connection> connections;
        private Settings settings;

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.settingsscreen);

            nutzerIdFeld = findViewById(R.id.nutzerid_feld);
            home = findViewById(R.id.vector_ek131);
            activity = findViewById(R.id.vector_ek127);
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

            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent nextScreen = new Intent(getApplicationContext(), homescreen_activity.class);
                    nextScreen.putExtra("USER", user);
                    nextScreen.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(nextScreen);
                    finish();
                }
            });

            activity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent nextScreen = new Intent(getApplicationContext(), activityscreen_activity.class);
                    nextScreen.putExtra("USER", user);
                    nextScreen.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(nextScreen);
                    finish();
                }
            });

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
                        settings.setMessages_from(timeFrom.getText().toString());
                        settings.setMessages_to(timeTo.getText().toString());
                        try {
                            settings = settingsService.updateSettings(settings);
                            setFields();

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
                            });
                        } catch (InterruptedException | PersistenceException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
            //custom code goes here

        }

        @Override
        protected void onStart() {
            super.onStart();
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
    }
	
	