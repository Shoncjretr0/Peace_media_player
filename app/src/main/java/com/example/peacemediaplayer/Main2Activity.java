package com.example.peacemediaplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;

public class Main2Activity extends AppCompatActivity {

    ListView simpleList;
    String countryList[] = {"India", "China", "australia", "Portugle", "America", "NewZealand"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.topBr);
        simpleList = (ListView)findViewById(R.id.listviewbuy);

        setSupportActionBar(toolbar);
        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_file, R.id.textView5, countryList);
        simpleList.setAdapter(arrayAdapter);


       String path = Environment.getExternalStorageDirectory().getAbsolutePath();  // +"/Your Folder/"
        File f = new File(path);
        File[] file = f.listFiles();
      /*  for (int i = 0; i < file.length; i++)
       {
           Log.d("Files", "FileName:" + file[i].getName());
        }*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.editprofile, menu);
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
            case R.id.item4:
                //nightmode


                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
