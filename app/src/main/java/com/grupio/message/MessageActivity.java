package com.grupio.message;

import android.app.Fragment;
import android.view.View;

import com.grupio.R;
import com.grupio.activities.BaseActivity;
import com.grupio.message.fragments.MessageListFragment;

/**
 * Created by JSN on 5/7/16.
 */
public class MessageActivity extends BaseActivity {

    @Override
    public boolean isHeaderForGridPage() {
        return true;
    }

    @Override
    public void initIds() {
    }

    @Override
    public void setListeners() {
    }

    @Override
    public Object setPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.layout_container;
    }

    @Override
    public void setUp() {
        handleRightBtn(true, "refresh");
        getFragmentManager().beginTransaction().add(R.id.container, MessageListFragment.getInstance(null), MessageListFragment.class.getName()).commit();
    }

    @Override
    public void handleRightBtnClick(View view) {
        Fragment mFrag = getFragmentManager().findFragmentById(R.id.container);
        if (mFrag != null && mFrag instanceof MessageListFragment) {
            ((MessageListFragment) mFrag).fetchDataFromServer();
        }
    }

    @Override
    public String getScreenName() {
        return null;
    }

    @Override
    public String getBannerName() {
        return null;
    }
}
