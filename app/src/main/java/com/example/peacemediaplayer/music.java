package com.example.peacemediaplayer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RemoteViews;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.peacemediaplayer.App.CHANNEL_ID;

public class music extends AppCompatActivity {

    ListView simpleList;
    Cursor cursor;
    private musicadapter mAdapter;
    private searchmusicadapter sadapter;
    TextView songname,text;
    ImageView play,shuffle,imagesongbottom;
    static int playconfirm=0;
    static MediaPlayer mediaPlayer;
    static String path,name;
    static int stopPosition,seekPosition;
    SearchView search;
    static int shuffleno=0;
    String output;
    //private NotificationManagerCompat notificationManager;
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private int notification_id;
    private RemoteViews remoteViews;
    private Context context;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        text=findViewById(R.id.text);
        songname=findViewById(R.id.textView6);
        play=findViewById(R.id.imageView5);
        search=findViewById(R.id.searchView);
        shuffle=findViewById(R.id.imageView6);
        imagesongbottom=findViewById(R.id.imageView3);
       // notificationManager = NotificationManagerCompat.from(this);
        //showNotification();
        context = this;
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this);

        remoteViews = new RemoteViews(getPackageName(),R.layout.notification_expanded);
        //remoteViews.setImageViewResource(R.id.notif_icon,R.mipmap.ic_launcher);
      //  remoteViews.setTextViewText(R.id.notif_title,"TEXT");
        //remoteViews.setProgressBar(R.id.progressBar,100,40,true);




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
                  addNotification();



              }
             else
                {
                    play.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
                    stopPosition = mediaPlayer.getCurrentPosition();
                    mediaPlayer.pause();
                    playconfirm=playconfirm-1;
                    addNotification();
                }

            }
        });

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomsheetlayout bottomSheet = new bottomsheetlayout();
                bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");



            }});

        shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(shuffleno==0){

                    shuffle.setImageResource(R.drawable.ic_repeat_red1);
                    shuffleno=shuffleno+1;

                }
                else if(shuffleno==1)
                {
                    shuffle.setImageResource(R.drawable.musicshuffleor);
                    shuffleno=shuffleno+1;
                    addNotification();
                }

                else
                {
                    shuffle.setImageResource(R.drawable.ic_repeat_red);
                    shuffleno=0;

                }


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
         final List id= new ArrayList<>();
         final List artist= new ArrayList<>();
         final List title= new ArrayList<>();
         final List data= new ArrayList<>();
         final List displayname= new ArrayList<>();
         final List duration= new ArrayList<>();

        while(cursor.moveToNext()) {
            songdetails.add(new AudioModel(cursor.getString(0),cursor.getString(1), cursor.getString(2),cursor.getString(3), cursor.getString(4), cursor.getString(5) ) );
            id.add(cursor.getString(0));
            artist.add(cursor.getString(1));
            title.add(cursor.getString(2));
            data.add(cursor.getString(3));
            displayname.add(cursor.getString(4));
            duration.add(cursor.getString(5));

        }
        Toolbar toolbar = findViewById(R.id.toolbar);
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
                Toast.makeText(music.this, path, Toast.LENGTH_SHORT ).show();
                if(playconfirm==1){
                    mediaPlayer.stop();
                    playconfirm=0;
                }

                songname.setText(name);
                android.media.MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                mmr.setDataSource(path);

                byte [] data = mmr.getEmbeddedPicture();
                if(data != null)
                {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    imagesongbottom.setImageBitmap(bitmap);
                    diskrotate();

                }
                else
                {
                    imagesongbottom.setImageResource(R.drawable.ic_music_note_black_24dp);
                    diskrotate();

                }
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
        
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(query!=null){

                     output=query;

                    String[] a= (String[]) id.toArray(new String[0]);
                    String[] b= (String[]) artist.toArray(new String[0]);
                    String[] c= (String[]) title.toArray(new String[0]);
                    String[] d= (String[]) data.toArray(new String[0]);
                    String[] e= (String[]) displayname.toArray(new String[0]);
                    String[] f= (String[]) duration.toArray(new String[0]);
                    final List<searchitemmodel> searchitems = new ArrayList<>();
                    for (int i=0;i<c.length;i++){


                        if(c[i].toLowerCase().contains(output.toLowerCase())){

                            searchitems.add(new searchitemmodel(a[i],b[i],c[i],d[i],e[i],f[i]));
                        }

                    }
                    sadapter = new searchmusicadapter(this, (ArrayList<searchitemmodel>) searchitems);
                    simpleList.setAdapter(sadapter);
                    simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                            AudioModel listbuy = songdetails.get(i);

                            path=listbuy.getData();
                            name=listbuy.getmName();
                            Toast.makeText(music.this, path, Toast.LENGTH_SHORT ).show();
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


                        // String[] a= songdetails.toArray(new String[0]);

                    //Toast.makeText(music.this, a[1],Toast.LENGTH_LONG).show();
                    Toast.makeText(music.this, output,Toast.LENGTH_LONG).show();



                }else{
                    Toast.makeText(music.this, "text field is empty",Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.musicmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Intent myIntent=new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody="https://shoncj.wordpress.com/2020/03/25/thunder-app/ try this app";
                String shareSub="hope you download it";
                myIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                myIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(myIntent,"Share Using"));
                return true;
            case R.id.item2:


                return true;
            case R.id.item3:
                View menuItemVieww = findViewById(R.id.item3); // SAME ID AS MENU ID
                PopupMenu popupMenuu = new PopupMenu(this, menuItemVieww);
                popupMenuu.inflate(R.menu.newmenu);
                popupMenuu.show();

                return true;
            case R.id.item4:
                //nightmode
                View menuItemView = findViewById(R.id.item4); // SAME ID AS MENU ID
                PopupMenu popupMenu = new PopupMenu(this, menuItemView);
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public void onBackPressed() {
        if(path!=null) {
            mediaPlayer.stop();
        }
       finish();
    }

    private void addNotification() {


        int icon = R.drawable.ic_music_note_black_24dp;
        long when = System.currentTimeMillis();
        Notification notification = new Notification(icon, "Custom Notification", when);

        NotificationManager mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification_expanded);
        contentView.setImageViewResource(R.id.image, R.drawable.ic_brightness_1_black_24dp);
        contentView.setTextViewText(R.id.title, "Custom notification");
        contentView.setTextViewText(R.id.text, "This is a custom layout");
        notification.contentView = contentView;

        Intent notificationIntent = new Intent(this, music.class);
        notification.contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        notification.flags |= Notification.FLAG_NO_CLEAR; //Do not clear the notification
        notification.defaults |= Notification.DEFAULT_LIGHTS; // LED
        //notification.defaults |= Notification.DEFAULT_VIBRATE; //Vibration
        //notification.defaults |= Notification.DEFAULT_SOUND; // Sound

        mNotificationManager.notify(1, notification);


    }

    public void showNotification() {
        RemoteViews collapsedView = new RemoteViews(getPackageName(),
                R.layout.notification_collapsed);
        RemoteViews expandedView = new RemoteViews(getPackageName(),
                R.layout.notification_expanded);
        Intent clickIntent = new Intent(this, NotificationReceiver.class);
        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(this,
                0, clickIntent, 0);
        collapsedView.setTextViewText(R.id.text_view_collapsed_1, "Hello World!");
        //expandedView.setOnClickPendingIntent(R.id.text_view_expanded, clickPendingIntent);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_music_note_black_24dp)
                .setCustomBigContentView(expandedView)
                .setStyle(new NotificationCompat.BigPictureStyle())
                .build();
        notificationManager.notify(1, notification);

    }
    void diskrotate() {

        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(5000);
        rotate.setRepeatCount(1000);
        rotate.setInterpolator(new LinearInterpolator());

        imagesongbottom.startAnimation(rotate);
    }
    }




