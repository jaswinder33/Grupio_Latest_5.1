package com.grupio.gridhome;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.dao.EventDAO;
import com.grupio.data.MenuData;
import com.grupio.db.EventTable;
import com.grupio.session.Preferences;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 22/9/16.
 */

/*public class MenuListAdapter extends BaseRecyclerView<MenuData> {

    private String eventColor;
    private boolean showAlertCount = false;


    private ImageView menuIcon;
    private TextView menuNameTxt, alertText;

    public MenuListAdapter(Context mContext) {
        super(mContext);
        eventColor = EventDAO.getInstance(mContext).getValue(EventTable.COLOR_THEME);
    }

    @Override
    public int getLayout() {
        return R.layout.menu_icon;
    }

    @Override
    public void initIds(View view) {
        menuIcon = (ImageView) view.findViewById(R.id.menuIcon);
        menuNameTxt = (TextView) view.findViewById(R.id.menuName);
        alertText = (TextView) view.findViewById(R.id.alertText);
    }

    @Override
    public void onBindView(int position) {
        String menuName = getItem(position).getMenuName();
        menuNameTxt.setText(menuName);
        showAlertCount = menuName.equalsIgnoreCase("mycalendar") || menuName.equalsIgnoreCase("chat") || menuName.equalsIgnoreCase("messages") || menuName.equalsIgnoreCase("Alerts");

        displayIcon(showAlertCount, menuName, position);
    }

    public void displayIcon(boolean showCountLayout, String menuName, int position) {

        if (showCountLayout) {
            String count = getAlertCount(menuName);
            if (!count.equals("0") && !count.equals("")) {
                alertText.setVisibility(View.VISIBLE);
                alertText.setText(count);
            }
        } else {
            alertText.setVisibility(View.GONE);
        }

        String displayMenuName = getItem(position).getmDisplayName();

        if (!displayMenuName.equals("")) {
            menuNameTxt.setText(displayMenuName);
        } else {
            menuNameTxt.setText(getMenuName(menuName));
        }

        String imageIconUrl = getItem(position).getMenuIconUrl();

        if (imageIconUrl.equalsIgnoreCase("") || imageIconUrl == null) {
            menuIcon.setImageResource(getMenuIcon(menuName));
        } else {
            File fileDir = Utility.getBaseFolder(getContext(), getContext().getString(R.string.Resources) + File.separator + getContext().getString(R.string.menus));
            File file = new File(fileDir, getItem(position).getMenuName());
            if (file.exists()) {
                ImageLoader.getInstance().displayImage("file://" + file.getAbsolutePath(), menuIcon);
            } else {
                menuIcon.setImageResource(getMenuIcon(displayMenuName));
            }
        }

        try {
            int color = Color.parseColor(eventColor);
            GradientDrawable drawable = (GradientDrawable) menuIcon.getBackground();
            drawable.setColor(color);
        } catch (Exception e) {
            e.printStackTrace();
        }

//       mHolder.menuIcon.getBackground().(color, PorterDuff.Mode.MULTIPLY);
    }

    public String getAlertCount(String menu) {

        switch (menu) {

            case "mycalendar":
                return Preferences.getInstances(getContext()).getCalendarCount();

            case "messages":
                return Preferences.getInstances(getContext()).getUnreadMessages();

            case "Alerts":
                return Preferences.getInstances(getContext()).getAlertCount();

            case "chat":
                return Preferences.getInstances(getContext()).getChatCount();

            default:
                return "";
        }

    }

        public String getMenuName(String menuDisplayName) {

            switch (menuDisplayName) {

                case "schedule":
                    return "Schedule";

                case "mycalendar":
                    return "My Calendar";

                case "speakers":
                    return "Speakers";

                case "exhibitors":
                    return "Exhibitors";

                case "sponsors":
                    return "Sponsors";

                case "maps":
                    return "Maps";

                case "detail":
                    return "Home";

                case "live":
                    return "Live";

                case "social":
                    return "Social";

                case "logistics":
                    return "Logistics";

                case "logistics2":
                    return "Logistics2";

                case "logistics3":
                    return "Logistics3";

                case "my account":
                    return "My Account";

                case "survey":
                    return "Survey";

                case "attendees":
                    return "Attendees";

                case "matches":
                    return "Matches";

                case "messages":
                    return "Messages";

                case "Alerts":
                    return "Alerts";

                case "downloads":
                    return "Downloads";

                case "qrcode":
                    return "QrCode";

                case "search":
                    return "Search";

                case "discussion_board":
                    return "Discussion Board";

                case "my_notes":
                    return "Notes";

                case "things_to_do":
                    return "Things To Do";

                case "photo_gallery":
                    return "Photo Gallery";

                case "i2i":
                    return "i2i";

                case "chat":
                    return "Chat";

                case "game":
                    return "Game";

                default:
                    return "";
            }
        }


        public int getMenuIcon(String menuDisplayName) {

        switch (menuDisplayName) {

            case "schedule":
                return R.drawable.dash_schedule_blue;

            case "mycalendar":
                return R.drawable.dash_calender;

            case "speakers":
                return R.drawable.dash_speakers_blue;

            case "exhibitors":
                return R.drawable.dash_exhibitor_blue;

            case "sponsors":
                return R.drawable.dash_sponsor_blue;

            case "maps":
                return R.drawable.dash_maps_blue;

            case "detail":
                return R.drawable.dash_home_new_blue;

            case "live":
                return R.drawable.dash_live_blue;

            case "social":
                return R.drawable.dash_social_blue;

            case "logistics":
            case "logistics2":
            case "logistics3":
                return R.drawable.dash_logistics_blue;

            case "my account":
                return R.drawable.dash_myaccount_new_blue;

            case "survey":
                return R.drawable.dash_survey_blue;

            case "attendees":
                return R.drawable.dash_attendees_icon_blue;

            case "matches":
                return R.drawable.dash_schedule_blue;

            case "messages":
                return R.drawable.dash_messages_blue;
            case "Alerts":
                return R.drawable.dash_alert_blue;
            case "downloads":
                return R.drawable.dash_download_blue;

            case "qrcode":
                return R.drawable.dash_qrcode_blue;

            case "search":
                return R.drawable.dash_search_blue;
            case "discussion_board":
                return R.drawable.dash_discussionboard;
            case "my_notes":
                return R.drawable.my_note;
            case "things_to_do":
                return R.drawable.todo;
            case "photo_gallery":
                return R.drawable.dash_photo_galler_white;
            case "i2i":
                return R.drawable.dash_i2i;
            case "chat":
                return R.drawable.dash_chat;
            case "game":
                return R.drawable.dash_game_white;

            default:
                return R.drawable.dash_home_new_blue;
        }

    }

}*/


