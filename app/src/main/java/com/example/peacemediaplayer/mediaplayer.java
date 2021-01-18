package com.example.peacemediaplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.VideoView;

import java.util.Objects;

public class mediaplayer extends AppCompatActivity implements
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {
    private static final String TAG ="dddd" ;
    String url=MainActivity.name;
    String vedioname=MainActivity.namee;
    int stopPosition,seekPosition;
    VideoView video;
    ImageView play;
    ImageView reverseseek;
    ImageView forwardseek;
    int playvariable;
    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;
    private static final int SWIPE_THRESHOLD = 300;
    private static final int SWIPE_VELOCITY_THRESHOLD = 300;
    private AudioManager mAudioManager;
    SeekBar brightnessbar;
    SeekBar volumebar;
    Toolbar tb1;
    Toolbar tb2;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediaplayer);
         video=findViewById(R.id.videoView);
          TextView tv=findViewById(R.id.textView);
        //  tb1=findViewById(R.id.toolbar2);
       //  tb1.animate().translationY(-tb1.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
        Toast.makeText(this, vedioname, Toast.LENGTH_SHORT).show();
        reverseseek=findViewById(R.id.leftseek);
        play=findViewById(R.id.pause);
        forwardseek=findViewById(R.id.rightseek);
         brightnessbar= findViewById(R.id.seekBarbrightness);
         volumebar=findViewById(R.id.seekBarvolume);
        tv.setText(vedioname);
        video.setVideoPath(url);
        video.start();
        mAudioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        brightnessbar.getThumb().mutate().setAlpha(0);
        volumebar.getThumb().mutate().setAlpha(0);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if(playvariable==0)
               {
                   play.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
                   onPause();
                   playvariable=playvariable+1;
               }
               else
               {
                   onResume();
                   play.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
                   playvariable=playvariable-1;
               }

            }


    });
        mDetector = new GestureDetectorCompat(this,this);
        mDetector.setOnDoubleTapListener(this);
        hideStatusBar();

            // Get system brightness
            // in the range [0, 255]

        }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause called");
        super.onPause();
        stopPosition = video.getCurrentPosition();
        video.pause();
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called");
        video.seekTo(stopPosition);
        video.start();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (this.mDetector.onTouchEvent(event)) {
            return true;

        }
        //Toast.makeText(this, "touched", Toast.LENGTH_SHORT).show();
        return super.onTouchEvent(event);
    }


    @Override
    public boolean onDown(MotionEvent event) {
        Log.d(DEBUG_TAG,"onDown: " + event.toString());

        return true;

    }




    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        boolean result = false;
        try {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                    result = true;
                }
            }
            else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    onSwipeBottom();
                } else {
                    onSwipeTop();
                }
                result = true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }



    @Override
    public void onLongPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
      //  Toast.makeText(this, "long press", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX,
                            float distanceY) {
        Log.d(DEBUG_TAG, "onScroll: " + event1.toString() + event2.toString());
      //  Toast.makeText(this, "scrolll", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDoubleTap: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDoubleTapEvent: " + event.toString());
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapConfirmed: " + event.toString());
        Toast.makeText(this, "single tap", Toast.LENGTH_SHORT).show();
       // tb1.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
       // new CountDownTimer(30000, 1000) {

        //    public void onTick(long millisUntilFinished) {

                //here you can have your logic to set text to edittext
        //    }

        //     public void onFinish() {

        //        hide();

        //     }

        //   }.start();

        return true;
    }
    void hideStatusBar() {
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public void onSwipeRight() {
        Toast.makeText(this, "right", Toast.LENGTH_SHORT).show();
        seekPosition = video.getCurrentPosition();
        int seek=seekPosition+1000;
        video.seekTo(seek);

    }

    public void onSwipeLeft() {
        Toast.makeText(this, "left", Toast.LENGTH_SHORT).show();
        seekPosition = video.getCurrentPosition();
        int seek=seekPosition-1000;
        video.seekTo(seek);

    }

    public void onSwipeTop() {

    }

    public void onSwipeBottom() {


    }

    void hide(){

        tb1.animate().translationY(-tb1.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
    }



}
