package com.grupio.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class MeetingAttendeeStatusData implements Parcelable, Serializable {

    public static final Creator<MeetingAttendeeStatusData> CREATOR = new Creator<MeetingAttendeeStatusData>() {

        @Override
        public MeetingAttendeeStatusData createFromParcel(Parcel source) {
            return new MeetingAttendeeStatusData(source);
        }

        @Override
        public MeetingAttendeeStatusData[] newArray(int size) {
            return new MeetingAttendeeStatusData[size];
        }
    };
    private static final long serialVersionUID = 1L;
    String attendee_id = "", attendeeName = "",
            timeSlot1Status = "", timeSlot2Status = "", timeSlot3Status = "",
            company = "", image = "",
            large_image = "",
            bio = "",
            email = "",
            enable_contacts = "",
            enable_messaging = "",
            hide_attendee = "",
            hide_contact_info = "",
            linkedin = "",
            primary_phone = "",
            secondary_phone = "",
            first_name = "",
            last_name = "",
            title = "",
            keywords = "",
            interest = "",
            type = "";
    String sessions[] = null;

    public MeetingAttendeeStatusData() {
    }

    public MeetingAttendeeStatusData(Parcel source) {
        readFromParcel(source);
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String[] getSessions() {
        return sessions;
    }

    public void setSessions(String[] sessions) {
        this.sessions = sessions;
    }

    public String getLarge_image() {
        return large_image;
    }

    public void setLarge_image(String large_image) {
        this.large_image = large_image;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEnable_contacts() {
        return enable_contacts;
    }

    public void setEnable_contacts(String enable_contacts) {
        this.enable_contacts = enable_contacts;
    }

    public String getEnable_messaging() {
        return enable_messaging;
    }

    public void setEnable_messaging(String enable_messaging) {
        this.enable_messaging = enable_messaging;
    }

    public String getHide_attendee() {
        return hide_attendee;
    }

    public void setHide_attendee(String hide_attendee) {
        this.hide_attendee = hide_attendee;
    }

    public String getHide_contact_info() {
        return hide_contact_info;
    }

    public void setHide_contact_info(String hide_contact_info) {
        this.hide_contact_info = hide_contact_info;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAttendee_id() {
        return attendee_id;
    }

    public void setAttendee_id(String attendee_id) {
        this.attendee_id = attendee_id;
    }

    public String getAttendeeName() {
        return attendeeName;
    }

    public void setAttendeeName(String attendeeName) {
        this.attendeeName = attendeeName;
    }

    public String getTimeSlot1Status() {
        return timeSlot1Status;
    }

    public void setTimeSlot1Status(String timeSlot1Status) {
        this.timeSlot1Status = timeSlot1Status;
    }

    public String getTimeSlot2Status() {
        return timeSlot2Status;
    }

    public void setTimeSlot2Status(String timeSlot2Status) {
        this.timeSlot2Status = timeSlot2Status;
    }

    public String getTimeSlot3Status() {
        return timeSlot3Status;
    }

    public void setTimeSlot3Status(String timeSlot3Status) {
        this.timeSlot3Status = timeSlot3Status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(attendee_id);
        dest.writeString(attendeeName);
        dest.writeString(timeSlot1Status);
        dest.writeString(timeSlot2Status);
        dest.writeString(timeSlot3Status);
        dest.writeString(company);
        dest.writeString(image);
        dest.writeString(bio);
        dest.writeString(email);
        dest.writeString(enable_contacts);
        dest.writeString(enable_messaging);
        dest.writeString(hide_attendee);
        dest.writeString(hide_contact_info);
        dest.writeString(linkedin);
        dest.writeString(primary_phone);
        dest.writeString(secondary_phone);
        dest.writeString(first_name);
        dest.writeString(last_name);
        dest.writeString(title);
        dest.writeString(keywords);
        dest.writeString(type);

        dest.writeInt(sessions.length);
        dest.writeStringArray(sessions);


    }

    public void readFromParcel(Parcel source) {

        attendee_id = source.readString();
        attendeeName = source.readString();
        timeSlot1Status = source.readString();
        timeSlot2Status = source.readString();
        timeSlot3Status = source.readString();
        company = source.readString();
        image = source.readString();
        bio = source.readString();
        email = source.readString();
        enable_contacts = source.readString();
        enable_messaging = source.readString();
        hide_attendee = source.readString();

        hide_contact_info = source.readString();
        linkedin = source.readString();
        primary_phone = source.readString();
        secondary_phone = source.readString();
        first_name = source.readString();
        last_name = source.readString();
        title = source.readString();
        keywords = source.readString();
        type = source.readString();

        int session_length = source.readInt();
        sessions = new String[session_length];
        source.readStringArray(sessions);
    }

}
