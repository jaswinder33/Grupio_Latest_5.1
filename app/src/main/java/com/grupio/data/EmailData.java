package com.grupio.data;

/**
 * Created by JSN on 18/11/16.
 */

public class EmailData {

    public String emailId;
    public String subject;
    public String text;

    public EmailData(String emailId, String subject, String text) {
        this.emailId = emailId;
        this.subject = subject;
        this.text = text;
    }
}
