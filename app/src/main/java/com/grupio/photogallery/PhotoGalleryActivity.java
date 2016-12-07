package com.grupio.photogallery;

import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.activities.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by JSN on 5/12/16.
 */

public class PhotoGalleryActivity extends BaseActivity<Void> {
    AlertDialog uploadConfirmationDialog;
    PhotoGalleryHolder mHolder;

    @Override
    public int getLayout() {
        return R.layout.layout_container;
    }

    @Override
    public void initIds() {
    }

    @Override
    public boolean isHeaderForGridPage() {
        return true;
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
    public Void setPresenter() {
        return null;
    }

    @Override
    public void setListeners() {
    }

    @Override
    public void setUp() {
        handleRightBtn(true, ADD);
        Utility.replaceFragment(this, new PhotoGalleryFragment(), false);
    }

    @Override
    public void handleRightBtnClick(View view) {
        uploadImageDialog();
    }

    @Override
    public void showImage() {
        super.showImage();

        Object[] dialogObjs = CustomDialog.getDialog(this).showWithCustomView(R.layout.layout_dialog_photo_upload_with_confirmation, PhotoGalleryHolder.class, R.style.AppTheme);

        mHolder = (PhotoGalleryHolder) dialogObjs[0];
        mHolder.cancelBtn.setOnClickListener(this);
        mHolder.uploadBtn.setOnClickListener(this);

        ImageLoader.getInstance().displayImage("file://" + getImagePath(), mHolder.imageView);
        uploadConfirmationDialog = (AlertDialog) dialogObjs[1];
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.cancel:
                uploadConfirmationDialog.dismiss();
                break;

            case R.id.ok:
                Fragment frag = getFragmentManager().findFragmentById(R.id.container);
                if (frag != null) {
                    uploadConfirmationDialog.dismiss();
                    ((PhotoGalleryFragment) frag).uploadImage(getImagePath(), mHolder.captionTxt.getText().toString());
                }
                break;
        }

    }

    public class PhotoGalleryHolder {
        public TextView cancelBtn, uploadBtn;
        public ImageView imageView;
        public EditText captionTxt;

        public PhotoGalleryHolder(View view) {
            cancelBtn = (TextView) view.findViewById(R.id.cancel);
            uploadBtn = (TextView) view.findViewById(R.id.ok);
            imageView = (ImageView) view.findViewById(R.id.image);
            captionTxt = (EditText) view.findViewById(R.id.comment);
        }
    }


}
