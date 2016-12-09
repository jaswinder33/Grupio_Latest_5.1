package com.grupio.attendee;

import android.content.Context;
import android.text.TextUtils;

import com.grupio.R;
import com.grupio.apis.SendContactAPI;
import com.grupio.backend.DateTime;
import com.grupio.dao.AttendeeDAO;
import com.grupio.dao.EventDAO;
import com.grupio.dao.ExhibitorDAO;
import com.grupio.dao.MenuDAO;
import com.grupio.dao.SessionDAO;
import com.grupio.dao.SpeakerDAO;
import com.grupio.data.AttendeesData;
import com.grupio.data.ExhibitorData;
import com.grupio.data.LogisticsData;
import com.grupio.data.ScheduleData;
import com.grupio.data.SpeakerData;
import com.grupio.data.SponsorData;
import com.grupio.db.EventTable;
import com.grupio.helper.AttendeeProcessor;
import com.grupio.helper.ExhibitorProcessor;
import com.grupio.helper.ScheduleHelper;
import com.grupio.helper.SpeakerProcessor;
import com.grupio.helper.SponsorHelper;
import com.grupio.interfaces.Person;
import com.grupio.message.apis.APICallBack;
import com.grupio.session.Preferences;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by JSN on 1/8/16.
 */
public class ListDetailController<T extends Person> implements ListDetailControllerInter {

    private T type;
    private AttendeesData mAttendeeData;
    private SpeakerData mSpeakerData;
    private ExhibitorData mExhibitorData;
    private ScheduleData mScheduleData;
    private SponsorData mSponsorData;
    private Context mContext;

    public ListDetailController(T type, Context mContext) {
        this.type = type;
        this.mContext = mContext;
    }

    @Override
    public void validateDetailData(Person data, OnValidationComplete mListener) {
        castObj(data, mListener);
        fetchEventColor(mListener);
        validateImage(mListener);
        validateName(mListener);
        validateTitle(mListener);
        validateLocation(mListener);

        validateCompany(mListener);
        validateFavBtn(mListener);
        validateWebsiteBtn(mListener);
        validateConnectBtn(mListener);
        validateMessageBtn(mListener);
        validateChatBtn(mListener);
        validateConnectInfo(mListener);
        validateInterest(mListener);


        validateBio(mListener);
        validateSessionList(mListener);
        validateResourceList(mListener);
        validateAttendeeList(mListener);


        validateSessionDate(mListener);
        validateSessionTime(mListener);
        validateMaxAttendeeLimit(mListener);

        validateSpeakerList(mListener);

        validateShareBtn(mListener);
        validateNotesBtn(mListener);

    }


    @Override
    public void sendContactRequest(String id, boolean hasPermission, final OnValidationComplete mListener) {

        if (!hasPermission) {
            if (isLoggedIn()) {
                mListener.onPermissionAskedForSendContactRequest();
            } else {
                mListener.onLoginRequiredForContactRequest();
            }
        } else {
            sendContactRequest(id, mListener);
        }
    }

    @Override
    public void sendMessage(OnValidationComplete mListener) {
        if (isLoggedIn()) {
            mListener.onValidatedForMessage();
        } else {
            mListener.onLoginRequiredForMessage();
        }
    }

    @Override
    public void doCall(OnValidationComplete mListener) {
        if (type instanceof AttendeesData) {
            mListener.onCallHandled(mAttendeeData.getPrimary_phone());
        } else if (type instanceof SpeakerData) {
            mListener.onCallHandled(mSpeakerData.getPrimary_phone());
        } else if (type instanceof ExhibitorData) {
            mListener.onCallHandled(mExhibitorData.getContact_phone());
        } else if (type instanceof SponsorData) {
            mListener.onCallHandled(mSponsorData.phone);
        }
    }

    @Override
    public void doEmail(OnValidationComplete mListener) {
        if (type instanceof AttendeesData) {
            mListener.onEmail(mAttendeeData.getEmail());
        } else if (type instanceof SpeakerData) {
            mListener.onEmail(mSpeakerData.getEmail());
        } else if (type instanceof ExhibitorData) {
            mListener.onEmail(mExhibitorData.getContact_email());
        } else if (type instanceof SponsorData) {
            mListener.onEmail(mSponsorData.email);
        }
    }

