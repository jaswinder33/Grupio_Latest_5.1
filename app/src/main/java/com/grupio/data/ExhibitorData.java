package com.grupio.data;


import com.grupio.interfaces.Person;

import java.util.ArrayList;
import java.util.List;

public class ExhibitorData implements Person {

    String exhibitorId = "";
    String name = "";
    String image = "";
    String imageLarge = "";
    String location = "";
    String description = "";
    String weburl = "";
    String mapId = "";
    String mapName = "";
    String mapUrl = "";
    String category = "";

    ArrayList<String> attendees;
    String datatype = "";

    String contact_email = "", contact_phone = "";
    String address = "";    // contains full address

    String sessionListAsString = "", resourceListAsSrings = "", attendeeListAsString = "";

    List<LogisticsData> exhibitorslinks = new ArrayList();
    List<mapList> mapListOfExhibitor;

    boolean isFav = false;

    public boolean isFav() {
        return isFav;
    }

    public void setIsFav(String value) {
        value = value == null ? "0" : value;

        isFav = value.equals("1");
    }

    public String getImageLarge() {
        return imageLarge;
    }

    public void setImageLarge(String imageLarge) {
        this.imageLarge = imageLarge;
    }

    public String getSessionListAsString() {
        return sessionListAsString;
    }

    public void setSessionListAsString(String sessionListAsString) {
        this.sessionListAsString = sessionListAsString;
    }

    public String getResourceListAsSrings() {
        return resourceListAsSrings;
    }

    public void setResourceListAsSrings(String resourceListAsSrings) {
        this.resourceListAsSrings = resourceListAsSrings;
    }

    public String getAttendeeListAsString() {
        return attendeeListAsString;
    }

    public void setAttendeeListAsString(String attendeeListAsString) {
        this.attendeeListAsString = attendeeListAsString;
    }

    public List<LogisticsData> getExhibitorslinks() {
        return exhibitorslinks;
    }

    public void setExhibitorslinks(List<LogisticsData> exhibitorslinks) {
        this.exhibitorslinks = exhibitorslinks;
    }

    public String getContact_email() {
        return contact_email;
    }

    public void setContact_email(String contact_email) {
        this.contact_email = contact_email;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<String> getAttendees() {
        return attendees;
    }

    public void setAttendees(ArrayList<String> attendees) {
        this.attendees = attendees;
    }

    public List<mapList> getMapListOfExhibitor() {
        return mapListOfExhibitor;
    }

    public void setMapListOfExhibitor(List<mapList> mapListOfExhibitor) {
        this.mapListOfExhibitor = mapListOfExhibitor;
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

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    public String getExhibitorId() {
        return exhibitorId;
    }

    public void setExhibitorId(String exhibitorId) {
        this.exhibitorId = exhibitorId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}

