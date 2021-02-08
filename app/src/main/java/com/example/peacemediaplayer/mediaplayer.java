package com.example.peacemediaplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.VideoView;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class mediaplayer extends AppCompatActivity implements
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {
    private static final String TAG = "dddd";
    String url = Main2Activity.path1;
    String vedioname = Main2Activity.name1;
    String duration = Main2Activity.duration1;
    int stopPosition, seekPosition;
    VideoView video;
    ImageView play,lock,viewswap;
    ImageView reverseseek;
    ImageView forwardseek;
    int playvariable;
    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;
    private static final int SWIPE_THRESHOLD = 300;
    private static final int SWIPE_VELOCITY_THRESHOLD = 300;
    private AudioManager mAudioManager;
    SeekBar brightnessbar;
    SeekBar volumebar = null;
    SeekBar videobar;
    RelativeLayout r1, r2;
    ImageView bri, vol;
    TextView brino, volno;
    TextView startno,endno;
    private AudioManager audioManager = null;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediaplayer);
        video = findViewById(R.id.videoView);
        TextView tv = findViewById(R.id.textView);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        Toast.makeText(this, vedioname, Toast.LENGTH_SHORT).show();
        reverseseek = findViewById(R.id.leftseek);
        play = findViewById(R.id.pause);
        forwardseek = findViewById(R.id.rightseekk);
        brightnessbar = findViewById(R.id.seekBarvolume);
        volumebar = findViewById(R.id.seekBarbrightness);
        viewswap=findViewById(R.id.swapview);
        r1 = findViewById(R.id.toolbar3);
        r2 = findViewById(R.id.toolbar2);
        bri = findViewById(R.id.imageView12);
        vol = findViewById(R.id.imageView16);
        brino = findViewById(R.id.textbri);
        volno = findViewById(R.id.textvol);
        videobar=findViewById(R.id.seekBar2);
        startno=findViewById(R.id.textView12);
        endno=findViewById(R.id.textView13);
        tv.setText(vedioname);
        Uri s = Uri.fromFile(new File(url));
        video.setVideoPath(String.valueOf(s));
        video.start();
        mAudioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        initControls();
        final Context context=mediaplayer.this;
        viewswap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int paddingDp = 250;
                float density = context.getResources().getDisplayMetrics().density;
                int paddingPixel = (int)(paddingDp * density);
                video.setPadding(paddingPixel,paddingPixel,paddingPixel,paddingPixel);


            }


        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (playvariable == 0) {
                    play.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
                    onPause();
                    playvariable = playvariable + 1;
                } else {
                    onResume();
                    play.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
                    playvariable = playvariable - 1;
                }

            }


        });
        mDetector = new GestureDetectorCompat(this, this);
        mDetector.setOnDoubleTapListener(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hidetab();
            }
        }, 3000);
        brightnessbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                brino.setText(Integer.toString(progress));
                mediaplayer.this.setScreenBrightness((float) seekBar.getProgress() / 100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


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
    public boolean onTouchEvent(MotionEvent event) {
        if (this.mDetector.onTouchEvent(event)) {
            return true;

        }
        //Toast.makeText(this, "touched", Toast.LENGTH_SHORT).show();
        return super.onTouchEvent(event);
    }


    @Override
    public boolean onDown(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDown: " + event.toString());

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
            } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
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
        settab();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hidetab();
            }
        }, 5000);


        return true;
    }


    public void onSwipeRight() {
        seekPosition = video.getCurrentPosition();
        int seek = seekPosition + 50000;
        video.seekTo(seek);

    }

    public void onSwipeLeft() {
        seekPosition = video.getCurrentPosition();
        int seek = seekPosition - 50000;
        video.seekTo(seek);

    }

    public void onSwipeTop() {

    }

    public void onSwipeBottom() {


    }

    public void hidetab() {

        if (r1.getVisibility() == View.VISIBLE) {
            r1.setVisibility(View.GONE);
        }
        if (r2.getVisibility() == View.VISIBLE) {
            r2.setVisibility(View.GONE);
        }
        if (brightnessbar.getVisibility() == View.VISIBLE) {
            brightnessbar.setVisibility(View.GONE);
        }
        if (volumebar.getVisibility() == View.VISIBLE) {
            volumebar.setVisibility(View.GONE);
        }
        if (brino.getVisibility() == View.VISIBLE) {
            brino.setVisibility(View.GONE);
        }
        if (volno.getVisibility() == View.VISIBLE) {
            volno.setVisibility(View.GONE);
        }
        if (bri.getVisibility() == View.VISIBLE) {
            bri.setVisibility(View.GONE);
        }
        if (vol.getVisibility() == View.VISIBLE) {
            vol.setVisibility(View.GONE);
        }


    }

    public void settab() {

        if (r1.getVisibility() == View.GONE) {
            r1.setVisibility(View.VISIBLE);
        }
        if (r2.getVisibility() == View.GONE) {
            r2.setVisibility(View.VISIBLE);
        }
        if (brightnessbar.getVisibility() == View.GONE) {
            brightnessbar.setVisibility(View.VISIBLE);
        }
        if (volumebar.getVisibility() == View.GONE) {
            volumebar.setVisibility(View.VISIBLE);
        }
        if (brino.getVisibility() == View.GONE) {
            brino.setVisibility(View.VISIBLE);
        }
        if (volno.getVisibility() == View.GONE) {
            volno.setVisibility(View.VISIBLE);
        }
        if (bri.getVisibility() == View.GONE) {
            bri.setVisibility(View.VISIBLE);
        }
        if (vol.getVisibility() == View.GONE) {
            vol.setVisibility(View.VISIBLE);
        }


    }

    private void setScreenBrightness(float num) {
        WindowManager.LayoutParams layoutParams = super.getWindow().getAttributes();
        layoutParams.screenBrightness = num;
        super.getWindow().setAttributes(layoutParams);
    }

    private void initControls() {

        try {
            volumebar = findViewById(R.id.seekBarbrightness);
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            volumebar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumebar.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));


            volumebar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                    volno.setText(String.valueOf(progress));

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {

                videobar.setMax(video.getDuration());
                videobar.postDelayed(onEverySecond, 1000);
                int a=Integer.parseInt(String.valueOf(video.getDuration()));
                String time = String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(a),
                        TimeUnit.MILLISECONDS.toSeconds(a) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(a))
                );
                endno.setText(time);
            }
        });
        videobar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

                if(fromUser) {
                    // this is when actually seekbar has been seeked to a new position
                    video.seekTo(progress);
                    int a=Integer.parseInt(String.valueOf(progress));
                    String time = String.format("%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(a),
                            TimeUnit.MILLISECONDS.toSeconds(a) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(a))
                    );
                    startno.setText(time);
                }
            }
        });


    }
    private Runnable onEverySecond=new Runnable() {

        @Override
        public void run() {

            if(videobar != null) {
                videobar.setProgress(video.getCurrentPosition());
            }

            if(video.isPlaying()) {
                videobar.postDelayed(onEverySecond, 1000);
            }

            int a=Integer.parseInt(String.valueOf(video.getCurrentPosition()));
            String time = String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(a),
                    TimeUnit.MILLISECONDS.toSeconds(a) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(a))
            );
            startno.setText(time);

        }
    };

}
