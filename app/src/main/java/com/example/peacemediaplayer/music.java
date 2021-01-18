package com.example.peacemediaplayer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class music extends AppCompatActivity {

    ListView simpleList;
    Cursor cursor;
    private musicadapter mAdapter;
    TextView songname,text;
    ImageView play;
    static int playconfirm=0;
    static MediaPlayer mediaPlayer;
    static String path,name;
    static int stopPosition,seekPosition;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        text=findViewById(R.id.text);
        songname=findViewById(R.id.textView6);
        play=findViewById(R.id.imageView5);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(path==null){

                    Toast.makeText(music.this, "no music selected", Toast.LENGTH_SHORT).show();

                }

             else if(playconfirm==0){

                  play.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
                    mediaPlayer.seekTo(stopPosition);
                    mediaPlayer.start();
                  playconfirm=playconfirm+1;

              }
             else
                {
                    play.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
                    stopPosition = mediaPlayer.getCurrentPosition();
                    mediaPlayer.pause();
                    playconfirm=playconfirm-1;
                }

            }
        });

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomsheetlayout bottomSheet = new bottomsheetlayout();
                bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");



            }});

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,

        };

        cursor =getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                null);

         final List<AudioModel> songdetails = new ArrayList<>();


        while(cursor.moveToNext()) {
            songdetails.add(new AudioModel(cursor.getString(0),cursor.getString(1), cursor.getString(2),cursor.getString(3), cursor.getString(4), cursor.getString(5) ) );

        }
        Toolbar toolbar = findViewById(R.id.topBr);
        setSupportActionBar(toolbar);
        simpleList = (ListView)findViewById(R.id.listviewbuy);
        mAdapter = new musicadapter(this, (ArrayList<AudioModel>) songdetails);
        simpleList.setAdapter(mAdapter);
        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                AudioModel listbuy = songdetails.get(i);

                path=listbuy.getData();
                 name=listbuy.getmName();
                if(playconfirm==1){
                    mediaPlayer.stop();
                    playconfirm=0;
                }

                songname.setText(name);
                play.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
                mediaPlayer = new MediaPlayer();

                try {
                    mediaPlayer.setDataSource(music.this,Uri.parse(new File(path).toString()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mediaPlayer.prepare();
                    playconfirm=1;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();



            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.musicmenu, menu);
        return true;
    }
    

    }



