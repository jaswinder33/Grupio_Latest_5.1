package com.grupio.data;


import com.grupio.interfaces.Person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class SpeakerData implements Serializable, Person {

    public String sessionId[];
    String attendee_id = "";
    String first_name = "";
    String last_name = "";
    String company = "";
    String title = "";
    String email = "";
    String type = "";
    String primary_phone = "";
    String secondary_phone = "";
    String imageURL = "";
    String largeImageUrl = "";
    String bio = "";
    String linkedin = "";
    String twitter_id = "";
    String website = "";
    String enable_contacts = "";
    String datatype = "";
    String hide_attendee = "n";
    String category = "";
    String hideContactInfo = "";
    List<LogisticsData> speakerlinks = new ArrayList();
    String sessionListAsString = "";
    String speakerlinksAsString = "";
    //for enable the message button in Attendee Details page...
    String enable_messaging = "";

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHideContactInfo() {
        return hideContactInfo;
    }

    public void setHideContactInfo(String hideContactInfo) {
        this.hideContactInfo = hideContactInfo;
    }

    public String getSessionListAsString() {
        return sessionListAsString;
    }

    public void setSessionListAsString(String sessionListAsString) {
        this.sessionListAsString = sessionListAsString;
    }

    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    public void setLargeImageUrl(String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpeakerlinksAsString() {
        return speakerlinksAsString;
    }

    public void setSpeakerlinksAsString(String speakerlinksAsString) {
        this.speakerlinksAsString = speakerlinksAsString;
    }

    public String isHideContactInfo() {
        return hideContactInfo;
    }

    public List<LogisticsData> getSpeakerlinks() {
        return speakerlinks;
    }

    public void setSpeakerlinks(List<LogisticsData> speakerlinks) {
        this.speakerlinks = speakerlinks;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
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

    public String getHide_attendee() {
        return hide_attendee;
    }

    public void setHide_attendee(String hide_attendee) {
        this.hide_attendee = hide_attendee;
    }

    public String getEnable_messaging() {
        return enable_messaging;
    }

    public void setEnable_messaging(String enable_messaging) {
        this.enable_messaging = enable_messaging;
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String[] getSessionId() {
        return sessionId;
    }

    public void setSessionId(String[] sessionId) {
        this.sessionId = sessionId;
    }

    public String getAttendee_id() {
        return attendee_id;
    }

    public void setAttendee_id(String attendee_id) {
        this.attendee_id = attendee_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageUrl(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSecondary_phone() {
        return secondary_phone;
    }

    public void setSecondary_phone(String secondary_phone) {
        this.secondary_phone = secondary_phone;
    }

    public String getEnable_contacts() {
        return enable_contacts;
    }

    public void setEnable_contacts(String enable_contacts) {
        this.enable_contacts = enable_contacts;
    }

}
