package com.grupio.attendee;

import android.content.Context;

import com.grupio.data.AttendeesData;
import com.grupio.data.LogisticsData;
import com.grupio.data.ScheduleData;
import com.grupio.data.SpeakerData;
import com.grupio.interfaces.Person;

import java.util.List;

/**
 * Created by JSN on 1/8/16.
 */
public class ListDetailPresenter<T extends Person> implements DetailPresenterInter, ListDetailControllerInter.OnValidationComplete {

    private ListDetailController mController;
    private DetailViewInter mListener;

    public ListDetailPresenter(T type, DetailViewInter mListener, Context mContext) {
        this.mListener = mListener;
        mController = new ListDetailController(type, mContext);
    }

    @Override
    public void validateData(Person mPerson) {
        mController.validateDetailData(mPerson, this);
    }

    @Override
    public void sendContactRequest(String id, boolean hasPermission) {

        if (hasPermission && mListener != null) {
            mListener.showProgress("Please wait...");
        }

        mController.sendContactRequest(id, hasPermission, this);
    }

    @Override
    public void sendMessage() {
        mController.sendMessage(this);
    }

    @Override
    public void doCall() {
        mController.doCall(this);
    }

    @Override
    public void doEmail() {
        mController.doEmail(this);
    }

    @Override
    public void openLinks(String type) {
        mController.openLinks(type, this);
    }

    @Override
    public void doFav(String id) {
        mController.doFav(id, this);
    }

    @Override
    public void onImageValidated(String urlStr) {
        if (mListener != null) {
            mListener.showImage(urlStr);
        }
    }

    @Override
    public void onNameValidated(String nameStr) {
        if (mListener != null) {
            mListener.showName(nameStr);
        }
    }

    @Override
    public void onTitleValidated(String titleStr) {
        if (mListener != null) {
            mListener.showTitle(titleStr);
        }
    }

    @Override
    public void onCompanyValidated(String companyStr) {
        if (mListener != null) {
            mListener.showCompany(companyStr);
        }
    }

    @Override
    public void onConnectBtnValidated(boolean flag) {
        if (mListener != null) {
            mListener.showConnectBtn();
        }
    }

    @Override
    public void onChatBtnValidated(boolean flag) {
        if (mListener != null) {
            mListener.showChatBtn();
        }
    }

    @Override
    public void onPressenceValidated(boolean flag) {
        if (mListener != null) {

        }
    }

    @Override
    public void onMessageBtnValidated(boolean flag) {
        if (mListener != null) {
            mListener.showMessageBtn();
        }
    }

    @Override
    public void onContactInfoValidated(boolean flag, String locale) {
        if (mListener != null) {
            mListener.showConnectInfo(locale);
        }
    }

    @Override
    public void onFavBtnValidated(boolean flag) {
        if (mListener != null) {
            mListener.showFavBtn(flag);
        }
    }

    @Override
    public void onWebsiteBtnValidated(boolean flag, String locale) {
        if (mListener != null) {
            mListener.showWebSiteBtn(locale);
        }
    }

    @Override
    public void onEmailValidated(String emailStr) {
        if (mListener != null) {
            mListener.showEmail(emailStr);
        }
    }

    @Override
    public void onPhoneVaidated(String phoneStr) {
        if (mListener != null) {
            mListener.showPhone(phoneStr);
        }
    }

    @Override
    public void onLinkedinValidated(String linkedinStr) {
        if (mListener != null) {
            mListener.showLinkedin(linkedinStr);
        }
    }

    @Override
    public void onTwitterValidated(String twitterStr) {
        if (mListener != null) {
            mListener.showTwitter(twitterStr);
        }
    }

    @Override
    public void onWebSiteValidated(String websiteStr) {
        if (mListener != null) {
            mListener.showWebSite(websiteStr);
        }
    }

    @Override
    public void onInterestsValidated(String interestStr, String headerLocale) {
        if (mListener != null) {
            mListener.showInterestLay(headerLocale);
            mListener.showIntersts(interestStr);
        }
    }

    @Override
    public void onBiovalidated(String bioStr, String headerLocale) {
        if (mListener != null) {
            mListener.showBioLay(headerLocale);
            mListener.showBio(bioStr);
        }
    }

    @Override
    public void onValidatedSessionList(String headerlocale, List<ScheduleData> mData) {
        if (mListener != null) {
            mListener.showSessionList(headerlocale, mData);
        }
    }

    @Override
    public void onValidatedResourceList(String headerlocale, List<LogisticsData> mList) {
        if (mListener != null) {
            mListener.showResourceList(headerlocale, mList);
        }
    }

    @Override
    public void onValidatedAttendeeList(String headerLocale, List<AttendeesData> mList) {
        if (mListener != null) {
            mListener.showAttendeeList(headerLocale, mList);
        }
    }

    @Override
    public void onContactRequestSent() {
        if (mListener != null) {
            mListener.hideProgress();
            mListener.onContactRequestSent();
        }
    }

    @Override
    public void onFailure(String message) {
        mListener.hideProgress();
        mListener.onFailure(message);
    }

    @Override
    public void onLoginRequiredForContactRequest() {
        if (mListener != null) {
            mListener.loginRequiredForContactRequest();
        }
    }

    @Override
    public void onLoginRequiredForMessage() {
        if (mListener != null) {
            mListener.loginRequiredForMessage();
        }
    }

    @Override
    public void onValidatedForMessage() {
        if (mListener != null) {
            mListener.goToMessageScreen();
        }
    }

    @Override
    public void onCallHandled(String num) {
        if (mListener != null) {
            mListener.doPhoneCall(num);
        }
    }

    @Override
    public void onEmail(String emailAdd) {
        if (mListener != null) {
            mListener.sendEmail(emailAdd);
        }
    }

    @Override
    public void onLinkOpen(String link) {
        if (mListener != null) {
            mListener.openWebLink(link);
        }
    }

    @Override
    public void onFavDone(boolean flag) {
        if (mListener != null) {
            mListener.doFavBtnAction(flag);
        }
    }

    @Override
    public void onEventColorFetch(String color) {
        mListener.setHeadersColors(color);
    }

    @Override
    public void onSessionHeaderValiation() {
        mListener.showSessionHeader();
    }

    @Override
    public void onDateValidation(String date) {
        mListener.showSessionDate(date);
    }

    @Override
    public void onTimeValidation(String time) {
        mListener.showSessionTime(time);
    }

    @Override
    public void onLocationValidation(String location) {
        mListener.showSessionLocation(location);
    }

    @Override
    public void onMaxAttendeeValidation(String maxAttendee) {
        mListener.showMaxAttendee(maxAttendee);
    }

    @Override
    public void onSpeakerListValidation(List<SpeakerData> mList, String headerLocale) {
        mListener.showSpeakerList(mList, headerLocale);
    }


    @Override
    public void onPermissionAskedForSendContactRequest() {
        if (mListener != null) {
            mListener.askUserToConfirmSendRequest();
        }
    }
}
