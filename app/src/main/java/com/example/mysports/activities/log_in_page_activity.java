
	 
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
    import android.util.Patterns;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.example.mysports.R;

    import java.io.IOException;
    import java.security.NoSuchAlgorithmException;

    import persistence.daos.DBUserDAO;
    import persistence.dtos.User;
    import persistence.exceptions.InvalidValueException;
    import persistence.exceptions.MandatoryValueException;
    import persistence.exceptions.PersistenceException;
    import persistence.validators.TextValidator;
    import service.ConnectionServiceDB;
    import service.UserService;
    import service.UserServiceImpl;

    public class log_in_page_activity extends Activity {

        private UserService userService;

        private TextView _don_t_have_an_account_;
        private Button log_in;
        private EditText email;
        private View email_view;
        private View password_view;
        private TextView password_ek1;
        private TextView error_field;

        public log_in_page_activity() throws PersistenceException {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.log_in_page);

            _don_t_have_an_account_ = findViewById(R.id._don_t_have_an_account_);
            log_in = findViewById(R.id.log_in);
            email = findViewById(R.id.email_address);
            error_field = findViewById(R.id.error_field);
            email_view = findViewById(R.id.email);
            password_view = findViewById(R.id.password);
            password_ek1 = findViewById(R.id.password_ek1);

            try {
                userService = new UserServiceImpl(new DBUserDAO(new ConnectionServiceDB(), getApplicationContext()), new TextValidator());
            } catch (PersistenceException e) {
                e.printStackTrace();
            }

            _don_t_have_an_account_.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    Intent nextScreen = new Intent(getApplicationContext(), register_page_activity.class);
                    startActivity(nextScreen);

                }
            });

            log_in.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    error_field.setText("");
                    email_view.setBackgroundColor(getResources().getColor(R.color.log_in_color));
                    password_view.setBackgroundColor(getResources().getColor(R.color.log_in_color));
                    String email_text = email.getText().toString();
                    String password_text = password_ek1.getText().toString();

                    if (validateCredentials(email_text, password_text)) {
                        //check database for login-data
                        User user = null;
                        try {
                            user = userService.loginUser(email_text, password_text);
                        } catch (InvalidValueException | MandatoryValueException | IOException | PersistenceException | NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }
                        if (user != null) {
                            Intent nextScreen = new Intent(getApplicationContext(), homescreen_activity.class);
                            startActivity(nextScreen);
                        } else {
                            error_field.setText(R.string.login_incorrect);
                        }

                    }

                }
            });

        }

        private boolean validateCredentials(String email, String password) {
            if (email.isEmpty()) {
                error_field.setText(R.string.email_missing);
                email_view.setBackgroundColor(getResources().getColor(R.color.rectangle_50_color));
                return false;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                error_field.setText(R.string.email_invalide);
                email_view.setBackgroundColor(getResources().getColor(R.color.rectangle_50_color));
                return false;
            } else if (password.isEmpty()) {
                error_field.setText(R.string.password_missing);
                password_view.setBackgroundColor(getResources().getColor(R.color.rectangle_50_color));
                return false;
            }
            return true;
        }
    }
	
	