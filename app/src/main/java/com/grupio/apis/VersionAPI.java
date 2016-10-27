package com.grupio.apis;

import android.content.Context;
import android.util.Log;

import com.grupio.R;
import com.grupio.api_request.APIRequest;
import com.grupio.api_request.GetRequest;
import com.grupio.dao.VersionDao;
import com.grupio.data.VersionData;
import com.grupio.helper.VersionProcessor;
import com.grupio.session.Preferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by JSN on 19/8/16.
 */

/**
 * This api fetch versions of all apis and store in db.
 *
 * @param </>event_id = event_id of event.
 *                 format = format required is json.
 *                 <p/>
 *                 Sample link:
 *                 https://conf.dharanet.com/conf/v1/main/getversions.php?format=json&event_id=151
 */
public class VersionAPI extends BaseCallable<List<ApiInter>> {

    public VersionAPI(Context mContext) {
        super(mContext);
    }

    @Override
    public String getEndPoint() {
        return mContext.getString(R.string.versions_api) + Preferences.getInstances(mContext).getEventId();
    }

    @Override
    public List<ApiInter> callApi() {
        APIRequest request = new GetRequest();
        String response =  request.requestResponse(url, new HashMap<String, String>(),mContext);

        Log.i("API", "VersionAPI");

        List<VersionData> versionList = new ArrayList<>();
        if (response != null) {
            VersionProcessor vp = new VersionProcessor();
            VersionDao.getInstance(mContext).insertDataInNewColumn(vp.parseResult(response));
            versionList.addAll(VersionDao.getInstance(mContext).getMisMatchedVersions());
        }
        return prepareMenuList(versionList);
    }


//    @Override
//    public List<ApiInter> call() throws Exception {
//
//        APIRequest request = new GetRequest();
//        String response =  request.requestResponse(url, new HashMap<String, String>(),mContext);
//
//        Log.i("API", "VersionAPI");
//
//        List<VersionData> versionList = new ArrayList<>();
//        if(response != null){
//            VersionProcessor vp = new VersionProcessor();
//            VersionDao.getInstance(mContext).insertDataInNewColumn(vp.parseResult(response));
//            versionList.addAll(VersionDao.getInstance(mContext).getMisMatchedVersions());
//        }
//        return  prepareMenuList(versionList);
//    }

    public List<ApiInter> prepareMenuList(List<VersionData> versionList) {

        List<ApiInter> mList = new ArrayList<>();

        for (int i = 0; i < versionList.size(); i++) {

            switch (versionList.get(i).name) {

                case VersionDao.MENU_VERSION:
                    mList.add(new MenuAPI(mContext));
                    break;
                case VersionDao.GRAPHICS_VERSION:
                    mList.add(new GraphicsAPI(mContext));
                    break;
                case VersionDao.TRACK_VERSION:
//                    mList.add(new (mContext));
                    break;
                case VersionDao.LANGUAGE_VERSION:
                    mList.add(new LocaleApi(mContext));
                    break;
                case VersionDao.ADS_VERSION:
                    mList.add(new Ads(mContext));
                    break;
                case VersionDao.SURVEYS_VERSION:
                    mList.add(new SurveyAPI(mContext));
                    break;
                case VersionDao.LOGISTICS_VERSION:
                    mList.add(new LogisticsAPI(mContext));
                    break;
                case VersionDao.MAP_VERSION:
                    mList.add(new MapsAPI(mContext));
                    break;
                case VersionDao.NEWSFEEDMENU_VERSION:
                    break;
                case VersionDao.MATCHES_VERSION:
                    break;

                case VersionDao.LIVEFEED_VERSION:
                    break;

                case VersionDao.SPONSORS_VERSION:
                    mList.add(new SponsorAPI(mContext));
                    break;

                case VersionDao.EXHIBITORS_VERSION:
                    mList.add(new ExhibitorAPI(mContext));
                    break;
                case VersionDao.ATTENDEE_VERSION:
                    mList.add(new AttendeeAPI(mContext));
                    break;
                case VersionDao.SPEAKER_VERSION:
                    mList.add(new SpeakerAPI(mContext));
                    break;
                case VersionDao.SCHEDULE_VERSION:
                    mList.add(new SessionsAPI(mContext));
                    break;
                case VersionDao.EVENT_VERSION:
                    mList.add(new EventDetailAPI(mContext));
                    break;
            }
        }
        return mList;
    }


}
