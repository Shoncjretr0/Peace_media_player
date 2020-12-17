package com.example.peacemediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

public class mediaplayer extends AppCompatActivity {
    private static final String TAG ="dddd" ;
    String url=MainActivity.name;
    int stopPosition;
    VideoView video;
    ImageView play;
    ImageView reverseseek;
    ImageView forwardseek;
    int playvariable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediaplayer);
         video=findViewById(R.id.videoView);
       
        reverseseek=findViewById(R.id.leftseek);
        play=findViewById(R.id.pause);
        forwardseek=findViewById(R.id.rightseek);
        video.setVideoPath(url);
        video.start();
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               play.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
               if(playvariable==0)
               {
                   onPause();
                   playvariable=playvariable+1;
               }
               else
               {
                   onResume();
                   playvariable=playvariable-1;
               }

            }

        
    });
    }
    @Override
    public void onPause() {
        Log.d(TAG, "onPause called");
        super.onPause();
        stopPosition = video.getCurrentPosition(); //stopPosition is an int
        video.pause();
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called");
        video.seekTo(stopPosition);
        video.start(); //Or use resume() if it doesn't work. I'm not sure
    }
}
