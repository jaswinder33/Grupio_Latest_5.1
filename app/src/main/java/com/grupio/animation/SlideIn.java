package com.grupio.animation;

import android.app.Activity;

import com.grupio.R;

/**
 * Created by JSN on 19/9/16.
 */
public class SlideIn {

    public static SlideIn getInstance(){
        return new SlideIn();
    }

    public void startAnimation(Activity mActivity){
        mActivity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
