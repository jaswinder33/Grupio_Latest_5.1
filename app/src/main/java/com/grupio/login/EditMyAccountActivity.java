package com.grupio.login;

import android.widget.Button;
import android.widget.EditText;

import com.grupio.R;
import com.grupio.activities.BaseActivity;
import com.grupio.data.AttendeesData;

/**
 * Created by JSN on 30/11/16.
 */

public class EditMyAccountActivity extends BaseActivity<MyAccountPresenter> implements MyAccountContract.View {

    private EditText fName, lName, company, title, primaryPhone, email, keyword, bio;
    private Button saveBtn;

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
    }

    @Override
    public void setUp() {
        getPresenter().fetchUserInfo(this);
    }

    @Override
    public void handleRightBtnClick() {

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
//only for my account activity
    }

    @Override
    public void showDetails(AttendeesData mData, boolean isFirstName) {
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

    }

    @Override
    public void enableHideProfileSwitch() {

    }

    @Override
    public void enableHideContact() {

    }
}
