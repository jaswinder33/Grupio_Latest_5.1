package com.grupio.activities;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.animation.SlideIn;
import com.grupio.attendee.ListDetailPresenter;
import com.grupio.dao.EventDAO;
import com.grupio.db.EventTable;
import com.grupio.interfaces.BaseFunctionality;
import com.grupio.interfaces.ClickHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;

/**
 * Created by JSN on 5/7/16.
 */
public abstract class BaseActivity<Presenter> extends AppCompatActivity implements BaseFunctionality, View.OnClickListener {

    public static final String TAG = "baseactivity";
    public static final int SD_READ_WRITE_PERMISSION = 100;
    public static final int CALL_PERMISSION = 101;
    public static final String REFRESH = "refresh";
    public static final String ADD = "add";

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public EditText searchEditTxt;
    protected TextView header, rightBtn;
    protected ImageView leftBtn;
    protected RelativeLayout background;
    private Presenter mPresenter;
    private ProgressDialog mProgressDialog;

    protected void onCreate(Bundle savedInstanceState, boolean initializeFB) {
        onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(getLayout());
        init();
        setUpHeader(isHeaderForGridPage());
        handleLeftBtn(isHeaderForGridPage());
        sendReport(getScreenName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerListeners();
        setUp();
    }

    public abstract boolean isHeaderForGridPage();

    /**
     * Setup header of screen.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    protected void setUpHeader(boolean isGridHome) {
        File fileDir = Utility.getBaseFolder(this, getString(R.string.Resources));
        File file;
        if (isGridHome) {
            file = new File(fileDir, getString(R.string.Resources) + File.separator + getString(R.string.main_header));
        } else {
            file = new File(fileDir, getString(R.string.Resources) + File.separator + getString(R.string.top_nav));
        }

        try {
            if (file.getAbsoluteFile().exists()) {
                Bitmap bitmap = ImageLoader.getInstance().loadImageSync("file://" + file.getAbsolutePath());
                header.setBackground(new BitmapDrawable(getResources(), bitmap));
            } else {
                header.setText(EventDAO.getInstance(this).getValue(EventTable.EVENT_NAME));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set background on Gridhome page
     *
     * @param isGridHome
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    protected void setBackground(boolean isGridHome) {
        File fileDir = Utility.getBaseFolder(this, getString(R.string.Resources));
        File file;
        if (isGridHome) {
            file = new File(fileDir, getString(R.string.Resources) + File.separator + getString(R.string.menu_background));
            if (file.getAbsoluteFile().exists()) {
                Bitmap bitmap = ImageLoader.getInstance().loadImageSync("file://" + file.getAbsolutePath());
                background.setBackground(new BitmapDrawable(getResources(), bitmap));
            }
        }
    }

    /**
     * Setup right button on header
     *
     * @param showBtn
     */
    protected void handleRightBtn(boolean showBtn) {
        if (!showBtn) {
            rightBtn.setVisibility(View.GONE);
            handleRightBtn(showBtn, null);
        }
    }

    /**
     * Handle resources of right button on header
     *
     * @param showBtn  whether to show button or not
     * @param whichBtn which resource has to set.
     */
    protected void handleRightBtn(boolean showBtn, String whichBtn) {
        if (showBtn) {
            rightBtn.setVisibility(View.VISIBLE);

            switch (whichBtn) {
                case REFRESH:
                    rightBtn.setBackgroundResource(R.drawable.refresh);
                    break;

                case ADD:
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
     *
     * @param isFromGridScreen whether to show grid button otherwise show backBtn
     */

    public void handleLeftBtn(boolean showBtn, boolean isFromGridScreen) {
        try {
            if (showBtn) {
                handleLeftBtn(isFromGridScreen);
            } else {
                leftBtn.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle resources of left button on header
     *
     * @param isFromGridScreen whether to show grid button otherwise show backBtn
     */
    protected void handleLeftBtn(boolean isFromGridScreen) {

        try {
            if (isFromGridScreen) {
                leftBtn.setImageResource(R.drawable.home);
            } else {
                leftBtn.setImageResource(R.drawable.btn_back);
            }

            leftBtn.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle clicks of both right and left buttons
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.leftBtn:
                onBackPressed();
                break;
            case R.id.rightBtn:
                handleRightBtnClick();
                break;
        }
    }

    public abstract void handleRightBtnClick();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SlideIn.getInstance().startAnimation(this);
    }

    @Override
    public void showProgressDialog(String msg) {
        if (mProgressDialog == null) {
            setProgressDialog(msg);
            mProgressDialog.show();
        }
    }

    @Override
    public void hideProgressDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        });
    }

    @Override
    public void showMessageDialog() {
    }

    @Override
    public void startBanner(String BannerName) {
        Log.i(TAG, "startBanner: " + BannerName);
    }

    /**
     * Send screen visit report to server
     */
    @Override
    public void sendReport(String screenName) {
        Log.i(TAG, "sendReport: " + screenName);
    }

    public abstract String getScreenName();

    @Override
    public Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setupSearchBar(boolean showSearchBar, String locale) {
        View searchBarBgId = findViewById(R.id.searchBarBgId);
        searchEditTxt = (EditText) findViewById(R.id.exhibitor_filter_edtTxt);

        if (showSearchBar) {
            searchBarBgId.setVisibility(View.VISIBLE);
            String color = EventDAO.getInstance(this).getValue(EventTable.COLOR_THEME);
            if (color != null && !color.isEmpty())
                searchBarBgId.setBackgroundColor(Color.parseColor(color));
        } else {
            searchBarBgId.setVisibility(View.GONE);
        }
    }

    /**
     * Add textwatcher to searchbar
     *
     * @param watcher
     */
    @Override
    public void addFilter(TextWatcher watcher) {
        searchEditTxt.addTextChangedListener(watcher);
    }

    @Override
    public void init() {
        header = (TextView) findViewById(R.id.header);
        leftBtn = (ImageView) findViewById(R.id.leftBtn);
        rightBtn = (TextView) findViewById(R.id.rightBtn);
        initIds();
    }

    public abstract void initIds();

    @Override
    public void registerListeners() {
        mPresenter = setPresenter();
        setListeners();
    }

    public abstract void setListeners();

    public abstract Presenter setPresenter();

    @Override
    public void unRegisterListeners() {
    }

    public abstract int getLayout();

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == SD_READ_WRITE_PERMISSION) {
            Log.i(TAG, "onRequestPermissionsResult: " + requestCode);
        } else if (requestCode == CALL_PERMISSION) {
            ((ListDetailPresenter) getPresenter()).doCall();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void setProgressDialog(String message) {
        mProgressDialog = new ProgressDialog(BaseActivity.this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(message);
    }

    public abstract void setUp();

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected class CustomDialog {

        private String okStr = "Ok";
        private String cancelStr = "Cancel";
        private ClickHandler mClick;

        public CustomDialog(ClickHandler mClick) {
            this.mClick = mClick;
        }

        public CustomDialog(String Ok, String cancel, ClickHandler mClick) {
            this(mClick);
            this.okStr = Ok;
            this.cancelStr = cancel;
        }

        public void show(String message) {
            new AlertDialog.Builder(BaseActivity.this)
                    .setMessage(message)
                    .setPositiveButton(okStr, (dialogInterface, i) -> mClick.handleClick())
                    .setNegativeButton(cancelStr, (dialogInterface, i) -> {
                    }).create().show();
        }

    }


}
