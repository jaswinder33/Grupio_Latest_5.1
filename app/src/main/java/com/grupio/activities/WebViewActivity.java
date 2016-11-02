package com.grupio.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.grupio.R;
import com.grupio.animation.SlideOut;
import com.grupio.attendee.ListActivity;
import com.grupio.attendee.ListDetailActivity;
import com.grupio.dao.ExhibitorDAO;
import com.grupio.dao.SessionDAO;
import com.grupio.data.ExhibitorData;
import com.grupio.data.ScheduleData;
import com.grupio.interfaces.Person;
import com.grupio.session.ConstantData;
import com.grupio.session.Preferences;


public class WebViewActivity extends BaseActivity {

    public static final String EXHIBITORS = "exhibitor";
    public static final String SESSIONS = "sessions";
    private WebView webView;
    private String url;
    private ProgressBar progressBar;

    @Override
    public boolean isHeaderForGridPage() {
        return false;
    }

    @Override
    public void handleRightBtnClick() {
    }

    @Override
    public String getScreenName() {
        return null;
    }

    @Override
    public String getBannerName() {
        return null;
    }

    @Override
    public void initIds() {
        webView = (WebView) findViewById(R.id.webview);
        progressBar = (ProgressBar) findViewById(R.id.progress);
    }

    @Override
    public void setListeners() {

    }

    @Override
    public Object setPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_web_view;
    }

    @Override
    public void setUp() {
        getdata();

        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);

        webView.setWebViewClient(new CustomWebClient());
        webView.setWebChromeClient(new CustomChromeClient());
        addCookies();
    }

    public void getdata() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            url = mBundle.getString("url");
        }
    }

    public void addCookies() {

        if (Preferences.getInstances(this).getAttendeeId() == null) {
            webView.loadUrl(url);
        } else {
            String cookieString = "attendee_id" + "="
                    + Preferences.getInstances(this).getAttendeeId() + "; domain=conf.dharanet.com";
            CookieManager.getInstance().setCookie(url, cookieString);
            webView.loadUrl(url);

        }
    }

    public void processUrl(String url, String type) {
        String[] ids = url.substring(url.lastIndexOf("/") + 1).split("@");
        if (ids.length == 1) {
            switch (type) {
                case EXHIBITORS:
                    ExhibitorData mExhibitor = ExhibitorDAO.getInstance(this).getExhibitorDetal(ids[0]);
                    showAlert(mExhibitor.getName(), mExhibitor);
                    break;
                case SESSIONS:
                    ScheduleData mSessionData = SessionDAO.getInstance(this).getSessionWithId(ids[0]);
                    showAlert(mSessionData.getName(), mSessionData);
                    break;
            }
        } else {
            Intent mIntent = null;
            switch (type) {
                case EXHIBITORS:
                    mIntent.putExtra("type", "Exhibitors");
                    mIntent.setClass(WebViewActivity.this, ListActivity.class);
                    break;
                case SESSIONS:
//                    mIntent.setClass(WebViewActivity.this, ListActivity.class);
                    break;
            }

            startActivity(mIntent);
            SlideOut.getInstance().startAnimation(this);
        }
    }

    public void showAlert(String messsag, Person mData) {
        new CustomDialog("Show details", "Cancel", () -> {
            sendIntent(mData);
        }).show(messsag);
    }

    public void sendIntent(Person mData) {
        Intent mIntent = new Intent(this, ListDetailActivity.class);

        if (mData instanceof ExhibitorData) {
            mIntent.putExtra("id", ((ExhibitorData) mData).getExhibitorId());
            mIntent.setType("exhibitor");
            mIntent.putExtra("data", mData);
            startActivity(mIntent);
            SlideOut.getInstance().startAnimation(this);
        }
//        else if(mData instanceof ScheduleData){
//            mIntent.putExtra("id",((ScheduleData) mData).getSession_id());
//            mIntent.setType("Sessions");
//        }
//        mIntent.putExtra("data", mData);
//        startActivity(mIntent);
//        SlideOut.getInstance().startAnimation(this);
    }

    public class CustomWebClient extends WebViewClient {


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            super.shouldOverrideUrlLoading(view, url);

            Log.i(TAG, "shouldOverrideUrlLoading: " + url);

            if (url.startsWith("http://www.grupio.com/interactive/" + ConstantData.EVENT_ID + "/exhibitor") || url.startsWith("https://www.grupio.com/interactive/" + ConstantData.EVENT_ID + "/exhibitor")) {
                processUrl(url, EXHIBITORS);
            } else if (url.startsWith("http://www.grupio.com/interactive/" + ConstantData.EVENT_ID + "/session") || url.startsWith("https://www.grupio.com/interactive/" + ConstantData.EVENT_ID + "/session")) {
                processUrl(url, SESSIONS);
            } else {
                view.loadUrl(url);
            }

            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Toast.makeText(WebViewActivity.this, error.getDescription(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class CustomChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);

            progressBar.setProgress(newProgress);
        }
    }
}
