package com.grupio.attendee.message;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.grupio.R;
import com.grupio.activities.BaseActivity;
import com.grupio.animation.SlideOut;
import com.grupio.data.AttendeesData;

import java.util.ArrayList;
import java.util.List;

public class SendMessageActivity extends BaseActivity<MessagePresenter> implements MessageView {

    public static final int INTENT_FOR_ATTENDEE_LIST = 301;
    private TextView sendTo;
    private EditText subject, message;
    private Button sendBtn, cancelBtn;
    private ArrayList<AttendeesData> mAttendeeList = new ArrayList<>();
    private String attendeeId = "";

    @Override
    public boolean isHeaderForGridPage() {
        return false;
    }

    @Override
    public void handleRightBtnClick(View view) {
        Intent mIntent = new Intent(this, ChooseAttendeeActivity.class);
        mIntent.putExtra("attendeeList", mAttendeeList);
        mIntent.putExtra("attendee_id", attendeeId);
        startActivityForResult(mIntent, INTENT_FOR_ATTENDEE_LIST);
        SlideOut.getInstance().startAnimation(this);
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
        sendTo = (TextView) findViewById(R.id.sendTo);
        subject = (EditText) findViewById(R.id.subject);
        message = (EditText) findViewById(R.id.message);
        sendBtn = (Button) findViewById(R.id.sendBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
    }

    @Override
    public void setListeners() {
        sendBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public MessagePresenter setPresenter() {
        return new MessagePresenter(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_send_message;
    }

    @Override
    public void setUp() {
        handleRightBtn(true, ADD);
        getData();
        getPresenter().fetchAttendeeName(attendeeId, this);
    }

    public void getData() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            attendeeId = mBundle.getString("attendee_id");
        }
    }

    @Override
    public void showProgress() {
        showProgressDialog("Sending message...");
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    @Override
    public void setAttendeeName(String name, List<AttendeesData> mList) {
        sendTo.setText(name);
        mAttendeeList = new ArrayList<>();
        mAttendeeList.addAll(mList);
    }

    @Override
    public void setSendTo(String name) {

    }

    @Override
    public void onFailure(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess() {
        Toast.makeText(this, "Message send successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {

            case R.id.sendBtn:
                getPresenter().sendMessage(subject.getText().toString(), message.getText().toString(), mAttendeeList, this);
                break;

            case R.id.cancelBtn:
                onBackPressed();
                break;


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_FOR_ATTENDEE_LIST && resultCode == RESULT_OK) {

            Bundle mBundle = data.getExtras();
            if (mBundle != null) {
                List<AttendeesData> mList = (List<AttendeesData>) mBundle.get("attendeeList");

                mAttendeeList.clear();
                mAttendeeList.addAll(mList);

                String name = "";
                for (int i = 0; i < mList.size(); i++) {
                    name += mList.get(i).getFirst_name() + " " + mList.get(i).getLast_name() + ", ";
                }

                sendTo.setText(name);
            }
        }
    }
}
