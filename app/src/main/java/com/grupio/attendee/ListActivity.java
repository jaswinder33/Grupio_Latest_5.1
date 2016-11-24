package com.grupio.attendee;

import android.app.Fragment;
import android.os.Bundle;

import com.grupio.R;
import com.grupio.activities.BaseActivity;
import com.grupio.data.AttendeesData;
import com.grupio.data.ExhibitorData;
import com.grupio.data.SpeakerData;
import com.grupio.data.SponsorData;
import com.grupio.fragments.BaseFragment;

/**
 * This activity is base for all Attendee/Speaker/Sponsor/Exhibitor/Session activities. Using Generics to detech types.
 */

/**
 * Created by JSN on 25/7/16.
 */
public class ListActivity extends BaseActivity {

    @Override
    public boolean isHeaderForGridPage() {
        return true;
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

        handleRightBtn(true, "refresh");
        Bundle mBundle = getIntent().getExtras();

        if (mBundle != null) {
            String type = mBundle.getString("type");

            BaseFragment bFrag = null;

            if (type != null) {

                switch (type) {
                    case "Attendee":
                        bFrag = new ListFragment<>(new AttendeesData(), null);
                        break;
                    case "Speaker":
                        bFrag = new ListFragment<>(new SpeakerData(), null);
                        break;
                    case "Exhibitors":
                        bFrag = new ListFragment<>(new ExhibitorData(), null);
                        handleRightBtn(false, "refresh");
                        break;
                    case ListConstant.SPONSOR:
                        bFrag = new ListFragment<>(new SponsorData(), null);
                        handleRightBtn(false, null);
                        break;
                }
                getFragmentManager().beginTransaction().add(R.id.container, bFrag, bFrag.getClass().getName()).commit();
            }
        }
    }

    @Override
    public void handleRightBtnClick() {


        Fragment mFrag = getFragmentManager().findFragmentById(R.id.container);

        if (mFrag != null && mFrag instanceof ListFragment) {
            ((ListFragment) mFrag).reload();
        }
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
    protected void onDestroy() {
        super.onDestroy();
        ListWatcher.getInstance().unregisterListener();
    }
}
