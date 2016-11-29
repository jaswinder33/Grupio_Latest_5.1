package com.grupio.helper;

import android.content.Context;

import com.grupio.dao.VersionDao;
import com.grupio.data.AttendeesData;
import com.grupio.data.LogisticsData;
import com.grupio.data.VersionData;
import com.grupio.data.mapList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by JSN on 26/7/16.
 */
public class AttendeeProcessor {

    Comparator<String> categorySort = new Comparator<String>() {
        @Override
        public int compare(String object1, String object2) {
            return object1.compareTo(object2);
        }
    };
    private boolean isFirstName = false;
    Comparator<AttendeesData> attendeeNameSort = new Comparator<AttendeesData>() {
        @Override
        public int compare(AttendeesData object1, AttendeesData object2) {

            if (isFirstName) {
                return object1.getFirst_name().toLowerCase().compareTo(object2.getFirst_name().toLowerCase());
            } else {
                return object1.getLast_name().toLowerCase().compareTo(object2.getLast_name().toLowerCase());
            }

        }
    };

    public List<AttendeesData> getAttendeesListFromJSON(Context mContext, String response, boolean saveVersion) {

        List<AttendeesData> mList = new ArrayList<>();

        JSONObject jObj = null;

        try {
            jObj = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jObj != null) {

             /*
            Store version in db
             */

            if (saveVersion) {
                try {
                    String version = jObj.getString("version");
                    VersionData vData = new VersionData();
                    vData.name = VersionDao.ATTENDEE_VERSION;
                    vData.oldVersion = version;
                    VersionDao.getInstance(mContext).insertDataInOldColumn(vData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            JSONArray jArray = null;
            try {
                jArray = jObj.getJSONArray("data");
            } catch (Exception e) {
                return null;
            }

            if (jArray != null && jArray.length() > 0) {

                AttendeesData data;

                for (int i = 0; i < jArray.length(); i++) {

                    data = new AttendeesData();

                    JSONObject jObject = null;
                    try {
                        jObject = jArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    if (jObject != null) {
                        try {
                            data.setFirst_name(jObject.getString("first_name").trim());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            data.setType(jObject.getString("type").trim());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            data.setCompany(jObject.getString("company").trim());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            String str = jObject.getString("large_image").trim();
                            if (!str.equals("") || str.contains("https")) {
//                            str = ConstantData.BASE_URL + ConstantData.DOMAIN + ConstantData.VERSON + ConstantData.AREA + str;
                                data.setImage(str);
                            }
                        } catch (Exception e) {
                        }

                        try {
                            data.setLast_name(jObject.getString("last_name").trim());
                        } catch (Exception e) {
                        }

                        try {
                            data.setTitle(jObject.getString("title").trim());
                        } catch (Exception e) {

                        }

                        try {
                            data.setEmail(jObject.getString("email").trim());
                        } catch (Exception e) {

                        }

                        try {
                            data.setPrimary_phone(jObject.getString("primary_phone").trim());
                        } catch (Exception e) {

                        }

                        try {
                            data.setSecondary_phone(jObject.getString("secondary_phone").trim());
                        } catch (Exception e) {

                        }

                        try {
                            data.setTwitter_id(jObject.getString("twitter_id").trim());
                        } catch (Exception e) {

                        }

                        try {
                            data.setLinkedin(jObject.getString("linkedin").trim());
                        } catch (Exception e) {
                        }

                        try {
                            data.setWebsite(jObject.getString("website").trim());
                        } catch (Exception e) {
                        }

                        try {
                            data.setAttendee_id(jObject.getString("attendee_id").trim());
                        } catch (Exception e) {
                        }

                        try {
                            data.setBio(jObject.getString("bio").trim());
                        } catch (Exception e) {
                        }

                        try {
                            data.setHide_contact_info((jObject.getString("hide_contact_info")));
                        } catch (Exception e) {

                        }


                        try {
                            data.setEnable_messaging((jObject.getString("enable_messaging")));

                        } catch (Exception e) {

                        }

                        try {
                            data.setEnable_contacts((jObject.getString("enable_contacts")));
                        } catch (Exception e) {

                        }

                        try {
                            data.setEnable_match(jObject.getString("enable_match"));
                        } catch (Exception e) {

                        }

                        try {
                            data.setCategory(jObject.getString("group_name"));
                        } catch (Exception e) {

                        }

                        try {
                            data.setIntrests(jObject.getString("intrests"));
                        } catch (Exception e) {

                        }


                        try {

                            data.sessionsAsString = jObject.getJSONArray("sessions").toString();

                            JSONArray jarray = jObject.getJSONArray("sessions");

                            if (jarray != null && jarray.length() != 0) {
                                String[] sessionsArray = new String[jarray.length()];
                                for (int j = 0; j < jarray.length(); j++) {
                                    sessionsArray[j] = jarray.getString(j);
                                }
                                data.setSessionId(sessionsArray);
                            }
                    /*
                     * Dummy for test
					 */
//					String[] sessionsArray = new String[1];
//					sessionsArray[0] = "333558";
//					data.setSessionId(sessionsArray);

                        } catch (Exception e) {

                        }

                        try {
                            data.setKeywords(jObject.getString("keywords").trim());
                        } catch (Exception e) {

                        }

                        List<mapList> mapList = null;

                        //set the attendee Link list..for map and url....
                        try {

                            data.mapListOfAttendeeAsString = jObject.getJSONArray("attendeelinks").toString();

                            JSONArray jarray = jObject.getJSONArray("attendeelinks");

                            mapList = new ArrayList<com.grupio.data.mapList>();
                            if (jarray != null && jarray.length() != 0) {

                                for (int j = 0; j < jarray.length(); j++) {
                                    JSONObject sessionMapObject = jarray.getJSONObject(j);
                                    com.grupio.data.mapList mapdata = new mapList();
                                    mapdata.setMapId(jObject.getString("attendee_id").trim());
                                    mapdata.setMapName(sessionMapObject.getString("name").trim());
                                    mapdata.setMapUrl(sessionMapObject.getString("url").trim());
                                    mapList.add(mapdata);
                                }
                            }
                            data.setMapListOfAttendee(mapList);
                        } catch (Exception e) {

                        }

                        mList.add(data);
                    }

                }

            }

        }


        return mList;
    }

    //	prepare attendee category list from attendee list
    public ArrayList<String> fetchCategoryList(List<AttendeesData> mList) {
        ArrayList<String> categoryList = new ArrayList<String>();

        for (int i = 0; i < mList.size(); i++) {

            if (!categoryList.contains(mList.get(i).getCategory()) && !mList.get(i).getCategory().equals("")) {
                categoryList.add(mList.get(i).getCategory());
            }
        }

        Collections.sort(categoryList, categorySort);

        // add the All item on top of list...
        categoryList.add(0, "All");
        return categoryList;
    }

    public List<LogisticsData> parseResources(String resources) {
        JSONArray jarray = null;
        try {
            jarray = new JSONArray(resources);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<LogisticsData> mapList = new ArrayList<>();

        if (jarray != null && jarray.length() != 0) {

            for (int j = 0; j < jarray.length(); j++) {
                JSONObject sessionMapObject = null;
                try {
                    sessionMapObject = jarray.getJSONObject(j);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (sessionMapObject != null) {
                    LogisticsData mapdata = new LogisticsData();

                    try {
                        mapdata.setName(sessionMapObject.getString("name").trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        mapdata.setUrl(sessionMapObject.getString("url").trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mapList.add(mapdata);
                }


            }
        }

        return mapList;

    }

    public void sortAttendee(boolean isFirstName, List<AttendeesData> mList) {
        this.isFirstName = isFirstName;
        Collections.sort(mList, attendeeNameSort);
    }



}
