package com.grupio.notes;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.activities.BaseActivity;
import com.grupio.attendee.ListConstant;
import com.grupio.services.EmailService;
import com.grupio.services.Service;

import java.util.List;

public class NotesDetailsActivity extends BaseActivity<NotesPresenter> implements NotesContract.View {

    private static final String INSERT = "0";
    private static final String UPDATE = "1";
    String id;
    String type;
    private TextView headingTxt;
    private Button saveBtn;
    private RuledView mRuledView;
    private NotesData mNotesData = new NotesData();

    @Override
    public int getLayout() {
        return R.layout.activity_notes_details;
    }

    @Override
    public void initIds() {
        headingTxt = (TextView) findViewById(R.id.heading);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        mRuledView = (RuledView) findViewById(R.id.notesDesc);
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
        getPresenter().fetchNote(type, id, this);
    }

    @Override
    public void handleRightBtnClick() {

        String text = mRuledView.getText().toString();

        if (TextUtils.isEmpty(text)) {
            showToast("Empty text");
            return;
        }

        Service mEmailService = new Service(new EmailService());
        mEmailService.sendMessage(text, this, null);

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
        mNotesData = note;
        mRuledView.setText(note.getNoteText());
    }

    @Override
    public void onNoteSaved(NotesData mNote) {
        mNotesData = mNote;
        showToast("Note Saved!");
    }

    @Override
    public void upateNoteId(String id) {

    }

    public void getData() {
        Bundle mbundle = getIntent().getExtras();
        if (mbundle != null) {
            id = mbundle.getString("id");
            type = mbundle.getString("type");
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

                if (mNotesData.getNoteId().equals(id)) {
                    mNote.setLastOperation(INSERT);
                } else {
                    mNote.setLastOperation(UPDATE);
                }

                if (type.equals(ListConstant.SESSION)) {
                    mNote.setNoteType(ListConstant.SESSION);
                } else if (type.equals(ListConstant.EXHIBITOR)) {
                    mNote.setNoteType(ListConstant.EXHIBITOR);
                } else {
                    mNote.setNoteType("notes");
                }

                getPresenter().saveNote(mNote, this);
                break;
        }
    }
}
