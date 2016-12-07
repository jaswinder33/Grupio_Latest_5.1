package com.grupio.logistics;

import android.os.Bundle;
import android.view.View;

import com.grupio.R;
import com.grupio.activities.BaseActivity;


public class LogisticsActivity extends BaseActivity {
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
        Bundle mBundle = new Bundle();
        mBundle.putString("type", getData());
        getFragmentManager().beginTransaction().add(R.id.container, LogisticsFragment.getInstance(mBundle), LogisticsFragment.class.getName()).commit();
    }

    public String getData() {
        Bundle mbundle = getIntent().getExtras();
        if (mbundle != null) {
            return mbundle.getString("type");
        }
        return "";
    }
}
