package com.grupio.login;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.animation.SlideOut;
import com.grupio.data.AttendeesData;
import com.grupio.fragments.BaseFragment;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by JSN on 30/11/16.
 */

public class MyAccountFragment extends BaseFragment<MyAccountPresenter> implements View.OnClickListener, MyAccountContract.View {

    private TextView title, name, company, email, primaryPhone, interest, keyword, bio;
    private Switch sendMessageSwitch, hideContactInfoSwitch, hideProfileSwitch;
    private Button editBtn;
    private ImageView userImage;

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
        switch (view.getId()) {
            case R.id.button_Edit:
                Intent mIntent = new Intent(getActivity(), EditMyAccountActivity.class);
                startActivity(mIntent);
                SlideOut.getInstance().startAnimation(getActivity());
                break;
            case R.id.image_profile:

                break;
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
    public void showImage(String url) {
        ImageLoader.getInstance().displayImage(url, userImage, Utility.getDisplayOptionsAttendee());
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

        showImage(getString(R.string.base_url) + mData.getImage());
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

    }

    @Override
    public void showInterest(List<String> mFullInterestList, List<String> mAttendeeInterest) {

    }
}
