package com.grupio.helper;


import android.content.Context;
import android.util.Log;

import com.grupio.apis.ApiInter;
import com.grupio.apis.AttendeeAPI;
import com.grupio.apis.ExhibitorAPI;
import com.grupio.apis.LogisticsAPI;
import com.grupio.apis.MapsAPI;
import com.grupio.apis.SessionsAPI;
import com.grupio.apis.SpeakerAPI;
import com.grupio.apis.SponsorAPI;
import com.grupio.apis.SurveyAPI;


/**
 * Created by JSN on 22/8/16.
 */
public class MenuListManager {

    public static final String TAG ="MenuListManager";

    public ApiInter getApiInstance(String name, Context context) {

        switch (name) {

            case "schedule":
                Log.i(TAG, "getApiInstance: schedule instance added");
                return new SessionsAPI(context);

            case "speakers":
                Log.i(TAG, "getApiInstance: speakers instance added");
                return new SpeakerAPI(context);

            case "maps":
                Log.i(TAG, "getApiInstance: maps instance added");
                return new MapsAPI(context);

            case "exhibitors":
                Log.i(TAG, "getApiInstance: exhibitors instance added");
                return new ExhibitorAPI(context);

            case "sponsors":
                Log.i(TAG, "getApiInstance: sponsors instance added");
                return new SponsorAPI(context);

            case "logistics":
            case "logistics1":
            case "logistics2":
            case "logistics3":
            case "logistics4":
            case "logistics5":
            case "logistics6":
            case "logistics7":
                Log.i(TAG, "getApiInstance: logistics instance added");
                return new LogisticsAPI(context);

            case "survey":
                Log.i(TAG, "getApiInstance: survey instance added");
                return new SurveyAPI(context);

            case "attendees":
                Log.i(TAG, "getApiInstance: attendees instance added");
                return new AttendeeAPI(context);

            default:
                Log.i(TAG, "getApiInstance: null instance added");
                return null;

        }


    }

}
