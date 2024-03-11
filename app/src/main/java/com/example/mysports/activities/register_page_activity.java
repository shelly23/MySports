
	 
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
    import android.app.DatePickerDialog;
    import android.graphics.drawable.Drawable;
    import android.os.Bundle;
    import android.util.Patterns;
    import android.view.View;
    import android.widget.Button;
    import android.widget.DatePicker;
    import android.widget.EditText;
    import android.widget.ProgressBar;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.example.mysports.R;

    import java.io.IOException;
    import java.security.NoSuchAlgorithmException;
    import java.text.SimpleDateFormat;
    import java.util.Calendar;
    import java.util.Date;
    import java.util.Locale;

    import persistence.daos.FBGame_ProgressDAO;
    import persistence.daos.FBSettingsDAO;
    import persistence.daos.FBUserDAO;
    import persistence.dtos.User;
    import persistence.exceptions.InvalidValueException;
    import persistence.exceptions.MandatoryValueException;
    import persistence.exceptions.PersistenceException;
    import persistence.validators.TextValidator;
    import service.GameProgressService;
    import service.GameProgressServiceImpl;
    import service.SettingsService;
    import service.SettingsServiceImpl;
    import service.UserService;
    import service.UserServiceImpl;

    public class register_page_activity extends Activity {

        private TextView mysport_ek1;

        private EditText nachname;
        private EditText vorname;
        private EditText edss;
        private EditText birthdate;
        private EditText email_adresse;
        private EditText passwort;
        private EditText passwort_wiederholen;
        private Button register;
        private TextView error_field;

        private boolean birthdate_set = false;

        private boolean edss_set = false;

        private ProgressBar spinner;

        private UserService userService;

        private SettingsService settingsService;

        private GameProgressService gameProgressService;

        private boolean back = false;


        public register_page_activity() throws PersistenceException {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.register_page);

            mysport_ek1 = findViewById(R.id.mysport_ek1);
            register = findViewById(R.id.register);
            nachname = findViewById(R.id.nachname);
            edss = findViewById(R.id.edss);
            birthdate = findViewById(R.id.birthdate);
            vorname = findViewById(R.id.vorname);
            email_adresse = findViewById(R.id.email);
            passwort = findViewById(R.id.password1);
            passwort_wiederholen = findViewById(R.id.password2);
            error_field = findViewById(R.id.error_field);
            spinner = findViewById(R.id.progressBar3);

            spinner.setVisibility(View.GONE);

            Drawable originalDrawableVN = vorname.getBackground();
            Drawable originalDrawableNN = nachname.getBackground();
            Drawable originalDrawableED = edss.getBackground();
            Drawable originalDrawableBD = birthdate.getBackground();
            Drawable originalDrawableEM = email_adresse.getBackground();
            Drawable originalDrawablePW = passwort.getBackground();
            Drawable originalDrawablePWW = passwort_wiederholen.getBackground();

            userService = new UserServiceImpl(new FBUserDAO(), new TextValidator());
            settingsService = new SettingsServiceImpl(new FBSettingsDAO());
            gameProgressService = new GameProgressServiceImpl(new FBGame_ProgressDAO());

            Calendar birthdateCal = Calendar.getInstance();

            DatePickerDialog.OnDateSetListener birthdatePicker = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    birthdateCal.set(Calendar.YEAR, year);
                    birthdateCal.set(Calendar.MONTH, month);
                    birthdateCal.set(Calendar.DAY_OF_MONTH, day);
                    updateLabel(birthdate, birthdateCal);
                    birthdate_set = true;
                }
            };

            birthdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatePickerDialog(register_page_activity.this, birthdatePicker, birthdateCal.get(Calendar.YEAR), birthdateCal.get(Calendar.MONTH), birthdateCal.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (back) {
                        finish();
                    } else {
                        error_field.setText("");
                        vorname.setBackground(originalDrawableVN);
                        nachname.setBackground(originalDrawableNN);
                        edss.setBackground(originalDrawableED);
                        birthdate.setBackground(originalDrawableBD);
                        email_adresse.setBackground(originalDrawableEM);
                        passwort.setBackground(originalDrawablePW);
                        passwort_wiederholen.setBackground(originalDrawablePWW);
                        spinner.setVisibility(View.VISIBLE);

                        String vorname_text = vorname.getText().toString();
                        String nachname_text = nachname.getText().toString();
                        String edss_text = edss.getText().toString();
                        String email_text = email_adresse.getText().toString();
                        String passwort_text = passwort.getText().toString();
                        String passwort_wiederholen_text = passwort_wiederholen.getText().toString();

                        if (validateData(vorname_text, nachname_text, email_text, passwort_text, passwort_wiederholen_text)) {
                            // save

                            try {
                                long id = userService.saveUser(new User(vorname_text, nachname_text, email_text, passwort_text, edss_text.isEmpty() ? -1 : Double.parseDouble(edss_text), birthdate.getText().toString().isEmpty() ? null : new Date(birthdateCal.getTime().getTime())));
                                settingsService.createSettings(id);
                                gameProgressService.createGame_Progress(id);
                            } catch (PersistenceException | InvalidValueException |
                                     MandatoryValueException | IOException |
                                     NoSuchAlgorithmException | InterruptedException e) {
                                e.printStackTrace();
                            }


                            Toast.makeText(getApplicationContext(), "Nutzer " + email_text + " erfolgreich angelegt.", Toast.LENGTH_LONG).show();
                            spinner.setVisibility(View.INVISIBLE);
                            back = true;
                            register.setText("Zurück");

                        /*
                        //Create a View object yourself through inflater
                        view.getContext();
                        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                        View popupView = inflater.inflate(R.layout.popupscreen, null);

                        //Specify the length and width through constants
                        int width = LinearLayout.LayoutParams.MATCH_PARENT;
                        int height = LinearLayout.LayoutParams.MATCH_PARENT;

                        //Make Inactive Items Outside Of PopupWindow
                        boolean focusable = true;
                        spinner.setVisibility(View.GONE);

                        //Create a window with our parameters
                        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                finish();
                            }
                        });

                        //Set the location of the window on the screen
                        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                        //Initialize the elements of our window, install the handler

                        TextView test2 = popupView.findViewById(R.id.titleText);
                        test2.setText("Nutzer " + email_text + " erfolgreich angelegt.");

                        Button buttonEdit = popupView.findViewById(R.id.messageButton);
                        buttonEdit.setText("Zurück zum Login");
                        buttonEdit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();
                            }
                        });*/
                        }
                    }

                }
            });

            //custom code goes here

        }

        private boolean validateData(String vorname, String nachname, String email, String passwort1, String passwort2) {
            if (vorname.isEmpty()) {
                error_field.setText(R.string.vorname_missing);
                error_field.setBackgroundColor(getResources().getColor(R.color.whiteOP));
                this.vorname.setBackgroundColor(getResources().getColor(R.color.rectangle_50_colorOP));
                spinner.setVisibility(View.INVISIBLE);
                return false;
            } else if (nachname.isEmpty()) {
                error_field.setText(R.string.nachname_missing);
                error_field.setBackgroundColor(getResources().getColor(R.color.whiteOP));
                this.nachname.setBackgroundColor(getResources().getColor(R.color.rectangle_50_colorOP));
                spinner.setVisibility(View.INVISIBLE);
                return false;
            } else if (email.isEmpty()) {
                error_field.setText(R.string.email_missing);
                error_field.setBackgroundColor(getResources().getColor(R.color.whiteOP));
                this.email_adresse.setBackgroundColor(getResources().getColor(R.color.rectangle_50_colorOP));
                spinner.setVisibility(View.INVISIBLE);
                return false;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                error_field.setText(R.string.email_invalide);
                error_field.setBackgroundColor(getResources().getColor(R.color.whiteOP));
                this.email_adresse.setBackgroundColor(getResources().getColor(R.color.rectangle_50_colorOP));
                spinner.setVisibility(View.INVISIBLE);
                return false;
            } else if (passwort1.isEmpty()) {
                error_field.setText(R.string.password_missing);
                error_field.setBackgroundColor(getResources().getColor(R.color.whiteOP));
                this.passwort.setBackgroundColor(getResources().getColor(R.color.rectangle_50_colorOP));
                spinner.setVisibility(View.INVISIBLE);
                return false;
            } else if (passwort2.isEmpty()) {
                error_field.setText(R.string.password_missing);
                error_field.setBackgroundColor(getResources().getColor(R.color.whiteOP));
                this.passwort_wiederholen.setBackgroundColor(getResources().getColor(R.color.rectangle_50_colorOP));
                spinner.setVisibility(View.INVISIBLE);
                return false;
            } else if (!passwort1.equals(passwort2)) {
                error_field.setText(R.string.password_not_equal);
                error_field.setBackgroundColor(getResources().getColor(R.color.whiteOP));
                this.passwort_wiederholen.setBackgroundColor(getResources().getColor(R.color.rectangle_50_colorOP));
                this.passwort.setBackgroundColor(getResources().getColor(R.color.rectangle_50_colorOP));
                spinner.setVisibility(View.INVISIBLE);
                return false;
            }
            return true;
        }

        private void updateLabel(EditText editText, Calendar myCalendar) {
            String myFormat = "dd/MM/yy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.GERMAN);
            editText.setText(dateFormat.format(myCalendar.getTime()));
        }

    }
	
	