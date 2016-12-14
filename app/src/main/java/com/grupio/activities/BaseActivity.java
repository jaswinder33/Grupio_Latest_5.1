package com.grupio.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.animation.SlideIn;
import com.grupio.animation.SlideOut;
import com.grupio.attendee.ListDetailPresenter;
import com.grupio.backend.Get_Image_Path;
import com.grupio.base.BasePresenter;
import com.grupio.dao.EventDAO;
import com.grupio.data.AdsData;
import com.grupio.db.EventTable;
import com.grupio.interfaces.BaseFunctionality;
import com.grupio.interfaces.ClickHandler;
import com.grupio.session.Preferences;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by JSN on 5/7/16.
 */
// extends BasePresenter<IBaseView, IBaseInteractor>
public abstract class BaseActivity<Presenter> extends AppCompatActivity implements BaseFunctionality<Presenter>, View.OnClickListener {

    public static final String TAG = "Baseactivity";

    //onActivity Results constants
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
    public static final String OVERFLOW = "overflow";
    private static final int ACTION_TAKE_PHOTO = 1;
    private static final int PHOTO_PICKED = 2;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public EditText searchEditTxt;
    protected TextView header, rightBtn;
    protected ImageView leftBtn;
    protected RelativeLayout background;
    private Presenter mPresenter;
    private ProgressDialog mProgressDialog;
    //Image Upload dialog variabes
    private String imagePath = "";
    private Object[] imageUploadDialogParams;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(getLayout());
        init();
        setUpHeader(isHeaderForGridPage());
        handleLeftBtn(isHeaderForGridPage());
        registerListeners();
        setUp();
        sendReport(getScreenName());
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

                case OVERFLOW:
                    rightBtn.setBackgroundResource(R.drawable.ic_dots_vertical);
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
        Intent mIntent;

        switch (v.getId()) {

            case R.id.leftBtn:
                onBackPressed();
                break;
            case R.id.rightBtn:
                handleRightBtnClick(v);
                break;

            case R.id.cameraBtn:
                File folder = Utility.getBaseFolder(this, getString(R.string.image));
                File file = new File(folder, Preferences.getInstances(this).getAttendeeId() + ".jpg");
                imagePath = file.getAbsolutePath();
                mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                mIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                mIntent.putExtra("outputX", 200);
                mIntent.putExtra("outputY", 200);
                mIntent.putExtra("aspectX", 1);
                mIntent.putExtra("aspectY", 1);
                mIntent.putExtra("scale", true);
                mIntent.putExtra("return-data", true);
                startActivityForResult(mIntent, ACTION_TAKE_PHOTO);
                break;

            case R.id.galleryBtn:
                mIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                mIntent.setType("image/*");
                mIntent.putExtra("circleCrop", true);
                startActivityForResult(mIntent, PHOTO_PICKED);
                break;

            case R.id.cancelBtn:
                getUploadImageDialog().dismiss();
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
    public void startBanner(String bannerName) {
        ((BasePresenter) getPresenter()).showBanner(bannerName, this);
    }

    @Override
    public void showBanner(List<AdsData> adsData) {
    }

    /**
     * Send screen visit report to server
     */
    @Override
    public void sendReport(String screenName) {
//        ((BasePresenter) getPresenter()).sendReport(screenName, this);
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

    /**
     * Navigate user to next screen
     *
     * @param bundle
     * @param className
     */
    @Override
    public void goToNextScreen(Bundle bundle, Class className) {
        Intent mIntent = new Intent(this, className);
        if (bundle != null) {
            mIntent.putExtras(bundle);
        }
        startActivity(mIntent);
        SlideOut.getInstance().startAnimation(this);
    }

    public abstract void handleRightBtnClick(View view);

    public void uploadImageDialog() {
        imageUploadDialogParams = BaseActivity.CustomDialog.getDialog(this).showWithCustomView(R.layout.dialog_image_upload, ImageHolder.class, R.style.DialogSlideAnim);
        ImageHolder mHolder = getImageHolder();
        mHolder.mCancelBtn.setOnClickListener(this);
        mHolder.mCameraBtn.setOnClickListener(this);
        mHolder.mGalleryBtn.setOnClickListener(this);
    }

    public AlertDialog getUploadImageDialog() {
        return (AlertDialog) imageUploadDialogParams[1];
    }

    public ImageHolder getImageHolder() {
        return (ImageHolder) imageUploadDialogParams[0];
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == PHOTO_PICKED) {
            Uri selectedImage = data.getData();
            imagePath = Get_Image_Path.getPath(this, selectedImage);
            showImage();
        } else if (resultCode == Activity.RESULT_OK && requestCode == ACTION_TAKE_PHOTO) {
            showImage();
        }
    }

    /**
     * Override this method to complete image upload action.
     */
    public void showImage() {
        Matrix matrix = null;
        try {
            ExifInterface exif = new ExifInterface(imagePath);
            int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int rotationInDegrees = Utility.exifToDegrees(rotation);
            matrix = new Matrix();
            if (rotation != 0f) {
                matrix.preRotate(rotationInDegrees);
            }

            getUploadImageDialog().dismiss();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class CustomDialog {

        private String okStr = "Ok";
        private String cancelStr = "Cancel";
        private ClickHandler mClick;
        private boolean isSingleBtn = false;
        private Context mContext;


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
            show(message, 0);
        }

        public void show(String message, int theme) {
            AlertDialog.Builder dialog;
            if (theme == 0) {
                dialog = new AlertDialog.Builder(mContext);
            } else {
                dialog = new AlertDialog.Builder(mContext, theme);
            }

            dialog.setMessage(message);
            dialog.setPositiveButton(okStr, (dialogInterface, i) -> {

                if (mClick != null) {
                    mClick.handleClick();
                } else {
                    dialogInterface.dismiss();
                }

            });
            if (!isSingleBtn) {
                dialog.setNegativeButton(cancelStr, (dialogInterface, i) -> {
                });
            }
            dialog.create().show();
        }

        public Object[] showWithCustomView(int view, Class<?> holder) {
            return showWithCustomView(view, holder, 0);
        }

        public Object[] showWithCustomView(int view, Class<?> holder, int theme) {
            View dialogView = LayoutInflater.from(mContext).inflate(view, null);
            AlertDialog dialog;
            if (theme == 0) {
                dialog = new AlertDialog.Builder(mContext).setView(dialogView).create();
            } else {
                dialog = new AlertDialog.Builder(mContext, theme).setView(dialogView).create();
            }

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

    private class ImageHolder {
        Button mCancelBtn, mCameraBtn, mGalleryBtn;

        public ImageHolder(View view) {
            mCancelBtn = (Button) view.findViewById(R.id.cancelBtn);
            mCameraBtn = (Button) view.findViewById(R.id.cameraBtn);
            mGalleryBtn = (Button) view.findViewById(R.id.galleryBtn);
        }
    }
}
