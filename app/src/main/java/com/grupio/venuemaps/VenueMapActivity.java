package com.grupio.venuemaps;

import android.os.Bundle;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.activities.BaseActivity;
import com.grupio.data.AlertData;
import com.grupio.data.LiveData;
import com.grupio.data.MapsData;
import com.grupio.data.SurveyData;


/**
 * This actiity handles 4 menus
 * VENUEMAPS
 * LIVE
 * SURVEY
 * ALERT
 * <p>
 * All are of similar type. These menus first have simple list of elements and then opens with webveiw.
 */
public class VenueMapActivity extends BaseActivity {

    private String type = "";

    @Override
    public boolean isHeaderForGridPage() {
        return true;
    }

    @Override
    public void handleRightBtnClick() {
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

        getData();

        VenueMapsFragment mFrag = null;

        switch (type) {
            case "venueMaps":
                mFrag = new VenueMapsFragment(new MapsData(), null);
                break;

            case "alert":
                mFrag = new VenueMapsFragment(new AlertData(), null);
                break;

            case "Live":
                mFrag = new VenueMapsFragment(new LiveData(), null);
                break;

            case "Survey":
                mFrag = new VenueMapsFragment(new SurveyData(), null);
                break;

        }
        Utility.addFragment(this, mFrag, false);
    }


    public void getData() {
        Bundle mbundle = getIntent().getExtras();

        if (mbundle != null) {
            type = mbundle.getString("type");
        }
    }
}
