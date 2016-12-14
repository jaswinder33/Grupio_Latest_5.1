package com.grupio.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MeetingData_Old implements Parcelable, Serializable {


    public static final Creator<MeetingData_Old> CREATOR = new Creator<MeetingData_Old>() {

        @Override
        public MeetingData_Old createFromParcel(Parcel source) {
            return new MeetingData_Old(source);
        }

        @Override
        public MeetingData_Old[] newArray(int size) {
            return new MeetingData_Old[size];
        }
    };
    private static final long serialVersionUID = 1L;
    String current_date = "", meeting_id = "", event_id = "",
            meetingDate = "", title = "", location = "", description = "",
            meetingStartTime1 = "", meetingStartTime2 = "",
            meetingStartTime3 = "", meetingEndTime1 = "",
            meetingEndTime2 = "", meetingEndTime3 = "",
            creater_attendee_id = "",
            creater_attendee_name = "",
            creater_attendee_company = "",
            creater_attendee_image = "",
            creater_attendee_type = "",
            creater_attendee_large_image = "",
            creater_attendee_title = "",
            creater_attendee_bio = "",
            creater_attendee_email = "",
            creater_attendee_enable_contacts = "",
            creater_attendee_enable_messaging = "",
            creater_attendee_hide_attendee = "",
            creater_attendee_hide_contact_info = "",
            creater_attendee_linkedin = "",
            creater_attendee_primary_phone = "",
            creater_attendee_secondary_phone = "",
            creater_attendee_first_name = "",
            creater_attendee_last_name = "",
            creater_attendee_keywords = "";
    String creater_attendee_sessions[] = null;
    List<MeetingAttendeeStatusData> invitiedAttendeeRecord = new ArrayList<MeetingAttendeeStatusData>();

    public MeetingData_Old() {
    }


    public MeetingData_Old(Parcel source) {

        readFromParcel(source);
    }

    public String[] getCreater_attendee_sessions() {
        return creater_attendee_sessions;
    }

    public void setCreater_attendee_sessions(String[] creater_attendee_sessions) {
        this.creater_attendee_sessions = creater_attendee_sessions;
    }

    public String getCreater_attendee_first_name() {
        return creater_attendee_first_name;
    }

    public void setCreater_attendee_first_name(String creater_attendee_first_name) {
        this.creater_attendee_first_name = creater_attendee_first_name;
    }

    public String getCreater_attendee_last_name() {
        return creater_attendee_last_name;
    }

    public void setCreater_attendee_last_name(String creater_attendee_last_name) {
        this.creater_attendee_last_name = creater_attendee_last_name;
    }

    public String getCreater_attendee_keywords() {
        return creater_attendee_keywords;
    }

    public void setCreater_attendee_keywords(String creater_attendee_keywords) {
        this.creater_attendee_keywords = creater_attendee_keywords;
    }

    public String getCreater_attendee_type() {
        return creater_attendee_type;
    }

    public void setCreater_attendee_type(String creater_attendee_type) {
        this.creater_attendee_type = creater_attendee_type;
    }

    public String getCreater_attendee_large_image() {
        return creater_attendee_large_image;
    }

    public void setCreater_attendee_large_image(String creater_attendee_large_image) {
        this.creater_attendee_large_image = creater_attendee_large_image;
    }

    public String getCreater_attendee_title() {
        return creater_attendee_title;
    }

    public void setCreater_attendee_title(String creater_attendee_title) {
        this.creater_attendee_title = creater_attendee_title;
    }

    public String getCreater_attendee_bio() {
        return creater_attendee_bio;
    }

    public void setCreater_attendee_bio(String creater_attendee_bio) {
        this.creater_attendee_bio = creater_attendee_bio;
    }

    public String getCreater_attendee_email() {
        return creater_attendee_email;
    }

    public void setCreater_attendee_email(String creater_attendee_email) {
        this.creater_attendee_email = creater_attendee_email;
    }

    public String getCreater_attendee_enable_contacts() {
        return creater_attendee_enable_contacts;
    }

    public void setCreater_attendee_enable_contacts(
            String creater_attendee_enable_contacts) {
        this.creater_attendee_enable_contacts = creater_attendee_enable_contacts;
    }

    public String getCreater_attendee_enable_messaging() {
        return creater_attendee_enable_messaging;
    }

    public void setCreater_attendee_enable_messaging(
            String creater_attendee_enable_messaging) {
        this.creater_attendee_enable_messaging = creater_attendee_enable_messaging;
    }

    public String getCreater_attendee_hide_attendee() {
        return creater_attendee_hide_attendee;
    }

    public void setCreater_attendee_hide_attendee(
            String creater_attendee_hide_attendee) {
        this.creater_attendee_hide_attendee = creater_attendee_hide_attendee;
    }

    public String getCreater_attendee_hide_contact_info() {
        return creater_attendee_hide_contact_info;
    }

    public void setCreater_attendee_hide_contact_info(
            String creater_attendee_hide_contact_info) {
        this.creater_attendee_hide_contact_info = creater_attendee_hide_contact_info;
    }

    public String getCreater_attendee_linkedin() {
        return creater_attendee_linkedin;
    }

    public void setCreater_attendee_linkedin(String creater_attendee_linkedin) {
        this.creater_attendee_linkedin = creater_attendee_linkedin;
    }

    public String getCreater_attendee_primary_phone() {
        return creater_attendee_primary_phone;
    }

    public void setCreater_attendee_primary_phone(
            String creater_attendee_primary_phone) {
        this.creater_attendee_primary_phone = creater_attendee_primary_phone;
    }

    public String getCreater_attendee_secondary_phone() {
        return creater_attendee_secondary_phone;
    }

    public void setCreater_attendee_secondary_phone(
            String creater_attendee_secondary_phone) {
        this.creater_attendee_secondary_phone = creater_attendee_secondary_phone;
    }

    public String getCreater_attendee_company() {
        return creater_attendee_company;
    }

    public void setCreater_attendee_company(String creater_attendee_company) {
        this.creater_attendee_company = creater_attendee_company;
    }

    public String getCreater_attendee_image() {
        return creater_attendee_image;
    }

    public void setCreater_attendee_image(String creater_attendee_image) {
        this.creater_attendee_image = creater_attendee_image;
    }

    public String getCreater_attendee_name() {
        return creater_attendee_name;
    }

    public void setCreater_attendee_name(String creater_attendee_name) {
        this.creater_attendee_name = creater_attendee_name;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }

    public String getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(String meeting_id) {
        this.meeting_id = meeting_id;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getCreater_attendee_id() {
        return creater_attendee_id;
    }

    public void setCreater_attendee_id(String creater_attendee_id) {
        this.creater_attendee_id = creater_attendee_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMeetingStartTime1() {
        return meetingStartTime1;
    }

    public void setMeetingStartTime1(String meetingStartTime1) {
        this.meetingStartTime1 = meetingStartTime1;
    }

    public String getMeetingStartTime2() {
        return meetingStartTime2;
    }

    public void setMeetingStartTime2(String meetingStartTime2) {
        this.meetingStartTime2 = meetingStartTime2;
    }

    public String getMeetingStartTime3() {
        return meetingStartTime3;
    }

    public void setMeetingStartTime3(String meetingStartTime3) {
        this.meetingStartTime3 = meetingStartTime3;
    }

    public String getMeetingEndTime1() {
        return meetingEndTime1;
    }

    public void setMeetingEndTime1(String meetingEndTime1) {
        this.meetingEndTime1 = meetingEndTime1;
    }

    public String getMeetingEndTime2() {
        return meetingEndTime2;
    }

    public void setMeetingEndTime2(String meetingEndTime2) {
        this.meetingEndTime2 = meetingEndTime2;
    }

    public String getMeetingEndTime3() {
        return meetingEndTime3;
    }

    public void setMeetingEndTime3(String meetingEndTime3) {
        this.meetingEndTime3 = meetingEndTime3;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public List<MeetingAttendeeStatusData> getInvitiedAttendeeRecord() {
        return invitiedAttendeeRecord;
    }

    public void setInvitiedAttendeeRecord(ArrayList<MeetingAttendeeStatusData> invitiedAttendeeRecord) {
        this.invitiedAttendeeRecord = invitiedAttendeeRecord;
    }

    public String getInvitiedAttendeeString() {
        String strInvitiedAttendees = "";

        for (int i = 0; i < invitiedAttendeeRecord.size(); i++) {
            if (i == 0)
                strInvitiedAttendees = invitiedAttendeeRecord.get(i).getAttendeeName();
            else
                strInvitiedAttendees = strInvitiedAttendees + "," + invitiedAttendeeRecord.get(i).getAttendeeName();

        }
        return strInvitiedAttendees;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(current_date);
        dest.writeString(meeting_id);

        dest.writeString(event_id);
        dest.writeString(creater_attendee_id);
        dest.writeString(meetingDate);
        dest.writeString(title);
        dest.writeString(location);
        dest.writeString(description);
        dest.writeString(meetingStartTime1);
        dest.writeString(meetingStartTime2);
        dest.writeString(meetingStartTime3);
        dest.writeString(meetingEndTime1);
        dest.writeString(meetingEndTime2);
        dest.writeString(meetingEndTime3);
        dest.writeString(creater_attendee_name);
        dest.writeString(creater_attendee_company);
        dest.writeString(creater_attendee_image);

        dest.writeString(creater_attendee_type);
        dest.writeString(creater_attendee_large_image);
        dest.writeString(creater_attendee_title);
        dest.writeString(creater_attendee_bio);
        dest.writeString(creater_attendee_email);
        dest.writeString(creater_attendee_enable_contacts);
        dest.writeString(creater_attendee_enable_messaging);
        dest.writeString(creater_attendee_hide_attendee);
        dest.writeString(creater_attendee_hide_contact_info);
        dest.writeString(creater_attendee_linkedin);
        dest.writeString(creater_attendee_primary_phone);
        dest.writeString(creater_attendee_secondary_phone);
        dest.writeString(creater_attendee_first_name);
        dest.writeString(creater_attendee_last_name);
        dest.writeString(creater_attendee_keywords);

//						 dest.writeInt(creater_attendee_sessions.length);
//						 for( int i=0; i<creater_attendee_sessions.length; i++ ){
//								 dest.writeString(creater_attendee_sessions[i]);
//						 }
        dest.writeInt(creater_attendee_sessions.length);
        dest.writeStringArray(creater_attendee_sessions);

        final int N = invitiedAttendeeRecord.size();
        dest.writeInt(N);
        if (N > 0) {

            for (int i = 0; i < invitiedAttendeeRecord.size(); i++) {

                dest.writeString(invitiedAttendeeRecord.get(i).getAttendee_id());
                dest.writeString(invitiedAttendeeRecord.get(i).getAttendeeName());
                dest.writeString(invitiedAttendeeRecord.get(i).getTimeSlot1Status());
                dest.writeString(invitiedAttendeeRecord.get(i).getTimeSlot2Status());
                dest.writeString(invitiedAttendeeRecord.get(i).getTimeSlot3Status());
                dest.writeString(invitiedAttendeeRecord.get(i).getCompany());
                dest.writeString(invitiedAttendeeRecord.get(i).getImage());
                dest.writeString(invitiedAttendeeRecord.get(i).getBio());
                dest.writeString(invitiedAttendeeRecord.get(i).getEmail());
                dest.writeString(invitiedAttendeeRecord.get(i).getEnable_contacts());
                dest.writeString(invitiedAttendeeRecord.get(i).getEnable_messaging());
                dest.writeString(invitiedAttendeeRecord.get(i).getHide_attendee());
                dest.writeString(invitiedAttendeeRecord.get(i).getHide_contact_info());
                dest.writeString(invitiedAttendeeRecord.get(i).getLinkedin());
                dest.writeString(invitiedAttendeeRecord.get(i).getPrimary_phone());
                dest.writeString(invitiedAttendeeRecord.get(i).getSecondary_phone());
                dest.writeString(invitiedAttendeeRecord.get(i).getFirst_name());
                dest.writeString(invitiedAttendeeRecord.get(i).getLast_name());
                dest.writeString(invitiedAttendeeRecord.get(i).getTitle());
                dest.writeString(invitiedAttendeeRecord.get(i).getKeywords());
                dest.writeString(invitiedAttendeeRecord.get(i).getType());

                dest.writeInt(invitiedAttendeeRecord.get(i).getSessions().length);
                dest.writeStringArray(invitiedAttendeeRecord.get(i).getSessions());

            }

        }

    }

    public void readFromParcel(Parcel source) {

        current_date = source.readString();
        meeting_id = source.readString();
        event_id = source.readString();
        creater_attendee_id = source.readString();
        meetingDate = source.readString();
        title = source.readString();
        location = source.readString();
        description = source.readString();
        meetingStartTime1 = source.readString();
        meetingStartTime2 = source.readString();
        meetingStartTime3 = source.readString();
        meetingEndTime1 = source.readString();
        meetingEndTime2 = source.readString();
        meetingEndTime3 = source.readString();
        creater_attendee_name = source.readString();
        creater_attendee_company = source.readString();
        creater_attendee_image = source.readString();

        creater_attendee_type = source.readString();
        creater_attendee_large_image = source.readString();
        creater_attendee_title = source.readString();
        creater_attendee_bio = source.readString();
        creater_attendee_email = source.readString();
        creater_attendee_enable_contacts = source.readString();
        creater_attendee_enable_messaging = source.readString();
        creater_attendee_hide_attendee = source.readString();
        creater_attendee_hide_contact_info = source.readString();
        creater_attendee_linkedin = source.readString();
        creater_attendee_primary_phone = source.readString();
        creater_attendee_secondary_phone = source.readString();
        creater_attendee_first_name = source.readString();
        creater_attendee_last_name = source.readString();
        creater_attendee_keywords = source.readString();

        int session_length = source.readInt();
        creater_attendee_sessions = new String[session_length];
        source.readStringArray(creater_attendee_sessions);

        final int N = source.readInt();
        for (int i = 0; i < N; i++) {

            MeetingAttendeeStatusData md = new MeetingAttendeeStatusData();

            md.setAttendee_id(source.readString());
            md.setAttendeeName(source.readString());
            md.setTimeSlot1Status(source.readString());
            md.setTimeSlot2Status(source.readString());
            md.setTimeSlot3Status(source.readString());
            md.setCompany(source.readString());
            md.setImage(source.readString());

            md.setBio(source.readString());
            md.setEmail(source.readString());
            md.setEnable_contacts(source.readString());
            md.setEnable_messaging(source.readString());
            md.setHide_attendee(source.readString());
            md.setHide_contact_info(source.readString());
            md.setLinkedin(source.readString());
            md.setPrimary_phone(source.readString());
            md.setSecondary_phone(source.readString());
            md.setFirst_name(source.readString());
            md.setLast_name(source.readString());
            md.setTitle(source.readString());
            md.setKeywords(source.readString());
            md.setType(source.readString());

            int sess_length = source.readInt();
            String arr[] = new String[sess_length];
            source.readStringArray(arr);
            md.setSessions(arr);

            invitiedAttendeeRecord.add(md);
        }

        //				  in.readList(_lqis Integer.class.getClassLoader());
        //				 invitiedAttendeeRecord = (ArrayList<MeetingAttendeeStatusData>)source.readValue(MeetingAttendeeStatusData.class.getClassLoader());

    }

}	
