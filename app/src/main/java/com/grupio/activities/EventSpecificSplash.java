package com.grupio.activities;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.animation.SlideOut;
import com.grupio.gridhome.GridHome;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;

public class EventSpecificSplash extends BaseActivity {

    private Handler mHandler;
    private ImageView imageView;
    private ProgressBar progressBar;

    @Override
    public int getLayout() {
        return R.layout.activity_splash_screen;
    }

    @Override
    public void setUp() {
        mHandler = new Handler();

        File fileDir = Utility.getBaseFolder(this, getString(R.string.Resources));
        File file = new File(fileDir, getString(R.string.Resources) + File.separator + getString(R.string.splash));

        System.out.println("event specific splash: " + file.getAbsoluteFile());

        if (file.getAbsoluteFile().exists()) {
            ImageLoader.getInstance().displayImage("file://" + file.getAbsolutePath(), imageView);

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(EventSpecificSplash.this, GridHome.class));
                    SlideOut.getInstance().startAnimation(EventSpecificSplash.this);
                    finish();
                }
            }, 3000);
        }
    }

    @Override
    public void initIds() {
        imageView = (ImageView) findViewById(R.id.image);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean isHeaderForGridPage() {
        return false;
    }

    @Override
    public Object setPresenter() {
        return null;
    }

    @Override
    public void setListeners() {
    }

    @Override
    public String getScreenName() {
        return null;
    }

    @Override
    public String getBannerName() {
        return null;
    }

    @Override
    public void handleRightBtnClick() {
    }

}
