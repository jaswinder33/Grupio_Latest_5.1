package com.grupio.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.apis.ApiInter;
import com.grupio.apis.MenuAPI;
import com.grupio.apis.VersionAPI;
import com.grupio.session.ConstantData;
import com.grupio.session.Preferences;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by JSN on 19/8/16.
 */
public class DataFetchService extends IntentService {

    public static final String TAG = "DataFetchService";
    static long startTime;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public DataFetchService() {
        super("DataFetchService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if(!Utility.hasInternet(this)){
            Toast.makeText(this, getResources().getText(R.string.no_internet), Toast.LENGTH_SHORT).show();
            return;
        }

         startTime = System.currentTimeMillis();

        Log.i(TAG, "onHandleIntent: service started@" + startTime);


        List<ApiInter> mMenuList = new ArrayList<>();

        if (Preferences.getInstances(this).isAppVisited()) {

            ConstantData.EVENT_ID = Preferences.getInstances(this).getEventId();

            ExecutorService es = Executors.newSingleThreadExecutor();
            Future<List<ApiInter>> mTask = es.submit(new VersionAPI(this));

            try {
                mMenuList.addAll(mTask.get());
                 es.shutdown();
                es.awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        } else {

            Log.i(TAG, "onHandleIntent: not visited case");

            ExecutorService es = Executors.newSingleThreadExecutor();
            Future<List<ApiInter>> mTask = es.submit(new MenuAPI(this));

            try {
                mMenuList.addAll(mTask.get());
                es.shutdown();
                es.awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        Log.i(TAG, "onHandleIntent: got menu list");

        fetchDataFromServer(mMenuList);
    }

    public void fetchDataFromServer(List<ApiInter> mList) {
        Log.i(TAG, "fetchDataFromServer: list size" + mList.size());
        DataFetchService.Builder mBuilder = new DataFetchService.Builder(this);
        mBuilder.addAll(mList).build();
    }

    private class Builder {

        private Context mContext;
        private List<ApiInter> mList;

        public Builder(Context mContext) {
            mList = new ArrayList<>();
            this.mContext = mContext;
        }

        public Builder addAPI(ApiInter task) {
            mList.add(task);
            return this;
        }

        public Builder addAll(List<ApiInter> pList) {
            mList.addAll(pList);
            return this;
        }

        public void build() {

            ExecutorService es = Executors.newFixedThreadPool(10);
            for (int i = 0; i < mList.size(); i++) {
                if(mList.get(i) instanceof Runnable) {
                    es.submit((Runnable) mList.get(i));
                }else if(mList.get(i) instanceof Callable){
                    es.submit((Callable) mList.get(i));
                }
                Log.i(TAG, "build: Task submitted" + mList.get(i).getClass().getSimpleName());
            }

            es.shutdown();
            try {
                es.awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            goToNextScreen();

        }

        public void goToNextScreen() {
            Log.i(TAG, "goToNextScreen");
            Log.i("Service", "EndTime: " + (System.currentTimeMillis() - startTime));
            sendBroadcast(new Intent("GO_TO_NEXT_SCREEN"));
        }

    }
}
