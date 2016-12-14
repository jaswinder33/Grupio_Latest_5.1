package com.grupio.helper;


import android.content.Context;

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
    public ApiInter getApiInstance(String name, Context context) {
        switch (name) {
            case "schedule":
                return new SessionsAPI(context);

            case "speakers":
                return new SpeakerAPI(context);

            case "maps":
                return new MapsAPI(context);

            case "exhibitors":
                return new ExhibitorAPI(context);

            case "sponsors":
                return new SponsorAPI(context);

            case "logistics":
            case "logistics1":
            case "logistics2":
            case "logistics3":
            case "logistics4":
            case "logistics5":
            case "logistics6":
            case "logistics7":
                return new LogisticsAPI(context);

            case "survey":
                return new SurveyAPI(context);

            case "attendees":
                return new AttendeeAPI(context);

            default:
                return null;

        }


    }

}
