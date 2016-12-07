package com.grupio.message;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.activities.BaseActivity;
import com.grupio.data.MessageData;
import com.grupio.session.Preferences;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by JSN on 7/7/16.
 */
public class MessageDetail extends BaseActivity<MsgDetailPresenter> implements MsgDetailView {

    MessageData mMessageData;
    private String threadId;
    private boolean isInbox = false;
    private MessagesDetailsAdapter mAdapter;
    private ListView messageDetailsListView;
    private EditText mEditText;
    AdapterView.OnItemClickListener messageListItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final MessageData mData = (MessageData) parent.getAdapter().getItem(position);
            //to show the change alert message to show full alert message..
            try {
                AlertDialog.Builder adialog = new AlertDialog.Builder(MessageDetail.this);
                adialog.setMessage(mData.getContent()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        if (mData.getReceiver_id().equals(Preferences.getInstances(MessageDetail.this).getAttendeeId())) {
                            getPresenter().markThisMsgRead(mData.getMessage_id(), threadId);
                        }
                        mEditText.setText("");
                        mEditText.clearFocus();
                    }
                });
                adialog.show();
            } catch (Exception e) {
            }
        }
    };
    private Button sendMessageButton;

    @Override
    public boolean isHeaderForGridPage() {
        return false;
    }

    @Override
    public void initIds() {
        messageDetailsListView = (ListView) findViewById(R.id.messageDetailsList);
        mEditText = (EditText) findViewById(R.id.sentId);
        sendMessageButton = (Button) findViewById(R.id.sendMessageButton);

        //set adapter
        mAdapter = new MessagesDetailsAdapter(this, isInbox);
        messageDetailsListView.setAdapter(mAdapter);
    }

    @Override
    public void setListeners() {
        sendMessageButton.setOnClickListener(this);
        messageDetailsListView.setOnItemClickListener(messageListItemClick);
    }

    @Override
    public MsgDetailPresenter setPresenter() {
        return new MsgDetailPresenter(this, this, threadId, isInbox);
    }

    @Override
    public int getLayout() {
        return R.layout.message_details_page;
    }

    @Override
    public void setUp() {
        handleRightBtn(false, null);
        threadId = getIntent().getStringExtra("threadId");
        isInbox = getIntent().getBooleanExtra("isInboxFolder", false);

        getPresenter().fetchMessageOfThisThread(threadId);

    }

    public void prepareMsgObj() {
        MessageData mData = mAdapter.getItem(0);
        mMessageData = new MessageData();
        mMessageData.setThread_id(threadId);
        mMessageData.setTitle(mData.getTitle());
        mMessageData.setSender_id(Preferences.getInstances(this).getAttendeeId());

        if (mData.getReceiver_id().equals(Preferences.getInstances(this).getAttendeeId())) {
            mMessageData.setReceiver_id(mData.getSender_id());

            mMessageData.setReceiver_first_name(mData.getSender_first_name());
            mMessageData.setReceiver_last_name(mData.getSender_last_name());

            mMessageData.setSender_first_name(mData.getReceiver_first_name());
            mMessageData.setSender_last_name(mData.getReceiver_last_name());

            mMessageData.setSender_email(mData.getReceiver_email());
            mMessageData.setReceiver_email(mData.getSender_email());
        } else {
            mMessageData.setReceiver_id(mData.getReceiver_id());
            mMessageData.setReceiver_id(mData.getReceiver_id());

            mMessageData.setReceiver_first_name(mData.getReceiver_first_name());
            mMessageData.setReceiver_last_name(mData.getReceiver_last_name());

            mMessageData.setSender_first_name(mData.getSender_first_name());
            mMessageData.setSender_last_name(mData.getSender_last_name());

            mMessageData.setSender_email(mData.getSender_email());
            mMessageData.setReceiver_email(mData.getReceiver_email());
        }

        mMessageData.setFolder("Inbox");
        mMessageData.setIs_unread("0");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mMessageData.setDatetime(Utility.convertMyTimeToUTC(sdf.format(new Date())));

    }

    @Override
    public void showMsgList(List<MessageData> mList) {
        mAdapter.clear();
        mAdapter.addAll(mList);
        mAdapter.notifyDataSetChanged();
        mEditText.setText("");
        mEditText.clearFocus();
        messageDetailsListView.smoothScrollToPosition(mList.size());
    }

    @Override
    public void failure(String msg) {
        if (msg != null) {
            Toast.makeText(MessageDetail.this, msg, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {

        super.onClick(v);

        switch (v.getId()) {
            case R.id.sendMessageButton:
                if (mEditText.getText().toString() != null && !mEditText.getText().toString().equals("")) {
                    prepareMsgObj();
                    mMessageData.setContent(mEditText.getText().toString());
                    getPresenter().sendMessage(mMessageData);
//                    if(mListener != null) {
//                        getPresenter().sendMessage(mMessageData);
//                    }
                } else {
                }

                break;
        }
    }

    @Override
    public void handleRightBtnClick(View view) {

    }

    @Override
    public void showProgress() {
        showProgressDialog("Loading...");
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }


    @Override
    public String getScreenName() {
        return "";
    }

    @Override
    public String getBannerName() {
        return "";
    }
}
