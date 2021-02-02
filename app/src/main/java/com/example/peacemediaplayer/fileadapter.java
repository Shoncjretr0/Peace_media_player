package com.example.peacemediaplayer;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class fileadapter extends ArrayAdapter<filemodel> {

    private Context mContext;
    private List<filemodel> moviesList = new ArrayList<>();
    ImageView i;

    public fileadapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<filemodel> list) {
        super(context, 0 , list);
        mContext = context;
        moviesList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.activity_file,parent,false);

        filemodel currentMovie = moviesList.get(position);




        TextView name = (TextView) listItem.findViewById(R.id.textView5);
        name.setText(currentMovie.getDispalyname());
        i=listItem.findViewById(R.id.imageView);
        String id=currentMovie.getId();
        Bitmap albumart=currentMovie.getAlbumartt();

        //ContentResolver crThumb = mContext.getContentResolver();
        //BitmapFactory.Options options=new BitmapFactory.Options();
       // options.inSampleSize = 1;
       // Bitmap curThumb = MediaStore.Video.Thumbnails.getThumbnail(crThumb, Long.parseLong(id), MediaStore.Video.Thumbnails.MICRO_KIND, options);
        i.setImageBitmap(albumart);


        return listItem;
    }
}

