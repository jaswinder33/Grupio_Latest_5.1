package com.grupio.social;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.grupio.BuildConfig;
import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.activities.BaseActivity;
import com.grupio.dao.EventDAO;
import com.grupio.db.EventTable;

public class SocialActivity extends BaseActivity<Void> {

    CallbackManager callbackManager;
    ShareDialog shareDialog;


    @Override
    public int getLayout() {
        return R.layout.layout_container;
    }

    @Override
    public void initIds() {
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
    public Void setPresenter() {
        return null;
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void setUp() {

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        Bundle mbundle = null;
        if (getData() != null) {
            mbundle = new Bundle();
            mbundle.putString("session_name", getData());
        }

        Utility.addFragment(this, SocialFragment.getInstance(mbundle), false);
    }

    @Override
    public void handleRightBtnClick(View view) {
    }

    public String getData() {
        String name = "";
        Bundle mBundle = getIntent().getExtras();

        if (mBundle != null) {
            name = mBundle.getString("session_name");
            handleLeftBtn(false);
        } else {
            handleLeftBtn(true);
            return null;
        }
        return name;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void showFacebookDialog(String text) {

        String description = EventDAO.getInstance(this).getValue(EventTable.DESCRIPTION);
        String imageUrl = getString(R.string.base_url) + EventDAO.getInstance(this).getValue(EventTable.LARGE_IMAGE);

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setQuote(text)
                    .setContentTitle(BuildConfig.APP_NAME)
                    .setContentDescription(
                            description)
                    .setContentUrl(Uri.parse(imageUrl))
                    .build();

            shareDialog.show(linkContent);
        }
    }
}
