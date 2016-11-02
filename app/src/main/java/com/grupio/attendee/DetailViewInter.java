package com.grupio.attendee;

import com.grupio.data.AttendeesData;
import com.grupio.data.LogisticsData;
import com.grupio.data.ScheduleData;

import java.util.List;

/**
 * Created by JSN on 1/8/16.
 */
public interface DetailViewInter {

    void showProgress(String str);

    void hideProgress();

    void showImage(String url);

    void showName(String name);

    void showTitle(String name);

    void showCompany(String name);

    void showConnectBtn();

    void showChatBtn();

    void showMessageBtn();

    void showConnectInfo(String localeStr);

    void showEmail(String email);

    void showPhone(String phone);

    void showLinkedin(String linkedinId);

    void showTwitter(String twitter);

    void showWebSite(String website);

    void showInterestLay(String locale);

    void showIntersts(String interest);

    void showBioLay(String locale);

    void showBio(String bio);

    void showSessionList(String headerLocale, List<ScheduleData> mList);

    void showResourceList(String headerLocale, List<LogisticsData> mList);

    void showAttendeeList(String headerLocale, List<AttendeesData> mList);

    void askUserToConfirmSendRequest();

    void onContactRequestSent();

    void onFailure(String msg);

    void loginRequiredForContactRequest();

    void loginRequiredForMessage();

    void goToMessageScreen();

    void doPhoneCall(String number);

    void sendEmail(String emailAddress);

    void openWebLink(String url);

    void showFavBtn(boolean flag);

    void showWebSiteBtn(String locale);

    void doFavBtnAction(boolean flag);

    void checkForPermission();

    void setHeadersColors(String colors);

}
