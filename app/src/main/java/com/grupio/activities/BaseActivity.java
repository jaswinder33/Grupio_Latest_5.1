package com.grupio.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.animation.SlideIn;
import com.grupio.gridhome.GridHome;

/**
 * Created by JSN on 5/7/16.
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected TextView header, rightBtn;
    protected ImageView leftBtn;
    protected View view;
    public EditText searchEditTxt;


    protected void onCreate(Bundle savedInstanceState, boolean initializeFB) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * Setup header of screen.
     */
    protected void setUpHeader() {
//        Utility.createGradientDrawable(header);
    }

    /**
     * Handle resources of right button on header
     * @param showBtn whether to show button or not
     * @param whichBtn which resource has to set.
     */
    protected void handleRightBtn(boolean showBtn, String whichBtn) {
        if (showBtn) {
            rightBtn.setVisibility(View.VISIBLE);

            switch (whichBtn) {
                case "refresh":
                    rightBtn.setBackgroundResource(R.drawable.refresh);
                    break;

                case "add":
                    rightBtn.setBackgroundResource(R.drawable.add_btn_top);
                    break;
            }
            rightBtn.setOnClickListener(this);
        } else {
            rightBtn.setVisibility(View.GONE);
        }
    }

    /**
     * Handle resources of left button on header
     * @param isFromGridScreen whether to show grid button otherwise show backBtn
     */

    public void handleLeftBtn(boolean showBtn, boolean isFromGridScreen){
        if(showBtn){
            handleLeftBtn(isFromGridScreen);
        }else{
            leftBtn.setVisibility(View.GONE);
        }
    }

    /**
     * Handle resources of left button on header
     * @param isFromGridScreen whether to show grid button otherwise show backBtn
     */
    protected void handleLeftBtn( boolean isFromGridScreen) {


        if (isFromGridScreen) {
            leftBtn.setImageResource(R.drawable.home);
        } else {
            leftBtn.setImageResource(R.drawable.btn_back);
        }

        leftBtn.setOnClickListener(this);


//        try {
//            if (GridHome.app_dashboard_button != null) {
//                leftBtn.setBackgroundDrawable(GridHome.app_dashboard_button);
//            }
//        } catch (Exception e) {
//        }


    }

    /**
     * Handle clicks of both right and left buttons
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.leftBtn:
                finish();
//                GridHome.ut_obj.callShowAnimationOutToIn(this);
//
                break;
//
            case R.id.rightBtn:
                //override right btn click according to need
                break;
        }
    }

    /**
     * Initialize view required to setup left/right buttons
     */
    public void doInitialThings(View view){
        this.view = view;
    }

    /**
     * Send report to visit of this screen to Server.
     * @param screenName which is sent to server
     */
    public void sendReport(String screenName){
//        Utility.sendThisReports(this, screenName);
    }

    /**
     * Start Banners on particular screen.
     */
    public void startBanner(String bannerName){
//        try {
//            ImageView bannerWebView = (ImageView) view.findViewById(R.id.bannerImageViewId);
//            ImageView bannerWebView2 = (ImageView) view.findViewById(R.id.bannerImageViewId2);
//            Utility.startBannerShow(bannerWebView, bannerWebView2, bannerName, this);
//        } catch (Exception e) {
//        }
    }

    /**
     * Setup search bar background and its hint
     */
    public void setUpSearchBar(boolean showSearchBar, String localeStr){

        View searchBarBgId = view.findViewById(R.id.searchBarBgId);
        searchEditTxt = (EditText) view.findViewById(R.id.exhibitor_filter_edtTxt);
//        if(showSearchBar){
//            searchBarBgId.setVisibility(View.VISIBLE);
//            //set the color code for ...
//            if(!GridHome.event_color.equals("")){
//                searchBarBgId.setBackgroundColor(Color.parseColor(GridHome.event_color));
//            }
//            if (!GridHome.displayName.equals("")) {
//                searchEditTxt.setHint(LocalisationDataProcessor.SEARCH + " " + GridHome.displayName);
//            } else {
//                searchEditTxt.setHint(LocalisationDataProcessor.SEARCH + "  " + localeStr);
//            }
//        }else{
//            searchBarBgId.setVisibility(View.GONE);
//        }
    }

    /**
     * Add textwatcher to searchbar
     * @param watcher
     */
    public void addFilter(TextWatcher watcher){
        searchEditTxt.addTextChangedListener(watcher);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SlideIn.getInstance().startAnimation(this);
//        GridHome.ut_obj.callShowAnimationOutToIn(this);
    }

    /**
     * Show Progress bar
     * @param msg string to show in loading bar
     */
    public void showProgress(String msg) {
        showDialog(msg);
//        GridHome.ut_obj.callProgressDialog(string + "...", BaseActivity.this);
    }

    /**
     * Hide Progress bar
     */
    public void hideProgress(){
        hideDialog();
//        GridHome.ut_obj.callProgressDialogDismiss();
    }


    public static final String TAG="baseactivity";
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if(requestCode == 100){
            Log.i(TAG, "onRequestPermissionsResult: " + requestCode );
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private ProgressDialog mProgressDialog;
    private void showDialog(String message) {
        if (mProgressDialog == null) {
            setProgressDialog(message);
            mProgressDialog.show();
        }

    }

    private void hideDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    private void setProgressDialog(String message) {
        mProgressDialog = new ProgressDialog(BaseActivity.this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(message);
    }

}
