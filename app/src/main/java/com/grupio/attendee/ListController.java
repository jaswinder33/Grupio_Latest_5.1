package com.grupio.attendee;

import android.app.Activity;
import android.content.Context;

import com.grupio.R;
import com.grupio.Utils.Utility;
import com.grupio.apis.AttendeeAPI;
import com.grupio.apis.SpeakerAPI;
import com.grupio.dao.AttendeeDAO;
import com.grupio.dao.EventDAO;
import com.grupio.dao.ExhibitorDAO;
import com.grupio.dao.SpeakerDAO;
import com.grupio.data.AttendeesData;
import com.grupio.data.ExhibitorData;
import com.grupio.data.SpeakerData;
import com.grupio.db.EventTable;
import com.grupio.interfaces.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by JSN on 25/7/16.
 */
public class ListController<T extends Person> implements ControllerInter {

    private Context mContext;
    private boolean isFirstName = false;
    private T type;
    private Person mPerson;

    /**
     * Constructor
     *
     * @param mContext
     */
    public ListController(T type, Context mContext) {
        this.mContext = mContext;
        this.type = type;
        isFirstName = EventDAO.getInstance(mContext).getValue(EventTable.NAME_ORDER).equals("firstname");
    }

    /**
     * Fetch category list from db.
     *
     * @param mListener
     */
    public void fetchCategoryList(onTaskComplete mListener) {
        fetchCatList(mListener);
    }

    /**
     * Fetch attendee/speaker list from server
     *
     * @param mListener
     */
    @Override
    public void fetchListFromServer(onTaskComplete mListener) {

        if (type instanceof AttendeesData) {
            fetchAttendeeListFromServer(mListener);
        } else if (type instanceof SpeakerData) {
            fetchSpeakerListFromServer(mListener);
        } else if (type instanceof ExhibitorData) {
            fetchExhibitorListFromServer(mListener);
        }
    }

    /**
     * Fetch attendee/speaker list from db.
     *
     * @param cateogory
     * @param mListener
     */
    @Override
    public void fetchListFromDB(String queryStr, String cateogory, onTaskComplete mListener) {
        if (type instanceof AttendeesData) {
            fetchAttendeeListFromDb(queryStr, cateogory, mListener);
        } else if (type instanceof SpeakerData) {
            fetchSpeakerListFromDb(queryStr, cateogory, mListener);
        } else if (type instanceof ExhibitorData) {
            fetchExhibitorListFromDb(queryStr, cateogory, mListener);
        }

    }

    public void fetchCatList(onTaskComplete mListener) {
        List<String> mlist = new ArrayList<>();

        if (type instanceof AttendeesData) {
            mlist.addAll(AttendeeDAO.getInstance(mContext).getCategoryList());
        } else if (type instanceof SpeakerData) {
            mlist.addAll(SpeakerDAO.getInstance(mContext).getCategoryList());
        } else if (type instanceof ExhibitorData) {
            /*
            Db query to fetch exhibitor category. But its not used. Separate API is implemented to fetch exhibitor category.
             */
            ExhibitorDAO.getInstance(mContext).getCategoryList();

//            mlist.addAll(fetchExhibitorCategory());
        }


        if (mlist != null && mlist.size() > 0) {
            mlist.add(0, "All");
            mListener.onCategoryFetch(mlist);
        } else {
            mListener.onCategoryFetch(null);
        }
    }

    public void fetchAttendeeListFromServer(final onTaskComplete mListener) {


        if (Utility.hasInternet(mContext)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean isSuccess = false;
                    ExecutorService es = Executors.newSingleThreadExecutor();
                    Future task = es.submit(new AttendeeAPI(mContext));

                    try {
                        task.get();
                        es.isShutdown();
                        isSuccess = true;
                    } catch (InterruptedException e) {
                        isSuccess = false;
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        isSuccess = false;
                        e.printStackTrace();
                    }

                    boolean finalIsSuccess = isSuccess;
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (finalIsSuccess) {
                                fetchAttendeeListFromDb(null, null, mListener);
                            } else {
                                mListener.onFailure("Error Occured...");
                            }
                        }
                    });
                }
            }).start();
        } else {
            mListener.onFailure(mContext.getResources().getText(R.string.no_internet).toString());
        }
    }


    public void fetchSpeakerListFromServer(final onTaskComplete mListener) {


        if (Utility.hasInternet(mContext)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean isSuccess = false;
                    ExecutorService es = Executors.newSingleThreadExecutor();
                    Future task = es.submit(new SpeakerAPI(mContext));

                    try {
                        task.get();
                        es.isShutdown();
                        isSuccess = true;
                    } catch (InterruptedException e) {
                        isSuccess = false;
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        isSuccess = false;
                        e.printStackTrace();
                    }

                    boolean finalIsSuccess = isSuccess;
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (finalIsSuccess) {
                                fetchCategoryList(mListener);
                            } else {
                                mListener.onFailure(mContext.getString(R.string.erroor_occured));
                            }
                        }
                    });
                }
            }).start();
        } else {
            mListener.onFailure(mContext.getResources().getText(R.string.no_internet).toString());
        }


