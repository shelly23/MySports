
	 
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
import android.content.Context;
import android.os.Build;
import android.os.Bundle;


import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

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

	public class register_page_activity extends Activity {

	private View _bg__register_page_ek2;
	private ImageView background_image_ek1;
	private TextView mysport_ek1;;
	private EditText nachname;
	private View nachname_view;
	private EditText vorname;
	private View vorname_view;
	private EditText email_adresse;
	private View email_adresse_view;
	private EditText passwort;
	private View passwort_view;
	private EditText passwort_wiederholen;
	private View passwort_wiederholen_view;
	private Button register;
	private TextView error_field;

	private UserService userService;

		public register_page_activity() throws PersistenceException {
		}

		@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_page);

		_bg__register_page_ek2 = (View) findViewById(R.id._bg__register_page_ek2);
		background_image_ek1 = (ImageView) findViewById(R.id.background_image_ek1);
		mysport_ek1 = (TextView) findViewById(R.id.mysport_ek1);
		register = (Button) findViewById(R.id.register);
		nachname = (EditText) findViewById(R.id.nachname);
		nachname_view = (View) findViewById(R.id.nachname_view);
		vorname = (EditText) findViewById(R.id.vorname);
		vorname_view = (View) findViewById(R.id.vorname_view);
		email_adresse = (EditText) findViewById(R.id.email);
		email_adresse_view = (View) findViewById(R.id.e_mail);
		passwort = (EditText) findViewById(R.id.password1);
		passwort_view = (View) findViewById(R.id.passwort1);
		passwort_wiederholen = (EditText) findViewById(R.id.password2);
		passwort_wiederholen_view = (View) findViewById(R.id.passwort2);
		error_field = (TextView) findViewById(R.id.error_field);

			try {
				userService = new UserServiceImpl(new DBUserDAO(new ConnectionServiceDB(), getApplicationContext()), new TextValidator());
			} catch (PersistenceException e) {
				e.printStackTrace();
			}


			register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				error_field.setText("");
				vorname_view.setBackgroundColor(getResources().getColor(R.color.log_in_color));
				nachname_view.setBackgroundColor(getResources().getColor(R.color.log_in_color));
				email_adresse_view.setBackgroundColor(getResources().getColor(R.color.log_in_color));
				passwort_view.setBackgroundColor(getResources().getColor(R.color.log_in_color));
				passwort_wiederholen_view.setBackgroundColor(getResources().getColor(R.color.log_in_color));

				String vorname_text = vorname.getText().toString();
				String nachname_text = nachname.getText().toString();
				String email_text = email_adresse.getText().toString();
				String passwort_text = passwort.getText().toString();
				String passwort_wiederholen_text = passwort_wiederholen.getText().toString();

				if (validateData(vorname_text, nachname_text, email_text, passwort_text, passwort_wiederholen_text)) {
					// save

					try {
						userService.saveUser(new User(vorname_text, nachname_text, email_text, passwort_text));
					} catch (PersistenceException | InvalidValueException | MandatoryValueException | IOException | NoSuchAlgorithmException e) {
						e.printStackTrace();
					}

					//Create a View object yourself through inflater
					view.getContext();
					LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
					View popupView = inflater.inflate(R.layout.popupscreen, null);

					//Specify the length and width through constants
					int width = LinearLayout.LayoutParams.MATCH_PARENT;
					int height = LinearLayout.LayoutParams.MATCH_PARENT;

					//Make Inactive Items Outside Of PopupWindow
					boolean focusable = true;

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
					});
				}

			}
		});

		//custom code goes here
	
	}

	private boolean validateData(String vorname, String nachname, String email, String passwort1, String passwort2) {
		if (vorname.isEmpty()) {
			error_field.setText(R.string.vorname_missing);
			vorname_view.setBackgroundColor(getResources().getColor(R.color.rectangle_50_color));
			return false;
		} else if (nachname.isEmpty()) {
			error_field.setText(R.string.nachname_missing);
			nachname_view.setBackgroundColor(getResources().getColor(R.color.rectangle_50_color));
			return false;
		} else if (email.isEmpty()) {
			error_field.setText(R.string.email_missing);
			email_adresse_view.setBackgroundColor(getResources().getColor(R.color.rectangle_50_color));
			return false;
		} else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
			error_field.setText(R.string.email_invalide);
			email_adresse_view.setBackgroundColor(getResources().getColor(R.color.rectangle_50_color));
			return false;
		} else if (passwort1.isEmpty()) {
			error_field.setText(R.string.password_missing);
			passwort_view.setBackgroundColor(getResources().getColor(R.color.rectangle_50_color));
			return false;
		} else if (passwort2.isEmpty()) {
			error_field.setText(R.string.password_missing);
			passwort_wiederholen_view.setBackgroundColor(getResources().getColor(R.color.rectangle_50_color));
			return false;
		} else if (!passwort1.equals(passwort2)) {
			error_field.setText(R.string.password_not_equal);
			passwort_wiederholen_view.setBackgroundColor(getResources().getColor(R.color.rectangle_50_color));
			passwort_view.setBackgroundColor(getResources().getColor(R.color.rectangle_50_color));
			return false;
		}
		return true;
	}

}
	
	