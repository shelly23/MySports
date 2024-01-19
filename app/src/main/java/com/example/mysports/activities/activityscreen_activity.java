
	 
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
    import android.net.Uri;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.ImageView;
    import android.widget.TextView;

    import com.example.mysports.R;

    import java.util.List;

    import persistence.daos.FBConnectionDAO;
    import persistence.daos.FBTraining_VideoDAO;
    import persistence.dtos.Connection;
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

            strength.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri;
                    try {
                        uri = findTraining(Type.strength, user);
                        Intent nextScreen = new Intent(getApplicationContext(), workout_activity.class);
                        nextScreen.putExtra("URI", uri);
                        nextScreen.putExtra("CONNECTION", connection);
                        nextScreen.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(nextScreen);
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
                        nextScreen.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(nextScreen);
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
                        nextScreen.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(nextScreen);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });


            //custom code goes here

        }

        private Uri findTraining(Type type, User user) throws InterruptedException {
            List<Connection> connections = connectionService.getUsersConnections(user.getId(), false);
            connection = connections.get(0);
            Training_Video trainingVideo = trainingVideoService.getTrainingVideo(connection, type);
            return trainingVideoService.getUrl(trainingVideo);
        }
    }
	
	