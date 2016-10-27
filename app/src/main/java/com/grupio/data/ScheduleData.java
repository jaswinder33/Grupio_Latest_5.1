package com.grupio.data;

import com.grupio.interfaces.Person;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class ScheduleData implements Person {
	public String has_child = "";
	String name="";
	String track="";
	String color = "";
	String start_time="";
	String end_time="";
	String [] speakes;
	String mapName="";
	String mapUrl="";
	List<ScheduleData> sessionsLink;
	List<mapList> mapListOfSche;
	String location="";
	String summary="",parent_session_name="";
	boolean showChild;
	String parent_session_id="";
	boolean plusminus=false;
	int childLen=0;
	String calenderAddId="";
	String datatype="";
	
	String meetingId	= "";
	String meetingTitle = "";
	String order = "";

	String maxSeatsAvailable;
	String speakerListAsString="";
	String resourceListAsString="";
	String session_id;

	public String getSpeakerListAsString() {
		return speakerListAsString;
	}

	public void setSpeakerListAsString(String speakerListAsString) {
		this.speakerListAsString = speakerListAsString;
	}

	public String getResourceListAsString() {
		return resourceListAsString;
	}

	public void setResourceListAsString(String resourceListAsString) {
		this.resourceListAsString = resourceListAsString;
	}

	public String getMaxSeatsAvailable() {
		return maxSeatsAvailable;
	}

	public void setMaxSeatsAvailable(String maxSeatsAvailable) {
		this.maxSeatsAvailable = maxSeatsAvailable;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		if(!color.contains("#") && !color.equals(""))
			color= "#" + color;
		this.color = color;
	}

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public String getMeetingTitle() {
		return meetingTitle;
	}

	public void setMeetingTitle(String meetingTitle) {
		this.meetingTitle = meetingTitle;
	}

	public int getChildLen() {
		return childLen;
	}

	public void setChildLen(int childLen) {
		this.childLen = childLen;
	}

	public boolean isPlusminus() {
		return plusminus;
	}

	public void setPlusminus(boolean plusminus) {
		this.plusminus = plusminus;
	}

	public String getParent_session_id() {
		return parent_session_id;
	}

	public void setParent_session_id(String parent_session_id) {
		this.parent_session_id = parent_session_id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String[] getSpeakes() {
		return speakes;
	}

	public void setSpeakes(String [] speakes) {
		this.speakes = speakes;
	}
	
	public String getSession_id() {
		return session_id;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTrack() {
		return track;
	}
	public void setTrack(String track) {
		this.track = track;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	
	public String getStartHour(String str){
		long time = Long.parseLong(str);
		//time*=1000;
		final SimpleDateFormat sdf = new SimpleDateFormat("h:mma");
		Calendar cal = Calendar.getInstance();
		
		cal.setTimeInMillis(time);
		return sdf.format(cal.getTime());
		
	}
	
	public String getEndHour(String str){
		long time = Long.parseLong(str);
		//time*=1000;
		final SimpleDateFormat sdf = new SimpleDateFormat("h:mma");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		return sdf.format(cal.getTime());
	}
	

	public List<ScheduleData> getSessionsLink() {
		return sessionsLink;
	}
	public void setSessionsLink(List<ScheduleData> sessionsLink) {
		this.sessionsLink = sessionsLink;
	}
	public String getMapName() {
		return mapName;
	}
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	public String getMapUrl() {
		return mapUrl;
	}
	public void setMapUrl(String mapUrl) {
		this.mapUrl = mapUrl;
	}

	public List<mapList> getMapListOfSche() {
		return mapListOfSche;
	}
	public void setMapListOfSche(List<mapList> mapListOfSche) {
		this.mapListOfSche = mapListOfSche;
	}
	public String getHas_child() {
		return has_child;
	}
	public void setHas_child(String has_child) {
		this.has_child = has_child;
	}
	public boolean isShowChild() {
		return showChild;
	}
	public void setShowChild(boolean showChild) {
		this.showChild = showChild;
	}
	public String getParent_session_name() {
		return parent_session_name;
	}
	public void setParent_session_name(String parent_session_name) {
		this.parent_session_name = parent_session_name;
	}
	public String getCalenderAddId() {
		return calenderAddId;
	}
	public void setCalenderAddId(String calenderAddId) {
		this.calenderAddId = calenderAddId;
	}
}
