
	 
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
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.ProgressBar;
    import android.widget.TextView;

    import com.example.mysports.R;

    import java.io.IOException;
    import java.util.Date;

    import persistence.daos.FBActivityDAO;
    import persistence.daos.FBDayDAO;
    import persistence.daos.FBMonthDAO;
    import persistence.daos.FBSettingsDAO;
    import persistence.dtos.Connection;
    import persistence.dtos.Day;
    import persistence.dtos.Feedback;
    import persistence.dtos.Settings;
    import persistence.dtos.Type;
    import persistence.dtos.User;
    import persistence.exceptions.InvalidValueException;
    import persistence.exceptions.MandatoryValueException;
    import persistence.exceptions.PersistenceException;
    import persistence.validators.TextValidator;
    import service.ActivityService;
    import service.ActivityServiceImpl;
    import service.DayService;
    import service.DayServiceImpl;
    import service.SettingsService;
    import service.SettingsServiceImpl;

    public class workout_ende_activity extends Activity {

        private User user;

        private Type type;

        private Day day;

        private persistence.dtos.Activity activity;

        private TextView title;

        private TextView points_view;

        private ImageView points_edit;

        private EditText edit_points;

        private Connection connection;

        private TextView dauer;

        private ProgressBar spinner;

        private ImageView duration_good;
        private ImageView duration_medium;
        private ImageView duration_bad;

        private ImageView total_good;
        private ImageView total_medium;
        private ImageView total_bad;

        private ImageView breaks_good;
        private ImageView breaks_medium;
        private ImageView breaks_bad;

        private Settings settings;

        private Button save;

        private Feedback totalFeedback = null;
        private Feedback breaksFeedback = null;

        private Feedback durationFeedback = null;

        private int points;

        private ActivityService activityService;
        private DayService dayService;

        private SettingsService settingsService;

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.workout_ende);

            user = (User) getIntent().getSerializableExtra("USER");
            type = (Type) getIntent().getSerializableExtra("TYPE");
            activity = (persistence.dtos.Activity) getIntent().getSerializableExtra("ACTIVITY");
            connection = (persistence.dtos.Connection) getIntent().getSerializableExtra("CONNECTION");

            activityService = new ActivityServiceImpl(new FBActivityDAO());
            dayService = new DayServiceImpl(new FBDayDAO(), new FBMonthDAO(), new TextValidator());
            settingsService = new SettingsServiceImpl(new FBSettingsDAO());

            title = findViewById(R.id.title);
            dauer = findViewById(R.id.time);
            duration_good = findViewById(R.id.good);
            duration_medium = findViewById(R.id.neutral);
            duration_bad = findViewById(R.id.bad);

            total_good = findViewById(R.id.good_1);
            total_medium = findViewById(R.id.neutral_1);
            total_bad = findViewById(R.id.bad_1);

            breaks_good = findViewById(R.id.good_2);
            breaks_medium = findViewById(R.id.neutral_2);
            breaks_bad = findViewById(R.id.bad_2);

            points_view = findViewById(R.id.points);
            edit_points = findViewById(R.id.edit_points);
            save = findViewById(R.id.save);

            spinner = findViewById(R.id.progressBar);
            spinner.setVisibility(View.INVISIBLE);

            title.setText(title.getText().toString().replace("Training", type.equals(Type.endurance) ? "Ausdauertraining" : "Krafttraining"));
            long timeInMillis = activity.getDuration();
            int secs = (int) (timeInMillis / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            dauer.setText("" + String.format("%02d", mins) + ":"
                    + String.format("%02d", secs));
            durationFeedback = getDurationFeedback();

            if (durationFeedback.equals(Feedback.good)) {
                duration_good.setVisibility(View.VISIBLE);
            }
            if (durationFeedback.equals(Feedback.medium)) {
                duration_medium.setVisibility(View.VISIBLE);
            }
            if (durationFeedback.equals(Feedback.bad)) {
                duration_bad.setVisibility(View.VISIBLE);
            }

            total_bad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    totalFeedback = Feedback.bad;
                    total_bad.setAlpha(1.0F);
                    total_good.setAlpha(0.25F);
                    total_medium.setAlpha(0.25F);
                    if (breaksFeedback != null) {
                        updatePoints();
                    }
                }
            });
            total_medium.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    totalFeedback = Feedback.medium;
                    total_medium.setAlpha(1.0F);
                    total_good.setAlpha(0.25F);
                    total_bad.setAlpha(0.25F);
                    if (breaksFeedback != null) {
                        updatePoints();
                    }
                }
            });
            total_good.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    totalFeedback = Feedback.good;
                    total_good.setAlpha(1.0F);
                    total_bad.setAlpha(0.25F);
                    total_medium.setAlpha(0.25F);
                    if (breaksFeedback != null) {
                        updatePoints();
                    }
                }
            });

            breaks_bad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    breaksFeedback = Feedback.bad;
                    breaks_bad.setAlpha(1.0F);
                    breaks_good.setAlpha(0.25F);
                    breaks_medium.setAlpha(0.25F);
                    if (totalFeedback != null) {
                        updatePoints();
                    }
                }
            });
            breaks_medium.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    breaksFeedback = Feedback.medium;
                    breaks_medium.setAlpha(1.0F);
                    breaks_good.setAlpha(0.25F);
                    breaks_bad.setAlpha(0.25F);
                    if (totalFeedback != null) {
                        updatePoints();
                    }
                }
            });
            breaks_good.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    breaksFeedback = Feedback.good;
                    breaks_good.setAlpha(1.0F);
                    breaks_bad.setAlpha(0.25F);
                    breaks_medium.setAlpha(0.25F);
                    if (totalFeedback != null) {
                        updatePoints();
                    }
                }
            });

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.setFeedback_breaks(breaksFeedback);
                    activity.setFeedback_total(totalFeedback);
                    activity.setPoints_chosen(Long.parseLong(edit_points.getText().toString()));
                    try {
                        spinner.setVisibility(View.VISIBLE);
                        boolean setActive = false;

                        day = dayService.getDay(user.getId(), new Date(System.currentTimeMillis()));
                        activity.setDate_id(day.getId());
                        activity.setDate(day.getCurrent_date());
                        activityService.setActivity(activity);
                        day.setActivity_duration(day.getActivity_duration() + timeInMillis);
                        day.setActivity_count(day.getActivity_count() + 1);
                        settings = settingsService.getUsersSettings(user.getId());
                        if (settings.getTraining_count() != -1 && settings.getTraining_count() <= day.getActivity_count()) {
                            if (!day.isActive()) {
                                day.setActive(true);
                                setActive = true;
                            }
                        }

                        if (!settings.getActivity_duration().equals("-1") && checkDuration(settings.getActivity_duration(), day.getActivity_duration())) {
                            if (!day.isActive()) {
                                day.setActive(true);
                                setActive = true;
                            }
                        }

                        dayService.update(day, setActive, activity.getType());
                    } catch (InterruptedException | PersistenceException | MandatoryValueException |
                             IOException | InvalidValueException e) {
                        throw new RuntimeException(e);
                    }
                    spinner.setVisibility(View.INVISIBLE);
                    Intent nextScreen = new Intent(getApplicationContext(), homescreen_activity.class);
                    nextScreen.putExtra("USER", user);
                    nextScreen.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(nextScreen);
                    finish();
                }
            });

            //custom code goes here

        }


        private Feedback getDurationFeedback() {
            String recommendedDuration = connection.getRecommendedDuration().replace(" ", "");
            String[] values = recommendedDuration.split("-");
            long duration = activity.getDuration();
            long val1 = Long.parseLong(values[0]);
            val1 = val1 * 60000;
            long val2 = Long.parseLong(values[1]);
            val2 = val2 * 60000;
            if (Long.min(val1, val2) <= duration && Long.max(val1, val2) >= duration) {
                return Feedback.good;
            }
            if (Long.min(val1, val2) * 0.75 <= duration && Long.max(val1, val2) * 1.25 >= duration) {
                return Feedback.medium;
            }
            return Feedback.bad;
        }

        private void updatePoints() {
            points = 0;

            int duration = 0;
            int total = 0;
            int breaks = 0;
            int breaks_1 = 10;

            switch (durationFeedback) {
                case good:
                    duration = 30;
                    break;
                case medium:
                    duration = 15;
                    break;
                default:
                    break;
            }

            switch (totalFeedback) {
                case good:
                    total = 30;
                    break;
                case medium:
                    total = 15;
                    break;
                default:
                    break;
            }

            switch (breaksFeedback) {
                case good:
                    breaks = 30;
                    break;
                case medium:
                    breaks = 15;
                    break;
                default:
                    break;
            }

            breaks_1 = (int) (breaks_1 - activity.getPausen());
            if (breaks_1 < 0) {
                breaks_1 = 0;
            }

            points = duration + total + breaks + breaks_1;

            edit_points.setText(String.valueOf(points));
            edit_points.setVisibility(View.VISIBLE);
            activity.setPoints_rec(points);
        }

        private boolean checkDuration(String goal, long millis) {
            goal = goal.replace(" ", "");
            String[] vals = goal.split(":");
            long hours = Long.parseLong(vals[0]);
            long minutes = Long.parseLong(vals[1]);
            long minutes1 = millis / 60000;
            long hours1 = minutes1 / 60;
            minutes1 = minutes1 % 60;

            if (hours1 > hours) {
                return true;
            }
            if (hours1 == hours && minutes1 >= minutes) {
                return true;
            }
            return false;
        }
    }
	
	