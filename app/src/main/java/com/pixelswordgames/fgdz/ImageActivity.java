package com.pixelswordgames.fgdz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.ortiz.touchview.TouchImageView;

public class ImageActivity extends AppCompatActivity {

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TouchImageView touchImageView = new TouchImageView(this);

        url = getIntent().getStringExtra("imageUrl");

        Glide.with(getApplicationContext())
                .load(url)
                .override(3500)
                .into(touchImageView);

        setContentView(touchImageView);
    }
}
