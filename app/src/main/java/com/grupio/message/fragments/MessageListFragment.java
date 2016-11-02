package com.grupio.message.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.grupio.R;
import com.grupio.animation.SlideOut;
import com.grupio.data.MessageData;
import com.grupio.fragments.BaseFragment;
import com.grupio.message.MessageAdapter;
import com.grupio.message.MessageCountWatcher;
import com.grupio.message.MessageDetail;
import com.grupio.message.MessageListFragInter;
import com.grupio.message.MessagesPresenter;
import com.grupio.session.Preferences;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * Created by JSN on 5/7/16.
 */

public class MessageListFragment extends BaseFragment<MessagesPresenter> implements MessageListFragInter, View.OnClickListener {

    private static MessageListFragment mMessageListFragment;
    private ListView mMessageList;
    private TextView messageCount, noDataAvailable;
    private List<MessageData> msglist = new ArrayList<>();
    private MessageAdapter mAdapter;
    private ImageButton mLoadMoreBtn;
    private boolean isInbox = true;
    AdapterView.OnItemClickListener messageListItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MessageData mData = (MessageData) parent.getAdapter().getItem(position);
            Intent mIntent = new Intent(getActivity(), MessageDetail.class);
            mIntent.putExtra("threadId", mData.getThread_id());
            mIntent.putExtra("isInboxFolder", isInbox);
            getActivity().startActivity(mIntent);
            SlideOut.getInstance().startAnimation(getActivity());
        }
    };
    TextWatcher searchWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String searchQuery = s.length() > 0 ? s.toString() : null;
            if (isInbox) {
                getPresenter().fetchInboxMsgs(searchQuery);
            } else {
                getPresenter().fetchSentMsgs(searchQuery);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
    private Button inboxMessageButton, sendMessageButton;

    public static MessageListFragment getInstance(Bundle mBundle) {
        mMessageListFragment = new MessageListFragment();
        if (mBundle != null) {
            mMessageListFragment.setArguments(mBundle);
        }
        return mMessageListFragment;
    }

    @Override
    public String getScreenName() {
        return "MESSAGE_VIEW";
    }

    @Override
    public String getBannerName() {
        return "messages";
    }

    @Override
    public MessagesPresenter setPresenter() {
        return new MessagesPresenter(getActivity(), this);
    }

    @Override
    public void initIds() {
        inboxMessageButton = (Button) view.findViewById(R.id.inboxMessageButton);
        sendMessageButton = (Button) view.findViewById(R.id.sendMessageButton);
        mLoadMoreBtn = (ImageButton) view.findViewById(R.id.loadMessagebutton);
        noDataAvailable = (TextView) view.findViewById(R.id.noDataAvailable);
        mMessageList = (ListView) view.findViewById(R.id.messageList);
        messageCount = (TextView) view.findViewById(R.id.messageCount);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_message_list;
    }

    @Override
    public void setUp() {
        setAdapter();
        setupSearchBar(true, "Search Messages");
        addFilter(searchWatcher);
    }

    public void setListeners() {
        inboxMessageButton.setOnClickListener(this);
        sendMessageButton.setOnClickListener(this);
        mLoadMoreBtn.setOnClickListener(this);
        MessageCountWatcher.getInstance().register(this);
    }

    public void setAdapter() {
        mAdapter = new MessageAdapter(getActivity(), true);
        mMessageList.setAdapter(mAdapter);
        mMessageList.setOnItemClickListener(messageListItemClick);
    }

    public void fetchDataFromServer() {
        getPresenter().fetchDataFromServer(isInbox);
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
    public void showMessageList(List<MessageData> mlist) {
        mLoadMoreBtn.setVisibility(View.VISIBLE);
        mMessageList.setVisibility(View.VISIBLE);
        noDataAvailable.setVisibility(View.GONE);
        mAdapter.clear();
        if (mlist.size() > 0) {
            mAdapter.addAll(mlist);
            mAdapter.notifyDataSetChanged();
        } else {
            showNoTxt();
        }

    }

    @Override
    public void showNoTxt() {
        noDataAvailable.setVisibility(View.VISIBLE);
        mLoadMoreBtn.setVisibility(View.GONE);
        mMessageList.setVisibility(View.GONE);
    }

    @Override
    public void showMessageCount() {
        messageCount.setVisibility(View.VISIBLE);
        String msgCount = Preferences.getInstances(getActivity()).getUnreadMessages();
        if (msgCount.equals("0")) {
            hideMessageCount();
        } else {
            messageCount.setText(msgCount);
        }
    }

    @Override
    public void hideMessageCount() {
        messageCount.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyAdapter() {
        getPresenter().fetchInboxMsgs(null);
    }

    @Override
    public void sentMessageList() {
        sendMessageButton.setBackgroundResource(R.drawable.send_on);
        inboxMessageButton.setBackgroundResource(R.drawable.inbox_off);
        mAdapter.setIsInbox(false);
        isInbox = false;
        getPresenter().fetchSentMsgs(null);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.loadMessagebutton:
                getPresenter().loadMoreMsgs(isInbox);
                break;
            case R.id.inboxMessageButton:
                sendMessageButton.setBackgroundResource(R.drawable.send_off);
                inboxMessageButton.setBackgroundResource(R.drawable.inbox_on);
                mAdapter.setIsInbox(true);
                isInbox = true;
                getPresenter().fetchInboxMsgs(null);
                break;
            case R.id.sendMessageButton:
                sentMessageList();
                break;
        }
    }

}
