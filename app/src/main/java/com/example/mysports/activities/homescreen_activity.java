
	 
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
import android.os.Bundle;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mysports.R;
import com.savvi.rangedatepicker.CalendarPickerView;
import com.savvi.rangedatepicker.SubTitle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

	public class homescreen_activity extends Activity {

	
	private View _bg__homescreen_ek2;
	private ImageView background_image_ek7;
	private ImageView path_ek18;
	private ImageView path_ek19;
	private ImageView path_ek20;
	private ImageView oval_1_ek6;
	private ImageView oval_1_copy_ek6;
	private ImageView oval_1_copy_2_ek6;
	private ImageView shape_ek18;
	private ImageView shape_ek19;
	private TextView figma_ek6;
	private ImageView shape_ek20;
	private ImageView charge_ek6;
	private ImageView ___ek6;
	private TextView _42__ek6;
	private ImageView vector_3_ek6;
	private TextView _9_42_am_ek6;
	private View menu_bar_ek11;
	private ImageView vector_ek122;
	private ImageView vector_ek123;
	private ImageView vector_ek124;
	private ImageView vector_ek125;
	private ImageView vector_ek126;
	private ImageView vector_ek127;
	private ImageView vector_ek128;
	private ImageView vector_ek129;
	private ImageView vector_ek130;
	private ImageView vector_ek131;
	private ImageView vector_ek132;
	private ImageView vector_ek133;
	private TextView so_06_13_20_27;
	private TextView sa_05_12_19_26;
	private TextView fr_04_11_18_25;
	private TextView do_03_10_17_24;
	private TextView mi_02_09_16_23;
	private TextView di_01_08_15_22;
	private TextView mo_07_14_21_28;
	private View calendar_back;
	private View line_7;
	private TextView februar_2022;
	private ImageView back;
	private ImageView front;
	private View ellipse_27;
	private View ellipse_28;
	private View rectangle_25;
	private View ellipse_27_ek1;
	private View ellipse_28_ek1;
	private View rectangle_25_ek1;
	private View ellipse_27_ek2;
	private View ellipse_28_ek2;
	private View rectangle_25_ek2;
	private View ellipse_27_ek3;
	private View ellipse_28_ek3;
	private View rectangle_25_ek3;
	private View tag_legende;
	private TextView aktiver_tag;
	private TextView akuter_schub;
	private View schub_legende;
	private View chat_rectangle;
	private ImageView ellipse_31;
	private ImageView ellipse_32;
	private TextView _47_8_;
	private View rectangle_26_ek1;
	private View line_8_ek1;
	private ImageView line_9_ek1;
	private TextView _11_aktive_tage__von_23___das_sind_fast_50___weiter_so__ek1;
	private View rectangle_26_ek2;
	private View line_8_ek2;
	private ImageView line_9_ek2;
	private View rectangle_26_ek3;
	private View line_8_ek3;
	private ImageView line_9_ek3;
	private TextView _11_aktive_tage__von_23___das_sind_fast_50___weiter_so__ek3;
	private View rectangle_2_ek2;
	private TextView statistiken_anzeigen;
	private View rectangle_2_ek3;
	private TextView akuten_schub_tracken;
	private View rectangle_2_ek4;
	private TextView aktivit_t_durchf_hren;
	private TextView chat_ek1;
	private ImageView vector_ek134;
	private TextView schritte_heute__1253_von_1500;
	private ImageView vector_ek135;
	private View ellipse_36;
	private View ellipse_37;
	private CalendarPickerView calendarPickerView;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.homescreen);

		
		_bg__homescreen_ek2 = (View) findViewById(R.id._bg__homescreen_ek2);
		background_image_ek7 = (ImageView) findViewById(R.id.background_image_ek7);
		menu_bar_ek11 = (View) findViewById(R.id.menu_bar_ek11);
		vector_ek123 = (ImageView) findViewById(R.id.vector_ek123);
		vector_ek125 = (ImageView) findViewById(R.id.vector_ek125);
		vector_ek127 = (ImageView) findViewById(R.id.vector_ek127);
		vector_ek129 = (ImageView) findViewById(R.id.vector_ek129);
		vector_ek131 = (ImageView) findViewById(R.id.vector_ek131);
		chat_rectangle = (View) findViewById(R.id.chat_rectangle);
		ellipse_31 = (ImageView) findViewById(R.id.ellipse_31);
		ellipse_32 = (ImageView) findViewById(R.id.ellipse_32);
		_47_8_ = (TextView) findViewById(R.id._47_8_);
		rectangle_26_ek1 = (View) findViewById(R.id.rectangle_26_ek1);
		_11_aktive_tage__von_23___das_sind_fast_50___weiter_so__ek1 = (TextView) findViewById(R.id._11_aktive_tage__von_23___das_sind_fast_50___weiter_so__ek1);
		rectangle_26_ek2 = (View) findViewById(R.id.rectangle_26_ek2);
		rectangle_26_ek3 = (View) findViewById(R.id.rectangle_26_ek3);
		_11_aktive_tage__von_23___das_sind_fast_50___weiter_so__ek3 = (TextView) findViewById(R.id._11_aktive_tage__von_23___das_sind_fast_50___weiter_so__ek3);
		rectangle_2_ek2 = (View) findViewById(R.id.rectangle_2_ek2);
		statistiken_anzeigen = (TextView) findViewById(R.id.statistiken_anzeigen);
		rectangle_2_ek3 = (View) findViewById(R.id.rectangle_2_ek3);
		akuten_schub_tracken = (TextView) findViewById(R.id.akuten_schub_tracken);
		rectangle_2_ek4 = (View) findViewById(R.id.rectangle_2_ek4);
		aktivit_t_durchf_hren = (TextView) findViewById(R.id.aktivit_t_durchf_hren);
		chat_ek1 = (TextView) findViewById(R.id.chat_ek1);
		vector_ek134 = (ImageView) findViewById(R.id.vector_ek134);
		schritte_heute__1253_von_1500 = (TextView) findViewById(R.id.schritte_heute__1253_von_1500);
		vector_ek135 = (ImageView) findViewById(R.id.vector_ek135);
		calendarPickerView = (CalendarPickerView) findViewById(R.id.calendar_view);

		Calendar timeFrom = Calendar.getInstance();
		timeFrom.add(Calendar.DATE, -15);

		Calendar timeTo = Calendar.getInstance();
		timeTo.add(Calendar.DATE, +15);

		ArrayList<SubTitle> subTitles = new ArrayList<>();

		ArrayList<Date> marked = new ArrayList<>();
		Calendar marked1 = Calendar.getInstance();
		marked1.add(Calendar.DATE, 5);
		marked.add(marked1.getTime());
		subTitles.add(new SubTitle(marked1.getTime(), "Schub"));
		marked1.add(Calendar.DATE, 1);
		marked.add(marked1.getTime());
		subTitles.add(new SubTitle(marked1.getTime(), "Schub"));

		ArrayList<Date> marked2 = new ArrayList<>();
		Calendar marked3 = Calendar.getInstance();
		marked3.add(Calendar.DATE, -5);
		marked.add(marked3.getTime());
		subTitles.add(new SubTitle(marked3.getTime(), "Aktiv"));
		marked3.add(Calendar.DATE, -1);
		marked.add(marked3.getTime());
		subTitles.add(new SubTitle(marked3.getTime(), "Aktiv"));

		calendarPickerView.init(timeFrom.getTime(), timeTo.getTime())
				.inMode(CalendarPickerView.SelectionMode.RANGE)
				.withSelectedDate(Calendar.getInstance().getTime())
				.withHighlightedDates(marked)
				.displayOnly()
				.withSubTitles(subTitles)
				.withSelectedDates(marked2)
		;
		//custom code goes here

	}

}
	
	