package com.grupio.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MessageData {

    int UnreadMessages, TotalMessages;

    String message_id = "", thread_id = "", sender_id = "", receiver_id = "", is_unread = "", datetime = "", folder = "", title = "", content = "", receiver_email = "",
            receiver_first_name = "", receiver_last_name = "", sender_email = "", sender_first_name = "", sender_last_name = "";

    public int getUnreadMessages() {
        return UnreadMessages;
    }

    public void setUnreadMessages(int unreadMessages) {
        UnreadMessages = unreadMessages;
    }

    public int getTotalMessages() {
        return TotalMessages;
    }

    public void setTotalMessages(int totalMessages) {
        TotalMessages = totalMessages;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getIs_unread() {
        return is_unread;
    }

    public void setIs_unread(String is_unread) {
        this.is_unread = is_unread;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
    /*    try {
            SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sourceFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date parsed = sourceFormat.parse(datetime); // => Date is in UTC now

//            String utcTime = sourceFormat.format(parsed);

            SimpleDateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            destFormat.setTimeZone(TimeZone.getDefault());
//            String required = destFormat.format(parsed);
            this.datetime = destFormat.format(parsed);

        } catch (Exception e) {
           e.printStackTrace();
        }*/

        this.datetime = datetime;
    }

    public String getDate() {
        String serverString = "";
        try {
            SimpleDateFormat clientFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat serverFormat = new SimpleDateFormat("MM/dd/yy");
            Date clientDate = clientFormat.parse(datetime);
            serverString = serverFormat.format(clientDate);
        } catch (Exception e) {
            // TODO: handle exception
        }


        return serverString;

    }

    public String getDay() {
        String serverString = "";
        try {
            SimpleDateFormat clientFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat serverFormat = new SimpleDateFormat("EEEE");
            Date clientDate = clientFormat.parse(datetime);
            serverString = serverFormat.format(clientDate);
        } catch (Exception e) {
            // TODO: handle exception
        }


        return serverString;
    }

    public String getTime() {
        String serverString = "";
        try {
            SimpleDateFormat clientFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat serverFormat = new SimpleDateFormat("hh:mm a");
            Date clientDate = clientFormat.parse(datetime);
            serverString = serverFormat.format(clientDate);
        } catch (Exception e) {
            // TODO: handle exception
        }


        return serverString;

    }

    public String getDayTime() {
        String serverString = "";
        try {
            SimpleDateFormat clientFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat serverFormat = new SimpleDateFormat("EEE hh:mm a");
            Date clientDate = clientFormat.parse(datetime);
            serverString = serverFormat.format(clientDate);
        } catch (Exception e) {
            // TODO: handle exception
        }


        return serverString;
    }

    public String getDateTime() {
        String serverString = "";
        try {
            SimpleDateFormat clientFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat serverFormat = new SimpleDateFormat("MMM dd hh:mm a");
            Date clientDate = clientFormat.parse(datetime);
            serverString = serverFormat.format(clientDate);
        } catch (Exception e) {
            // TODO: handle exception
        }


        return serverString;
    }

    public String getDayTimeDate() {

        String serverString = "";
        String currentdate = "";

        SimpleDateFormat clientFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


        try {
            Date clientDate = clientFormat.parse(datetime);
            serverString = dateFormat.format(clientDate);

            Calendar c = Calendar.getInstance();
            currentdate = dateFormat.format(c.getTime());

            int client_year = clientDate.getYear();
            int client_months = clientDate.getMonth();
            int client_date = clientDate.getDate();

            int current_year = c.getTime().getYear();
            int current_months = c.getTime().getMonth();
            int current_date = c.getTime().getDate();

            if (currentdate.equalsIgnoreCase(serverString))
                return getTime();
            else if (client_year == current_year && client_months == current_months) {
                if (client_date > current_date - 7) {
                    return getDay();
                } else {
                    return getDate();
                }
            } else
                return getDate();

        } catch (Exception e) {
            // TODO: handle exception
        }


        return "";

    }

    public String getDayTimeDateDetail() {

        String serverString = "";
        String currentdate = "";

        SimpleDateFormat clientFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


        try {
            Date clientDate = clientFormat.parse(datetime);
            serverString = dateFormat.format(clientDate);

            Calendar c = Calendar.getInstance();
            currentdate = dateFormat.format(c.getTime());

            int client_year = clientDate.getYear();
            int client_months = clientDate.getMonth();
            int client_date = clientDate.getDate();

            int current_year = c.getTime().getYear();
            int current_months = c.getTime().getMonth();
            int current_date = c.getTime().getDate();

            if (currentdate.equalsIgnoreCase(serverString))
                return getTime();
            else if (client_year == current_year && client_months == current_months) {
                if (client_date > current_date - 7) {
                    return getDayTime();
                } else {
                    return getDateTime();
                }
            } else
                return getDateTime();

        } catch (Exception e) {
            // TODO: handle exception
        }


        return "";

    }

    public String gettime() {
        return datetime;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReceiver_email() {
        return receiver_email;
    }

    public void setReceiver_email(String receiver_email) {
        this.receiver_email = receiver_email;
    }

    public String getReceiver_first_name() {
        return receiver_first_name;
    }

    public void setReceiver_first_name(String receiver_first_name) {
        this.receiver_first_name = receiver_first_name;
    }

    public String getReceiver_last_name() {
        return receiver_last_name;
    }

    public void setReceiver_last_name(String receiver_last_name) {
        this.receiver_last_name = receiver_last_name;
    }

    public String getSender_email() {
        return sender_email;
    }

    public void setSender_email(String sender_email) {
        this.sender_email = sender_email;
    }

    public String getSender_first_name() {
        return sender_first_name;
    }

    public void setSender_first_name(String sender_first_name) {
        this.sender_first_name = sender_first_name;
    }

    public String getSender_last_name() {
        return sender_last_name;
    }

    public void setSender_last_name(String sender_last_name) {
        this.sender_last_name = sender_last_name;
    }


}
