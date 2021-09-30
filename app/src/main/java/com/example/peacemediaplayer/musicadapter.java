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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.media.MediaMetadataRetriever;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class musicadapter extends ArrayAdapter<AudioModel> {

    private Context mContext;
    private List<AudioModel> moviesList = new ArrayList<>();
    ImageView imagedisk;

    public musicadapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<AudioModel> list) {
        super(context, 0 , list);
        mContext = context;
        moviesList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.activity_musiclayout,parent,false);

        AudioModel currentMovie = moviesList.get(position);

        ImageView image = (ImageView)listItem.findViewById(R.id.roundedImageView);
        imagedisk= listItem.findViewById(R.id.imageView23);
        String g=currentMovie.getData();
        String f=currentMovie.getMid();
        Uri sArtworkUri = Uri.parse(g);
        Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, Long.parseLong(f));
       // if(albumArtUri.equals(null)) {

            //image.setImageURI(albumArtUri);
      //  }
       //  else {


      //  }

        image.setImageResource(R.drawable.ic_music_note_black_24dp);
        android.media.MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(currentMovie.getData().toString());

        byte [] data = mmr.getEmbeddedPicture();
        if(data != null)
        {
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            image.setImageBitmap(bitmap);

        }
        else
        {
            image.setImageResource(R.drawable.ic_music_note_black_24dp);

        }



        TextView name = (TextView) listItem.findViewById(R.id.textView5);
        name.setText(currentMovie.getmName());

        TextView release = (TextView) listItem.findViewById(R.id.textView7);



        String duration = currentMovie.getDuration();
        int a=Integer.parseInt(duration);
        String time = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(a),
                TimeUnit.MILLISECONDS.toSeconds(a) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(a))
        );
        release.setText(time);

        return listItem;
    }

    void diskrotate() {
        imagedisk.setBackgroundResource(R.drawable.diskanim);
        RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(5000);
        rotate.setRepeatCount(1000);
        rotate.setInterpolator(new LinearInterpolator());

        imagedisk.startAnimation(rotate);
    }

}