//        APICall<SpeakerListAPI> api = new APICall<>(new SpeakerListAPI());
//        api.doCall(mContext, new ApiCallBack() {
//            @Override
//            public void onSuccess(Object response) {
//                fetchCategoryList(mListener);
//            }
//
//            @Override
//            public void onFailure(Object msg) {
//                mListener.onFailure((String) msg);
//            }
//        });


//        SpeakerListAPI api = new SpeakerListAPI(mContext);
//        api.doCall(new APICallBack() {
//            @Override
//            public void onSuccess() {
//                fetchCategoryList(mListener);
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                mListener.onFailure(msg);
//            }
//        });
    }

    private void fetchExhibitorListFromServer(onTaskComplete mListener) {

    }

    public void fetchAttendeeListFromDb(String queryStr, String cateogory, onTaskComplete mListener) {
        List<AttendeesData> mlist = new ArrayList<>();

        if (queryStr == null) {
            if (cateogory == null || cateogory.equals("All")) {
                mlist.addAll(AttendeeDAO.getInstance(mContext).getAttendeeList(isFirstName, 0));
            } else {
                mlist.addAll(AttendeeDAO.getInstance(mContext).sortAttendeeByCategory(isFirstName, cateogory, 0));
            }
        } else {
            if (cateogory == null || cateogory.equals("All")) {
                mlist.addAll(AttendeeDAO.getInstance(mContext).searchAttendeeByName(null, queryStr, isFirstName));
            } else {
                mlist.addAll(AttendeeDAO.getInstance(mContext).searchAttendeeByName(cateogory, queryStr, isFirstName));
            }
        }

        mlist = mlist != null ? mlist : new ArrayList<>();
        mListener.onListFetch(mlist);

    }

    public void fetchSpeakerListFromDb(String queryStr, String cateogory, onTaskComplete mListener) {
        List<SpeakerData> mlist = new ArrayList<>();

        if (queryStr == null) {
            if (cateogory == null || cateogory.equals("All")) {
                mlist.addAll(SpeakerDAO.getInstance(mContext).getSpeakerList(isFirstName, 0));
            } else {
                mlist.addAll(SpeakerDAO.getInstance(mContext).sortSpeakerByCategory(isFirstName, cateogory, 0));
            }
        } else {
            if (cateogory == null || cateogory.equals("All")) {
                mlist.addAll(SpeakerDAO.getInstance(mContext).searchSpeakerByName(null, queryStr, isFirstName));
            } else {
                mlist.addAll(SpeakerDAO.getInstance(mContext).searchSpeakerByName(cateogory, queryStr, isFirstName));
            }
        }

        mlist = mlist != null ? mlist : new ArrayList<SpeakerData>();
        mListener.onListFetch(mlist);

    }

    public void fetchExhibitorListFromDb(String queryStr, String cateogory, onTaskComplete mListener) {
        List<ExhibitorData> mlist = new ArrayList<>();

        if (queryStr == null) {
            if (cateogory == null || cateogory.equals("All")) {
                mlist.addAll(ExhibitorDAO.getInstance(mContext).getExhibitorList(0));
            } else {
                mlist.addAll(ExhibitorDAO.getInstance(mContext).sortExhibitorByCategory(cateogory, 0));
            }
        } else {
            if (cateogory == null || cateogory.equals("All")) {
                mlist.addAll(ExhibitorDAO.getInstance(mContext).searchExhibitorByName(null, queryStr));
            } else {
                mlist.addAll(ExhibitorDAO.getInstance(mContext).searchExhibitorByName(cateogory, queryStr));
            }
        }

        mlist = mlist != null ? mlist : new ArrayList<>();
        mListener.onListFetch(mlist);

    }

    /**Exhibitor's category is coming from server in separate API
     * temp code
     */
//    public List<String> fetchExhibitorCategory(){
//        String resp = Utility.getFromSD(mContext, ConstantData.EXHIBITOR_CATEGORY);
//
//        ExhibitorProcessor edp = new ExhibitorProcessor();
//        return edp.parseCategoryResult(resp);
//    }


}
