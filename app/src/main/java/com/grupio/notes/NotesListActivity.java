package com.grupio.notes;

import android.app.Fragment;
import android.os.Bundle;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.activities.BaseActivity;
import com.grupio.backend.Permissions;

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
        return true;
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
        NoteListFragment mFrag = new NoteListFragment(type);
        Utility.addFragment(this, mFrag, false);
        Permissions.getInstance().hasCalendarPermission(this).askForPermissions(this, 102);
    }

    @Override
    public void handleRightBtnClick() {
        Fragment mFrag = getFragmentManager().findFragmentById(R.id.container);
        if (mFrag != null) {
            ((NoteListFragment) mFrag).goToNoteDetail("0");
        }
    }

    public void getData() {
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            type = mBundle.getString("from");
        }
    }
}
