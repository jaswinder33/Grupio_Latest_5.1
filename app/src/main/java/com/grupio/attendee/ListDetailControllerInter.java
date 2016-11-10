package com.grupio.attendee;

import com.grupio.data.AttendeesData;
import com.grupio.data.LogisticsData;
import com.grupio.data.ScheduleData;
import com.grupio.interfaces.Person;

import java.util.List;

/**
 * Created by JSN on 1/8/16.
 */
public interface ListDetailControllerInter {

    void validateDetailData(Person data, OnValidationComplete mListener);

    void sendContactRequest(String id, boolean hasPermission, OnValidationComplete mListener);

    void sendMessage(OnValidationComplete mListener);

    void doCall(OnValidationComplete mListener);

    void doEmail(OnValidationComplete mListener);

    void openLinks(String type, OnValidationComplete mListener);

    void doFav(String id, OnValidationComplete mListener);

    interface OnValidationComplete {

        void onImageValidated(String urlStr);

        void onNameValidated(String nameStr);

        void onTitleValidated(String titleStr);

        void onCompanyValidated(String companyStr);

        void onConnectBtnValidated(boolean flag);

        void onChatBtnValidated(boolean flag);

        void onPressenceValidated(boolean flag);

        void onMessageBtnValidated(boolean flag);

        void onContactInfoValidated(boolean flag, String locale);

        void onFavBtnValidated(boolean flag);

        void onWebsiteBtnValidated(boolean flag, String locale);

        void onEmailValidated(String emailStr);

        void onPhoneVaidated(String phoneStr);

        void onLinkedinValidated(String linkedinStr);

        void onTwitterValidated(String linkedinStr);

        void onWebSiteValidated(String websiteStr);

        void onInterestsValidated(String interestStr, String headerLocale);

        void onBiovalidated(String bioStr, String headerLocale);

        void onValidatedSessionList(String headerlocale, List<ScheduleData> mData);

        void onValidatedResourceList(String headerlocale, List<LogisticsData> mList);

        void onValidatedAttendeeList(String headerLocale, List<AttendeesData> mList);

        void onContactRequestSent();

        void onFailure(String message);

        void onLoginRequiredForContactRequest();

        void onPermissionAskedForSendContactRequest();

        void onLoginRequiredForMessage();

        void onValidatedForMessage();

        void onCallHandled(String num);

        void onEmail(String emailAdd);

        void onLinkOpen(String link);

        void onFavDone(boolean flag);

        void onEventColorFetch(String color);

        void onSessionHeaderValiation();

        void onDateValidation(String date);

        void onTimeValidation(String time);

        void onLocationValidation(String location);

        void onMaxAttendeeValidation(String maxAttendee);


    }

}
