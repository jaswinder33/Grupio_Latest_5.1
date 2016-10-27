package com.grupio.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.grupio.BuildConfig;
import com.grupio.animation.SlideOut;
import com.grupio.eventlist.EventListActivity;
import com.grupio.gridhome.GridHome;
import com.grupio.service.DataFetchService;
import com.grupio.session.Preferences;


public class SplashScreen extends AppCompatActivity {

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            startActivity(new Intent(SplashScreen.this, GridHome.class));
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        sendReport("APP_LAUNCH");
        if (Preferences.getInstances(this).isAppVisited() || BuildConfig.SINGLE_EVENT) {
            startService();
        } else {
            Intent intent = new Intent(this, EventListActivity.class);
            startActivity(intent);
            SlideOut.getInstance().startAnimation(this);
            finish();
        }
    }

    public void startService() {
        Intent mIntent = new Intent(SplashScreen.this, DataFetchService.class);
        startService(mIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(broadcastReceiver, new IntentFilter("GO_TO_NEXT_SCREEN"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }

}
