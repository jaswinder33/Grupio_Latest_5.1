package com.grupio.helper;

import android.content.Context;

import com.grupio.R;
import com.grupio.dao.VersionDao;
import com.grupio.data.ExhibitorData;
import com.grupio.data.LogisticsData;
import com.grupio.data.VersionData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by JSN on 8/8/16.
 */
public class ExhibitorProcessor {

    public List<ExhibitorData> getExhibitorsListFromJSON(Context mContext, String response) {

        List<ExhibitorData> mList = new ArrayList<>();

        JSONObject mJObj = null;
        try {
            mJObj = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (mJObj != null) {

            try {
                VersionData vData = new VersionData();
                vData.name = VersionDao.EXHIBITORS_VERSION;
                vData.oldVersion = mJObj.getString("version");
                VersionDao.getInstance(mContext).insertDataInOldColumn(vData);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONArray jArray = null;

            try {
                jArray = mJObj.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            if (jArray != null && jArray.length() > 0) {
                ExhibitorData data = null;

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jObject = null;
                    try {
                        jObject = jArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    if (jObject != null) {
                        data = new ExhibitorData();

                        try {
                            data.setExhibitorId(jObject.getString("exhibitor_id").trim());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            data.setName(jObject.getString("name").trim());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            String strng = jObject.getString("image").trim();
                            if (!strng.equals("") || strng.contains("https"))
                                strng = mContext.getString(R.string.base_url) + strng;

                            data.setImage(strng);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        try {

                            // String strng = jObject.getString("image").trim();
                            String strng = jObject.getString("large_image").trim();
                            if (!strng.equals("") || strng.contains("https"))
                                strng = mContext.getString(R.string.base_url) + strng;
                            data.setImageLarge(strng);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            data.setLocation(jObject.getString("location").trim());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            data.setCategory(jObject.getString("category").trim());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            data.setDescription(jObject.getString("description").trim());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            data.setWeburl(jObject.getString("weburl").trim());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            JSONArray jarray = jObject.getJSONArray("exhibitorlinks");
                            data.setResourceListAsSrings(jarray.toString());
                        } catch (Exception e) {
//                            e.printStackTrace();
                        }

                        // Parse list of attendee IDS
                        try {
                            JSONArray jarray = jObject.getJSONArray("attendees");
                            data.setAttendeeListAsString(jarray.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            data.setContact_email(jObject.getString("contact_email").trim());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            data.setContact_phone(jObject.getString("contact_phone").trim());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            data.setAddress(jObject.getString("address").trim());
                        } catch (Exception e) {
                        }


                        mList.add(data);
                    }
                }
            }


        }


        return mList;
    }

    public List<LogisticsData> parseResourceList(String response) {
        List<LogisticsData> mList = new ArrayList<>();

        JSONArray jarray = null;
        try {
            jarray = new JSONArray(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (jarray != null && jarray.length() != 0) {

            for (int j = 0; j < jarray.length(); j++) {

                JSONObject sessionMapObject = null;
                try {
                    sessionMapObject = jarray.getJSONObject(j);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if (sessionMapObject != null) {
                    LogisticsData linkObj = new LogisticsData();

                    try {
                        linkObj.setUrl(sessionMapObject.getString("url"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    try {
                        linkObj.setDoctype(sessionMapObject.getString("type"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        linkObj.setLogistics_id(j + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    try {
                        linkObj.setName(sessionMapObject.getString("name"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    mList.add(linkObj);
                }

            }
        }

        return mList;

    }

    public List<String> parseAttendeeList(String response) {

        List<String> mList = new ArrayList<>();

        JSONArray jarray = null;
        try {
            jarray = new JSONArray(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jarray != null && jarray.length() != 0) {
            for (int j = 0; j < jarray.length(); j++) {
                try {
                    mList.add(jarray.getString(j).trim());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


        return mList;
    }

    public ArrayList<String> parseCategoryResult(String response) {

        ArrayList<String> mTList = new ArrayList<String>();

        JSONArray mArr = null;
        try {
            mArr = new JSONArray(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (mArr != null && mArr.length() > 0) {
            for (int i = 0; i < mArr.length(); i++) {
                try {
                    mTList.add(mArr.getString(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        Comparator<String> ExhibitorTrackSort = new Comparator<String>() {
            @Override
            public int compare(String object1, String object2) {
                // sort by map name...
                return object1.toLowerCase().compareTo(object2.toLowerCase());
            }
        };

        Collections.sort(mTList, ExhibitorTrackSort);

        return mTList;

    }


}
