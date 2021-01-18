package com.example.peacemediaplayer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;

import android.os.Environment;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static String name,namee;
    int uploads=0;
    Button button1,button2,w;
    Uri imageuri;
    ImageView image;
    private static int PICK_IMAGE = 123;
    public ArrayList<Uri> ImageList = new ArrayList<Uri>();
    private String text;

    RelativeLayout rr;
    private Menu menu;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String mode = "day";
    String modetext;
    ListView simpleList;
    String countryList[] = {"India", "China", "australia", "Portugle", "America", "NewZealand"};
    SharedPreferences sharedpreferences;
    private static final int STORAGE_PERMISSION_CODE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.topBr);
        simpleList = (ListView)findViewById(R.id.listviewbuy);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_folder, R.id.textView, countryList);
        simpleList.setAdapter(arrayAdapter);

        setSupportActionBar(toolbar);
        loadData();
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
       // String nameee = sharedpreferences.getString("mode", "");
       // Toast.makeText(MainActivity.this, nameee,Toast.LENGTH_LONG).show();



       // File dir = new File(Environment.getExternalStorageDirectory() + "/yourfolder");
       /* File dir = new File(String.valueOf(Environment.getExternalStorageDirectory()));
        File[] files = dir.listFiles();
        if (files != null) {
            int numberOfFiles = files.length;
            Toast.makeText(MainActivity.this, numberOfFiles,Toast.LENGTH_LONG).show();
            String v=String.valueOf(Environment.getExternalStorageDirectory());
            Toast.makeText(MainActivity.this, v ,Toast.LENGTH_LONG).show();

        }*/









       // image.setOnClickListener(new View.OnClickListener() {
      //      @Override
      //      public void onClick(View view) {

     //            rr.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.nightmode));

      //      }
     //   });








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
                String shareBody="https://shoncj.wordpress.com/2020/03/25/thunder-app/ try this app";
                String shareSub="hope you download it";
                myIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                myIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(myIntent,"Share Using"));
                return true;
            case R.id.item2:

                    select();
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
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_wb_sunny_black_24dp));
            loadData();

        }
        else Toast.makeText(MainActivity.this, "error", Toast.LENGTH_LONG).show();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
       modetext = sharedPreferences.getString(mode, "");

    }

    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] { permission },
                    requestCode);
        }
        else {
            Toast.makeText(MainActivity.this,
                    "",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

       if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(MainActivity.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }


}



