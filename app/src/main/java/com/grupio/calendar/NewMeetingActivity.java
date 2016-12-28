package com.grupio.calendar;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.activities.BaseActivity;
import com.grupio.attendee.message.ChooseAttendeeActivity;
import com.grupio.data.MeetingData;

/**
 * Created by JSN on 12/12/16.
 */

public class NewMeetingActivity extends BaseActivity<NewMeetingPresenter> implements NewMeetingContract.View {

    private EditText title, location, description;
    private TextView meetingDate, meetingDateSelector, meetingTimeZone;
    private Button goToNextStep, cancelAndReturn, createAndExit;
    private Object[] dialogParams;
    private DatePickerHolder mHolder;
    private AlertDialog dialog;

    @Override
    public int getLayout() {
        return R.layout.layout_new_meeting;
    }

    @Override
    public void initIds() {
        title = (EditText) findViewById(R.id.meetingTitle);
        location = (EditText) findViewById(R.id.meetingLocation);
        description = (EditText) findViewById(R.id.meetingDescription);

        meetingDate = (TextView) findViewById(R.id.dateTxt);
        meetingDateSelector = (TextView) findViewById(R.id.selectDate);
        meetingTimeZone = (TextView) findViewById(R.id.timezone);

        goToNextStep = (Button) findViewById(R.id.goToNextStep);
        cancelAndReturn = (Button) findViewById(R.id.cancelAndReturn);
        createAndExit = (Button) findViewById(R.id.createAndExit);
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
    public NewMeetingPresenter setPresenter() {
        return new NewMeetingPresenter(this);
    }

    @Override
    public void setListeners() {
        meetingDateSelector.setOnClickListener(this);
        goToNextStep.setOnClickListener(this);
        cancelAndReturn.setOnClickListener(this);
        goToNextStep.setOnClickListener(this);
    }

    @Override
    public void setUp() {
        getPresenter().showData(this, getData());
        getPresenter().fetchTimeZone(this);
    }

    @Override
    public void handleRightBtnClick(View view) {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        MeetingData mMeetingData = new MeetingData();
        mMeetingData.title = title.getText().toString();
        mMeetingData.location = location.getText().toString();
        mMeetingData.description = description.getText().toString();
        mMeetingData.meetingDate = meetingDate.getText().toString();


        switch (v.getId()) {
            case R.id.goToNextStep:
                getPresenter().validateGoToNext(this, mMeetingData);
                break;

            case R.id.cancelAndReturn:
                onBackPressed();
                break;

            case R.id.createAndExit:
                getPresenter().createMeeting(this, mMeetingData);
                break;

            case R.id.selectDate:
                dialogParams = CustomDialog.getDialog(this).showWithCustomView(R.layout.layout_datepicker, DatePickerHolder.class);
                mHolder = (DatePickerHolder) dialogParams[0];
                dialog = (AlertDialog) dialogParams[1];
                mHolder.mDoneBtn.setOnClickListener(this);
                break;

            case R.id.doneBtn:

                int day = mHolder.datePicker.getDayOfMonth();
                int month = mHolder.datePicker.getMonth() + 1;
                int year = mHolder.datePicker.getYear();

                setDate(year + "-" + month + "-" + day);
                dialog.dismiss();
                break;
        }
    }

    @Override
    public void showErrorMessage(String msg) {
        CustomDialog.getDialog(this).singledBtnDialog(true).show(msg);
    }

    @Override
    public void showTimeZone(String timezone) {
        meetingTimeZone.setText(timezone);
    }

    @Override
    public void goToNextScreen(MeetingData data) {

        Bundle mBundle = new Bundle();
        mBundle.putSerializable("data", data);
        mBundle.putString("from", "calendar");

        goToNextScreen(mBundle, ChooseAttendeeActivity.class);
        showToast("Functionality Pending...");
    }

    @Override
    public void showData(MeetingData data) {
        title.setText(data.title);
        location.setText(data.location);
        description.setText(data.description);
        meetingDate.setText(data.meetingDate);
    }

    public void setDate(String date) {
        meetingDate.setText(date);
    }

    public MeetingData getData() {

        MeetingData mMeetingData = null;
        Bundle mBundle = getIntent().getExtras();

        if (mBundle != null) {
            mMeetingData = (MeetingData) mBundle.getSerializable("data");
        }

        return mMeetingData;

    }

    public class DatePickerHolder {
        private Button mDoneBtn;
        private DatePicker datePicker;

        public DatePickerHolder(View view) {
            mDoneBtn = (Button) view.findViewById(R.id.doneBtn);
            datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        }
    }
}
