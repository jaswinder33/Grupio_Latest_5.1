package com.grupio.attendee;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.activities.BaseActivity;
import com.grupio.animation.SlideOut;
import com.grupio.attendee.message.SendMessageActivity;
import com.grupio.backend.Permissions;
import com.grupio.data.AttendeesData;
import com.grupio.data.ExhibitorData;
import com.grupio.data.LogisticsData;
import com.grupio.data.ScheduleData;
import com.grupio.data.SpeakerData;
import com.grupio.helper.ScheduleHelper;
import com.grupio.interfaces.ClickHandler;
import com.grupio.interfaces.Person;
import com.grupio.login.LoginActivity;
import com.grupio.logistics.DocumentController;
import com.grupio.logistics.LogisticsAdapter;
import com.grupio.notes.NotesDetailsActivity;
import com.grupio.schedule.ScheduleTrackListActivity;
import com.grupio.services.EmailService;
import com.grupio.services.Service;
import com.grupio.session.Preferences;
import com.grupio.social.SocialActivity;
import com.grupio.speakers.SpeakerAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class ListDetailActivity extends BaseActivity<ListDetailPresenter> implements DetailViewInter {

    public static final int CONNECT_REQUEST = 201;


    public static final String LIST_DETAIL = "ListDetailActivity";

    public static final String SPONSOR = "sponsor";
    public static final String EXHIBITOR = "exhibitor";
    public static final String ATTENDEE = "attendee";
    public static final String SPEAKER = "speaker";
    Button mFavSessionBtn, mSessionNotes, mSessionSocial;
    AdapterView.OnItemClickListener sessionListItemClick = (parent, view, position, id1) -> {

        ScheduleData sData = (ScheduleData) parent.getAdapter().getItem(position);
        Intent intent = new Intent(ListDetailActivity.this, ListDetailActivity.class);
        intent.setType(ListConstant.SESSION);
        intent.putExtra("id", sData.getSession_id());
        intent.putExtra("data", sData);
        startActivity(intent);
        SlideOut.getInstance().startAnimation(ListDetailActivity.this);

    };
    AdapterView.OnItemClickListener attendeeListItemClick = (parent, view, position, id1) -> {

        AttendeesData mData = (AttendeesData) parent.getAdapter().getItem(position);

        Intent mIntent = new Intent(ListDetailActivity.this, ListDetailActivity.class);
        mIntent.putExtra("id", mData.getAttendee_id());
        mIntent.setType("attendee");
        mIntent.putExtra("data", mData);

        startActivity(mIntent);
        SlideOut.getInstance().startAnimation(ListDetailActivity.this);
    };

    AdapterView.OnItemClickListener speakerListItemClick = (parent, view, position, id1) -> {

        SpeakerData mData = (SpeakerData) parent.getAdapter().getItem(position);

        Intent mIntent = new Intent(ListDetailActivity.this, ListDetailActivity.class);
        mIntent.putExtra("id", mData.getAttendee_id());
        mIntent.setType("speaker");
        mIntent.putExtra("data", mData);

        startActivity(mIntent);
        SlideOut.getInstance().startAnimation(ListDetailActivity.this);
    };

    private String id;
    private Person mperson;
    private ImageView image;
    private TextView name, title, company;
    private Button chatBtn, msgBtn, connectBtn;
    private Button mFavBtn, websiteBtn;
    //Contact Info Variables
    private LinearLayout infoLay;
    private RelativeLayout emaillayout, phoneLayout, linkedinLayout, twitterlayout, websiteLayout;
    private TextView contactInfoTxt, email, phoneNum, linkedinId, twitterId, website;
    private ImageView emailLine, phoneNumLine, linkedinLine, twitterLine, websiteLine;
    //Interst Info Variables
    private LinearLayout interestLay;
    private TextView interestTxtHeader, interestTxt;
    //Bio Variables
    private LinearLayout bioLay;
    private TextView bioTxtHeader, bioTxt;
    //SessionList variables
    private LinearLayout sessionListLay;
    private TextView sessionheader;
    private ListView sessionList;
    //Resource list variables
    private LinearLayout resourceListLay;
    private TextView resourceHeader;
    private ListView resourceList;
    //Attendee list variables
    private LinearLayout attendeeListLay;
    private TextView attendeeHeader;
    private ListView attendeeList;
    private String type = "";


    AdapterView.OnItemClickListener resourceListItemClick = (parent, view, position, id1) -> {

        LogisticsData mapObjt = (LogisticsData) parent.getAdapter().getItem(position);
        switch (type) {
            case "attendee":
                DocumentController<AttendeesData, LogisticsData> mController = new DocumentController<>(new AttendeesData(), new LogisticsData(), ListDetailActivity.this);
                mController.handleDocument(mapObjt);
                break;

            case "speaker":
                DocumentController<SpeakerData, LogisticsData> mController1 = new DocumentController<>(new SpeakerData(), new LogisticsData(), ListDetailActivity.this);
                mController1.handleDocument(mapObjt);
                break;

            case "exhibitor":
                DocumentController<ExhibitorData, LogisticsData> mController2 = new DocumentController<>(new ExhibitorData(), new LogisticsData(), ListDetailActivity.this);
                mController2.handleDocument(mapObjt);
                break;

            case ListConstant.SESSION:
                DocumentController<ScheduleHelper, LogisticsData> mController3 = new DocumentController<>(new ScheduleHelper(), new LogisticsData(), ListDetailActivity.this);
                mController3.handleDocument(mapObjt);
                break;

        }
    };
    //Schedule variables
    private LinearLayout sessionLay;
    private TextView sessionTitle, sessionDate, sessionTime, sessionLocation, maxAttendeeLimit;
    private RelativeLayout mSessionBtnsLay;
    private ScheduleData mScheduleData;

    @Override
    public boolean isHeaderForGridPage() {
        return false;
    }

    /**
     * Retrieve data sent from list
     */
    private void getData() {

        Intent mIntent = getIntent();
        if (mIntent != null) {
            type = getIntent().getType();
            id = mIntent.getStringExtra("id");
            mperson = (Person) mIntent.getSerializableExtra("data");
        }

        if (type.equals(ListConstant.SESSION)) {
            mScheduleData = (ScheduleData) mperson;
        }

    }

    /**
     * Ids of all variables used in layout
     */
    @Override
    public void initIds() {
        image = (ImageView) findViewById(R.id.image);
        name = (TextView) findViewById(R.id.name);
        title = (TextView) findViewById(R.id.title);
        company = (TextView) findViewById(R.id.company);

        // Header buttons
        chatBtn = (Button) findViewById(R.id.chatButton);
        msgBtn = (Button) findViewById(R.id.sendMessageButton);
        connectBtn = (Button) findViewById(R.id.connectBtn);


        /**
         * Contact Info Variables id
         */

        //contact info header
        infoLay = (LinearLayout) findViewById(R.id.infoLay);
        contactInfoTxt = (TextView) findViewById(R.id.contactInfoTxtId);

        // contact info layouts
        emaillayout = (RelativeLayout) findViewById(R.id.emailLayoutId);
        phoneLayout = (RelativeLayout) findViewById(R.id.phoneLayoutId);
        linkedinLayout = (RelativeLayout) findViewById(R.id.linkedinLayoutId);
        twitterlayout = (RelativeLayout) findViewById(R.id.twitterLayoutId);
        websiteLayout = (RelativeLayout) findViewById(R.id.socialLayoutId);

        // contact info fields
        email = (TextView) findViewById(R.id.att_email);
        phoneNum = (TextView) findViewById(R.id.att_primary_phone);
        linkedinId = (TextView) findViewById(R.id.att_linkedin);
        twitterId = (TextView) findViewById(R.id.att_twitter);
        website = (TextView) findViewById(R.id.att_website_id);

        // contact info separators
        emailLine = (ImageView) findViewById(R.id.line_email);
        phoneNumLine = (ImageView) findViewById(R.id.line_phone);
        linkedinLine = (ImageView) findViewById(R.id.line_linkedin);
        twitterLine = (ImageView) findViewById(R.id.line_twitter);

        /**
         * Interest Variables id
         */
        //Interest layout
        interestLay = (LinearLayout) findViewById(R.id.interestLay);

        //Interest header
        interestTxtHeader = (TextView) findViewById(R.id.interestTxtId);

        //Interest field
        interestTxt = (TextView) findViewById(R.id.interest);


        /**
         * Bio Variables ids
         */

        //Bio Layout
        bioLay = (LinearLayout) findViewById(R.id.bioLay);

        //bio header
        bioTxtHeader = (TextView) findViewById(R.id.attendeesBioText);

        //Bio field
        bioTxt = (TextView) findViewById(R.id.bioTxt);

        /**
         * Session List variables
         */
        sessionListLay = (LinearLayout) findViewById(R.id.sessionListLay);
        sessionheader = (TextView) findViewById(R.id.sessionListTxtHeader);
        sessionList = (ListView) findViewById(R.id.sessionsList);

        /**
         * Resource List variables
         */
        resourceListLay = (LinearLayout) findViewById(R.id.resourceListLay);
        resourceHeader = (TextView) findViewById(R.id.resourcelistTxtHeader);
        resourceList = (ListView) findViewById(R.id.resourceList);

        /**
         * Attendee List variables
         */
        attendeeListLay = (LinearLayout) findViewById(R.id.attendeeListLay);
        attendeeHeader = (TextView) findViewById(R.id.attendeelistTxtHeader);
        attendeeList = (ListView) findViewById(R.id.attendeeList);

        mFavBtn = (Button) findViewById(R.id.addSessionBtn);
        websiteBtn = (Button) findViewById(R.id.exbWebBtn);

        /**
         * Session Variables
         */

        sessionLay = (LinearLayout) findViewById(R.id.sessionlay);

        sessionTitle = (TextView) findViewById(R.id.sessionTitle);
        sessionDate = (TextView) findViewById(R.id.sessionDate);
        sessionTime = (TextView) findViewById(R.id.sessionTime);
        sessionLocation = (TextView) findViewById(R.id.sessionLocation);
        maxAttendeeLimit = (TextView) findViewById(R.id.sessionAttendeeLimit);
        mSessionBtnsLay = (RelativeLayout) findViewById(R.id.sessionBtnsLay);

        mFavSessionBtn = (Button) findViewById(R.id.addFavSessionBtn);
        mSessionNotes = (Button) findViewById(R.id.shareSocialBtn);
        mSessionSocial = (Button) findViewById(R.id.noteSocialBtn);

    }

    @Override
    public void setListeners() {

        email.setOnClickListener(this);
        phoneNum.setOnClickListener(this);
        linkedinId.setOnClickListener(this);
        twitterId.setOnClickListener(this);
        website.setOnClickListener(this);

        switch (type) {
            case "attendee":
                connectBtn.setOnClickListener(this);
                msgBtn.setOnClickListener(this);
                break;

            case "exhibitor":
                websiteBtn.setOnClickListener(this);
                mFavBtn.setOnClickListener(this);
                break;

            case ListConstant.SESSION:
                mFavSessionBtn.setOnClickListener(this);
                break;
        }
    }

    @Override
    public ListDetailPresenter setPresenter() {
        switch (type) {
            case "attendee":
                return new ListDetailPresenter(new AttendeesData(), this, ListDetailActivity.this);

            case "speaker":
                return new ListDetailPresenter(new SpeakerData(), this, ListDetailActivity.this);

            case "exhibitor":
                return new ListDetailPresenter(new ExhibitorData(), this, ListDetailActivity.this);

            case ListConstant.SESSION:
                return new ListDetailPresenter(new ScheduleData(), this, ListDetailActivity.this);

            default:
                return null;
        }
    }

    @Override
    public int getLayout() {
        return R.layout.layout_list_detail;
    }

    @Override
    public void setUp() {
        handleRightBtn(false, null);
        getData();
        registerListeners();
    }

    @Override
    public void showProgress(String str) {
        showProgressDialog(str);
    }

    @Override
    public void hideProgress() {
        hideProgressDialog();
    }

    @Override
    public void showImage(String url) {
        image.setVisibility(View.VISIBLE);
        ImageLoader.getInstance().displayImage(url, image, Utility.getDisplayOptionsAttendee());
    }

    @Override
    public void showName(String nameStr) {
        name.setVisibility(View.VISIBLE);
        name.setText(nameStr);
    }

    @Override
    public void showTitle(String titleStr) {
        if (type.equals(ListConstant.SESSION)) {
            sessionTitle.setVisibility(View.VISIBLE);
            sessionTitle.setText(titleStr);
        } else {
            title.setVisibility(View.VISIBLE);
            title.setText(titleStr);
        }
    }

    @Override
    public void showCompany(String companyStr) {
        company.setVisibility(View.VISIBLE);
        company.setText(companyStr);
    }

    @Override
    public void showConnectBtn() {
        connectBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void showChatBtn() {
        chatBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessageBtn() {
        msgBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void showConnectInfo(String localeStr) {
        infoLay.setVisibility(View.VISIBLE);
        contactInfoTxt.setText(localeStr);
    }

    @Override
    public void showEmail(String emailStr) {
        emaillayout.setVisibility(View.VISIBLE);
        email.setText(emailStr);
        emailLine.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPhone(String phone) {
        phoneLayout.setVisibility(View.VISIBLE);
        phoneNum.setText(phone);
        phoneNumLine.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLinkedin(String linkedinIdStr) {
        linkedinLayout.setVisibility(View.VISIBLE);
        linkedinLine.setVisibility(View.VISIBLE);
        linkedinId.setText("Linkedin Profile");
    }

    @Override
    public void showTwitter(String twitterStr) {
        twitterlayout.setVisibility(View.VISIBLE);
        twitterLine.setVisibility(View.VISIBLE);
        twitterId.setText("Twitter Profile");
    }

    @Override
    public void showWebSite(String websiteStr) {
        websiteLayout.setVisibility(View.VISIBLE);
        website.setText("Website");
    }

    @Override
    public void showInterestLay(String locale) {
        interestLay.setVisibility(View.VISIBLE);
        interestTxtHeader.setText(locale);
    }

    @Override
    public void showIntersts(String interestStr) {
        interestTxt.setText(interestStr);
    }

    @Override
    public void showBioLay(String locale) {
        bioLay.setVisibility(View.VISIBLE);
        bioTxtHeader.setText(locale);
    }

    @Override
    public void showBio(String bioStr) {
        bioTxt.setText(bioStr);
    }

    @Override
    public void showSessionList(String headerLocale, List<ScheduleData> mList) {
        sessionListLay.setVisibility(View.VISIBLE);
        sessionheader.setText(headerLocale);

        SessionAdapter mAdapter = new SessionAdapter(this);
        mAdapter.setShowHeaders(false);
        mAdapter.addAll(mList);
        sessionList.setAdapter(mAdapter);
        Utility.setListViewHeightBasedOnChildren(sessionList);

        sessionList.setOnItemClickListener(sessionListItemClick);

    }

    @Override
    public void showResourceList(String headerLocale, List<LogisticsData> mList) {

        resourceListLay.setVisibility(View.VISIBLE);
        resourceHeader.setText(headerLocale);

        LogisticsAdapter mAdapter = null;

        switch (type) {
            case "attendee":
                mAdapter = new LogisticsAdapter(this, new AttendeesData());
                break;

            case "speaker":
                mAdapter = new LogisticsAdapter(this, new SpeakerData());
                break;

            case "exhibitor":
                mAdapter = new LogisticsAdapter(this, new ExhibitorData());
                break;

            case ListConstant.SESSION:
                mAdapter = new LogisticsAdapter(this, new ScheduleData());
                break;

        }

        mAdapter.addAll(mList);
        resourceList.setAdapter(mAdapter);
        Utility.setListViewHeightBasedOnChildren(resourceList);
        resourceList.setOnItemClickListener(resourceListItemClick);

    }

    @Override
    public void showAttendeeList(String headerLocale, List<AttendeesData> mList) {

        attendeeListLay.setVisibility(View.VISIBLE);
        attendeeHeader.setText(headerLocale);

        AttendeeListAdapter mAdapter = new AttendeeListAdapter(this);
        mAdapter.addAll(mList);
        attendeeList.setAdapter(mAdapter);
        Utility.setListViewHeightBasedOnChildren(attendeeList);
        attendeeList.setOnItemClickListener(attendeeListItemClick);

    }

    @Override
    public void askUserToConfirmSendRequest() {
        askSendRequestConfirmationDialog();
    }

    @Override
    public void onContactRequestSent() {
        showSendRequestSentConfirmation();
    }

    @Override
    public void onFailure(String msg) {
        Toast.makeText(ListDetailActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginRequiredForContactRequest() {
        Intent loginPageIntent = new Intent(ListDetailActivity.this, LoginActivity.class);
        loginPageIntent.putExtra("from", "ListDetailActivity");
        loginPageIntent.putExtra("isForConnectBtn", true);
        startActivityForResult(loginPageIntent, CONNECT_REQUEST);
        SlideOut.getInstance().startAnimation(this);
    }

    @Override
    public void loginRequiredForMessage() {
        AttendeesData mData = (AttendeesData) mperson;
        Intent mIntent = new Intent(ListDetailActivity.this, LoginActivity.class);
        mIntent.putExtra("from", "ListDetailActivity");
        mIntent.putExtra("isForMessageBtn", true);
        mIntent.putExtra("attendee_id", mData.getAttendee_id());
        startActivity(mIntent);
        SlideOut.getInstance().startAnimation(this);
    }

    @Override
    public void goToMessageScreen() {
        AttendeesData mData = (AttendeesData) mperson;
        Intent mIntent = new Intent(ListDetailActivity.this, SendMessageActivity.class);
        mIntent.putExtra("attendee_id", mData.getAttendee_id());
        startActivity(mIntent);
        SlideOut.getInstance().startAnimation(this);
    }

    @Override
    public void doPhoneCall(String number) {

        switch (type) {
            case "attendee":
                handlePhoneClickForAttendee(number);
                break;
            default:
                handlePhoneClickForOthers(number);
                break;
        }
    }

    @Override
    public void sendEmail(String emailAddress) {
        Service emailService = new Service(new EmailService());
        emailService.sendMessage(emailAddress, ListDetailActivity.this, null);
    }

    @Override
    public void openWebLink(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    public void showFavBtn(boolean flag) {
        mFavBtn.setVisibility(View.VISIBLE);
        doFavBtnAction(flag);
    }

    @Override
    public void showWebSiteBtn(String locale) {
        websiteBtn.setVisibility(View.VISIBLE);
        websiteBtn.setText(locale);
    }

    @Override
    public void doFavBtnAction(boolean flag) {

        int drawable = flag ? R.drawable.btn_addtoschedule_on_detail : R.drawable.btn_addtoschedule_off_detail;
        if (type.equals(ListConstant.SESSION)) {
            mFavSessionBtn.setBackgroundResource(drawable);
        } else {
            mFavBtn.setBackgroundResource(drawable);
        }
    }

    @Override
    public void checkForPermission() {
        Permissions.getInstance().checkCallPermission(this).askForPermissions(this, CALL_PERMISSION);
    }

    @Override
    public void setHeadersColors(String colors) {
        contactInfoTxt.setBackgroundColor(Color.parseColor(colors));
        interestTxtHeader.setBackgroundColor(Color.parseColor(colors));
        bioTxtHeader.setBackgroundColor(Color.parseColor(colors));
        sessionheader.setBackgroundColor(Color.parseColor(colors));
        resourceHeader.setBackgroundColor(Color.parseColor(colors));
        attendeeHeader.setBackgroundColor(Color.parseColor(colors));
    }

    @Override
    public void showSessionHeader() {
        sessionLay.setVisibility(View.VISIBLE);
        mSessionBtnsLay.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSessionDate(String date) {
        sessionDate.setVisibility(View.VISIBLE);
        sessionDate.setText(date);
    }

    @Override
    public void showSessionTime(String time) {
        sessionTime.setVisibility(View.VISIBLE);
        sessionTime.setText(time);
    }

    @Override
    public void showSessionLocation(String location) {
        sessionLocation.setVisibility(View.VISIBLE);
        sessionLocation.setText(location);
    }

    @Override
    public void showMaxAttendee(String maxAttendee) {
        maxAttendeeLimit.setVisibility(View.VISIBLE);
        maxAttendeeLimit.setText(maxAttendee);
    }

    @Override
    public void showSpeakerList(List<SpeakerData> mList, String headerLocale) {
        attendeeListLay.setVisibility(View.VISIBLE);
        attendeeHeader.setText(headerLocale);

        SpeakerAdapter mAdapter = new SpeakerAdapter(this);
        mAdapter.addAll(mList);
        attendeeList.setAdapter(mAdapter);
        Utility.setListViewHeightBasedOnChildren(attendeeList);
        attendeeList.setOnItemClickListener(speakerListItemClick);

    }

    @Override
    public void showNotesButton() {
        mSessionNotes.setVisibility(View.VISIBLE);
        mSessionNotes.setOnClickListener(this);
    }

    @Override
    public void showSocialButton() {
        mSessionSocial.setVisibility(View.VISIBLE);
        mSessionSocial.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.connectBtn:
                getPresenter().sendContactRequest(id, false);
                break;

            case R.id.sendMessageButton:
                getPresenter().sendMessage();
                break;

            case R.id.chatButton:
                break;

            case R.id.att_email:
                getPresenter().doEmail();
                break;

            case R.id.att_primary_phone:
                getPresenter().doCall();
                break;

            case R.id.att_linkedin:
                getPresenter().openLinks("linkedin");
                break;

            case R.id.att_twitter:
                getPresenter().openLinks("twitter");
                break;

            case R.id.att_website_id:
                getPresenter().openLinks("website");
                break;

            case R.id.addFavSessionBtn:
            case R.id.addSessionBtn:

                if (type.equals(ListConstant.SESSION)) {

                    if (!ScheduleHelper.isLoginRequiredToLike(this, ScheduleTrackListActivity.class)) {

                        ClickHandler mAddScheduleToCalendar = () -> {
                            mScheduleData.setCalenderAddId(ScheduleHelper.saveToCalendar(this, mScheduleData));
                        };

                        ClickHandler mRemoveScheduleFromCalendar = () -> {
                            ScheduleHelper.removeFromCalendar(this, mScheduleData);
                        };

                        ClickHandler mAddSchedule = () -> {
                            mScheduleData.setSessionFav(true);
                            BaseActivity.CustomDialog.getDialog(this, mAddScheduleToCalendar).show(this.getString(R.string.add_schedule_to_calendar));
                            getPresenter().doFav(id);
                        };

                        ClickHandler mRemoveSchedule = () -> {
                            mScheduleData.setSessionFav(false);
                            getPresenter().doFav(id);
                            if (mScheduleData.getCalenderAddId() != null) {
                                BaseActivity.CustomDialog.getDialog(this, mRemoveScheduleFromCalendar).singledBtnDialog(true).show(this.getString(R.string.remove_schedule_from_calendar));
                            }
                        };

                        if (mScheduleData.isSessionFav()) {
                            BaseActivity.CustomDialog.getDialog(this, mRemoveSchedule).show(this.getString(R.string.remove_schedule));
                        } else {
                            BaseActivity.CustomDialog.getDialog(this, mAddSchedule).show(this.getString(R.string.add_schedule));
                        }
                    }
                } else {
                    getPresenter().doFav(id);
                }
                break;

            case R.id.exbWebBtn:
                getPresenter().openLinks("websiteBtn");
                break;

            case R.id.shareSocialBtn:
                Intent intent = new Intent(this, SocialActivity.class);
                intent.putExtra("session_name", mScheduleData.getName());
                startActivity(intent);
                SlideOut.getInstance().startAnimation(this);
                break;

            case R.id.noteSocialBtn:

                Intent mIntent;
                if (Preferences.getInstances(this).getUserInfo() == null) {
                    mIntent = new Intent(this, LoginActivity.class);
                    mIntent.putExtra("from", ListConstant.SESSION);
                } else {
                    mIntent = new Intent(this, NotesDetailsActivity.class);
                    if (type.equals(ListConstant.SESSION)) {
                        mIntent.putExtra("type", ListConstant.SESSION);
                    } else if (type.equals(ListConstant.EXHIBITOR)) {
                        mIntent.putExtra("type", ListConstant.EXHIBITOR);
                    }
                }

                if (type.equals(ListConstant.SESSION)) {
                    mIntent.putExtra("id", mScheduleData.getSession_id());
                }

                startActivity(mIntent);
                SlideOut.getInstance().startAnimation(this);

                break;

        }

    }

    @Override
    public void handleRightBtnClick() {
    }

    @Override
    public String getScreenName() {
        switch (type) {
            case "attendee":
                return "ATTENDEE_DETAIL_VIEW";

            case "speaker":
                return "SPEAKER_DETAIL_VIEW";

            case "exhibitor":
                return "EXHIBITOR_VIEW";
        }
        return null;
    }

    @Override
    public String getBannerName() {
        return null;
    }

    public void askSendRequestConfirmationDialog() {
        CustomDialog.getDialog("Submit", this, () -> getPresenter().sendContactRequest(id, true)).show(getString(R.string.send_info_request));
    }

    public void showSendRequestSentConfirmation() {

        CustomDialog.getDialog(this, () -> {
        }).show(getString(R.string.contact_sent));

    }

    private void handlePhoneClickForOthers(final String number) {

        ClickHandler mYesBtnClick = () -> {
            if (Permissions.getInstance().hasCallPermission(ListDetailActivity.this)) {
                try {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                    startActivity(intent);
                } catch (Exception e) {
                }
            } else {
                Permissions.getInstance().checkCallPermission(ListDetailActivity.this).askForPermissions(ListDetailActivity.this, CALL_PERMISSION);
            }
        };

        CustomDialog.getDialog(this, mYesBtnClick).show("Call" + ": " + number + "?");

    }

    private void handlePhoneClickForAttendee(final String number) {

        final Dialog adb = new Dialog(ListDetailActivity.this);
        adb.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        adb.requestWindowFeature(Window.FEATURE_NO_TITLE);
        adb.setTitle(null);
        adb.setCancelable(true);
        adb.show();

        LinearLayout linear = new LinearLayout(ListDetailActivity.this);
        linear.setBackgroundResource(R.drawable.share_dialog_bg);
        linear.setOrientation(LinearLayout.VERTICAL);
        linear.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linear.setPadding(0, 10, 0, 10);

        TextView txtTitle = new TextView(ListDetailActivity.this);
        txtTitle.setBackgroundColor(Color.parseColor("#FFFFFF"));
        txtTitle.setGravity(Gravity.CENTER);
        txtTitle.setTextColor(Color.LTGRAY);
        txtTitle.setTextSize(16);
        txtTitle.setWidth(600);
        txtTitle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtTitle.setPadding(5, 5, 5, 5);
        txtTitle.setText(number);
        linear.addView(txtTitle);

        View v = new View(ListDetailActivity.this);
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
        v.setBackgroundColor(Color.GRAY);
        linear.addView(v);

        Button call = new Button(ListDetailActivity.this);
        call.setBackgroundColor(Color.parseColor("#FFFFFF"));
        call.setTextColor(Color.BLUE);
        call.setTextSize(20);
        call.setWidth(600);
        call.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        call.setPadding(5, 10, 5, 10);
        call.setText("Call");

        linear.addView(call);

        call.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                adb.dismiss();

                if (Permissions.getInstance().hasCallPermission(ListDetailActivity.this)) {
                    try {
                        String Numb = number;
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Numb));
                        startActivity(intent);
                    } catch (Exception e) {
                    }
                } else {
                    Permissions.getInstance().checkCallPermission(ListDetailActivity.this).askForPermissions(ListDetailActivity.this, CALL_PERMISSION);
                }
            }
        });

        v = new View(ListDetailActivity.this);
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
        v.setBackgroundColor(Color.GRAY);
        linear.addView(v);

        Button msg = new Button(ListDetailActivity.this);
        msg.setBackgroundColor(Color.parseColor("#FFFFFF"));
        msg.setTextColor(Color.BLUE);
        msg.setTextSize(20);
        msg.setWidth(600);
        msg.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        msg.setPadding(5, 10, 5, 10);
        msg.setText("Send Text Message");

        linear.addView(msg);

        msg.setOnClickListener(view -> {
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
            smsIntent.addCategory(Intent.CATEGORY_DEFAULT);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.setData(Uri.parse("sms:" + number));
            startActivity(smsIntent);
            adb.dismiss();
        });


        /**
         * Cancel button
         */
        LinearLayout linear2 = new LinearLayout(ListDetailActivity.this);
        linear2.setOrientation(LinearLayout.VERTICAL);
        linear2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linear2.setPadding(0, 10, 0, 10);

        Button cancel = new Button(ListDetailActivity.this);
        cancel.setBackgroundResource(R.drawable.share_dialog_bg);
        cancel.setTextColor(Color.BLUE);
        cancel.setTextSize(20);
        cancel.setWidth(600);
        cancel.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        cancel.setPadding(5, 10, 5, 10);
        cancel.setText("Cancel");
        linear2.addView(cancel);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                adb.dismiss();
            }
        });

        LinearLayout parent_linear = new LinearLayout(ListDetailActivity.this);
        parent_linear.setOrientation(LinearLayout.VERTICAL);
        parent_linear.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        parent_linear.addView(linear);
        parent_linear.addView(linear2);

        Animation slideUpIn;
        slideUpIn = AnimationUtils.loadAnimation(ListDetailActivity.this, R.anim.slide_in_bottom);
        parent_linear.startAnimation(slideUpIn);

        adb.setContentView(parent_linear);

        // set the dialog at bottom of screen
        Window window = adb.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);
        adb.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CONNECT_REQUEST && resultCode == RESULT_OK) {
            getPresenter().sendContactRequest(id, false);
        } else if (requestCode == VIEW_DOC && resultCode == RESULT_OK) {

            Bundle mBundle = data.getExtras();
            LogisticsData mDatat = (LogisticsData) mBundle.getSerializable("data");

            DocumentController<ScheduleData, LogisticsData> mController = new DocumentController<>(new ScheduleData(), new LogisticsData(), this);
            mController.viewDoc(mDatat);
        } else if (requestCode == DOWNLODA_DOC && resultCode == RESULT_OK) {

            Bundle mBundle = data.getExtras();
            LogisticsData mDatat = (LogisticsData) mBundle.getSerializable("data");

            DocumentController<ScheduleData, LogisticsData> mController = new DocumentController<>(new ScheduleData(), new LogisticsData(), this);
            mController.downloadResource(mDatat);

        } else if (requestCode == URL_DOC && resultCode == RESULT_OK) {
            Bundle mBundle = data.getExtras();
            LogisticsData mDatat = (LogisticsData) mBundle.getSerializable("data");

            DocumentController<ScheduleData, LogisticsData> mController = new DocumentController<>(new ScheduleData(), new LogisticsData(), this);
            mController.handleDocument(mDatat);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().validateData(mperson);
    }


}
