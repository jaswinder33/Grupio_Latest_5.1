package com.grupio.notes;


import com.grupio.R;
import com.grupio.activities.BaseActivity;

public class NotesActivity extends BaseActivity<NotesPresenter> {


    @Override
    public int getLayout() {
        return R.layout.activity_notes;
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
    public NotesPresenter setPresenter() {
        return null;
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void setUp() {
        handleRightBtn(true, EMAIL);
    }

    @Override
    public void handleRightBtnClick() {
        getPresenter().doEmail(this);
    }
}
