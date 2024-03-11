
	 
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
    import android.content.Intent;
    import android.graphics.Paint;
    import android.net.Uri;
    import android.os.Bundle;
    import android.view.Gravity;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.widget.Button;
    import android.widget.ImageView;
    import android.widget.LinearLayout;
    import android.widget.PopupWindow;
    import android.widget.TextView;

    import com.akexorcist.roundcornerprogressbar.TextRoundCornerProgressBar;
    import com.bumptech.glide.Glide;
    import com.example.mysports.R;

    import persistence.daos.FBConnectionDAO;
    import persistence.daos.FBGame_ProgressDAO;
    import persistence.daos.FBRewardDAO;
    import persistence.dtos.Connection;
    import persistence.dtos.Day;
    import persistence.dtos.Game_Progress;
    import persistence.dtos.Reward;
    import persistence.dtos.Settings;
    import persistence.dtos.User;
    import service.ConnectionService;
    import service.ConnectionServiceImpl;
    import service.GameProgressService;
    import service.GameProgressServiceImpl;
    import service.RewardService;
    import service.RewardServiceImpl;


    public class gamescreen_activity extends Activity {

        private TextRoundCornerProgressBar progressBar;

        private ImageView gamescreen;

        private GameProgressService gameProgressService;

        private ConnectionService connectionService;

        private RewardService rewardService;

        private User user;

        private Settings settings;

        private Connection connection;
        private Day day;

        private Game_Progress gameProgress;

        private TextView start;
        private TextView end;

        private ImageView settings_btn;
        private ImageView home;
        private ImageView activity;
        private ImageView statistik;

        private int field;
        private Reward reward;
        private Uri uri;

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.gamescreen);

            gameProgressService = new GameProgressServiceImpl(new FBGame_ProgressDAO());
            rewardService = new RewardServiceImpl(new FBRewardDAO());
            connectionService = new ConnectionServiceImpl(new FBConnectionDAO());

            home = findViewById(R.id.vector_ek131);
            activity = findViewById(R.id.vector_ek127);
            settings_btn = findViewById(R.id.vector_ek123);
            statistik = findViewById(R.id.vector_ek129);

            progressBar = findViewById(R.id.bar);
            start = findViewById(R.id.start);
            end = findViewById(R.id.end);
            gamescreen = findViewById(R.id.gamescreen);

            user = (User) getIntent().getSerializableExtra("USER");
            settings = (Settings) getIntent().getSerializableExtra("SETTINGS");
            day = (Day) getIntent().getSerializableExtra("DAY");

            settings_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent nextScreen = new Intent(getApplicationContext(), settingsscreen_activity.class);
                    nextScreen.putExtra("USER", user);
                    nextScreen.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(nextScreen);
                }
            });

            activity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent nextScreen = new Intent(getApplicationContext(), activityscreen_activity.class);
                    nextScreen.putExtra("USER", user);
                    nextScreen.putExtra("DAY", day);
                    nextScreen.putExtra("SETTINGS", settings);
                    nextScreen.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(nextScreen);
                }
            });

            statistik.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent nextScreen = new Intent(getApplicationContext(), statistik_activity.class);
                    nextScreen.putExtra("USER", user);
                    nextScreen.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(nextScreen);
                }
            });

            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent nextScreen = new Intent(getApplicationContext(), homescreen_activity.class);
                    nextScreen.putExtra("USER", user);
                    nextScreen.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(nextScreen);
                }
            });

            //custom code goes here

        }

        public void onResume() {
            super.onResume();
            try {
                gameProgress = gameProgressService.getGame_progress(user.getId());
                connection = connectionService.getUsersConnections(user.getId(), false).get(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            long points = gameProgress.getPoints();
            field = (int) (points / 100);
            if (field > gameProgress.getField()) {
                gameProgress.setField(field);
                try {
                    reward = rewardService.getReward(connection);
                    uri = rewardService.getUrl(reward);
                    gameProgressService.update(gameProgress);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // Post a Runnable to show the popup after the activity is fully loaded
                findViewById(android.R.id.content).post(new Runnable() {
                    @Override
                    public void run() {
                        showPopup();
                    }
                });

            }

            switch (field) {
                case 1:
                    gamescreen.setImageResource(R.drawable.screen_1);
                    break;
                case 2:
                    gamescreen.setImageResource(R.drawable.screen_2);
                    break;
                case 3:
                    gamescreen.setImageResource(R.drawable.screen_3);
                    break;
                case 4:
                    gamescreen.setImageResource(R.drawable.screen_4);
                    break;
                case 5:
                    gamescreen.setImageResource(R.drawable.screen_5);
                    break;
                case 6:
                    gamescreen.setImageResource(R.drawable.screen_6);
                    break;
                default:
                    break;
            }

            long min = (points / 100) * 100;
            long max = min + 100;

            start.setText(String.valueOf(min));
            end.setText(String.valueOf(max));

            progressBar.setProgress(points % 100);
            progressBar.setProgressText(String.valueOf(points));

        }

        private void showPopup() {
            // popup mit belohnung

            //Create a View object yourself through inflater
            Context c = getApplicationContext();
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.popupscreen_game, findViewById(R.id.popup_root));

            //Specify the length and width through constants
            int width = LinearLayout.LayoutParams.MATCH_PARENT;
            int height = LinearLayout.LayoutParams.MATCH_PARENT;

            //Make Inactive Items Outside Of PopupWindow
            boolean focusable = true;

            //Create a window with our parameters
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

            //Set the location of the window on the screen
            popupWindow.showAtLocation(findViewById(R.id.gamescreen), Gravity.CENTER, 0, 0);

            //Initialize the elements of our window, install the handler

            TextView title = popupView.findViewById(R.id.titleText1);
            title.setText(title.getText().toString().replace("[]", String.valueOf(field)));

            TextView belohnung = popupView.findViewById(R.id.bel_content);
            belohnung.setText(reward.getBelohnung());

            ImageView thumbnail = popupView.findViewById(R.id.thumbnail);
            thumbnail.setImageURI(uri);
            // Load the image into the ImageView using Glide
            Glide.with(getApplicationContext())
                    .load(uri)
                    .into(thumbnail);

            TextView link = popupView.findViewById(R.id.link);
            link.setText(reward.getTitle());
            link.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG | Paint.FAKE_BOLD_TEXT_FLAG);
            link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(reward.getLink()));
                    startActivity(browserIntent);
                }
            });

            Button back = popupView.findViewById(R.id.messageButtonBack);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                }
            });
        }


    }
	
	