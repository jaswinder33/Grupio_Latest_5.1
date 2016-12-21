package com.grupio.data;

import com.grupio.interfaces.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 14/12/16.
 */

public class MeetingData implements Person {

    public String id;
    public String currentDate;
    public String meetingTime;
    public String meetingDate;
    public String invities;
    public String creator;
    public String title;
    public String location;
    public String description;

    public String startTime;
    public String endTime;
    public String status;

    public AttendeesData mCreatorData;
    public List<AttendeesData> invitiesList = new ArrayList<>();

    public MeetingData(MeetingData data) {
        this.id = data.id;
        this.currentDate = data.currentDate;
        this.meetingTime = data.meetingTime;
        this.meetingDate = data.meetingDate;
        this.invities = data.invities;
        this.creator = data.creator;
        this.title = data.title;
        this.location = data.location;
        this.description = data.description;
        this.startTime = data.startTime;
        this.endTime = data.endTime;
        this.mCreatorData = data.mCreatorData;
        this.invitiesList = data.invitiesList;
    }

    public MeetingData() {
    }
}
