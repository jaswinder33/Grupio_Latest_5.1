package com.grupio.social;

import android.os.Bundle;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.activities.BaseActivity;

public class SocialActivity extends BaseActivity {

    @Override
    public int getLayout() {
        return R.layout.layout_container;
    }

    @Override
    public void initIds() {

    }

    @Override
    public boolean isHeaderForGridPage() {
        return false;
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
    public Object setPresenter() {
        return null;
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void setUp() {
        handleLeftBtn(getData());
        Utility.addFragment(this, SocialFragment.getInstance(), false);
    }

    @Override
    public void handleRightBtnClick() {

    }


    public boolean getData() {

        boolean isFromGridPage = false;
        Bundle mBundle = getIntent().getExtras();

        if (mBundle != null) {
            isFromGridPage = mBundle.getBoolean("isFromGrid");
        }

        return isFromGridPage;
    }
}
