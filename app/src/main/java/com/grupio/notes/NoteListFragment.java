package com.grupio.notes;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.activities.BaseActivity;
import com.grupio.animation.SlideOut;
import com.grupio.fragments.BaseFragment;
import com.grupio.interfaces.ClickHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 18/11/16.
 */

public class NoteListFragment extends BaseFragment<NotesPresenter> implements NotesContract.View, View.OnClickListener {

    private String type;
    AdapterView.OnItemClickListener mItemClick = (adapterView, view1, i, l) -> {
        NotesData mNoteData = (NotesData) adapterView.getAdapter().getItem(i);
        goToNoteDetail(mNoteData.getNoteId());
    };
    private TextView emailBtn, deleteBtn;
    private ListView mListView;
    private TextView newNoteBtn, noItemBtn;
    private LinearLayout newNoteLay;
    private NotesAdapter mAdapter;

    public NoteListFragment(String type) {
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

        noItemBtn = (TextView) view.findViewById(R.id.noItemTxt);

        mAdapter = new NotesAdapter(getActivity());
        mListView.setAdapter(mAdapter);

        if (type.equals(NotesListActivity.THINGS_TO_DO)) {
            noItemBtn.setText(getActivity().getString(R.string.no_todo));
            emailBtn.setText(getString(R.string.email_to_do));
            deleteBtn.setText(getString(R.string.delete_to_do));
            newNoteBtn.setText(getString(R.string.add_todo));
        } else {
            noItemBtn.setText(getActivity().getString(R.string.no_note_msg));
            emailBtn.setText(getString(R.string.email_notes));
            deleteBtn.setText(getString(R.string.delete_notes));
            newNoteBtn.setText(getString(R.string.add_note_btn));
        }

        emailBtn.setTextColor(Color.WHITE);
        deleteBtn.setTextColor(Color.WHITE);
    }

    @Override
    public void setListeners() {
        emailBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        newNoteBtn.setOnClickListener(this);
        mListView.setOnItemClickListener(mItemClick);
    }

    @Override
    public void setUp() {
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().fetchList(type, getActivity());
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
        Log.i("NoteListFragment", "showList: size:" + mList);
        mAdapter.clear();
        mAdapter.addAll(mList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNote(NotesData note) {
        //note detail function
    }

    @Override
    public void onNoteSaved(NotesData mNote) {
        //note detail function
    }

    @Override
    public void upateNoteId(String id) {
        //note detail function
    }

    @Override
    public void setHeaderColor(String colorCode) {
        emailBtn.setBackgroundColor(Color.parseColor(colorCode));
        deleteBtn.setBackgroundColor(Color.parseColor(colorCode));
        newNoteBtn.setBackgroundColor(Color.parseColor(colorCode));
    }

    @Override
    public void failure(String msg) {
        showToast(msg);
    }

    @Override
    public void showDeleteBtn() {

    }

    @Override
    public void setHeaderText(String text) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete:

                List<NotesData> mListToDelete = new ArrayList<>();
                mListToDelete.addAll(mAdapter.getSelected());

                Log.i("NoteListFragment", "onClick: Received elements count: " + mListToDelete.size());

                if (mListToDelete.size() > 0) {
                    ClickHandler mPositiveBtn = () -> getPresenter().deleteNotes(mListToDelete, getActivity());
                    BaseActivity.CustomDialog.getDialog(getActivity(), mPositiveBtn).show(getString(R.string.delete_note_msg));
                } else {
                    BaseActivity.CustomDialog.getDialog(getActivity(), () -> {
                    }).singledBtnDialog(true).show(getString(R.string.empty_delete_list_msg));
                }

                break;
            case R.id.email:
                List<NotesData> mListToEmail = new ArrayList<>();
                mListToEmail.addAll(mAdapter.getSelected());
                if (mListToEmail.size() > 0) {
                    getPresenter().doEmail(mListToEmail, getActivity());
                } else {
                    BaseActivity.CustomDialog.getDialog(getActivity(), () -> {
                    }).singledBtnDialog(true).show(getString(R.string.empty_delete_list_msg));
                }
                break;
            case R.id.newNoteBtn:
                goToNoteDetail("0");
                break;
        }
    }

    public void goToNoteDetail(String id) {
        Intent mIntent = new Intent(getActivity(), NotesDetailsActivity.class);
        mIntent.putExtra("type", type);
        mIntent.putExtra("id", id);
        startActivity(mIntent);
        SlideOut.getInstance().startAnimation(getActivity());
    }

}