    @Override
    public void openLinks(String typeOfLink, OnValidationComplete mListener) {
        String url = "";
        if (type instanceof AttendeesData) {
            switch (typeOfLink) {
                case "linkedin":
                    url = mAttendeeData.getLinkedin();
                    break;
                case "twitter":
                    url = mAttendeeData.getTwitter_id();
                    break;
                case "website":
                    url = mAttendeeData.getWebsite();
                    break;
            }
        } else if (type instanceof SpeakerData) {
            switch (typeOfLink) {
                case "linkedin":
                    url = mSpeakerData.getLinkedin();
                    break;
                case "twitter":
                    url = mSpeakerData.getTwitter_id();
                    break;
                case "website":
                    url = mSpeakerData.getWebsite();
                    break;
            }
        } else if (type instanceof ExhibitorData) {
            //these parameters are not present for exhibitors
            switch (typeOfLink) {
                case "websiteBtn":
                    url = mExhibitorData.getWeburl();
                    break;
            }
        } else if (type instanceof SponsorData) {
            switch (typeOfLink) {
                case "websiteBtn":
                    url = mSponsorData.webUrl;
                    break;
            }
        }

        if (!url.contains("https") && !url.contains("http")) {
            url = "https://" + url;
        }

        mListener.onLinkOpen(url);
    }

    @Override
    public void doFav(String id, OnValidationComplete mListener) {

        boolean flag = false;
        if (type instanceof ExhibitorData) {
            flag = mExhibitorData.isFav();
        } else if (type instanceof ScheduleData) {
            flag = mScheduleData.isSessionFav();
        }
        flag = !flag;
        ExhibitorDAO.getInstance(mContext).likeUnlikeExhb(id, flag ? 1 : 0);

        if (type instanceof ExhibitorData) {
            mExhibitorData.setIsFav(flag ? "1" : "0");
        } else if (type instanceof ScheduleData) {
            ScheduleHelper.addRemoveSession(flag ? "add" : "delete", mScheduleData.getSession_id(), mContext);
        }
        ListWatcher.getInstance().notifyList();
        mListener.onFavDone(flag);
    }

    public boolean isLoggedIn() {
        String userInfo = Preferences.getInstances(mContext).getUserInfo();
        return userInfo != null;
    }

    /**
     * API used to send ContactRequest to attendee
     *
     * @param id
     * @param mListener
     */
    public void sendContactRequest(String id, final OnValidationComplete mListener) {

        SendContactAPI api = new SendContactAPI(mContext, new APICallBack() {
            @Override
            public void onSuccess() {
                mListener.onContactRequestSent();
            }

            @Override
            public void onFailure(String msg) {
                mListener.onFailure(msg);
            }
        });
        api.doCall(id);
    }

    private void castObj(Person data, OnValidationComplete mListener) {
        if (type instanceof AttendeesData) {
            mAttendeeData = (AttendeesData) data;
            mAttendeeData = AttendeeDAO.getInstance(mContext).getAttendeeDetal(mAttendeeData.getAttendee_id());
        } else if (type instanceof SpeakerData) {
            mSpeakerData = (SpeakerData) data;
            mSpeakerData = SpeakerDAO.getInstance(mContext).getSpeakerDetail(mSpeakerData.getAttendee_id());
        } else if (type instanceof ExhibitorData) {
            mExhibitorData = (ExhibitorData) data;
            mExhibitorData = ExhibitorDAO.getInstance(mContext).getExhibitorDetal(mExhibitorData.getExhibitorId());
        } else if (type instanceof ScheduleData) {
            mScheduleData = (ScheduleData) data;
            mScheduleData = SessionDAO.getInstance(mContext).getSessionWithId(mScheduleData.getSession_id());
            mListener.onSessionHeaderValiation();
        } else if (type instanceof SponsorData) {
            mSponsorData = (SponsorData) data;
            mListener.onSponsorHeaderValidation();
        }
    }

