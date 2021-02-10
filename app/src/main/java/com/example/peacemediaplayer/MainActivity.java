package com.example.peacemediaplayer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    public static String name,namee;
    int uploads=0;
    Button button1,button2,w;
    Uri imageuri;
    ImageView image;
    private static int PICK_IMAGE = 123;
    public ArrayList<Uri> ImageList = new ArrayList<Uri>();
    private String text;
    Cursor cursor;

    RelativeLayout rr;
    private Menu menu;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String mode = "day";
    String modetext;
    ListView simpleList;
    private videoadapter mAdapter;
    SharedPreferences sharedpreferences;
    public static String path;


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.topBr);
        simpleList = (ListView)findViewById(R.id.listviewbuy);


        setSupportActionBar(toolbar);
        loadData();

        String selection=MediaStore.Video.Media.DATA + " != 0";
        Toast.makeText(MainActivity.this, selection, Toast.LENGTH_SHORT).show();
        String[] projection = {
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.ARTIST,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DURATION,

        };

        cursor =getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                null);

         List<String> songs = new ArrayList<String>();
        List<String> foldername = new ArrayList<String>();
        List<String> foldernamecompare = new ArrayList<String>();
        List<String> foldernamee = new ArrayList<String>();
        final List<videomodel> songdetails = new ArrayList<>();
        while(cursor.moveToNext()) {
            /*songs.add(cursor.getString(0) + "||"
                    + cursor.getString(1) + "||"
                    + cursor.getString(2) + "||"
                    + cursor.getString(3) + "||"
                    + cursor.getString(4) + "||"
                    + cursor.getString(5));*/

               foldernamecompare.add( Objects.requireNonNull(new File(cursor.getString(3)).getParent()));
            foldername.add( Objects.requireNonNull(new File(cursor.getString(3)).getParentFile()).getName() +"|"+ new File(cursor.getString(3)).getParent() );
        }
        Set<String> set = new HashSet<>(foldername);
        foldernamee.addAll(set);

        String[] a= foldernamee.toArray(new String[0]);
        String[] b= foldernamecompare.toArray(new String[0]);
        for (int i=0;i<a.length;i++){

            int count=0;
            int indexOfDash = a[i].indexOf('|');
            String before = a[i].substring(0, indexOfDash);
            String after = a[i].substring(indexOfDash + 1);
             for(int j=0;j<b.length;j++){
                 if(b[j].equals(after)){
                     count=count+1;
                 }
             }
           songdetails.add( new videomodel(before,after,count));
        }

        //int indexOfDash = dd.indexOf('|');
       // String before = dd.substring(0, indexOfDash);
      //  String after = dd.substring(indexOfDash + 1);
        //Toast.makeText(MainActivity.this, before +","+after, Toast.LENGTH_SHORT).show();



       // ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_folder, R.id.textView,foldernamee);
        //simpleList.setAdapter(arrayAdapter);
        mAdapter = new videoadapter(this, (ArrayList<videomodel>) songdetails );
        simpleList.setAdapter(mAdapter);
        //if (fullText.toLowerCase().indexOf(singleWord.toLowerCase()) > -1) {

           // find = true;
       // }
        // File f = new File("/home/jigar/Desktop/1.txt");
        // String g=f.getParent();
        // String h="/home/jigar/Desktop/1.txt";
        //String result = Objects.requireNonNull(new File(h).getParentFile()).getName();
        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                videomodel listbuy = songdetails.get(i);

                path = listbuy.getPath();

                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
            });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.editprofile, menu);
        this.menu = menu;
        if(modetext.equals("day"))
        {
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_wb_sunny_black_24dp));
        }
        return true;
    }






    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Intent myIntent=new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody="https://shoncj.wordpress.com/2021/02/05/peace-media-player/?preview==true";
                String shareSub="hope you download it";
                myIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                myIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(myIntent,"Share Using"));
                return true;
            case R.id.item2:

                   // select();
                return true;
            case R.id.item3:
               //music
                startActivity(new Intent(MainActivity.this, music.class));
                return true;
            case R.id.item4:
                //nightmode
                 modde();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

}
void select(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMAGE);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK) {

                imageuri = data.getData();
                ImageList.add(imageuri);
                rrmain();
                startActivity(new Intent(MainActivity.this, mediaplayer.class));

            }

        }


    }
    public void rrmain(){
        name=imageuri.toString();
        File file= new File(imageuri.getPath());
        namee= file.getName();


    }
    public void modde(){

        if(modetext.equals("day")) {
            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(mode, "night");
            editor.apply();
            Toast.makeText(MainActivity.this, "night mode", Toast.LENGTH_SHORT).show();
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_brightness_3_black_24dp));
            loadData();
        }
        else if(modetext.equals("night")){
            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(mode, "day");
            editor.apply();
            Toast.makeText(MainActivity.this, "day mode", Toast.LENGTH_SHORT ).show();
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.vol));
            loadData();

        }
        else if(modetext.equals(null)){
            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(mode, "day");
            editor.apply();
            Toast.makeText(MainActivity.this, "day mode", Toast.LENGTH_SHORT ).show();
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.vol));
            loadData();

        }
        else Toast.makeText(MainActivity.this, "error", Toast.LENGTH_LONG).show();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
       modetext = sharedPreferences.getString(mode, "");

    }






}



