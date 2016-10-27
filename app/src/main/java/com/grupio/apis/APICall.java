package com.grupio.apis;

import android.content.Context;
import android.widget.Toast;

import com.grupio.R;
import com.grupio.Utils.Utility;

/**
 * Created by JSN on 19/8/16.
 */

/**
 * This is generic class to hit apis. All api call will go through this class.
 *
 * @param <T>
 */
public class APICall<T extends ApiInter> {

    private T type;

    public APICall(T type) {
        this.type = type;
    }

    public void doCall(Context mContext) {
        doCall(mContext, null);
    }

    public void doCall(Context mContext, T param) {
        if (Utility.hasInternet(mContext)) {
//           param.run();
        } else {
            Toast.makeText(mContext, mContext.getResources().getText(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

}
