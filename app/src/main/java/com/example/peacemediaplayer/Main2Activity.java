package com.example.peacemediaplayer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main2Activity extends AppCompatActivity {

    ListView simpleList;
    String countryList[] = {"India", "China", "australia", "Portugle", "America", "NewZealand"};
    String patha=MainActivity.path;
    Cursor cursor;
    private fileadapter mAdapter;
    public static String path1;
    public static String duration1;
    public static String name1;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.topBr);
        simpleList = (ListView)findViewById(R.id.listviewbuy);

        setSupportActionBar(toolbar);

        String selection=MediaStore.Video.Media.DATA + " != 0";
        String[] projection = {
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.ARTIST,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DURATION

        };

        cursor =getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                null);

        List<String> songs = new ArrayList<String>();
        List<String> foldername = new ArrayList<String>();
        List<String> foldernamee = new ArrayList<String>();
        List<String> path = new ArrayList<String>();
        List<String> name = new ArrayList<String>();
        List<String> duration = new ArrayList<String>();
        List<String> id = new ArrayList<String>();
        final List<filemodel> songdetails = new ArrayList<>();
        while(cursor.moveToNext()) {


            foldername.add( Objects.requireNonNull( new File(cursor.getString(3)).getParent() ));
            path.add(cursor.getString(3));
            name.add( cursor.getString(2));
            duration.add( cursor.getString(5) );
            id.add(cursor.getString(0));
        }


        String[] a= foldername.toArray(new String[0]);
        String[] b= path.toArray(new String[0]);
        String[] c= name.toArray(new String[0]);
        String[] d= duration.toArray(new String[0]);
        String[] e= id.toArray(new String[0]);

            for (int i = 0; i < a.length; i++) {
                if (a[i].equals(patha)) {
                    ContentResolver crThumb = getContentResolver();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 1;
                    Bitmap curThumb = MediaStore.Video.Thumbnails.getThumbnail(crThumb, Long.parseLong(e[i]), MediaStore.Video.Thumbnails.MICRO_KIND, options);
                    songdetails.add(new filemodel(a[i], b[i], c[i], d[i], e[i], curThumb));
                    mAdapter = new fileadapter(this, (ArrayList<filemodel>) songdetails);
                    simpleList.setAdapter(mAdapter);
                }




            }



        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                filemodel listbuy = songdetails.get(i);

                path1 = listbuy.getData();
                duration1=listbuy.getDuration();
                name1=listbuy.getDispalyname();

                startActivity(new Intent(Main2Activity.this, mediaplayer.class));
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
                //music
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
