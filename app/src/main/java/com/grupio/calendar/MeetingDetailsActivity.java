package com.grupio.calendar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.activities.BaseActivity;
import com.grupio.animation.SlideOut;
import com.grupio.attendee.ListDetailActivity;
import com.grupio.data.AttendeesData;
import com.grupio.data.MeetingData;
import com.grupio.interfaces.ClickHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by JSN on 19/12/16.
 */

public class MeetingDetailsActivity extends BaseActivity<MeetingDetailPresenter> implements MeetingDetailContract.View {

    private TextView meetingTitle, meetingDate, meetingTime, meetingLocation, deleteMeeting, manageMeeting;
    private TextView descriptionTxtHeader, descriptionText;
    private TextView organizerHeader;
    private ImageView organizerImage;
    private TextView organizerName, organizerTitle;
    private TextView inviteeeHeader;
    private ListView inviteeeList;
    private RelativeLayout organizerLayout;

    @Override
    public int getLayout() {
        return R.layout.layout_meeting_details;
    }

    @Override
    public void initIds() {
        meetingTitle = (TextView) findViewById(R.id.meetingTitle);
        meetingDate = (TextView) findViewById(R.id.meetingDate);
        meetingTime = (TextView) findViewById(R.id.meetingTime);
        meetingLocation = (TextView) findViewById(R.id.meetingLocation);
        deleteMeeting = (TextView) findViewById(R.id.deleteMeeting);
        manageMeeting = (TextView) findViewById(R.id.manageMeeting);
        descriptionTxtHeader = (TextView) findViewById(R.id.attendeesBioText);
        descriptionText = (TextView) findViewById(R.id.bioTxt);
        organizerHeader = (TextView) findViewById(R.id.orgaizerHeader);
        organizerImage = (ImageView) findViewById(R.id.attendee_image);
        organizerName = (TextView) findViewById(R.id.attendee_name);
        organizerTitle = (TextView) findViewById(R.id.attendee_company_title);
        inviteeeHeader = (TextView) findViewById(R.id.inviteesHeader);
        inviteeeList = (ListView) findViewById(R.id.inviteeeList);

        organizerLayout = (RelativeLayout) findViewById(R.id.organizerlay);
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
    public MeetingDetailPresenter setPresenter() {
        return new MeetingDetailPresenter(this);
    }

    @Override
    public void setListeners() {
        deleteMeeting.setOnClickListener(this);
        manageMeeting.setOnClickListener(this);
        organizerLayout.setOnClickListener(this);
    }

    @Override
    public void setUp() {
        getPresenter().fetchData(getData(), this);
    }

    @Override
    public void handleRightBtnClick(View view) {

    }

    @Override
    public void showOrganizer(AttendeesData organizerData, boolean isFirstName) {
        String url = getString(R.string.image_base_url) + organizerData.getImage();
        ImageLoader.getInstance().displayImage(url, organizerImage, Utility.getDisplayOptionsAttendee());

        if (isFirstName) {
            organizerName.setText(organizerData.getFirst_name() + " " + organizerData.getLast_name());
        } else {
            organizerName.setText(organizerData.getLast_name() + ", " + organizerData.getFirst_name());
        }


        String title = organizerData.getTitle();
        String company = organizerData.getCompany();

        if (company != null && !company.equals("") && title != null && !title.equals("")) {
            title += ", " + company;
        } else {
            title += company;
        }

        organizerTitle.setText(title);
    }

    @Override
    public void showInvitieesList(List<AttendeesData> invitieesList) {
        InviteeAdapter mAdapter = new InviteeAdapter(this);
        mAdapter.addAll(invitieesList);
        inviteeeList.setAdapter(mAdapter);
        Utility.setListViewHeightBasedOnChildren(inviteeeList);
    }

    @Override
    public void onMeetingDeleted() {
        ClickHandler onOk = () ->
                onBackPressed();
        CustomDialog.getDialog(this, onOk).singledBtnDialog(true).show(getString(R.string.meeting_delete_permission));
    }

    @Override
    public void showMeetingData(MeetingData meetingData) {
        meetingTitle.setText(meetingData.title);
        meetingDate.setText(getString(R.string.date) + ": " + meetingData.meetingDate);
        meetingTime.setText(getString(R.string.time) + ": " + meetingData.startTime + " - " + meetingData.endTime);
        meetingLocation.setText(getString(R.string.location) + ": " + meetingData.location);
    }

    @Override
    public void setDescription(String locale, String descriptionTxt) {
        descriptionTxtHeader.setText(locale);
        descriptionText.setText(descriptionTxt);
    }

    @Override
    public void setGraphics(String headerColor) {
        descriptionTxtHeader.setBackgroundColor(Color.parseColor(headerColor));
        organizerHeader.setBackgroundColor(Color.parseColor(headerColor));
        inviteeeHeader.setBackgroundColor(Color.parseColor(headerColor));
    }

    @Override
    public void updateMeetingStatus(AttendeesData mAttendeeData, int attendeePosition, String status) {
        getPresenter().updateMeetingStatus(mAttendeeData, attendeePosition, status, this);
    }

    @Override
    public void onMeetingStatusUpdated(int position, String status) {
        ((AttendeesData) inviteeeList.getAdapter().getItem(position)).setMeetingStatus(status);
        ((InviteeAdapter) inviteeeList.getAdapter()).notifyDataSetChanged();
    }

    public MeetingData getData() {
        MeetingData meetingData = null;
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            meetingData = new MeetingData((MeetingData) mBundle.getSerializable("data"));
        }
        return meetingData;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.manageMeeting:
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("data", getPresenter().getMeetingData());
                goToNextScreen(mBundle, NewMeetingActivity.class);

                break;

            case R.id.deleteMeeting:

                ClickHandler deleteMeeting = () ->
                        getPresenter().deleteMeeting(getPresenter().getMeetingData(), this);

                CustomDialog.getDialog(this, deleteMeeting).show(getString(R.string.meeting_delete_permission));

                break;

            case R.id.organizerlay:

                MeetingData meetingData = getPresenter().getMeetingData();

                Intent mIntent = new Intent(this, ListDetailActivity.class);
                mIntent.putExtra("id", meetingData.mCreatorData.getAttendee_id());
                mIntent.setType("attendee");
                mIntent.putExtra("data", meetingData.mCreatorData);
                startActivity(mIntent);
                SlideOut.getInstance().startAnimation(this);
                break;
        }
    }
}