public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.ViewHolder> {

    String eventColor;
    private Context mContext;
    private List<MenuData> menuList = new ArrayList<>();
    private boolean showAlertCount = false;

    public MenuListAdapter(Context mContext) {
        this.mContext = mContext;
        eventColor = EventDAO.getInstance(mContext).getValue(EventTable.COLOR_THEME);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.menu_icon, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String menuName = menuList.get(position).getMenuName();
        holder.menuName.setText(menuName);
        showAlertCount = menuName.equalsIgnoreCase("mycalendar") || menuName.equalsIgnoreCase("chat") || menuName.equalsIgnoreCase("messages") || menuName.equalsIgnoreCase("Alerts");

        displayIcon(holder, showAlertCount, menuName, position);

    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public MenuData getItemAtPos(int pos) {
        return menuList.get(pos);
    }

    public void addItem(MenuData menuData) {
        menuList.add(menuData);
    }

    public void addAll(List<MenuData> mlist) {
        menuList.addAll(mlist);
    }

    public void removeItem(int position) {
        menuList.remove(position);
    }

    public void getTheme() {

    }

    public void displayIcon(ViewHolder holder, boolean showCountLayout, String menuName, int position) {

        if (showCountLayout) {
            String count = getAlertCount(menuName);
            if (!count.equals("0") && !count.equals("")) {
                holder.alertText.setVisibility(View.VISIBLE);
                holder.alertText.setText(count);
            }
        } else {
            holder.alertText.setVisibility(View.GONE);
        }

        String displayMenuName = getItemAtPos(position).getmDisplayName();

        if (!displayMenuName.equals("")) {
            holder.menuName.setText(displayMenuName);
        } else {
            holder.menuName.setText(getMenuName(menuName));
        }

        String imageIconUrl = getItemAtPos(position).getMenuIconUrl();

        if (imageIconUrl.equalsIgnoreCase("") || imageIconUrl == null) {
            holder.menuIcon.setImageResource(getMenuIcon(menuName));
        } else {
            File fileDir = Utility.getBaseFolder(mContext, mContext.getString(R.string.Resources) + File.separator + mContext.getString(R.string.menus));
            File file = new File(fileDir, getItemAtPos(position).getMenuName());
            if (file.exists()) {
                ImageLoader.getInstance().displayImage("file://" + file.getAbsolutePath(), holder.menuIcon);
            } else {
                holder.menuIcon.setImageResource(getMenuIcon(displayMenuName));
            }
        }

        try {
            int color = Color.parseColor(eventColor);
            GradientDrawable drawable = (GradientDrawable) holder.menuIcon.getBackground();
            drawable.setColor(color);
        } catch (Exception e) {
            e.printStackTrace();
        }

//       mHolder.menuIcon.getBackground().(color, PorterDuff.Mode.MULTIPLY);
    }


    public String getMenuName(String menuDisplayName) {

        switch (menuDisplayName) {

            case "schedule":
                return "Schedule";

            case "mycalendar":
                return "My Calendar";

            case "speakers":
                return "Speakers";

            case "exhibitors":
                return "Exhibitors";

            case "sponsors":
                return "Sponsors";

            case "maps":
                return "Maps";

            case "detail":
                return "Home";

            case "live":
                return "Live";

            case "social":
                return "Social";

            case "logistics":
                return "Logistics";

            case "logistics2":
                return "Logistics2";

            case "logistics3":
                return "Logistics3";

            case "my account":
                return "My Account";

            case "survey":
                return "Survey";

            case "attendees":
                return "Attendees";

            case "matches":
                return "Matches";

            case "messages":
                return "Messages";

            case "Alerts":
                return "Alerts";

            case "downloads":
                return "Downloads";

            case "qrcode":
                return "QrCode";

            case "search":
                return "Search";

            case "discussion_board":
                return "Discussion Board";

            case "my_notes":
                return "Notes";

            case "things_to_do":
                return "Things To Do";

            case "photo_gallery":
                return "Photo Gallery";

            case "i2i":
                return "i2i";

            case "chat":
                return "Chat";

            case "game":
                return "Game";

            default:
                return "";
        }
    }

    public int getMenuIcon(String menuDisplayName) {

        switch (menuDisplayName) {

            case "schedule":
                return R.drawable.dash_schedule_blue;

            case "mycalendar":
                return R.drawable.dash_calender;

            case "speakers":
                return R.drawable.dash_speakers_blue;

            case "exhibitors":
                return R.drawable.dash_exhibitor_blue;

            case "sponsors":
                return R.drawable.dash_sponsor_blue;

            case "maps":
                return R.drawable.dash_maps_blue;

            case "detail":
                return R.drawable.dash_home_new_blue;

            case "live":
                return R.drawable.dash_live_blue;

            case "social":
                return R.drawable.dash_social_blue;

            case "logistics":
            case "logistics2":
            case "logistics3":
                return R.drawable.dash_logistics_blue;

            case "my account":
                return R.drawable.dash_myaccount_new_blue;

            case "survey":
                return R.drawable.dash_survey_blue;

            case "attendees":
                return R.drawable.dash_attendees_icon_blue;

            case "matches":
                return R.drawable.dash_schedule_blue;

            case "messages":
                return R.drawable.dash_messages_blue;
            case "Alerts":
                return R.drawable.dash_alert_blue;
            case "downloads":
                return R.drawable.dash_download_blue;

            case "qrcode":
                return R.drawable.dash_qrcode_blue;

            case "search":
                return R.drawable.dash_search_blue;
            case "discussion_board":
                return R.drawable.dash_discussionboard;
            case "my_notes":
                return R.drawable.my_note;
            case "things_to_do":
                return R.drawable.todo;
            case "photo_gallery":
                return R.drawable.dash_photo_galler_white;
            case "i2i":
                return R.drawable.dash_i2i;
            case "chat":
                return R.drawable.dash_chat;
            case "game":
                return R.drawable.dash_game_white;

            default:
                return R.drawable.dash_home_new_blue;
        }

    }

    public String getAlertCount(String menu) {

        switch (menu) {

            case "mycalendar":
                return Preferences.getInstances(mContext).getCalendarCount();

            case "messages":
                return Preferences.getInstances(mContext).getUnreadMessages();

            case "Alerts":
                return Preferences.getInstances(mContext).getAlertCount();

            case "chat":
                return Preferences.getInstances(mContext).getChatCount();
            default:
                return "";
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView menuIcon;
        TextView menuName, alertText;
        public ViewHolder(View itemView) {
            super(itemView);
            menuIcon = (ImageView) itemView.findViewById(R.id.menuIcon);
            menuName = (TextView) itemView.findViewById(R.id.menuName);
            alertText = (TextView) itemView.findViewById(R.id.alertText);
        }
    }
}
