package com.grupio.notes;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.fragments.BaseFragment;

import java.util.List;

/**
 * Created by JSN on 18/11/16.
 */

public class NoteListFragment<T> extends BaseFragment<NotesPresenter> implements NotesContract.View, View.OnClickListener {

    private T type;
    private TextView emailBtn, deleteBtn;
    private ListView mListView;
    private TextView newNoteBtn;
    private LinearLayout newNoteLay;

    public NoteListFragment(T type) {
        this.type = type;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_notes_list;
    }

    @Override
    public void initIds() {
        mListView = (ListView) view.findViewById(R.id.list);
        deleteBtn = (TextView) view.findViewById(R.id.delete);
        emailBtn = (TextView) view.findViewById(R.id.email);
        newNoteBtn = (TextView) view.findViewById(R.id.newNoteBtn);
        newNoteLay = (LinearLayout) view.findViewById(R.id.noNoteLay);
        mListView.setEmptyView(newNoteLay);
    }

    @Override
    public void setListeners() {
        emailBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    @Override
    public void setUp() {

    }

    @Override
    public NotesPresenter setPresenter() {
        return new NotesPresenter(this);
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
    public void showProgress() {
        showProgressDialog("Loading...");
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    @Override
    public void showList(List<NotesData> mList) {
    }

    @Override
    public void showNote(NotesData note) {
    }

    @Override
    public void onNoteSaved(NotesData mNote) {
    }

    @Override
    public void upateNoteId(String id) {
    }

    @Override
    public void setHeaderColor(String colorCode) {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete:
                break;
            case R.id.email:
                break;
        }
    }
}
