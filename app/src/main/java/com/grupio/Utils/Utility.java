package com.grupio.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;


import com.grupio.R;
import com.grupio.session.ConstantData;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by JSN on 24/8/16.
 */
public class Utility {

    /**
     * Download files from given url
     * @param context
     * @param fileUrl url from which resource will get downloaded.
     * @param fileName name with which resource will stored on device.
     * @param folder folder inside Grupio folder.
     * @param extension extenion of file.
     */
    public static void downloadFile(Context context, String fileUrl, String fileName, String folder, String extension) {

        synchronized (Utility.class) {

            File fileDir = getBaseFolder(context, folder);

            if(extension!= null && !extension.equals("")){
                fileName = fileName + "." + extension;
            }

            File mFile = new File(fileDir , fileName );

            URL url = null;
            try {
                url = new URL(fileUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            HttpURLConnection c = null;
            try {
                c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(mFile);
                InputStream is = c.getInputStream();

                byte[] buffer = new byte[1024];
                int len = 0;
                int total = 0;

                while ((len = is.read(buffer)) != -1) {
                    total += len;
                    fos.write(buffer, 0, len);
                }
                fos.close();
                is.close();
                fos.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Convert UTC to device specific time
     * @param simpleDate
     * @return
     */
    public static  String servertoClientString(String simpleDate) {
        String serverString = "";
        try {
            SimpleDateFormat clientFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat serverFormat = new SimpleDateFormat("dd-MMM-yyyy");
            Date clientDate = clientFormat.parse(simpleDate);
            serverString = serverFormat.format(clientDate);

        } catch (Exception e) {
        }
        return serverString;
    }

    /**
     * Display settings for Universal Image Loader
     * @return
     */
    public static DisplayImageOptions getDisplayOptionsEventList() {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.no_image_2)
                .showImageForEmptyUri(R.drawable.no_image_2)
                .resetViewBeforeLoading(true)
                .showImageOnFail(R.drawable.no_image_2).cacheInMemory(true)
                .cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.ARGB_8888).considerExifParams(true)
                .build();
    }


    public static void readFileFromSd(String fileName){
    }

    /**
     * Returns default Grupio folder location on device storage.
     * @param context
     * @param folder
     * @return
     */
    public static File getBaseFolder(Context context,String folder){

        File cacheDir;

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(Environment.getExternalStorageDirectory(), ConstantData.BASE_FOLDER + File.separator + folder);
        } else {
            cacheDir = context.getFilesDir();
        }

        if (!cacheDir.exists())
            cacheDir.mkdirs();

        return cacheDir;
    }



}
