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

    public AttendeesData mCreatorData;
    public List<AttendeesData> invitiesList = new ArrayList<>();

}
