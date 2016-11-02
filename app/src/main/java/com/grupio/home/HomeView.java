package com.grupio.home;

/**
 * Created by JSN on 5/10/16.
 */

public interface HomeView {

    void setWebLogo(String url);

    void setEventDate(String date);

    void setEventName(String name);

    void setVenue(String venue);

    void setAddress(String address);

    void setStaticMap(String map);

    void setDescrption(String descrption);

    void hideDescription();

    void hideEventWebsite();

}
