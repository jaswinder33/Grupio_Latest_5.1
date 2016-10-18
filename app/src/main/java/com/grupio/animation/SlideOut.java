package com.grupio.animation;

import android.app.Activity;

import com.grupio.R;

/**
 * Created by JSN on 19/9/16.
 */
public class SlideOut {

    public static SlideOut getInstance(){
        return new SlideOut();
    }

    public void startAnimation(Activity mActivity){
        mActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

}
