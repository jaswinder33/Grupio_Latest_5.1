package com.grupio.backend;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by JSN on 21/9/16.
 */
public class Permissions {

    List<String> permissions;

    private Permissions(){
        permissions = new ArrayList<>();
    }

    public static Permissions getInstance() {
        return new Permissions();
    }

    public Permissions hasStorageReadPermission(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        return this;
    }

    public Permissions hasStorageWritePermission(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        return this;
    }

    public void askForPermissions(Activity activity, int requestcode){
        if(permissions.size() > 0){
            ActivityCompat.requestPermissions(activity, permissions.toArray(new String[permissions.size()]), requestcode);
        }
    }

    public Permissions checkForAllPermissions(Activity activity){
        hasStorageReadPermission(activity);
        hasStorageWritePermission(activity);
        return this;
    }

}
