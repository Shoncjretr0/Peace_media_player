package com.example.peacemediaplayer;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static String name,namee,mode;
    int uploads=0;
    Button button1,button2,w;
    Uri imageuri;
    ImageView image;
    private static int PICK_IMAGE = 123;
    public ArrayList<Uri> ImageList = new ArrayList<Uri>();
    private String text;

    RelativeLayout rr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        Button button =findViewById(R.id.button);
        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        w=findViewById(R.id.button3);
        setSupportActionBar(toolbar);
        image=findViewById(R.id.tv_header_title);
        rr=findViewById(R.id.relativeLayout);
        mode="zero";

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 rr.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.nightmode));

            }
        });





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, PICK_IMAGE);

            }
        });

       button1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               startActivity(new Intent(MainActivity.this, folder.class));
           }
       });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, file.class));
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.editprofile, menu);
        return true;
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



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
