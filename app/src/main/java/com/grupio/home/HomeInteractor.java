package com.grupio.home;

import android.content.Context;

import com.grupio.dao.EventDAO;
import com.grupio.db.EventTable;

/**
 * Created by JSN on 6/10/16.
 */

public class HomeInteractor implements HomeInteractorImp {

    @Override
    public void setHomeInfo(Context mContext, OnHomeInteractionComplete listener) {
        setWebLogoImage(mContext, listener);
        setEventName(mContext, listener);
        setEventDate(mContext, listener);
        setVenue(mContext, listener);
        setAddress(mContext, listener);
        setStaticMap(mContext, listener);
        setEventWebsite(mContext, listener);
        setDescription(mContext, listener);
    }

    @Override
    public String fetchLatitude(Context mContext) {
        return EventDAO.getInstance(mContext).getValue(EventTable.LATTITUDE);
    }

    @Override
    public String fetchLongitude(Context mContext) {
        return EventDAO.getInstance(mContext).getValue(EventTable.LONGITUDE);
    }

    @Override
    public String fetchEventWebsiteBtn(Context mContext) {
        return EventDAO.getInstance(mContext).getValue(EventTable.EVENT_WEBSITE_URL);
    }

    public void setWebLogoImage(Context mContext, OnHomeInteractionComplete listener) {
        listener.onWebLogoFetch(EventDAO.getInstance(mContext).getValue(EventTable.WEB_LOGO_IMAGE));
    }

    public void setEventName(Context mContext, OnHomeInteractionComplete listener) {
        listener.onEventName(EventDAO.getInstance(mContext).getValue(EventTable.EVENT_NAME));
    }

    public void setEventDate(Context mContext, OnHomeInteractionComplete listener) {
        String startDate = EventDAO.getInstance(mContext).getValue(EventTable.START_DATE);

        String start[] = startDate.split("-");

        String endDate = EventDAO.getInstance(mContext).getValue(EventTable.END_DATE);
        String end[] = endDate.split("-");

        String eventDate = start[0] + "/" + end[0] + "\n" + start[1] + "/" + end[1];

        listener.onEventDate(eventDate);
    }

    public void setVenue(Context mContext, OnHomeInteractionComplete listener) {
        listener.onVenue(EventDAO.getInstance(mContext).getValue(EventTable.VENUE));
    }

    public void setAddress(Context mContext, OnHomeInteractionComplete listener) {

        /*
        "address1": "barbecue nation,janakpuri",
        "address2": "Delhi, India",
        "city": "new delhi",
        "state": "new delhi",
        "country": "India",
        "zipcode": "78701",
         */

        String address1 = EventDAO.getInstance(mContext).getValue(EventTable.ADDRESS1);
        String address2 = EventDAO.getInstance(mContext).getValue(EventTable.ADDRESS2);
        String city = EventDAO.getInstance(mContext).getValue(EventTable.CITY);
        String state = EventDAO.getInstance(mContext).getValue(EventTable.STATE);
        String country = EventDAO.getInstance(mContext).getValue(EventTable.COUNTRY);
        String zipcode = EventDAO.getInstance(mContext).getValue(EventTable.ZIPCODE);
        String eventAddress = address1 + ",\n" + address2 + ",\n" + city + ",\n" + state + ",\n" + country + ",\n" + zipcode;
        listener.onAddress(eventAddress);

    }

    public void setStaticMap(Context mContext, OnHomeInteractionComplete listener) {
        listener.onStaticMap(EventDAO.getInstance(mContext).getValue(EventTable.STATIC_MAP_URL));
    }

    public void setEventWebsite(Context mContext, OnHomeInteractionComplete listener) {
        listener.onEventWebSiteBtn(fetchEventWebsiteBtn(mContext));
    }

    public void setDescription(Context mContext, OnHomeInteractionComplete listener) {
        listener.onDescription(EventDAO.getInstance(mContext).getValue(EventTable.DESCRIPTION));
    }
}
