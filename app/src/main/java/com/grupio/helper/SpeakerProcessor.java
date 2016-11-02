package com.grupio.helper;

import android.content.Context;

import com.grupio.R;
import com.grupio.dao.VersionDao;
import com.grupio.data.LogisticsData;
import com.grupio.data.SpeakerData;
import com.grupio.data.VersionData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JSN on 4/8/16.
 */
public class SpeakerProcessor {


    public SpeakerProcessor() {
    }

    public List<SpeakerData> getSpeakersListFromJSON(Context mcontext, String str1) {

        List<SpeakerData> mList = new ArrayList<>();

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(str1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonObject != null) {
            try {
                VersionData vData = new VersionData();
                vData.name = VersionDao.SPEAKER_VERSION;
                vData.oldVersion = jsonObject.getString("version");
                VersionDao.getInstance(mcontext).insertDataInOldColumn(vData);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONArray jArray = null;
            try {
                jArray = jsonObject.getJSONArray("data");
            } catch (Exception e) {
                return null;
            }
            if (jArray != null && jArray.length() > 0) {

                SpeakerData data = null;

                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject jObject = null;
                    try {
                        jObject = jArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    if (jObject != null) {
                        data = new SpeakerData();
                        try {
                            data.setFirst_name(jObject.getString("first_name").trim());
                        } catch (Exception e) {
                        }

                        try {
                            data.setLast_name(jObject.getString("last_name").trim());
                        } catch (Exception e) {
                        }

                        try {
                            data.setCompany(jObject.getString("company").trim());
                        } catch (Exception e) {
                        }

                        try {
                            data.setTitle(jObject.getString("title").trim());
                        } catch (Exception e) {
                        }

                        try {
                            data.setAttendee_id(jObject.getString("attendee_id").trim());
                        } catch (Exception e) {
                        }

                        try {

                            // String str = jObject.getString("image").trim();
                            String str = jObject.getString("large_image").trim();
                            if (!str.equals("") || str.contains("https"))
                                str = mcontext.getString(R.string.base_url) + str;
                            data.setImageUrl(str);

                        } catch (Exception e) {
                        }

                        try {
                            data.setEmail((jObject.getString("email")));
                        } catch (Exception e) {
                        }

                        try {
                            data.setBio(jObject.getString("bio"));
                        } catch (Exception e) {
                        }

                        try {
                            data.setPrimary_phone((jObject.getString("primary_phone")));
                        } catch (Exception e) {
                        }

                        try {
                            data.setEnable_contacts(jObject.getString("enable_contacts"));
                        } catch (Exception e) {
                        }

                        try {
                            data.setEnable_messaging((jObject.getString("enable_messaging")));
                        } catch (Exception e) {
                        }

                        try {
                            JSONArray jarray = jObject.getJSONArray("sessions");
                            data.setSessionListAsString(jarray.toString());

                            if (jarray != null && jarray.length() != 0) {
                                String[] sessionsArray = new String[jarray.length()];
                                for (int j = 0; j < jarray.length(); j++) {
                                    // Log.i("json array obj "+j,
                                    // ""+jarray.getString(j));
                                    sessionsArray[j] = jarray.getString(j);
                                }
                                data.setSessionId(sessionsArray);
                            }
                        } catch (Exception e) {
                        }

                        try {
                            data.setSpeakerlinksAsString(jObject.getJSONArray("speakerlinks").toString());

                        } catch (Exception e) {
                        }

                        try {
                            data.setHide_attendee(jObject.getString("hide_attendee"));
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
                            data.setHideContactInfo(jObject.getString("hide_contact_info").trim());
                        } catch (Exception e) {
                        }
                        mList.add(data);
                    }
                }
            }

        }


        return mList;
    }

    public List<LogisticsData> parseLinksJson(String response) {

        List<LogisticsData> tempList = new ArrayList();

        JSONArray jarray = null;

        try {
            jarray = new JSONArray(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jarray != null && jarray.length() > 0) {

            LogisticsData linkObj;

            for (int j = 0; j < jarray.length(); j++) {

                linkObj = new LogisticsData();


                try {
                    linkObj.setUrl(jarray.getJSONObject(j).getString("url"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    linkObj.setDoctype(jarray.getJSONObject(j).getString("type"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    linkObj.setLogistics_id(j + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    linkObj.setName(jarray.getJSONObject(j).getString("name"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
                tempList.add(linkObj);

            }

        }

        return tempList;

    }


}
