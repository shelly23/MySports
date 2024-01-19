
	 
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
    import android.media.MediaPlayer;
    import android.net.Uri;
    import android.os.Bundle;
    import android.os.Handler;
    import android.view.View;
    import android.widget.Button;
    import android.widget.MediaController;
    import android.widget.TextView;
    import android.widget.VideoView;

    import com.example.mysports.R;

    import persistence.dtos.Connection;

    public class workout_activity extends Activity {


        long timeInMilliseconds = 0L;
        private VideoView video;
        private MediaPlayer mediaPlayer;
        private Uri uri;
        private Connection connection;
        private Handler customHandler = new Handler();
        private TextView timerValue;
        private TextView duration;

        private Button pause;

        private Button end;

        private long pausen = 0L;

        private CountUpTimer timer;
        private boolean original = true;
        private Runnable updateTimerThread = new Runnable() {

            public void run() {

                timeInMilliseconds = timer.getTime();

                int secs = (int) (timeInMilliseconds / 1000);
                int mins = secs / 60;
                secs = secs % 60;
                timerValue.setText("" + mins + ":"
                        + String.format("%02d", secs));
                customHandler.postDelayed(this, 0);
            }

        };

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.workout);


            uri = (Uri) getIntent().getParcelableExtra("URI");
            connection = (Connection) getIntent().getSerializableExtra("CONNECTION");

            // finding videoview by its id

            timerValue = findViewById(R.id.actual);
            duration = findViewById(R.id.duration);
            video = findViewById(R.id.videoView);
            pause = findViewById(R.id.pause);
            end = findViewById(R.id.end);

            pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (original) {
                        timer.pause();
                        customHandler.removeCallbacks(updateTimerThread);
                        mediaPlayer.pause();
                        pause.setText(R.string.fortsetzen);
                        original = false;
                        pausen++;
                    } else {
                        //TBD
                        original = true;
                        mediaPlayer.start();
                        pause.setText(R.string.pause);
                        timer.resume();
                        customHandler.postDelayed(updateTimerThread, 0);
                    }
                }
            });

            end.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            duration.setText(connection.getRecommendedDuration() + " Minuten");

            // sets the resource from the
            // videoUrl to the videoView
            video.setVideoURI(uri);

            video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer = mp;

                }
            });

            // creating object of
            // media controller class
            MediaController mediaController = new MediaController(this);

            // sets the anchor view
            // anchor view for the videoView
            mediaController.setAnchorView(video);

            // sets the media player to the videoView
            mediaController.setMediaPlayer(video);

            // sets the media controller to the videoView
            video.setMediaController(mediaController);

            // starts the video
            video.start();

            timer = new CountUpTimer(true);
            //
            // startTime = SystemClock.uptimeMillis();
            customHandler.postDelayed(updateTimerThread, 0);

            //custom code goes here

        }
    }
	
	