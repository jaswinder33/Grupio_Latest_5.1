package com.grupio.eventlist;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.grupio.BuildConfig;
import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.activities.BaseActivity;
import com.grupio.activities.EventSpecificSplash;
import com.grupio.animation.SlideOut;
import com.grupio.backend.Permissions;
import com.grupio.data.EventData;
import com.grupio.gridhome.GridHome;
import com.grupio.login.LoginActivity;
import com.grupio.service.DataFetchService;
import com.grupio.session.ConstantData;
import com.grupio.session.Preferences;

import java.io.File;
import java.util.List;

public class EventListActivity extends BaseActivity<EventListPresenter> implements View.OnClickListener, EventListView {

    EditText.OnEditorActionListener editor = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                String searchQuery = v.getText().toString();

                if (searchQuery.equals("")) {
                    onSearchError("Please Enter an Event Name!");
                } else {
                    getPresenter().fetchEventListFromServer(searchQuery);
                }

                return true;
            }
            return false;
        }
    };
    private ListView lstEvent;
    private TextView noDataAvailableTxt;
    private boolean isLoginRequired = false;
    ListView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Preferences.getInstances(EventListActivity.this).setIsAppVisited(false);

            deleteDatabase(ConstantData.DATABASE);

            File cacheDir;

            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                cacheDir = new File(Environment.getExternalStorageDirectory(), BuildConfig.BASE_FOLDER);

                if (cacheDir.exists()) {
                    Utility.deleteRecursive(cacheDir);
                }
            }

            EventData eData = (EventData) parent.getAdapter().getItem(position);
            ConstantData.EVENT_ID = eData.getEvent_id();
            Preferences.getInstances(EventListActivity.this).setEventId(eData.getEvent_id());

            isLoginRequired = eData.getLoginRequired().equals("y");
            showProgess();
            startService();
        }
    };
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            hideProgress();


            Intent mIntent;
            if (isLoginRequired) {
                mIntent = new Intent(EventListActivity.this, LoginActivity.class);
            } else {
                File fileDir = Utility.getBaseFolder(EventListActivity.this, getString(R.string.Resources));
                File file = new File(fileDir, getString(R.string.Resources) + File.separator + getString(R.string.splash));

                String filePath = file.getAbsolutePath();
                Log.i(TAG, "onReceive: SplashExists on " + filePath);

                if (new File(filePath).exists()) {
                    System.out.println("exist");
                    mIntent = new Intent(EventListActivity.this, EventSpecificSplash.class);
                } else {
                    System.out.println("dsn't exist");
                    mIntent = new Intent(EventListActivity.this, GridHome.class);
                }
            }

            startActivity(mIntent);
            finish();
            SlideOut.getInstance().startAnimation(EventListActivity.this);
        }
    };

    @Override
    public int getLayout() {
        return R.layout.activity_event_list;
    }

    @Override
    public void setUp() {
        handleLeftBtn(false, true);
        handleRightBtn(true, "refresh");
        setupSearchBar(true, "");
        searchEditTxt.setOnEditorActionListener(editor);
    }

    @Override
    public void initIds() {
        lstEvent = (ListView) findViewById(R.id.lstEvent);
        noDataAvailableTxt = (TextView) findViewById(R.id.txtNoData);
        lstEvent.setEmptyView(noDataAvailableTxt);
    }

    @Override
    public boolean isHeaderForGridPage() {
        return false;
    }

    @Override
    public EventListPresenter setPresenter() {
        return new EventListPresenter(this, this);
    }

    @Override
    public void setListeners() {
        lstEvent.setOnItemClickListener(onItemClickListener);
    }

    @Override
    public String getScreenName() {
        return "EVENT_LIST_VIEW";
    }

    @Override
    public String getBannerName() {
        return null;
    }

    @Override
    public void handleRightBtnClick() {
        getPresenter().fetchEventListFromServer("");
    }

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
    public void onFailure(String msg) {
        runOnUiThread(() -> Toast.makeText(this, msg, Toast.LENGTH_LONG).show());
    }

    @Override
    public void showProgess() {
        showProgressDialog("Loading Events...");
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    @Override
    public void showEventList(List<EventData> mlist) {

        runOnUiThread(() -> {
            Log.i("Event List Size", mlist.size() + "");
            checkForPermissions();
            EventListAdapter mAdapter = new EventListAdapter(EventListActivity.this);
            mAdapter.addAll(mlist);
            lstEvent.setAdapter(mAdapter);
        });

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

    public void startService() {
        Intent mIntent = new Intent(EventListActivity.this, DataFetchService.class);
        startService(mIntent);
    }

    public void checkForPermissions() {
        Permissions.getInstance().checkForAllPermissions(this).askForPermissions(this, 100);
    }

    /**
     * Override searchBar behaviour of baseclass
     *
     * @param showSearchBar
     * @param locale
     */
    @Override
    public void setupSearchBar(boolean showSearchBar, String locale) {
        View searchBarBgId = findViewById(R.id.searchBarBgId);
        searchEditTxt = (EditText) findViewById(R.id.exhibitor_filter_edtTxt);
        if (showSearchBar) {
            searchBarBgId.setVisibility(View.VISIBLE);
        } else {
            searchBarBgId.setVisibility(View.GONE);
        }
    }

    /**
     * Override header setup behaviour of baseclass.
     *
     * @param isGridHome
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    protected void setUpHeader(boolean isGridHome) {

    }

}
