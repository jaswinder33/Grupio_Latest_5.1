package com.grupio.login;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.grupio.R;
import com.grupio.activities.BaseActivity;
import com.grupio.data.AttendeesData;

import java.util.List;

/**
 * Created by JSN on 30/11/16.
 */

public class EditMyAccountActivity extends BaseActivity<MyAccountPresenter> implements MyAccountContract.View {

    private EditText fName, lName, company, title, primaryPhone, email, keyword, bio;
    private Button saveBtn;
    private RadioGroup messageRadioGroup, hideProfileRadioGroup, hideContactRadioGroup;
    private RadioButton yesMessage, noMessage, yesProfile, noProfile, yesHideContact, noHideContact;
    private MultiSelectSpinner mIntersetSpinner;
    private AttendeesData mAttendeeData = new AttendeesData();
    RadioGroup.OnCheckedChangeListener radioGroupListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            Log.i(TAG, "onCheckedChanged: RadioGroup: " + radioGroup.getId() + " int: " + i);
            switch (i) {
                case R.id.yesMessageRadioButtonId:
                    mAttendeeData.setEnable_messaging("y");
                    break;
                case R.id.noMessageRadioButtonId:
                    mAttendeeData.setEnable_messaging("n");
                    break;
                case R.id.yesHideProfileRadioButtonId:
                    mAttendeeData.setHideMe("y");
                    break;
                case R.id.noHideProfileRadioButtonId:
                    mAttendeeData.setHideMe("n");
                    break;

                case R.id.yesHideConactRadioButtonId:
                    mAttendeeData.setHide_contact_info("y");
                    break;
                case R.id.noHideConactRadioButtonId:
                    mAttendeeData.setHide_contact_info("n");
                    break;
            }
        }
    };

    @Override
    public int getLayout() {
        return R.layout.layout_myaccount_edit;
    }

    @Override
    public void initIds() {
        fName = (EditText) findViewById(R.id.firstNameEditTxtId);
        lName = (EditText) findViewById(R.id.lastNameEditTxtId);
        company = (EditText) findViewById(R.id.companyEditTxtId);
        title = (EditText) findViewById(R.id.titleEditTxtId);
        primaryPhone = (EditText) findViewById(R.id.phoneNoEditTxtId);
        email = (EditText) findViewById(R.id.emailEditTxtId);
        keyword = (EditText) findViewById(R.id.keywordsEditTxtId);
        bio = (EditText) findViewById(R.id.bioEditTxtId);
        saveBtn = (Button) findViewById(R.id.updateProfileButtonId);
        messageRadioGroup = (RadioGroup) findViewById(R.id.messageRadioGroupId);
        hideProfileRadioGroup = (RadioGroup) findViewById(R.id.hideProfileRadioGroupId);
        hideContactRadioGroup = (RadioGroup) findViewById(R.id.hideConactRadioGroupId);
        yesMessage = (RadioButton) findViewById(R.id.yesMessageRadioButtonId);
        noMessage = (RadioButton) findViewById(R.id.noMessageRadioButtonId);
        yesProfile = (RadioButton) findViewById(R.id.yesHideProfileRadioButtonId);
        noProfile = (RadioButton) findViewById(R.id.noHideProfileRadioButtonId);
        yesHideContact = (RadioButton) findViewById(R.id.yesHideConactRadioButtonId);
        noHideContact = (RadioButton) findViewById(R.id.noHideConactRadioButtonId);
        mIntersetSpinner = (MultiSelectSpinner) findViewById(R.id.interest_spinner);
    }

    @Override
    public boolean isHeaderForGridPage() {
        return false;
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
    public MyAccountPresenter setPresenter() {
        return new MyAccountPresenter(this);
    }

    @Override
    public void setListeners() {
        saveBtn.setOnClickListener(this);
        messageRadioGroup.setOnCheckedChangeListener(radioGroupListener);
        hideProfileRadioGroup.setOnCheckedChangeListener(radioGroupListener);
        hideContactRadioGroup.setOnCheckedChangeListener(radioGroupListener);
    }

    @Override
    public void setUp() {
        handleRightBtn(true, REFRESH);
        getPresenter().fetchUserInfo(this);
    }

    @Override
    public void handleRightBtnClick() {
        getPresenter().fetchInterest(this);
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
        //only for my account activity
    }

    @Override
    public void showDetails(AttendeesData mData, boolean isFirstName) {
        mAttendeeData = new AttendeesData(mData);
        fName.setText(mData.getFirst_name());
        lName.setText(mData.getLast_name());
        company.setText(mData.getCompany());
        title.setText(mData.getTitle());
        primaryPhone.setText(mData.getPrimary_phone());
        email.setText(mData.getEmail());
        keyword.setText(mData.getKeywords());
        bio.setText(mData.getBio());
    }

    @Override
    public void enableMessageSwitch() {
        yesMessage.setChecked(true);
    }

    @Override
    public void enableHideProfileSwitch() {
        yesProfile.setChecked(true);
    }

    @Override
    public void enableHideContact() {
        yesHideContact.setChecked(true);
    }

    @Override
    public void onProfileUpdate() {
        onBackPressed();
    }

    @Override
    public void onFailure(String msg) {
        showToast(msg);
    }

    @Override
    public void showInterest(List<String> mFullInterestList, List<String> mAttendeeInterest) {
        if (mFullInterestList != null && !mFullInterestList.isEmpty()) {
            mIntersetSpinner.setEnabled(true);
            mIntersetSpinner.setItems(mFullInterestList);
            mIntersetSpinner.setSelection(mAttendeeInterest);
//            mIntersetSpinner.setSelection(0);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.updateProfileButtonId:

                mAttendeeData.setFirst_name(fName.getText().toString());
                mAttendeeData.setLast_name(lName.getText().toString());
                mAttendeeData.setCompany(company.getText().toString());
                mAttendeeData.setTitle(title.getText().toString());
                mAttendeeData.setPrimary_phone(primaryPhone.getText().toString());
                mAttendeeData.setEmail(email.getText().toString());
                mAttendeeData.setKeywords(keyword.getText().toString());
                mAttendeeData.setBio(bio.getText().toString());
                mAttendeeData.setIntrests(mIntersetSpinner.getItemAtPosition(0).toString());

                new Gson().toJson(mAttendeeData);

                Log.i(TAG, "onClick: [ " + new Gson().toJson(mAttendeeData) + " ]");

                getPresenter().updateUserInfo(mAttendeeData, this);
                break;
        }
    }
}
