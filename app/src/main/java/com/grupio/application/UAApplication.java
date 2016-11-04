package com.grupio.application;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.grupio.session.Preferences;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;


/**
 * Created by JSN on 20/9/16.
 */
public class UAApplication extends Application{

    public static void initImageLoader(Context context) {
        /**
         * This configuration tuning is custom. You can tune every option, you may tune some of them,
         * or you can create default configuration by
         * ImageLoaderConfiguration.createDefault(this);
         * method.
         **/
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                        //.writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(this);

        try {
            TelephonyManager tManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            Preferences.getInstances(this).setDeviceID(tManager.getDeviceId());
            Log.i("Application", "onCreate: " + tManager.getDeviceId());
        } catch (Exception e) {
        }
    }

}
