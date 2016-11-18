package com.grupio.notes;

import android.os.Bundle;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.activities.BaseActivity;

public class NotesListActivity extends BaseActivity<Void> {

    public static final String My_NOTES = "my_notes";
    public static final String THINGS_TO_DO = "things_to_do";

    public String type = "";

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
        getData();
        handleRightBtn(true, ADD);
        if (type.equals(My_NOTES)) {
            NoteListFragment<NotesData> mFrag = new NoteListFragment<>(new NotesData());
            Utility.addFragment(this, mFrag, false);
        } else {

        }
    }

    @Override
    public void handleRightBtnClick() {
    }


    public void getData() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            type = mBundle.getString("from");
        }

    }
}
