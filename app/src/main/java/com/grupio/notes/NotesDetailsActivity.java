package com.grupio.notes;

import android.os.Bundle;

import com.grupio.R;
import com.grupio.activities.BaseActivity;

import java.util.List;

public class NotesDetailsActivity extends BaseActivity<NotesPresenter> implements NotesContract.View {


    String id;
    String type;

    @Override
    public int getLayout() {
        return R.layout.activity_notes_details;
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
        return new NotesPresenter(this);
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void setUp() {

    }

    @Override
    public void handleRightBtnClick() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showList(List<NotesData> mList) {

    }

    @Override
    public void showNote(String text) {

    }

    public void getData() {
        Bundle mbundle = getIntent().getExtras();

        if (mbundle != null) {
            id = mbundle.getString("id");
            type = mbundle.getString("type");

        }

    }
}
