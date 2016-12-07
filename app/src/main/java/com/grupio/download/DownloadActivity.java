package com.grupio.download;

import android.content.Intent;
import android.view.View;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.activities.BaseActivity;
import com.grupio.animation.SlideOut;

public class DownloadActivity extends BaseActivity<Void> {


    @Override
    public int getLayout() {
        return R.layout.layout_container;
    }

    @Override
    public void initIds() {

    }

    @Override
    public boolean isHeaderForGridPage() {
        return true;
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
    public Void setPresenter() {
        return null;
    }

    @Override
    public void setListeners() {
    }

    @Override
    public void setUp() {
        handleRightBtn(true, VIEW_ALL);
        Utility.replaceFragment(this, new DownloadFragment(), false);
    }

    @Override
    public void handleRightBtnClick(View view) {
        Intent mIntent = new Intent(this, ViewAllActivity.class);
        startActivity(mIntent);
        SlideOut.getInstance().startAnimation(this);
    }
}
