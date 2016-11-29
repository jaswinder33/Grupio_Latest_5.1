package com.grupio.notes;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.grupio.R;
import com.grupio.activities.BaseActivity;
import com.grupio.backend.DateTime;
import com.grupio.data.EmailData;
import com.grupio.services.EmailService;
import com.grupio.services.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ToDoDetailsActivity extends BaseActivity<NotesPresenter> implements NotesContract.View {


    private Button saveBtn, deleteBtn;
    private TextView headingTxt;
    private EditText toDoTextField;
    private RelativeLayout noteHeaderLay;
    private Switch reminderToggle;
    private LinearLayout dateTimelay;

    private TextView dateTime;
    private Button dateBtn, timeBtn;
    private DatePicker datePicker;
    private TimePicker timePicker;

    private NotesData mNotesData = new NotesData();
    DatePicker.OnDateChangedListener datePickerListener = (datePicker1, i, i1, i2) -> {

        String year = String.valueOf(i);
        String month = String.format("%02d", i1);
        String day = String.format("%02d", i2);

        String date = year + "-" + (month) + "-" + day;
        String updatedDate = DateTime.getInstance().updateDate(date, mNotesData.getNoteDate(), mNotesData.getTimeZone(), true);
        dateTime.setText(updatedDate);
    };
    TimePicker.OnTimeChangedListener timeChangeListener = (timePicker1, i, i1) -> {

        String hr = String.valueOf(i);
        String min = String.valueOf(i1);
        String time = hr + ":" + min;

        String updatedDate = DateTime.getInstance().updateDate(time, mNotesData.getNoteDate(), mNotesData.getTimeZone(), false);
        dateTime.setText(updatedDate);
    };
    ToggleButton.OnCheckedChangeListener mToggleListener = (compoundButton, b) -> {
        if (b) {
            dateTimelay.setVisibility(View.VISIBLE);
//            mNotesData.setNoteReminder("1");

            String time = DateTime.getInstance().currentTimeInTimeZone(mNotesData.getTimeZone());
            time = DateTime.getInstance().formatDate("dd-MMM-yyyy hh:mma", time);
            mNotesData.setNoteDate(time);
            dateTime.setText(time);

            setDatePicker(mNotesData.getNoteDate(), mNotesData.getTimeZone());
            setTimePicker(mNotesData.getNoteDate(), mNotesData.getTimeZone());

        } else {
            DateTime.getInstance().removeFromCalendar(this, mNotesData);
            mNotesData.setNoteReminder("0");
            mNotesData.setNoteDate("");
            dateTimelay.setVisibility(View.GONE);
        }
    };

    @Override
    public int getLayout() {
        return R.layout.activity_to_do_details;
    }

    @Override
    public void initIds() {
        headingTxt = (TextView) findViewById(R.id.heading);
        noteHeaderLay = (RelativeLayout) findViewById(R.id.noteHeaderLay);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);
        toDoTextField = (EditText) findViewById(R.id.todofield);
        reminderToggle = (Switch) findViewById(R.id.reminderToggle);
        dateTimelay = (LinearLayout) findViewById(R.id.dateTimelay);
        dateTime = (TextView) findViewById(R.id.dateTime);
        dateBtn = (Button) findViewById(R.id.dateBtn);
        timeBtn = (Button) findViewById(R.id.timeBtn);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
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
        dateBtn.setOnClickListener(this);
        timeBtn.setOnClickListener(this);
        reminderToggle.setOnCheckedChangeListener(mToggleListener);
    }

    @Override
    public void setUp() {
        getData();
        handleRightBtn(true, EMAIL);
        getPresenter().fetchNote(mNotesData.getNoteType(), mNotesData.getNoteId(), this);

    }

    @Override
    public void handleRightBtnClick() {
        String text = toDoTextField.getText().toString();

        if (TextUtils.isEmpty(text)) {
            showToast("Empty text");
            return;
        }

        Service<EmailData> mEmailService = new Service(new EmailService());
        EmailData emailData = new EmailData("", "My Todos", text);
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
        note.setId(note.getId().equals("0") ? mNotesData.getId() : note.getId());

        mNotesData = new NotesData(note);
        Log.i(TAG, "showNote: Note obj" + note.toString());
        Log.i(TAG, "showNote: mNotesData obj" + mNotesData.toString());
        toDoTextField.setText(mNotesData.getNoteText());
        dateTime.setText(mNotesData.getNoteDate());

        if (TextUtils.isEmpty(mNotesData.getNoteReminder()) || mNotesData.getNoteReminder().equals("0")) {
            reminderToggle.setChecked(false);
            mNotesData.setNoteDate("");
        }
    }

    @Override
    public void onNoteSaved(NotesData mNote) {
        mNotesData = mNote;
        runOnUiThread(() -> showToast("Note Saved!"));
    }

    @Override
    public void upateNoteId(String id) {
        mNotesData.setNoteId(id);
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

        if (mNotesData.getNoteId().equals("0")) {
            long id = System.currentTimeMillis();
            mNotesData.setId(String.valueOf(id));
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.saveBtn:

                String text = toDoTextField.getText().toString();

                if (TextUtils.isEmpty(text)) {
                    showToast("Empty text");
                    return;
                }

                NotesData mNote = new NotesData();
                mNote.setId(mNotesData.getId());
                mNote.setNoteText(text);
                mNote.setNoteSync("0");

                mNote.setNoteDate(mNotesData.getNoteDate());
                mNote.setTimeZone(mNotesData.getTimeZone());
                mNote.setNoteType(mNotesData.getNoteType());


                if (mNotesData.getNoteId().equals("0")) {
                    mNote.setLastOperation(NotesDetailsActivity.INSERT);
                    mNote.setNoteId(mNotesData.getId());
                } else {
                    mNote.setLastOperation(NotesDetailsActivity.UPDATE);
                    mNote.setNoteId(mNotesData.getNoteId());
                }

                if (reminderToggle.isChecked() && !mNotesData.getNoteReminder().equals("0")) {
                    DateTime.getInstance().removeFromCalendar(this, mNote);
                }


                if (reminderToggle.isChecked() && mNotesData.getNoteReminder().equals("0") || !mNotesData.getNoteDate().equals(dateTime.getText().toString())) {
                    mNote.setNoteReminder(DateTime.getInstance().saveToCalendar(this, mNote));
                } else {
                    mNote.setNoteReminder("0");
                }

                getPresenter().saveNote(mNote, this);
                break;

            case R.id.deleteBtn:
                List<NotesData> mNoteList = new ArrayList<>();
                mNoteList.add(mNotesData);
                DateTime.getInstance().removeFromCalendar(this, mNotesData);
                getPresenter().deleteNotes(mNoteList, this);
                break;

            case R.id.dateBtn:
                setDatePicker(mNotesData.getNoteDate(), mNotesData.getTimeZone());
                datePicker.setVisibility(View.VISIBLE);
                timePicker.setVisibility(View.GONE);
                setButton(false);
                break;

            case R.id.timeBtn:
                setTimePicker(mNotesData.getNoteDate(), mNotesData.getTimeZone());
                datePicker.setVisibility(View.GONE);
                timePicker.setVisibility(View.VISIBLE);
                setButton(true);
                break;

        }
    }

    private void setButton(boolean istime) {
        if (istime) {
            dateBtn.setBackgroundResource(R.drawable.left_round_stroke);
            timeBtn.setBackgroundResource(R.drawable.right_round_btn);
            timeBtn.setTextColor(Color.WHITE);
        } else {
            dateBtn.setBackgroundResource(R.drawable.left_round_btn);
            dateBtn.setTextColor(Color.WHITE);
            timeBtn.setBackgroundResource(R.drawable.right_round_stroke);
        }
    }

    public void setDatePicker(String date, String timeZone) {
        int day = DateTime.getInstance().getDateVarialbe(Calendar.DATE, date, timeZone);
        int month = DateTime.getInstance().getDateVarialbe(Calendar.MONTH, date, timeZone);
        int year = DateTime.getInstance().getDateVarialbe(Calendar.YEAR, date, timeZone);

        datePicker.init(year, month, day, datePickerListener);
    }

    public void setTimePicker(String date, String timeZone) {
        int hr = DateTime.getInstance().getDateVarialbe(Calendar.HOUR, date, timeZone);
        int min = DateTime.getInstance().getDateVarialbe(Calendar.MINUTE, date, timeZone);

        timePicker.setCurrentHour(hr);
        timePicker.setCurrentMinute(min);

        timePicker.setOnTimeChangedListener(timeChangeListener);
    }

}
