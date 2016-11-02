package com.grupio.schedule;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.grupio.R;
import com.grupio.animation.SlideIn;

public class ScheduleDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_detail);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SlideIn.getInstance().startAnimation(this);
    }
}
