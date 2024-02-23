
	 
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
    import android.graphics.Typeface;
    import android.net.Uri;
    import android.os.Bundle;
    import android.view.Gravity;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.widget.CompoundButton;
    import android.widget.ImageView;
    import android.widget.LinearLayout;
    import android.widget.PopupWindow;
    import android.widget.RelativeLayout;
    import android.widget.Switch;
    import android.widget.TextView;

    import com.example.mysports.R;

    import java.util.List;

    import persistence.daos.FBConnectionDAO;
    import persistence.daos.FBTraining_VideoDAO;
    import persistence.dtos.Connection;
    import persistence.dtos.Day;
    import persistence.dtos.Settings;
    import persistence.dtos.Training_Video;
    import persistence.dtos.Type;
    import persistence.dtos.User;
    import service.ConnectionService;
    import service.ConnectionServiceImpl;
    import service.Training_VideoService;
    import service.Training_VideoServiceImpl;

    public class activityscreen_activity extends Activity {

        private ImageView strength;
        private ImageView endurance;
        private ImageView personalized;
        private ImageView relaxation;

        private User user;
        private Day day;

        private Settings settings;

        private long content_id;

        private Training_VideoService trainingVideoService;
        private ConnectionService connectionService;

        private Connection connection;

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activityscreen);

            trainingVideoService = new Training_VideoServiceImpl(new FBTraining_VideoDAO());
            connectionService = new ConnectionServiceImpl(new FBConnectionDAO());

            strength = findViewById(R.id.kraft);
            endurance = findViewById(R.id.ausdauer);
            personalized = findViewById(R.id.personalized);
            relaxation = findViewById(R.id.entspannung);

            user = (User) getIntent().getSerializableExtra("USER");
            day = (Day) getIntent().getSerializableExtra("DAY");
            settings = (Settings) getIntent().getSerializableExtra("SETTINGS");

            strength.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri;
                    try {
                        uri = findTraining(Type.strength, user);
                        Intent nextScreen = new Intent(getApplicationContext(), workout_activity.class);
                        nextScreen.putExtra("URI", uri);
                        nextScreen.putExtra("CONNECTION", connection);
                        nextScreen.putExtra("USER", user);
                        nextScreen.putExtra("TYPE", Type.strength);
                        nextScreen.putExtra("CONTENT", content_id);
                        nextScreen.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(nextScreen);
                        finish();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            endurance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri;
                    try {
                        uri = findTraining(Type.endurance, user);
                        Intent nextScreen = new Intent(getApplicationContext(), workout_activity.class);
                        nextScreen.putExtra("URI", uri);
                        nextScreen.putExtra("CONNECTION", connection);
                        nextScreen.putExtra("USER", user);
                        nextScreen.putExtra("TYPE", Type.endurance);
                        nextScreen.putExtra("CONTENT", content_id);
                        nextScreen.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(nextScreen);
                        finish();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            relaxation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri;
                    try {
                        uri = findTraining(Type.relaxation, user);
                        Intent nextScreen = new Intent(getApplicationContext(), workout_activity.class);
                        nextScreen.putExtra("URI", uri);
                        nextScreen.putExtra("CONNECTION", connection);
                        nextScreen.putExtra("USER", user);
                        nextScreen.putExtra("CONTENT", content_id);
                        nextScreen.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(nextScreen);
                        finish();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            personalized.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Create a View object yourself through inflater
                    view.getContext();
                    LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.popupscreen_activity, findViewById(R.id.popup_root));

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

                    RelativeLayout thoughts = popupView.findViewById(R.id.thoughts);
                    RelativeLayout sliderview = popupView.findViewById(R.id.sliderview);

                    final long[] level = {0};

                    Switch switch1 = popupView.findViewById(R.id.switch1);

                    TextView thought = popupView.findViewById(R.id.textView1);
                    TextView slide = popupView.findViewById(R.id.textView);

                    Typeface fontBold = Typeface.createFromAsset(getAssets(), "montserrat_bold.ttf");
                    Typeface fontRegular = Typeface.createFromAsset(getAssets(), "montserrat_regular.ttf");

                    TextView bett = popupView.findViewById(R.id.bett);
                    TextView ausgeglichen = popupView.findViewById(R.id.ausgeglichen);
                    TextView baum = popupView.findViewById(R.id.baum);
                    TextView geschlaucht = popupView.findViewById(R.id.geschlaucht);
                    TextView lust = popupView.findViewById(R.id.lust);
                    TextView fit = popupView.findViewById(R.id.fit);
                    TextView lang = popupView.findViewById(R.id.lang);

                    bett.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            bett.setAlpha(1);
                            ausgeglichen.setAlpha(0.5F);
                            baum.setAlpha(0.5F);
                            geschlaucht.setAlpha(0.5F);
                            lust.setAlpha(0.5F);
                            fit.setAlpha(0.5F);
                            lang.setAlpha(0.5F);
                            level[0] = 0;
                        }
                    });

                    ausgeglichen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ausgeglichen.setAlpha(1);
                            bett.setAlpha(0.5F);
                            baum.setAlpha(0.5F);
                            geschlaucht.setAlpha(0.5F);
                            lust.setAlpha(0.5F);
                            fit.setAlpha(0.5F);
                            lang.setAlpha(0.5F);
                            level[0] = 5;
                        }
                    });

                    baum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            baum.setAlpha(1);
                            bett.setAlpha(0.5F);
                            ausgeglichen.setAlpha(0.5F);
                            geschlaucht.setAlpha(0.5F);
                            lust.setAlpha(0.5F);
                            fit.setAlpha(0.5F);
                            lang.setAlpha(0.5F);
                            level[0] = 10;
                        }
                    });

                    geschlaucht.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            geschlaucht.setAlpha(1);
                            bett.setAlpha(0.5F);
                            baum.setAlpha(0.5F);
                            ausgeglichen.setAlpha(0.5F);
                            lust.setAlpha(0.5F);
                            fit.setAlpha(0.5F);
                            lang.setAlpha(0.5F);
                            level[0] = 1;
                        }
                    });

                    lust.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            lust.setAlpha(1);
                            bett.setAlpha(0.5F);
                            baum.setAlpha(0.5F);
                            ausgeglichen.setAlpha(0.5F);
                            geschlaucht.setAlpha(0.5F);
                            fit.setAlpha(0.5F);
                            lang.setAlpha(0.5F);
                            level[0] = 9;
                        }
                    });

                    fit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            fit.setAlpha(1);
                            bett.setAlpha(0.5F);
                            baum.setAlpha(0.5F);
                            ausgeglichen.setAlpha(0.5F);
                            lust.setAlpha(0.5F);
                            geschlaucht.setAlpha(0.5F);
                            lang.setAlpha(0.5F);
                            level[0] = 7;
                        }
                    });

                    lang.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            lang.setAlpha(1);
                            bett.setAlpha(0.5F);
                            baum.setAlpha(0.5F);
                            ausgeglichen.setAlpha(0.5F);
                            lust.setAlpha(0.5F);
                            fit.setAlpha(0.5F);
                            geschlaucht.setAlpha(0.5F);
                            level[0] = 3;
                        }
                    });

                    switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                            if (b) {
                                thought.setTypeface(fontRegular);
                                thoughts.setVisibility(View.INVISIBLE);
                                slide.setTypeface(fontBold);
                                sliderview.setVisibility(View.VISIBLE);
                            } else {
                                thought.setTypeface(fontBold);
                                thoughts.setVisibility(View.VISIBLE);
                                slide.setTypeface(fontRegular);
                                sliderview.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
            });


            //custom code goes here

        }

        private Uri findTraining(Type type, User user) throws InterruptedException {
            List<Connection> connections = connectionService.getUsersConnections(user.getId(), false);
            connection = connections.get(0);
            Training_Video trainingVideo = trainingVideoService.getTrainingVideo(connection, type);
            content_id = trainingVideo.getId();
            return trainingVideoService.getUrl(trainingVideo);
        }

        private Uri findPersonalizedTraining(User user, long level) throws InterruptedException {
            List<Connection> connections = connectionService.getUsersConnections(user.getId(), false);
            connection = connections.get(0);
            Training_Video trainingVideo = trainingVideoService.getPersonalized_Video(connection, level);
            content_id = trainingVideo.getId();
            return trainingVideoService.getUrl(trainingVideo);
        }
    }
	
	