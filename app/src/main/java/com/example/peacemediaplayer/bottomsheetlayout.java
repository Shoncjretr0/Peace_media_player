package com.example.peacemediaplayer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class bottomsheetlayout extends BottomSheetDialogFragment {

    String name=music.name;
    String path=music.path;
    public TextView fg,hj;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_bottomsheetlayout, container, false);

         fg=v.findViewById(R.id.textView10);
         hj=v.findViewById(R.id.textView11);
         fg.setText(name);
        MediaMetadataRetriever metaRetriver = new MediaMetadataRetriever();
        metaRetriver.setDataSource(path);
        try {

            hj.setText(metaRetriver
                    .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));

        } catch (Exception e) {

            hj.setText("Unknown Artist");
        }


        return v;
    }


}
