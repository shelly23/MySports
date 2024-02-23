
	 
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
    import android.app.NotificationChannel;
    import android.app.NotificationManager;
    import android.content.Intent;
    import android.content.pm.PackageManager;
    import android.graphics.drawable.Drawable;
    import android.os.Build;
    import android.os.Bundle;
    import android.util.Patterns;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ProgressBar;
    import android.widget.TextView;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.app.ActivityCompat;
    import androidx.core.content.ContextCompat;

    import com.example.mysports.R;
    import com.example.mysports.databinding.LogInPageBinding;

    import java.io.IOException;
    import java.security.NoSuchAlgorithmException;
    import java.util.Date;

    import persistence.daos.FBDayDAO;
    import persistence.daos.FBMonthDAO;
    import persistence.daos.FBUserDAO;
    import persistence.dtos.Day;
    import persistence.dtos.User;
    import persistence.exceptions.InvalidValueException;
    import persistence.exceptions.MandatoryValueException;
    import persistence.exceptions.PersistenceException;
    import persistence.validators.TextValidator;
    import service.DayService;
    import service.DayServiceImpl;
    import service.UserService;
    import service.UserServiceImpl;

    public class log_in_page_activity extends AppCompatActivity {

        private LogInPageBinding binding;

        private UserService userService;
        private DayService dayService;

        private Day currentDay;

        private TextView _don_t_have_an_account_;
        private Button log_in;
        private EditText email;
        private TextView password;
        private TextView error_field;

        private User user;

        private ProgressBar spinner;

        public log_in_page_activity() throws PersistenceException {
        }

        @Override
        protected void onResume() {
            super.onResume();
            spinner.setVisibility(View.GONE);
            log_in.setText(R.string.log_in_string);
            user = null;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            binding = LogInPageBinding.inflate(getLayoutInflater());

            setContentView(binding.getRoot());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Create a notification channel for Oreo and higher versions
                NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }

            // Check if notification permission is granted
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // If not granted, request permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 0);
            }

            _don_t_have_an_account_ = findViewById(R.id.don_t_have_an_account_);
            log_in = findViewById(R.id.log_in);
            email = findViewById(R.id.email_address);
            error_field = findViewById(R.id.error_field);
            password = findViewById(R.id.password_ek1);
            spinner = findViewById(R.id.progressBar1);

            spinner.setVisibility(View.GONE);
            log_in.setText(R.string.log_in_string);


            Drawable originalDrawablePW = password.getBackground();
            Drawable originalDrawableEM = email.getBackground();
            Drawable originalDrawableEF = error_field.getBackground();

            userService = new UserServiceImpl(new FBUserDAO(), new TextValidator());
            dayService = new DayServiceImpl(new FBDayDAO(), new FBMonthDAO(), new TextValidator());

            _don_t_have_an_account_.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    Intent nextScreen = new Intent(getApplicationContext(), register_page_activity.class);
                    startActivity(nextScreen);
                    finish();

                }
            });

            log_in.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    error_field.setText("");
                    error_field.setBackground(originalDrawableEF);
                    email.setBackground(originalDrawableEM);
                    password.setBackground(originalDrawablePW);
                    String email_text = email.getText().toString();
                    String password_text = password.getText().toString();

                    if (validateCredentials(email_text, password_text)) {
                        //check database for login-data
                        user = null;
                        try {
                            spinner.setVisibility(View.VISIBLE);

                            user = userService.loginUser(email_text, password_text);
                            if (user != null) {
                                Date current = new Date(System.currentTimeMillis());
                                currentDay = dayService.getDay(user.getId(), current);
                                if (currentDay == null) {
                                    currentDay = dayService.saveDay(new Day(0, -1, new Date(System.currentTimeMillis()), false, 0, 0, 0,
                                            false, false, 0, user.getId()));
                                }
                            }
                        } catch (InvalidValueException | MandatoryValueException | IOException |
                                 PersistenceException | NoSuchAlgorithmException |
                                 InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (user != null) {
                            Intent nextScreen = new Intent(getApplicationContext(), homescreen_activity.class);
                            nextScreen.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            nextScreen.putExtra("USER", user);
                            startActivity(nextScreen);
                            finish();

                        } else {
                            spinner.setVisibility(View.INVISIBLE);
                            error_field.setText(R.string.login_incorrect);
                            error_field.setBackgroundColor(getResources().getColor(R.color.whiteOP));
                        }

                    }

                }
            });

        }

        private boolean validateCredentials(String email, String password) {
            if (email.isEmpty()) {
                error_field.setText(R.string.email_missing);
                error_field.setBackgroundColor(getResources().getColor(R.color.whiteOP));
                this.email.setBackgroundColor(getResources().getColor(R.color.rectangle_50_colorOP));
                return false;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                error_field.setText(R.string.email_invalide);
                error_field.setBackgroundColor(getResources().getColor(R.color.whiteOP));
                this.email.setBackgroundColor(getResources().getColor(R.color.rectangle_50_colorOP));
                return false;
            } else if (password.isEmpty()) {
                error_field.setText(R.string.password_missing);
                error_field.setBackgroundColor(getResources().getColor(R.color.whiteOP));
                this.password.setBackgroundColor(getResources().getColor(R.color.rectangle_50_colorOP));
                return false;
            }
            return true;
        }
    }
	
	