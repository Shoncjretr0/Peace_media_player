package com.example.peacemediaplayer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.FileInputStream;

public class bottomsheetlayout extends BottomSheetDialogFragment {

    String name=music.name;
    String path=music.path;
    ImageView top,bottom;
    Bitmap bitmap=null;
    public TextView fg,hj;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_bottomsheetlayout, container, false);

        fg=v.findViewById(R.id.textView10);
        hj=v.findViewById(R.id.textView11);
        top=v.findViewById(R.id.imageView13);
        bottom=v.findViewById(R.id.imageview001);
        fg.setText(name);
        diskrotate();
        MediaMetadataRetriever metaRetriver = new MediaMetadataRetriever();
        Toast.makeText(getActivity(), path,
                Toast.LENGTH_LONG).show();
        if(path!=null) {
            metaRetriver.setDataSource(path);
            try {

                hj.setText(metaRetriver
                        .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));


                android.media.MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                mmr.setDataSource(path);

                byte [] data = mmr.getEmbeddedPicture();
                if(data != null)
                {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    bottom.setImageBitmap(bitmap);
                    diskrotate();
                }
                else
                {
                    bottom.setImageResource(R.drawable.ic_music_note_black_24dp);
                    diskrotate();
                }

            } catch (Exception e) {

                hj.setText("Unknown Artist");
            }

        }
        return v;
    }
    void diskrotate() {
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(5000);
        rotate.setRepeatCount(1000);
        rotate.setInterpolator(new LinearInterpolator());

        top.startAnimation(rotate);
    }


}