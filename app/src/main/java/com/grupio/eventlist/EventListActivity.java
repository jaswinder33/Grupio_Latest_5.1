package com.grupio.eventlist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.activities.BaseActivity;
import com.grupio.activities.EventSpecificSplash;
import com.grupio.animation.SlideIn;
import com.grupio.animation.SlideOut;
import com.grupio.backend.Permissions;
import com.grupio.data.EventData;
import com.grupio.login.LoginActivity;
import com.grupio.services.DataFetchService;
import com.grupio.session.ConstantData;

import java.io.File;
import java.util.List;

public class EventListActivity extends BaseActivity implements View.OnClickListener, EventListView {

    private ListView lstEvent;
    private EventListPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        init();

        View view = getWindow().getDecorView().getRootView();
        doInitialThings(view);
        sendReport("EVENT_LIST_VIEW");
        handleLeftBtn(false, true);
        handleRightBtn(true, "refresh");
        setUpSearchBar(true,"");
        setListener();
        searchEditTxt.setOnEditorActionListener(editor);

        mPresenter = new EventListPresenter(this, this);
    }

    private void init() {
        header = (TextView) findViewById(R.id.header);
        leftBtn = (ImageView) findViewById(R.id.leftBtn);
        rightBtn = (TextView) findViewById(R.id.rightBtn);
        lstEvent = (ListView) findViewById(R.id.lstEvent);
    }

    public void setListener() {
        lstEvent.setOnItemClickListener(onItemClickListener);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.rightBtn:
                mPresenter.fetchEventListFromServer("");
                break;
        }
    }

    EditText.OnEditorActionListener editor = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                String searchQuery = v.getText().toString();

                if (searchQuery.equals("")) {
                    onSearchError("Please Enter an Event Name!");
                } else {
                    mPresenter.fetchEventListFromServer(searchQuery);
                }

                return true;
            }
            return false;
        }
    };

    @Override
    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onSearchError(String errorMsg) {
//        GridHome.ut_obj.showMessage("Message", errorMsg, EventListActivity.this);
    }

    @Override
    public void showProgess() {
        showProgress("Loading Events...");
    }

    @Override
    public void showEventList(List<EventData> mlist) {
        Log.i("Event List Size", mlist.size() + "");

        checkForPermissions();

        EventListAdapter mAdapter = new EventListAdapter(this);
        mAdapter.addAll(mlist);
        lstEvent.setAdapter(mAdapter);

    }

    private boolean isLoginRequired = false;

    ListView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            EventData eData = (EventData) parent.getAdapter().getItem(position);
            ConstantData.EVENT_ID = eData.getEvent_id();

            isLoginRequired = eData.getLoginRequired().equals("y") ? true : false;
            startService();
        }
    };

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

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            hideProgress();

            Intent myintent;
            if(isLoginRequired){
//                 intent = new Intent(EventListActivity.this, LoginActivity.class);
                startActivity(new Intent(EventListActivity.this, LoginActivity.class));
            }else{

                File fileDir = Utility.getBaseFolder(EventListActivity.this, ConstantData.RESOURCES);
                File file = new File(fileDir,ConstantData.RESOURCES + File.separator + ConstantData.SPLASH);
                if(file.exists()){
                    startActivity(new Intent(EventListActivity.this, EventSpecificSplash.class));
                    finish();
                    SlideOut.getInstance().startAnimation(EventListActivity.this);
                }else{

                }


            }

        }
    };

    public void startService() {
        Intent mIntent = new Intent(EventListActivity.this, DataFetchService.class);
        startService(mIntent);
    }

    public void checkForPermissions(){
        Permissions.getInstance().checkForAllPermissions(this).askForPermissions(this, 100);
    }

}
