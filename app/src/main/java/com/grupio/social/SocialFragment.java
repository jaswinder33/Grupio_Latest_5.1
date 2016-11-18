package com.grupio.social;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.grupio.R;
import com.grupio.dao.EventDAO;
import com.grupio.db.EventTable;
import com.grupio.fragments.BaseFragment;
import com.grupio.message.apis.APICallBack;
import com.grupio.services.LinkedinShare;
import com.grupio.services.Service;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

/**
 * Created by JSN on 11/11/16.
 */

public class SocialFragment extends BaseFragment<Void> implements View.OnClickListener {

    private static SocialFragment mSocialFragment;
    APICallBack mListener = new APICallBack() {
        @Override
        public void onSuccess() {
            showToast("Successfully posted.");
        }

        @Override
        public void onFailure(String msg) {
            showToast(msg);
        }
    };
    private EditText mShareTxtField;
    private ImageButton twitter, linkedinBtn, facebookBtn;
    private Button shareButton;

    public SocialFragment() {
    }

    public static SocialFragment getInstance(Bundle mBundle) {
        mSocialFragment = new SocialFragment();
        if (mBundle != null) {
            mSocialFragment.setArguments(mBundle);
        }
        return mSocialFragment;
    }

    @Override
    public int getLayout() {
        return R.layout.layout_social;
    }

    @Override
    public void initIds() {
        mShareTxtField = (EditText) view.findViewById(R.id.shareField);
        twitter = (ImageButton) view.findViewById(R.id.twitterBtn);
        linkedinBtn = (ImageButton) view.findViewById(R.id.linkedinBtn);
        facebookBtn = (ImageButton) view.findViewById(R.id.facebookBtn);
        shareButton = (Button) view.findViewById(R.id.shareBtn);
    }

    @Override
    public void setListeners() {
        shareButton.setOnClickListener(this);
        twitter.setOnClickListener(this);
        linkedinBtn.setOnClickListener(this);
        facebookBtn.setOnClickListener(this);
    }

    @Override
    public void setUp() {
        String text = "";
        Bundle mBundle = getArguments();
        if (mBundle != null) {
            text = "Attending: " + getArguments().getString("session_name") + " at " + EventDAO.getInstance(getActivity()).getValue(EventTable.EVENT_NAME) + " ";
        }
        text += EventDAO.getInstance(getActivity()).getValue(EventTable.HASHTAG);

        mShareTxtField.setText(text);

    }

    @Override
    public Void setPresenter() {
        return null;
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
    public void onClick(View view) {

        String shareTxt = mShareTxtField.getText().toString();

        if (TextUtils.isEmpty(shareTxt)) {
            showToast("Cannot share empty message!");
            return;
        }


        switch (view.getId()) {

            case R.id.shareBtn:
                break;
            case R.id.twitterBtn:
                TweetComposer.Builder builder = new TweetComposer.Builder(getActivity())
                        .text(shareTxt);
                builder.show();

                break;
            case R.id.linkedinBtn:
                Service<String> mService = new Service(new LinkedinShare());
                mService.sendMessage(mShareTxtField.getText().toString(), getActivity(), mListener);
                break;
            case R.id.facebookBtn:
                ((SocialActivity) getActivity()).showFacebookDialog(shareTxt);
                break;
        }
    }
}
