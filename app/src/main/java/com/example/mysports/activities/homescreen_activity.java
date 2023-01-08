
	 
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
    import android.os.Build;
    import android.os.Bundle;
    import android.text.Html;
    import android.view.Gravity;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.widget.Button;
    import android.widget.DatePicker;
    import android.widget.EditText;
    import android.widget.LinearLayout;
    import android.widget.PopupWindow;
    import android.widget.TextView;

    import androidx.annotation.RequiresApi;

    import com.example.mysports.R;
    import com.github.mikephil.charting.charts.PieChart;
    import com.github.mikephil.charting.data.PieData;
    import com.github.mikephil.charting.data.PieDataSet;
    import com.github.mikephil.charting.data.PieEntry;
    import com.savvi.rangedatepicker.CalendarPickerView;
    import com.savvi.rangedatepicker.SubTitle;

    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.time.Month;
    import java.time.Year;
    import java.util.ArrayList;
    import java.util.Calendar;
    import java.util.Date;
    import java.util.Locale;

    import persistence.daos.DBDayDAO;
    import persistence.dtos.Day;
    import persistence.dtos.User;
    import persistence.exceptions.PersistenceException;
    import persistence.validators.TextValidator;
    import service.ConnectionServiceDB;
    import service.DayService;
    import service.DayServiceImpl;

    public class homescreen_activity extends Activity {

        private CalendarPickerView calendarPickerView;
        private User user;
        private Day day;
        private java.sql.Date currentDate;
        private TextView greeting;
        private TextView stepMessage;
        private TextView activeDays;
        private TextView herrlicher;
        private PieChart pieChart;
        private float stepsToday;
        private float stepsTotal;
        private Button schubTracken;

        private DayService dayService;

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.homescreen);
            currentDate = new java.sql.Date(System.currentTimeMillis());

            try {
                dayService = new DayServiceImpl(new DBDayDAO(new ConnectionServiceDB(), getApplicationContext()), new TextValidator());
                user = (User) getIntent().getSerializableExtra("USER");
                day = dayService.getDay(user.getId(), currentDate);
            } catch (PersistenceException e) {
                e.printStackTrace();
            }

            stepsToday = day.getSteps();
            stepsTotal = 1500; // TODOMOCK --> SETTINGS

            greeting = findViewById(R.id.greeting);
            calendarPickerView = findViewById(R.id.calendar_view);
            pieChart = findViewById(R.id.piechart);
            stepMessage = findViewById(R.id.schritte_heute__1253_von_1500);
            activeDays = findViewById(R.id.active_days);
            herrlicher = findViewById(R.id.herrlicher);
            schubTracken = findViewById(R.id.button_schub);

            stepMessage.setText(stepMessage.getText().toString().replace("{}", String.valueOf((int) stepsToday)));

            ArrayList<PieEntry> pieEntries = new ArrayList<>();
            float percentToday = (stepsToday / stepsTotal) * 100;
            pieEntries.add(new PieEntry(stepsToday, (int) percentToday + "%"));
            pieEntries.add(new PieEntry(stepsTotal - stepsToday, ""));

            ArrayList<Integer> colours = new ArrayList<>();
            colours.add(getResources().getColor(R.color._11_aktive_tage__von_23___das_sind_fast_50___weiter_so__color));
            colours.add(getResources().getColor(R.color.menu_bar_ek1_color));

            PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
            pieDataSet.setColors(colours);

            PieData pieData = new PieData(pieDataSet);
            pieData.setDrawValues(false);
            pieChart.setData(pieData);
            pieChart.setDrawEntryLabels(false);
            pieChart.getDescription().setEnabled(false);
            pieChart.setRotationEnabled(false);
            pieChart.getLegend().setEnabled(false);
            pieChart.setDrawHoleEnabled(true);
            pieChart.setHoleColor(getResources().getColor(R.color.whiteTR));
            pieChart.setDrawEntryLabels(true);
            //pieChart.invalidate();

            String greet_Text;
            Date morning = null;
            Date evening = null;

            SimpleDateFormat sdf = new SimpleDateFormat("HH-mm");

            try {
                morning = sdf.parse("10-00");
                evening = sdf.parse("18-00");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar now = Calendar.getInstance();

            if (morning != null && now.before(morning)) {
                greet_Text = "Guten Morgen, ";
                herrlicher.setText(herrlicher.getText().toString().replace("{}", "Morgen"));
            } else if (evening != null && now.after(evening)) {
                greet_Text = "Guten Abend, ";
                herrlicher.setText(herrlicher.getText().toString().replace("{}", "Abend"));
            } else {
                greet_Text = "Hallo, ";
                herrlicher.setText(herrlicher.getText().toString().replace("{}", "Tag"));
            }

            greet_Text += user.getPrename();
            greet_Text += "!";
            greeting.setText(greet_Text);


            Calendar timeFrom = Calendar.getInstance();
            timeFrom.add(Calendar.DATE, -30);

            Calendar timeTo = Calendar.getInstance();
            timeTo.add(Calendar.DATE, +30);

            ArrayList<SubTitle> subTitles = new ArrayList<>();
            ArrayList<Date> markedSchub = new ArrayList<>();
            ArrayList<Date> markedAktiv = new ArrayList<>();

            for (int i = -30; i <= 30; i++) {

                Calendar dayToBeChecked = Calendar.getInstance();
                dayToBeChecked.add(Calendar.DATE, i);

                try {
                    Day toBeChecked = dayService.getDay(user.getId(), new java.sql.Date(dayToBeChecked.getTime().getTime()));
                    if (toBeChecked != null) {
                        if (toBeChecked.isActive()) {
                            markedAktiv.add(dayToBeChecked.getTime());
                            subTitles.add(new SubTitle(dayToBeChecked.getTime(), "Aktiv"));
                        } else if (toBeChecked.isAttack()) {
                            markedSchub.add(dayToBeChecked.getTime());
                            subTitles.add(new SubTitle(dayToBeChecked.getTime(), "Schub"));
                        }
                    }
                } catch (PersistenceException e) {
                    e.printStackTrace();
                }
            }

            int month = Calendar.getInstance().get(Calendar.MONTH);
            int activeDaysInt = 0;

            Calendar calendar = Calendar.getInstance();

            for (Date date : markedAktiv) {
                calendar = Calendar.getInstance();
                calendar.setTime(date);
                if (calendar.get(Calendar.MONTH) == month) {
                    activeDaysInt++;
                }
            }

            int daysOfMonth = Month.of(month + 1).length(Year.now().isLeap());

            float percent = ((float) activeDaysInt / (float) daysOfMonth) * 100;

            activeDays.setText(Html.fromHtml(activeDays.getText().toString().replace("{}", String.valueOf(activeDaysInt)).replace("[]", String.valueOf((int) percent))));

            if (activeDaysInt == 1) {
                activeDays.setText(Html.fromHtml(activeDays.getText().toString().replace("aktive Tage", "aktiver Tag")));
            }

            calendarPickerView.init(timeFrom.getTime(), timeTo.getTime())
                    .inMode(CalendarPickerView.SelectionMode.RANGE)
                    .withSelectedDate(Calendar.getInstance().getTime())
                    .withHighlightedDates(markedSchub)
                    .withHighlightedDates(markedAktiv)
                    .displayOnly()
                    .withSubTitles(subTitles)
            ;
            calendarPickerView.setDrawingCacheBackgroundColor(getResources().getColor(R.color.whiteTR));
            calendarPickerView.setBackgroundColor(getResources().getColor(R.color.whiteTR));
            calendarPickerView.setDrawingCacheBackgroundColor(getResources().getColor(R.color.whiteTR));

            schubTracken.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Create a View object yourself through inflater
                    v.getContext();
                    LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.popupscreen_schub, null);

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
                    popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                    //Initialize the elements of our window, install the handler

                    TextView test2 = popupView.findViewById(R.id.titleText1);
                    test2.setText("Akuten Schub tracken");

                    Calendar dateFromCal = Calendar.getInstance();
                    Calendar dateToCal = Calendar.getInstance();

                    EditText dateFrom = popupView.findViewById(R.id.dateFrom);

                    DatePickerDialog.OnDateSetListener dateFromPicker = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int day) {
                            dateFromCal.set(Calendar.YEAR, year);
                            dateFromCal.set(Calendar.MONTH, month);
                            dateFromCal.set(Calendar.DAY_OF_MONTH, day);
                            updateLabel(dateFrom, dateFromCal);
                        }
                    };

                    dateFrom.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new DatePickerDialog(homescreen_activity.this, dateFromPicker, dateFromCal.get(Calendar.YEAR), dateFromCal.get(Calendar.MONTH), dateFromCal.get(Calendar.DAY_OF_MONTH)).show();
                        }
                    });

                    Button buttonEdit = popupView.findViewById(R.id.messageButton);
                    buttonEdit.setText("Zurück zum Login");
                    buttonEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                }

                //custom code goes here

            });

        }

        private void updateLabel(EditText editText, Calendar myCalendar) {
            String myFormat = "MM/dd/yy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.GERMAN);
            editText.setText(dateFormat.format(myCalendar.getTime()));
        }

    }
	
	