    public void validateImage(OnValidationComplete mListener) {

        boolean showImage = false;

        String imageUrl = "", largeImageUrl = "";

        if (type instanceof AttendeesData) {
            showImage = EventDAO.getInstance(mContext).getValue(EventTable.HIDE_ATTENDEE_IMAGES).equalsIgnoreCase("n");
            imageUrl = mAttendeeData.getImage();
            largeImageUrl = mAttendeeData.getLarge_image();
        } else if (type instanceof SpeakerData) {
            showImage = EventDAO.getInstance(mContext).getValue(EventTable.HIDE_SPEAKER_IMAGES).equalsIgnoreCase("n");
            imageUrl = mSpeakerData.getImageURL();
            largeImageUrl = mSpeakerData.getLargeImageUrl();
        } else if (type instanceof ExhibitorData) {
            showImage = EventDAO.getInstance(mContext).getValue(EventTable.HIDE_EXHIBITOR_IMAGES).equalsIgnoreCase("n");
            imageUrl = mExhibitorData.getImage();
            largeImageUrl = mExhibitorData.getImageLarge();
        } else if (type instanceof ScheduleData) {

        } else if (type instanceof SponsorData) {
            showImage = EventDAO.getInstance(mContext).getValue(EventTable.HIDE_SPONSOR_IMAGES).equals("n");
            imageUrl = mSponsorData.url;
            largeImageUrl = mSponsorData.largeUrl;
        }

        if (showImage) {
            String url = !TextUtils.isEmpty(imageUrl) ? largeImageUrl : imageUrl;
            if (!url.contains("https") && !url.contains("http")) {
                url = "http://conf.dharanet.com/conf/v1/main" + url;
            }
            mListener.onImageValidated(url);
        }
    }

    public void validateName(OnValidationComplete mListener) {

        String firstName = "", lastName = "";

        if (type instanceof AttendeesData) {
            firstName = mAttendeeData.getFirst_name();
            lastName = mAttendeeData.getLast_name();
        } else if (type instanceof SpeakerData) {
            firstName = mSpeakerData.getFirst_name();
            lastName = mSpeakerData.getLast_name();
        } else if (type instanceof ExhibitorData) {
            firstName = mExhibitorData.getName();
        } else if (type instanceof ScheduleData) {
            firstName = mScheduleData.getName();
        } else if (type instanceof SponsorData) {
            firstName = mSponsorData.name;
        }

        String name_order = EventDAO.getInstance(mContext).getValue(EventTable.NAME_ORDER);
        if (type instanceof ExhibitorData || type instanceof ScheduleData || type instanceof SponsorData) {
            mListener.onNameValidated(firstName);
        } else {
            if (name_order.equals("firstname_lastname")) {
                mListener.onNameValidated(firstName + " " + lastName);
            } else {
                mListener.onNameValidated(lastName + ", " + firstName);
            }
        }
    }

    public void validateTitle(OnValidationComplete mListener) {
        String title = "";

        if (type instanceof AttendeesData) {
            title = mAttendeeData.getTitle();
        } else if (type instanceof SpeakerData) {
            title = mSpeakerData.getTitle();
        } else if (type instanceof ExhibitorData) {
            // no title present for exhibitors
        } else if (type instanceof ScheduleData) {
            title = mScheduleData.getName();
        }

        if (!TextUtils.isEmpty(title)) {
            mListener.onTitleValidated(title);
        }
    }

    /**
     * Used company place holder to show location in exhibitor detail
     *
     * @param mListener
     */
    private void validateLocation(OnValidationComplete mListener) {
        String location = "";

        if (type instanceof ExhibitorData) {
            location = mExhibitorData.getLocation();
        } else if (type instanceof ScheduleData) {
            location = mScheduleData.getLocation();
        }

        if (!TextUtils.isEmpty(location)) {
//            if(!LocalisationDataProcessor.BOOTH.equals("")) {
//                location = LocalisationDataProcessor.BOOTH + ":" + location;
//            }

            if (type instanceof ExhibitorData) {
                location = "Booth" + ":" + location;
                mListener.onCompanyValidated(location);
            } else if (type instanceof ScheduleData) {
                mListener.onLocationValidation("Location: " + location);
            }
        }
    }


