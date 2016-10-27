package com.grupio.helper;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.grupio.R;
import com.grupio.dao.VersionDao;
import com.grupio.data.VersionData;
import com.grupio.service.DataFetchService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JSN on 24/8/16.
 */
public class GraphicsProcessor {

    private String splash_image = "";
    private String menu_background_image;
    private String main_top_nav_img;
    private String top_nav_img;
    private String app_background_img;
    private String app_back_button_img;
    private String app_dashboard_button_img;
    private String dlscreen_background;

    public Map<String, String> parseResult(String response, Context mContext) {

        Map<String, String> urlList = new HashMap<>();

        JSONObject mJobj = null;
        try {
            mJobj = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (mJobj != null) {

            /*
            Store version in db
             */
            try {
                String version = mJobj.getString("version");
                VersionData vData = new VersionData();
                vData.name = VersionDao.GRAPHICS_VERSION;
                vData.oldVersion = version;
                VersionDao.getInstance(mContext).insertDataInOldColumn(vData);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            /*
            Set screen params
             */
            setParams(mContext);

            /*
            Process data
             */
            JSONArray jArray = null;
            try {
                jArray = mJobj.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (jArray != null && jArray.length() > 0) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = jArray.getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (jsonObject != null) {

                    try {
                        urlList.put(mContext.getString(R.string.splash), jsonObject.getString(splash_image));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        urlList.put(mContext.getString(R.string.menu_background), jsonObject.getString(menu_background_image));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        urlList.put(mContext.getString(R.string.main_header), jsonObject.getString(main_top_nav_img));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        urlList.put(mContext.getString(R.string.top_nav), jsonObject.getString(top_nav_img));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        urlList.put(mContext.getString(R.string.app_background), jsonObject.getString(app_background_img));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        urlList.put(mContext.getString(R.string.app_back_button), jsonObject.getString(app_back_button_img));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        urlList.put(mContext.getString(R.string.app_dashboard_button), jsonObject.getString(app_dashboard_button_img));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        urlList.put(mContext.getString(R.string.dlscreen_background), jsonObject.getString(dlscreen_background));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return urlList;
    }

    public void setParams(Context mContext) {

        DisplayMetrics metrics = new DisplayMetrics();

        WindowManager windowManager = (WindowManager) ((DataFetchService) mContext).getApplication().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);

        switch (metrics.densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                splash_image = "splash_screen_240_290";
                menu_background_image = "menu_background_240_290";
                main_top_nav_img = "main_top_nav_240_33";
                top_nav_img = "top_nav_240_33";
                app_background_img = "app_background_240_290";
                app_back_button_img = "back_button_240_320";
                app_dashboard_button_img = "dashboard_button_240_320";
                dlscreen_background = "dlscreen_background_480_212";

                break;

            case DisplayMetrics.DENSITY_MEDIUM:

                Display display = ((Activity) mContext).getWindowManager().getDefaultDisplay();


                if (display.getWidth() >= 600) {
                    splash_image = "splash_screen_320_416";
                    menu_background_image = "menu_background_800_1017";
                    main_top_nav_img = "main_top_nav_800_110";
                    top_nav_img = "top_nav_800_110";
                    app_background_img = "app_background_800_1017";
                    app_back_button_img = "back_button_800_1280";
                    app_dashboard_button_img = "dashboard_button_800_1280";
                    dlscreen_background = "dlscreen_background_480_212";

                } else {
                    splash_image = "splash_screen_320_416";
                    menu_background_image = "menu_background_320_416";
                    main_top_nav_img = "main_top_nav_320_45";
                    top_nav_img = "top_nav_320_45";
                    app_background_img = "app_background_320_460";
                    app_back_button_img = "back_button_320_480";
                    app_dashboard_button_img = "dashboard_button_320_480";
                    dlscreen_background = "dlscreen_background_480_212";
                }

                break;

            case DisplayMetrics.DENSITY_HIGH:
                splash_image = "splash_screen_480_670";
                menu_background_image = "menu_background_480_670";
                main_top_nav_img = "main_top_nav_480_66";
                top_nav_img = "top_nav_480_66";
                app_background_img = "app_background_480_800";
                app_back_button_img = "back_button_480_800";
                app_dashboard_button_img = "dashboard_button_480_800";
                dlscreen_background = "dlscreen_background_480_212";

                break;
            case DisplayMetrics.DENSITY_XHIGH:
                splash_image = "splash_screen_800_1017";
                menu_background_image = "menu_background_800_1017";
                main_top_nav_img = "main_top_nav_800_110";
                top_nav_img = "top_nav_800_110";
                app_background_img = "app_background_800_1017";
                app_back_button_img = "back_button_800_1280";
                app_dashboard_button_img = "dashboard_button_800_1280";
                dlscreen_background = "dlscreen_background_480_212";

                break;

            case DisplayMetrics.DENSITY_TV:
                splash_image = "splash_screen_480_670";
                menu_background_image = "menu_background_480_670";
                main_top_nav_img = "main_top_nav_480_66";
                top_nav_img = "top_nav_480_66";
                app_background_img = "app_background_480_800";
                app_back_button_img = "back_button_480_800";
                app_dashboard_button_img = "dashboard_button_480_800";
                dlscreen_background = "dlscreen_background_480_212";
                break;

            case DisplayMetrics.DENSITY_XXHIGH:
                splash_image = "splash_screen_800_1017";
                menu_background_image = "menu_background_800_1017";
                main_top_nav_img = "main_top_nav_800_110";
                top_nav_img = "top_nav_800_110";
                app_background_img = "app_background_800_1017";
                app_back_button_img = "back_button_800_1280";
                app_dashboard_button_img = "dashboard_button_800_1280";
                dlscreen_background = "dlscreen_background_480_212";
                break;

            case DisplayMetrics.DENSITY_XXXHIGH:
                splash_image = "splash_screen_800_1017";
                menu_background_image = "menu_background_800_1017";
                main_top_nav_img = "main_top_nav_800_110";
                top_nav_img = "top_nav_800_110";
                app_background_img = "app_background_800_1017";
                app_back_button_img = "back_button_800_1280";
                app_dashboard_button_img = "dashboard_button_800_1280";
                dlscreen_background = "dlscreen_background_480_212";
                break;
        }
    }

}
