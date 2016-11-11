package com.grupio.social;

import com.grupio.fragments.BaseFragment;

/**
 * Created by JSN on 11/11/16.
 */

public class SocialFragment extends BaseFragment<SocialPresenter> {

    public static SocialFragment getInstance() {
        return new SocialFragment();
    }

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public void initIds() {

    }

    @Override
    public void setListeners() {

    }

    @Override
    public void setUp() {

    }

    @Override
    public SocialPresenter setPresenter() {
        return null;
    }

    @Override
    public String getScreenName() {
        return null;
    }

    @Override
    public String getBannerName() {
        return null;
    }
}
