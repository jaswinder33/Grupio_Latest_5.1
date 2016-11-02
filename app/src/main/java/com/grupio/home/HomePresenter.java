package com.grupio.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.grupio.activities.WebViewActivity;
import com.grupio.animation.SlideOut;

/**
 * Created by JSN on 6/10/16.
 */

public class HomePresenter implements HomePresenterImp, HomeInteractorImp.OnHomeInteractionComplete {

    HomeView mListener;
    HomeInteractor mInteractor;

    public HomePresenter(Context mContext, HomeView mListener) {
        this.mListener = mListener;
        mInteractor = new HomeInteractor();
        setHomeInfo(mContext);
    }

    @Override
    public void onWebLogoFetch(String url) {
        if (mListener != null) {
            mListener.setWebLogo(url);
        }
    }

    @Override
    public void onEventDate(String date) {
        if (mListener != null) {
            mListener.setEventDate(date);
        }
    }

    @Override
    public void onEventName(String name) {
        if (mListener != null) {
            mListener.setEventName(name);
        }
    }

    @Override
    public void onVenue(String venue) {
        if (mListener != null) {
            mListener.setVenue(venue);
        }
    }

    @Override
    public void onAddress(String address) {
        if (mListener != null) {
            mListener.setAddress(address);
        }
    }

    @Override
    public void onStaticMap(String url) {
        if (mListener != null) {
            mListener.setStaticMap(url);
        }
    }

    @Override
    public void onEventWebSiteBtn(String url) {
        if (mListener != null) {
            if (TextUtils.isEmpty(url)) {
                mListener.hideEventWebsite();
            }
        }
    }

    @Override
    public void onDescription(String description) {
        if (mListener != null) {
            if (TextUtils.isEmpty(description)) {
                mListener.hideDescription();
            } else {
                mListener.setDescrption(description);
            }
        }
    }

    @Override
    public void setHomeInfo(Context mContext) {
        mInteractor.setHomeInfo(mContext, this);
    }

    @Override
    public void openMap(Context mContext) {
        Intent mapIntent = new Intent(mContext, MapsActivity.class);
        mContext.startActivity(mapIntent);
        SlideOut.getInstance().startAnimation((Activity) mContext);
    }

    @Override
    public void getDirections(Context mContext) {
        String latitude = mInteractor.fetchLatitude(mContext);
        String longitude = mInteractor.fetchLongitude(mContext);
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude + "");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        mContext.startActivity(mapIntent);
    }

    @Override
    public void openEventWebsite(Context mContext) {

        String eventWebsite = mInteractor.fetchEventWebsiteBtn(mContext);

        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra("url", eventWebsite);
        mContext.startActivity(intent);
        SlideOut.getInstance().startAnimation((Activity) mContext);

    }


}
