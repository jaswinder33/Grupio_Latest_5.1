package com.grupio.notes;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.util.List;

public class NotesDetailsActivity extends BaseActivity<NotesPresenter> implements NotesContract.View {

    private static final String INSERT = "0";
    private static final String UPDATE = "1";
    private TextView headingTxt;
    private Button saveBtn;
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
        saveBtn = (Button) findViewById(R.id.saveBtn);
        mRuledView = (RuledView) findViewById(R.id.notesDesc);
        noteHeaderLay = (RelativeLayout) findViewById(R.id.noteHeaderLay);
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
    }

    @Override
    public void setUp() {
        getData();
        handleRightBtn(true, EMAIL);
        getPresenter().fetchNote(mNotesData.getNoteType(), mNotesData.getNoteId(), this);
    }

    @Override
    public void handleRightBtnClick() {

        String text = mRuledView.getText().toString();

        if (TextUtils.isEmpty(text)) {
            showToast("Empty text");
            return;
        }

        Service<EmailData> mEmailService = new Service(new EmailService());
        EmailData emailData = new EmailData("", "My To-dos", text);
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
        mNotesData.setId(id);
    }

    @Override
    public void setHeaderColor(String colorCode) {
        noteHeaderLay.setBackgroundColor(Color.parseColor(colorCode));
    }

    public void getData() {
        Bundle mbundle = getIntent().getExtras();
        if (mbundle != null) {
            mNotesData.setNoteId(mbundle.getString("id"));
            mNotesData.setNoteType(mbundle.getString("type"));
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
                mNote.setNoteId(mNotesData.getNoteId());
                mNote.setNoteText(text);
                mNote.setNoteSync("0");

                if (mNotesData.getId().equals("0")) {
                    mNote.setLastOperation(INSERT);
                } else {
                    mNote.setLastOperation(UPDATE);
                }

                if (mNotesData.getNoteType().equals(ListConstant.SESSION)) {
                    mNote.setNoteType(ListConstant.SESSION);
                } else if (mNotesData.getNoteType().equals(ListConstant.EXHIBITOR)) {
                    mNote.setNoteType(ListConstant.EXHIBITOR);
                } else {
                    mNote.setNoteType("notes");
                }

                getPresenter().saveNote(mNote, this);
                break;
        }
    }
}
