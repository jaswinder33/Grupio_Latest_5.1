package com.grupio.attendee;

import com.grupio.interfaces.Person;

/**
 * Created by JSN on 1/8/16.
 */
public interface DetailPresenterInter {
    void validateData(Person mPerson);

    void sendContactRequest(String id, boolean hasPermission);

    void sendMessage();

    void doCall();

    void doEmail();

    void openLinks(String type);

    void doFav(String id);

}
