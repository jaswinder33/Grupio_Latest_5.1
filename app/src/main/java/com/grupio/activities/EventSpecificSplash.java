package com.grupio.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.animation.SlideOut;
import com.grupio.gridhome.GridHome;
import com.grupio.session.ConstantData;

import java.io.File;

public class EventSpecificSplash extends AppCompatActivity {

    private Handler mHandler;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        init();

        mHandler = new Handler();

        File fileDir = Utility.getBaseFolder(this, ConstantData.RESOURCES);
        File file = new File(fileDir, ConstantData.RESOURCES + File.separator + ConstantData.SPLASH);
        if (file.exists()) {

            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions);
            bitmap = Bitmap.createBitmap(bitmap);
            imageView.setImageBitmap(bitmap);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(EventSpecificSplash.this, GridHome.class));
                    SlideOut.getInstance().startAnimation(EventSpecificSplash.this);
                }
            }, 3000);

        }
    }

    private ImageView imageView;
    private ProgressBar progressBar;

    public void init() {
        imageView = (ImageView) findViewById(R.id.image);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.INVISIBLE);
    }

}
