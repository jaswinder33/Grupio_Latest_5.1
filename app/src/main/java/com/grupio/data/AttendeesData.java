package com.grupio.data;

import com.grupio.interfaces.Person;

import java.util.ArrayList;
import java.util.List;

public class AttendeesData implements Person {

    public String mapListOfAttendeeAsString = "";
    public String sessionsAsString = "";
    /*
     * For grupio chat
     *
     */
    public String isFriend = "";
    public String requestText = "";
    public String presence_status = "offline";
    public String channelId = "";
    public boolean isExistInFriendTable = false;
    /**
     * Whether request sent or received
     */

    public String request = "";
    String attendee_id;
    String first_name = "";
    String last_name = "";
    String title = "";
    String company = "";
    String email = "";
    String type = "";
    String primary_phone = "";
    String secondary_phone = "";
    String image = "";
    String large_image = "";
    String bio = "";
    String linkedin = "";
    String twitter_id = "";
    String website = "";
    String keywords = "";
    String category = "";
    String intrests = "";
    //for enable the message button in Attendee Details page...
    String enable_messaging = "";
    //for enable the Contacts button in Attendee Details page...
    String enable_contacts = "";
    String hide_contact_info = "";
    String datatype = "";
    String sessionId[];
    List<mapList> mapListOfAttendee;
    boolean isAttendeeSelected = false;
    ArrayList<ScheduleData> sessionData = new ArrayList<ScheduleData>();
    ArrayList<MeetingData> meetingData = new ArrayList<MeetingData>();
    Boolean alreadyAdded = false;
    String enable_match = "";

    public String getIntrests() {
        return intrests;
    }

    public void setIntrests(String intrests) {
        this.intrests = intrests;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEnable_match() {
        return enable_match;
    }

    public void setEnable_match(String enable_match) {
        this.enable_match = enable_match;
    }


    public Boolean getAlreadyAdded() {
        return alreadyAdded;
    }

    public void setAlreadyAdded(Boolean alreadyAdded) {
        this.alreadyAdded = alreadyAdded;
    }

    public ArrayList<MeetingData> getMeetingData() {
        return meetingData;
    }

    public void setMeetingData(ArrayList<MeetingData> meetingData) {
        this.meetingData = meetingData;
    }

    public ArrayList<ScheduleData> getSessionData() {
        return sessionData;
    }

    public void setSessionData(ArrayList<ScheduleData> sessionData) {
        this.sessionData = sessionData;
    }

    public String getHide_contact_info() {
        return hide_contact_info;
    }

    public void setHide_contact_info(String hide_contact_info) {
        this.hide_contact_info = hide_contact_info;
    }

    public String getLarge_image() {
        return large_image;
    }

    public void setLarge_image(String large_image) {
        this.large_image = large_image;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public List<mapList> getMapListOfAttendee() {
        return mapListOfAttendee;
    }

    public void setMapListOfAttendee(List<mapList> mapListOfAttendee) {
        this.mapListOfAttendee = mapListOfAttendee;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String[] getSessionId() {
        return sessionId;
    }

    public void setSessionId(String[] sessionId) {
        this.sessionId = sessionId;
    }

    public String getEnable_messaging() {
        return enable_messaging;
    }

    public void setEnable_messaging(String enable_messaging) {
        this.enable_messaging = enable_messaging;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAttendee_id() {
        return attendee_id;
    }

    public void setAttendee_id(String attendee_id) {
        this.attendee_id = attendee_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrimary_phone() {
        return primary_phone;
    }

    public void setPrimary_phone(String primary_phone) {
        this.primary_phone = primary_phone;
    }

    public String getSecondary_phone() {
        return secondary_phone;
    }

    public void setSecondary_phone(String secondary_phone) {
        this.secondary_phone = secondary_phone;
    }


    public String getTwitter_id() {
        return twitter_id;
    }

    public void setTwitter_id(String twitter_id) {
        this.twitter_id = twitter_id;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }


    public String getEnable_contacts() {
        return enable_contacts;
    }

    public void setEnable_contacts(String enable_contacts) {
        this.enable_contacts = enable_contacts;
    }

    public boolean isAttendeeSelected() {
        return isAttendeeSelected;
    }

    public void setAttendeeSelected(boolean attendeeSelected) {
        isAttendeeSelected = attendeeSelected;
    }


}
