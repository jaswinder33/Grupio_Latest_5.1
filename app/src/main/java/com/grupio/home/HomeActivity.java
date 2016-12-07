package com.grupio.home;

import android.view.View;

import com.grupio.R;
import com.grupio.activities.BaseActivity;

public class HomeActivity extends BaseActivity {

    @Override
    public boolean isHeaderForGridPage() {
        return true;
    }

    @Override
    public void handleRightBtnClick(View view) {
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
    public void initIds() {

    }

    @Override
    public void setListeners() {

    }

    @Override
    public Object setPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.layout_container;
    }

    @Override
    public void setUp() {
        setUpHeader(false);
        handleLeftBtn(true);
        handleRightBtn(false, "refresh");
        getFragmentManager().beginTransaction().add(R.id.container, HomeActivityFragment.getInstance(null), HomeActivityFragment.class.getName()).commit();
    }
}