    public void validateCompany(OnValidationComplete mListener) {
        String company = "";

        if (type instanceof AttendeesData) {
            company = mAttendeeData.getCompany();
        } else if (type instanceof SpeakerData) {
            company = mSpeakerData.getCompany();
        } else if (type instanceof ExhibitorData) {
            // no title present for exhibitors
        }

        if (!TextUtils.isEmpty(company)) {
            mListener.onCompanyValidated(company);
        }
    }

    private void validateWebsiteBtn(OnValidationComplete mListener) {

        String url = "";

        if (type instanceof ExhibitorData) {
            url = mExhibitorData.getWeburl();
        } else if (type instanceof SponsorData) {
            url = mSponsorData.webUrl;
        }
        if (!TextUtils.isEmpty(url)) {
//                mListener.onWebsiteBtnValidated(true, LocalisationDataProcessor.GO_TO_WEBSITE);
            mListener.onWebsiteBtnValidated(true, "Go To Website");
        }

    }

    private void validateFavBtn(OnValidationComplete mListener) {
        if (type instanceof ExhibitorData) {
            mListener.onFavBtnValidated(mExhibitorData.isFav());
        } else if (type instanceof ScheduleData) {
            mListener.onFavBtnValidated(mScheduleData.isSessionFav());
        }
    }

    public void validateConnectBtn(OnValidationComplete mListener) {
        if (type instanceof AttendeesData) {
            if (mAttendeeData.getEnable_match().equals("y")) {
                mListener.onConnectBtnValidated(true);
            }
        }
    }

    public void validateMessageBtn(OnValidationComplete mListener) {

        boolean isMessageEnable = MenuDAO.getInstance(mContext).checkIfMenuExists(mContext.getString(R.string.messages));

        if (type instanceof AttendeesData) {
            if (mAttendeeData.getEnable_messaging().equals("y") && isMessageEnable) {
                mListener.onMessageBtnValidated(true);
            }

        } else if (type instanceof SpeakerData) {
            if (mSpeakerData.getEnable_messaging().equals("y") && isMessageEnable) {
                mListener.onMessageBtnValidated(true);
            }
        }
    }

    public void validateChatBtn(OnValidationComplete mListener) {
        boolean isChatEnable = MenuDAO.getInstance(mContext).checkIfMenuExists(mContext.getString(R.string.chat));


        if (type instanceof AttendeesData) {

            if (isChatEnable) {

                if (Preferences.getInstances(mContext).getUserInfo() != null) {
                    mListener.onChatBtnValidated(true);
                    mListener.onPressenceValidated(true);
                }
            }
        }
    }

    public void validateConnectInfo(OnValidationComplete mListener) {

        String email = "", phone = "", twitter = "", linkedin = "", website = "";


        if (type instanceof AttendeesData) {

            if (EventDAO.getInstance(mContext).getValue(EventTable.HIDE_ATTENDEE_INFO).equalsIgnoreCase("n")) {
                email = mAttendeeData.getEmail();
                phone = mAttendeeData.getPrimary_phone();
                twitter = mAttendeeData.getTwitter_id();
                linkedin = mAttendeeData.getLinkedin();
                website = mAttendeeData.getWebsite();
            }
        } else if (type instanceof SpeakerData) {

            if (EventDAO.getInstance(mContext).getValue(EventTable.HIDE_SPEAKER_INFO).equalsIgnoreCase("n")) {
                email = mSpeakerData.getEmail();
                phone = mSpeakerData.getPrimary_phone();
                twitter = mSpeakerData.getTwitter_id();
                linkedin = mSpeakerData.getLinkedin();
                website = mSpeakerData.getWebsite();
            }
        } else if (type instanceof ExhibitorData) {
            email = mExhibitorData.getContact_email();
            phone = mExhibitorData.getContact_phone();
        } else if (type instanceof SponsorData) {
            email = mSponsorData.email;
            phone = mSponsorData.phone;
        }

        if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(phone) || !TextUtils.isEmpty(twitter) || !TextUtils.isEmpty(linkedin) || !TextUtils.isEmpty(website)) {
//            mListener.onContactInfoValidated(true, LocalisationDataProcessor.CONTACT_INFORMATION);
            mListener.onContactInfoValidated(true, "Contact Information");
        }

