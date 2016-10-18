package com.grupio.gridhome;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.activities.EventSpecificSplash;
import com.grupio.animation.SlideOut;
import com.grupio.data.EventData;
import com.grupio.data.MenuData;
import com.grupio.eventlist.EventListActivity;
import com.grupio.session.ConstantData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 22/9/16.
 */
public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.ViewHolder> {

    private Context mContext;
    private List<MenuData> menuList = new ArrayList<>();
    private boolean showAlertCount = false;

    public MenuListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.menu_icon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String menuName  = menuList.get(position).getMenuName();
        holder.menuName.setText(menuName);
        if(menuName.equalsIgnoreCase("mycalendar") || menuName.equalsIgnoreCase("chat")  || menuName.equalsIgnoreCase("messages") || menuName.equalsIgnoreCase("Alerts")){
            showAlertCount = true;
        }else{
            showAlertCount = false;
        }

        displayIcon(holder, showAlertCount, menuName, position);

    }

    @Override
    public int getItemCount() {
        return menuList.size();
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

    public void addItem(MenuData menuData) {
        menuList.add(menuData);
    }

    public void addAll(List<MenuData> mlist) {
        menuList.addAll(mlist);
    }

    public void removeItem(int position) {
        menuList.remove(position);
    }

    public void getTheme(){

    }

    public void displayIcon(ViewHolder mHolder, boolean showCountLayout, String menuName, int position){

        if(showCountLayout){
            mHolder.alertText.setVisibility(View.VISIBLE);
        }else{
            mHolder.alertText.setVisibility(View.GONE);
        }

        String displayMenuName = menuList.get(position).getmDisplayName();

        if (!displayMenuName.equals("")) {
            mHolder.menuName.setText(displayMenuName);
        } else {
            mHolder.menuName.setText(getMenuName(menuName));
        }

        String imageIconUrl = menuList.get(position).getMenuIconUrl();

        if(imageIconUrl.equalsIgnoreCase("") || imageIconUrl == null){
            mHolder.menuIcon.setImageResource(getMenuIcon(menuName));
        }else{
            File fileDir = Utility.getBaseFolder(mContext, ConstantData.RESOURCES + File.separator + ConstantData.MENUS);
            File file = new File(fileDir,menuList.get(position).getMenuName());
            if(file.exists()){
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions);
                bitmap = Bitmap.createBitmap(bitmap);
                mHolder.menuIcon.setImageBitmap(bitmap);
            }else{
                mHolder.menuIcon.setImageResource(getMenuIcon(displayMenuName));
            }

        }


    }

    public String getMenuName(String menuDisplayName){

        switch (menuDisplayName){

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
    };

    public int getMenuIcon(String menuDisplayName){

        switch (menuDisplayName){

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
}
