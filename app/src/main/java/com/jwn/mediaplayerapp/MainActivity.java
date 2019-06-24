package com.jwn.mediaplayerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private Button playButton;
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSeekBar = findViewById(R.id.mSeekBar);

        mediaPlayer = new MediaPlayer();
        // constructor
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.game_field);
        mSeekBar.setMax(mediaPlayer.getDuration()); // set the seekbar's Maximum property to the duration of the mp3

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b) {// if the touch is coming from the user(see method parameters)
                    mediaPlayer.seekTo(i);// i represents seekbar progress (see parameters)
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {// when file is finished run this function

                int duration = mediaPlayer.getDuration();// get duration milliseconds of the file being played
                String mDuration = String.valueOf(duration/1000);

                Toast.makeText(getApplicationContext(), "duration " + mDuration + " seconds", Toast.LENGTH_LONG).show();
            }
        });

        playButton = findViewById(R.id .playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if media player is playing
                if(mediaPlayer.isPlaying()) {
                    // stop and give option to start again
                    pauseMusic();
                }else{
                    startMusic();
                }
            }
        });
    }

    public void pauseMusic(){
        if (mediaPlayer !=null) {//if mediaPlayer is on, pause it
            mediaPlayer.pause();
            playButton.setText("Play");
        }
    }

    public void startMusic(){
        if (mediaPlayer != null) {
            mediaPlayer.start();
            playButton.setText("Pause");
        }
    }

    //purge resources used by player (cause they use a lot of it)
    @Override
    protected void onDestroy() {
        if(mediaPlayer !=null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release(); // release the resources back to the system
            mediaPlayer = null; // kill the media player
        }

        super.onDestroy();
    }
}// end MainActivity

