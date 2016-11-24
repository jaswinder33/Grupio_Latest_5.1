package com.grupio.notes;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.grupio.R;
import com.grupio.activities.BaseActivity;
import com.grupio.data.EmailData;
import com.grupio.services.EmailService;
import com.grupio.services.Service;

public class ToDoDetailsActivity extends BaseActivity<Void> {


    private Button saveBtn, deleteBtn;
    private TextView headingTxt, toDoTextField;
    private RelativeLayout noteHeaderLay;
    private Switch reminderToggle;
    private LinearLayout dateTimelay;
    ToggleButton.OnCheckedChangeListener mToggleListener = (compoundButton, b) -> {
        if (b) {
            dateTimelay.setVisibility(View.VISIBLE);
        } else {
            dateTimelay.setVisibility(View.GONE);
        }
    };
    private TextView dateTime;
    private Button dateBtn, timeBtn;
    private DatePicker datePicker;
    private TimePicker timePicker;

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
        toDoTextField = (TextView) findViewById(R.id.todofield);
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
    public Void setPresenter() {
        return null;
    }

    @Override
    public void setListeners() {
        saveBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        reminderToggle.setOnCheckedChangeListener(mToggleListener);
    }

    @Override
    public void setUp() {

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

}
