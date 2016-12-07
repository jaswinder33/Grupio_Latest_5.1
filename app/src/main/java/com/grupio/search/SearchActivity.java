package com.grupio.search;


import android.view.View;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.activities.BaseActivity;

public class SearchActivity extends BaseActivity<Void> {
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
        Utility.replaceFragment(this, new SearchFragment(), false);
    }

    @Override
    public void handleRightBtnClick(View view) {

    }


}