        if (!TextUtils.isEmpty(email)) {
            mListener.onEmailValidated(email);
        }

        if (!TextUtils.isEmpty(phone)) {
            mListener.onPhoneVaidated(phone);
        }

        if (!TextUtils.isEmpty(twitter)) {
            mListener.onTwitterValidated(twitter);
        }

        if (!TextUtils.isEmpty(linkedin)) {
            mListener.onLinkedinValidated(linkedin);
        }

        if (!TextUtils.isEmpty(website)) {
            mListener.onWebSiteValidated(website);
        }
    }

    public void validateInterest(OnValidationComplete mListener) {
        if (type instanceof AttendeesData) {
            if (!TextUtils.isEmpty(mAttendeeData.getIntrests())) {
//                mListener.onInterestsValidated(mAttendeeData.getIntrests(), LocalisationDataProcessor.INTERESTS);
                mListener.onInterestsValidated(mAttendeeData.getIntrests(), "Interest");
            }
        }
    }

    public void validateBio(OnValidationComplete mListener) {

        String text = "";
        String locale = "";

        if (type instanceof AttendeesData) {
            text = mAttendeeData.getBio();
//            locale = LocalisationDataProcessor.ATTENDEE_BIO;
            locale = "Bio";
        } else if (type instanceof SpeakerData) {
            text = mSpeakerData.getBio();
//            locale = LocalisationDataProcessor.SPEAKER_BIO;
            locale = "Bio";
        } else if (type instanceof ExhibitorData) {
            text = mExhibitorData.getDescription();
//            locale = LocalisationDataProcessor.EXHIBITORS_DESCRIPTION;
            locale = "Description";
        } else if (type instanceof ScheduleData) {
//            locale = LocalisationDataProcessor.EXHIBITORS_DESCRIPTION;
            text = mScheduleData.getSummary();
            locale = "Description";
        } else if (type instanceof SponsorData) {
            text = mSponsorData.description;
            locale = "Description";
        }
        if (!TextUtils.isEmpty(text)) {

            mListener.onBiovalidated(text, locale);
        }
    }

    public void validateSessionList(OnValidationComplete mListener) {


        List<ScheduleData> mList = new ArrayList<>();

        JSONArray ids = null;


        if (type instanceof AttendeesData) {
            try {
                ids = new JSONArray(mAttendeeData.sessionsAsString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (type instanceof SpeakerData) {
            try {
                ids = new JSONArray(mSpeakerData.getSessionListAsString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        if (ids != null && ids.length() > 0) {

            String id = "";

            for (int i = 0; i < ids.length(); i++) {

                try {
                    id = ids.getString(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mList.add(SessionDAO.getInstance(mContext).getSessionWithId(id));
            }

            if (mList.size() > 0) {
//                mListener.onValidatedSessionList(LocalisationDataProcessor.SESSIONS, mList);
                mListener.onValidatedSessionList("Sessions", mList);
            }
        }

    }

    public void validateResourceList(OnValidationComplete mListener) {
        List<LogisticsData> mList = new ArrayList<>();

        if (type instanceof AttendeesData) {

            String resourcesAsString = mAttendeeData.mapListOfAttendeeAsString;

            AttendeeProcessor ap = new AttendeeProcessor();
            mList.addAll(ap.parseResources(resourcesAsString));

        } else if (type instanceof SpeakerData) {

            String resourcesAsString = mSpeakerData.getSpeakerlinksAsString();

            SpeakerProcessor sp = new SpeakerProcessor();
            mList.addAll(sp.parseLinksJson(resourcesAsString));
        } else if (type instanceof ExhibitorData) {
            String resourceAsString = mExhibitorData.getResourceListAsSrings();
            ExhibitorProcessor ep = new ExhibitorProcessor();
            mList.addAll(ep.parseResourceList(resourceAsString));
        } else if (type instanceof ScheduleData) {
            String resourceAsString = mScheduleData.getResourceListAsString();

            ScheduleHelper scheduleHelper = new ScheduleHelper();
            mList.addAll(scheduleHelper.getSessionLinks(resourceAsString));

        } else if (type instanceof SponsorData) {
            String resources = mSponsorData.sponsorLink;

            SponsorHelper sHelper = new SponsorHelper();
            mList.addAll(sHelper.parseResourceList(resources));
        }

        if (mList.size() > 0) {
//            mListener.onValidatedResourceList(LocalisationDataProcessor.RESOURCES, mList);
            mListener.onValidatedResourceList("Resource", mList);
        }
    }

    private void validateAttendeeList(OnValidationComplete mListener) {

        List<AttendeesData> mAttendeeList = new ArrayList<>();


        List<String> mList = new ArrayList<>();

        if (type instanceof ExhibitorData) {
            String attendeeAsString = mExhibitorData.getAttendeeListAsString();
            ExhibitorProcessor ep = new ExhibitorProcessor();
            mList.addAll(ep.parseAttendeeList(attendeeAsString));
        }
//
        if (mList.size() > 0) {
            mAttendeeList.addAll(AttendeeDAO.getInstance(mContext).fetchAttendeesOfParticularids(mList));
        }
//
        if (mAttendeeList.size() > 0) {
//            mListener.onValidatedAttendeeList(LocalisationDataProcessor.COMPANY_REPRESENTATIVES, mAttendeeList);
            mListener.onValidatedAttendeeList("Company Representatives", mAttendeeList);
        }

    }

    public void fetchEventColor(OnValidationComplete mListener) {
        mListener.onEventColorFetch(EventDAO.getInstance(mContext).getValue(EventTable.COLOR_THEME));
    }

    public void validateSessionDate(OnValidationComplete mListener) {
        if (type instanceof ScheduleData) {
            String formattedDate = DateTime.getInstance().formatDate("MMM d, yyyy", mScheduleData.getStart_time());
            mListener.onDateValidation("Date: " + formattedDate);
        }
    }

    public void validateSessionTime(OnValidationComplete mListener) {

        if (type instanceof ScheduleData) {
            String startTime = DateTime.getInstance().getTime(mScheduleData.getStart_time());
            String endTime = DateTime.getInstance().getTime(mScheduleData.getEnd_time());

            mListener.onTimeValidation("Time: " + startTime + " - " + endTime);
        }

    }

    public void validateMaxAttendeeLimit(OnValidationComplete mListener) {
        if (type instanceof ScheduleData && !mScheduleData.getMaxSeatsAvailable().equals("0")) {
            mListener.onMaxAttendeeValidation(mScheduleData.getMaxSeatsAvailable());
        }

    }

    public void validateSpeakerList(OnValidationComplete mListener) {

        if (type instanceof ScheduleData) {

            List<String> mSpeakerIdList = new ArrayList<>();

            List<SpeakerData> mSpeakerList = new ArrayList<>();

            String speakers = mScheduleData.getSpeakerListAsString();
            ScheduleHelper mHelper = new ScheduleHelper();
            mSpeakerIdList.addAll(mHelper.getSpeakerList(speakers));

            for (int i = 0; i < mSpeakerIdList.size(); i++) {
                mSpeakerList.add(SpeakerDAO.getInstance(mContext).getSpeakerDetail(mSpeakerIdList.get(i)));
            }

            mSpeakerList.removeAll(Collections.singleton(null));
            if (!mSpeakerList.isEmpty()) {
                mListener.onSpeakerListValidation(mSpeakerList, "Speakers");
            }
        }
    }

    public void validateShareBtn(OnValidationComplete mListener) {

        if (type instanceof ScheduleData) {
            boolean isMenuPresent = MenuDAO.getInstance(mContext).checkIfMenuExists(mContext.getResources().getString(R.string.social));

            if (isMenuPresent) {
                mListener.showSocialBtn();
            }
        }

    }

    public void validateNotesBtn(OnValidationComplete mListener) {
        if (type instanceof ScheduleData) {
            boolean showBtn = EventDAO.getInstance(mContext).getValue(EventTable.SHOW_NOTES_BUTTON).equals("y");
            if (showBtn) {
                mListener.showNotesBtn();
            }
        }
    }


}
