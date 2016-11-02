package com.grupio.activities;

import android.os.Bundle;

/**
 * Created by JSN on 13/10/16.
 */

public abstract class SimpleBaseActivity<Presenter> extends BaseActivity<Presenter> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
