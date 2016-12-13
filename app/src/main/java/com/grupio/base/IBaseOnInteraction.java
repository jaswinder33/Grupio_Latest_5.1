package com.grupio.base;

import com.grupio.data.AdsData;

import java.util.List;

/**
 * Created by JSN on 12/12/16.
 */

public interface IBaseOnInteraction {
    void onFailure(String msg);

    void onShowBanner(List<AdsData> adData);
}
