package com.grupio.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.activities.BaseActivity;
import com.grupio.animation.SlideOut;
import com.grupio.backend.Get_Image_Path;
import com.grupio.data.AttendeesData;
import com.grupio.fragments.BaseFragment;
import com.grupio.interfaces.ClickHandler;
import com.grupio.session.Preferences;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by JSN on 30/11/16.
 */

public class MyAccountFragment extends BaseFragment<MyAccountPresenter> implements View.OnClickListener, MyAccountContract.View {

    private static final int ACTION_TAKE_PHOTO = 1;
    private static final int PHOTO_PICKED = 2;
    private TextView title, name, company, email, primaryPhone, interest, keyword, bio;
    private Switch sendMessageSwitch, hideContactInfoSwitch, hideProfileSwitch;
    private Button editBtn;
    private ImageView userImage;
    private String imagePath = "";
    private Holder mHolder;
    private AlertDialog mAlertDialog;

    @Override
    public int getLayout() {
        return R.layout.layout_myaccount;
    }

    @Override
    public void initIds() {
        title = (TextView) view.findViewById(R.id.text_title);
        name = (TextView) view.findViewById(R.id.text_name);
        company = (TextView) view.findViewById(R.id.text_company);
        email = (TextView) view.findViewById(R.id.text_email);
        primaryPhone = (TextView) view.findViewById(R.id.text_phone);
        interest = (TextView) view.findViewById(R.id.text_interests);
        keyword = (TextView) view.findViewById(R.id.text_keyword);

        sendMessageSwitch = (Switch) view.findViewById(R.id.toggle_privacy);
        hideProfileSwitch = (Switch) view.findViewById(R.id.toggle_profile);
        hideContactInfoSwitch = (Switch) view.findViewById(R.id.toggle_contact);

        bio = (TextView) view.findViewById(R.id.text_bio);
        editBtn = (Button) view.findViewById(R.id.button_Edit);
        userImage = (ImageView) view.findViewById(R.id.image_profile);
    }

    @Override
    public void setListeners() {
        editBtn.setOnClickListener(this);
        userImage.setOnClickListener(this);
    }

    @Override
    public void setUp() {
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().fetchUserInfo(getActivity());
    }

    @Override
    public MyAccountPresenter setPresenter() {
        return new MyAccountPresenter(this);
    }

    @Override
    public String getScreenName() {
        return "MYACCOUNT_VIEW";
    }

    @Override
    public String getBannerName() {
        return "my account";
    }

    @Override
    public void onClick(View view) {
        Intent mIntent;

        switch (view.getId()) {
            case R.id.button_Edit:
                mIntent = new Intent(getActivity(), EditMyAccountActivity.class);
                startActivity(mIntent);
                SlideOut.getInstance().startAnimation(getActivity());
                break;
            case R.id.image_profile:
                Object[] objects = BaseActivity.CustomDialog.getDialog(getActivity()).showWithCustomView(R.layout.dialog_image_upload, Holder.class);
                mHolder = (Holder) objects[0];
                mAlertDialog = (AlertDialog) objects[1];

                mHolder.mCancelBtn.setOnClickListener(this);
                mHolder.mCameraBtn.setOnClickListener(this);
                mHolder.mGalleryBtn.setOnClickListener(this);
                break;

            case R.id.cancelBtn:
                if (mAlertDialog != null && mAlertDialog.isShowing()) {
                    mAlertDialog.dismiss();
                }
                break;

            case R.id.cameraBtn:
                File folder = Utility.getBaseFolder(getActivity(), getString(R.string.image));
                File file = new File(folder, Preferences.getInstances(getActivity()).getAttendeeId() + ".jpg");
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
        }
    }

    public void hideImageDialog() {
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
    }

    @Override
    public void showProgress(String msg) {
        showProgressDialog(msg);
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    @Override
    public void showImage(String url, boolean isUpdated) {
        ImageLoader.getInstance().displayImage(url, userImage, Utility.getDisplayOptionsAttendee());

        if (isUpdated) {
            ClickHandler mOkClick = () -> {
            };
            BaseActivity.CustomDialog.getDialog(getActivity(), mOkClick).singledBtnDialog(true).show(getString(R.string.image_upload_success));
        }
    }

    @Override
    public void showDetails(AttendeesData mData, boolean isFirstName) {
        title.setText(mData.getTitle());
        String nameStr;
        if (isFirstName) {
            nameStr = mData.getFirst_name() + " " + mData.getLast_name();
        } else {
            nameStr = mData.getLast_name() + ", " + mData.getFirst_name();
        }
        name.setText(nameStr);
        company.setText(mData.getCompany());
        email.setText(mData.getEmail());
        primaryPhone.setText(mData.getPrimary_phone());
        interest.setText(mData.getIntrests());
        keyword.setText(mData.getKeywords());
        bio.setText(mData.getBio());

        showImage(getString(R.string.base_url) + mData.getImage(), false);
    }

    @Override
    public void enableMessageSwitch() {
        sendMessageSwitch.setChecked(true);
    }

    @Override
    public void enableHideProfileSwitch() {
        hideProfileSwitch.setChecked(true);
    }

    @Override
    public void enableHideContact() {
        hideContactInfoSwitch.setChecked(true);
    }

    @Override
    public void onProfileUpdate() {
    }

    @Override
    public void onFailure(String msg) {
        showToast(msg);
    }

    @Override
    public void showInterest(List<String> mFullInterestList, List<String> mAttendeeInterest) {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PHOTO_PICKED) {
                Uri selectedImage = data.getData();
                imagePath = Get_Image_Path.getPath(getActivity(), selectedImage);
            }
            showImage();
        }
    }

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
            hideImageDialog();
            getPresenter().updateImage(imagePath, getActivity());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class Holder {
        Button mCancelBtn, mCameraBtn, mGalleryBtn;

        public Holder(View view) {
            mCancelBtn = (Button) view.findViewById(R.id.cancelBtn);
            mCameraBtn = (Button) view.findViewById(R.id.cameraBtn);
            mGalleryBtn = (Button) view.findViewById(R.id.galleryBtn);
        }
    }
}
