package com.grupio.notes;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.activities.BaseActivity;
import com.grupio.attendee.ListConstant;
import com.grupio.data.EmailData;
import com.grupio.services.EmailService;
import com.grupio.services.Service;

import java.util.ArrayList;
import java.util.List;

public class NotesDetailsActivity extends BaseActivity<NotesPresenter> implements NotesContract.View {

    public static final String UPDATE = "1";
    public static final String DELETE = "2";
    protected static final String INSERT = "0";
    private TextView headingTxt;
    private Button saveBtn, deleteBtn;
    private RuledView mRuledView;
    private NotesData mNotesData = new NotesData();
    private RelativeLayout noteHeaderLay;

    @Override
    public int getLayout() {
        return R.layout.activity_notes_details;
    }

    @Override
    public void initIds() {
        headingTxt = (TextView) findViewById(R.id.heading);
        mRuledView = (RuledView) findViewById(R.id.notesDesc);
        noteHeaderLay = (RelativeLayout) findViewById(R.id.noteHeaderLay);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
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
        saveBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    @Override
    public void setUp() {
        getData();
        handleRightBtn(true, EMAIL);
        getPresenter().fetchNote(mNotesData.getNoteType(), mNotesData.getNoteId(), this);
    }

    @Override
    public void handleRightBtnClick(View view) {

        String text = mRuledView.getText().toString();

        if (TextUtils.isEmpty(text)) {
            showToast("Empty text");
            return;
        }

        Service<EmailData> mEmailService = new Service(new EmailService());
        EmailData emailData = new EmailData("", "My Notes", text);
        mEmailService.sendMessage(emailData, this, null);

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
        onBackPressed();
    }

    @Override
    public void showNote(NotesData note) {
        mNotesData.setId(note.getId());
        mRuledView.setText(note.getNoteText());
    }

    @Override
    public void onNoteSaved(NotesData mNote) {
        mNotesData = mNote;
        runOnUiThread(() -> showToast("Note Saved!"));
    }

    @Override
    public void upateNoteId(String id) {
        if (mNotesData.getNoteType().equals(NotesListActivity.THINGS_TO_DO) || mNotesData.getNoteType().equals(NotesListActivity.My_NOTES)) {
            mNotesData.setNoteId(id);
        } else {
            mNotesData.setId(id);
        }
    }

    @Override
    public void setHeaderColor(String colorCode) {
        noteHeaderLay.setBackgroundColor(Color.parseColor(colorCode));
    }

    @Override
    public void failure(String msg) {
        showToast(msg);
    }

    @Override
    public void showDeleteBtn() {
        deleteBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void setHeaderText(String text) {
        headingTxt.setText(text);
    }

    public void getData() {
        Bundle mbundle = getIntent().getExtras();
        if (mbundle != null) {
            mNotesData.setNoteId(mbundle.getString("id"));
            mNotesData.setNoteType(mbundle.getString("type"));
        }

        Log.i("NoteDetail", "getData: Type: " + mNotesData.getNoteType() + "\nId: " + mNotesData.getId());


        if (mNotesData.getNoteId().equals("0") && (mNotesData.getNoteType().equals(NotesListActivity.THINGS_TO_DO) || mNotesData.getNoteType().equals(NotesListActivity.My_NOTES))) {
            long id = System.currentTimeMillis();
            mNotesData.setId(String.valueOf(id));
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.saveBtn:

                String text = mRuledView.getText().toString();

                if (TextUtils.isEmpty(text)) {
                    showToast("Empty text");
                    return;
                }

                NotesData mNote = new NotesData();
                mNote.setId(mNotesData.getId());
                mNote.setNoteText(text);
                mNote.setNoteSync("0");

                if (mNotesData.getId().equals("0") && (mNotesData.getNoteType().equals(ListConstant.SESSION) || mNotesData.getNoteType().equals(ListConstant.EXHIBITOR))) {
                    mNote.setLastOperation(INSERT);
                    mNote.setNoteId(mNotesData.getNoteId());
                } else if ((mNotesData.getNoteType().equals(ListConstant.SESSION) || mNotesData.getNoteType().equals(ListConstant.EXHIBITOR))) {
                    mNote.setLastOperation(UPDATE);
                    mNote.setNoteId(mNotesData.getNoteId());
                } else if (mNotesData.getNoteId().equals("0") && (mNotesData.getNoteType().equals(NotesListActivity.THINGS_TO_DO) || mNotesData.getNoteType().equals(NotesListActivity.My_NOTES))) {
                    mNote.setLastOperation(INSERT);
                    mNote.setNoteId(mNotesData.getId());
                } else if (!mNotesData.getNoteId().equals("0") && (mNotesData.getNoteType().equals(NotesListActivity.THINGS_TO_DO) || mNotesData.getNoteType().equals(NotesListActivity.My_NOTES))) {
                    mNote.setLastOperation(UPDATE);
                    mNote.setNoteId(mNotesData.getNoteId());
                }

                mNote.setNoteType(mNotesData.getNoteType());

                getPresenter().saveNote(mNote, this);
                break;

            case R.id.deleteBtn:
                List<NotesData> mNoteList = new ArrayList<>();
                mNoteList.add(mNotesData);
                getPresenter().deleteNotes(mNoteList, this);
                break;
        }
    }
}
