package com.grupio.activities;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.LayoutInflater;
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
import java.lang.reflect.InvocationTargetException;

/**
 * Created by JSN on 5/7/16.
 */
public abstract class BaseActivity<Presenter> extends AppCompatActivity implements BaseFunctionality, View.OnClickListener {

    public static final String TAG = "Baseactivity";
    public static final int SD_READ_WRITE_PERMISSION = 100;
    public static final int CALL_PERMISSION = 101;
    public static final int CALENDAR_PERMISSION = 102;

    //Resource list variables
    public static final int VIEW_DOC = 103;
    public static final int DOWNLODA_DOC = 104;
    public static final int URL_DOC = 105;

    //right button variables
    public static final String REFRESH = "refresh";
    public static final String ADD = "add";
    public static final String EMAIL = "email";
    public static final String VIEW_ALL = "view_all";
    public static final String DOWNLOAD_ALL = "download_all";
    public static final String LOGOUT = "logout";

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public EditText searchEditTxt;
    protected TextView header, rightBtn;
    protected ImageView leftBtn;
    protected RelativeLayout background;
    private Presenter mPresenter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(getLayout());
        init();
        setUpHeader(isHeaderForGridPage());
        handleLeftBtn(isHeaderForGridPage());
        sendReport(getScreenName());
        registerListeners();
        setUp();
    }

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

                case EMAIL:
                    rightBtn.setBackgroundResource(R.drawable.ic_envelope);
                    break;

                case VIEW_ALL:
                    rightBtn.setBackgroundResource(R.drawable.book_btn);
                    rightBtn.setText("View All");
                    break;
                case DOWNLOAD_ALL:
                    rightBtn.setBackgroundResource(R.drawable.book_btn);
                    rightBtn.setText("Download All");
                    break;

                case LOGOUT:
                    rightBtn.setBackgroundResource(R.drawable.logout);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
        runOnUiThread(() -> {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
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

    @Override
    public void registerListeners() {
        mPresenter = setPresenter();
        setListeners();
    }

    @Override
    public void unRegisterListeners() {
    }

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


    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public abstract int getLayout();

    public abstract void initIds();

    public abstract boolean isHeaderForGridPage();

    public abstract String getScreenName();

    public abstract Presenter setPresenter();

    public abstract void setListeners();

    public abstract void setUp();

    public abstract void handleRightBtnClick();

    public static class CustomDialog {

        private String okStr = "Ok";
        private String cancelStr = "Cancel";
        private ClickHandler mClick;
        private boolean isSingleBtn = false;
        private Context mContext;
        private AlertDialog dialog;

        private CustomDialog(Context mContext) {
            this.mContext = mContext;
        }

        private CustomDialog(Context mContext, ClickHandler mClick) {
            this(mContext);
            this.mClick = mClick;
        }

        private CustomDialog(String Ok, Context mContext, ClickHandler mClick) {
            this(mContext, mClick);
            this.okStr = Ok;
        }

        private CustomDialog(String Ok, String cancel, Context mContext, ClickHandler mClick) {
            this(Ok, mContext, mClick);
            this.cancelStr = cancel;
        }

        public static CustomDialog getDialog(Context mContext) {
            return new CustomDialog(mContext);
        }

        public static CustomDialog getDialog(Context mContext, ClickHandler mClick) {
            return new CustomDialog(mContext, mClick);
        }

        public static CustomDialog getDialog(String Ok, Context mContext, ClickHandler mClick) {
            return new CustomDialog(Ok, mContext, mClick);
        }

        public static CustomDialog getDialog(String Ok, String cancel, Context mContext, ClickHandler mClick) {
            return new CustomDialog(Ok, cancel, mContext, mClick);
        }

        public CustomDialog singledBtnDialog(boolean isSingleBtn) {
            this.isSingleBtn = isSingleBtn;
            return this;
        }

        public void show(String message) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
            dialog.setMessage(message);
            dialog.setPositiveButton(okStr, (dialogInterface, i) -> {
                mClick.handleClick();
            });
            if (!isSingleBtn) {
                dialog.setNegativeButton(cancelStr, (dialogInterface, i) -> {

                });
            }
            dialog.create().show();
        }

        public Object[] showWithCustomView(int view, Class<?> holder) {
            View dialogView = LayoutInflater.from(mContext).inflate(view, null);
            dialog = new AlertDialog.Builder(mContext).setView(dialogView).create();
            dialog.show();
            try {
                return new Object[]{holder.getConstructor(holder.getConstructors()[0].getParameterTypes()).newInstance(null, dialogView), dialog};
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}
