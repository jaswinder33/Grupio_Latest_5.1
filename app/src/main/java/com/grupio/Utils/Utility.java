package com.grupio.Utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.grupio.BuildConfig;
import com.grupio.R;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by JSN on 24/8/16.
 */
public class Utility {

    /**
     * Download files from given url
     *
     * @param context
     * @param fileUrl   url from which resource will get downloaded.
     * @param fileName  name with which resource will stored on device.
     * @param folder    folder inside Grupio folder.
     * @param extension extenion of file.
     */
    public static void downloadFile(Context context, String fileUrl, String fileName, String folder, String extension) {

        synchronized (Utility.class) {

            File fileDir = getBaseFolder(context, folder);

            if (extension != null && !extension.equals("")) {
                fileName = fileName + "." + extension;
            }

            File mFile = new File(fileDir, fileName);

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
     *
     * @param simpleDate
     * @return
     */
    public static String servertoClientString(String simpleDate) {
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
     *
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

    public static DisplayImageOptions getDisplayOptionsAttendee() {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.demo_pic)
                .showImageForEmptyUri(R.drawable.demo_pic)
                .resetViewBeforeLoading(true)
                .showImageOnFail(R.drawable.demo_pic).cacheInMemory(true)
                .cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.ARGB_8888).considerExifParams(true)
                .build();
    }


    public static void readFileFromSd(String fileName) {
    }

    /**
     * Returns default Grupio folder location on device storage.
     *
     * @param context
     * @param folder
     * @return
     */
    public static File getBaseFolder(Context context, String folder) {

        File cacheDir;

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(Environment.getExternalStorageDirectory(), BuildConfig.BASE_FOLDER + File.separator + folder);
        } else {
            cacheDir = context.getFilesDir();
        }

        if (!cacheDir.exists())
            cacheDir.mkdirs();

        return cacheDir;
    }

    /**
     * Check for Internet connectivity
     *
     * @param mContext
     * @return
     */
    public static boolean hasInternet(Context mContext) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)
                    for (int i = 0; i < info.length; i++)
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Check if given file exists on sd card
     *
     * @param mContext
     * @param sourceFolder
     * @param fileName
     * @return
     */
    public static boolean isThisfileExists(Context mContext, String sourceFolder, String fileName) {
        File fileDir = Utility.getBaseFolder(mContext, sourceFolder);
        File file = new File(fileDir, fileName);
        return file.exists();
    }

    public static void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        fileOrDirectory.delete();
    }

    public static String convertUTCtoMyTime(String time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SimpleDateFormat dfNew = new SimpleDateFormat("yyyy-MM-dd hh:mm a");

        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedDate = "";
        try {
            java.util.Date date = df.parse(time);
            df.setTimeZone(TimeZone.getDefault());
            formattedDate = dfNew.format(date);
        } catch (ParseException e) {

            e.printStackTrace();
        }

        return formattedDate;

    }

    public static String convertMyTimeToUTC(String time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getDefault());
        String formattedDate = "";
        try {
            java.util.Date date = df.parse(time);
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            formattedDate = df.format(date);
        } catch (ParseException e) {

            e.printStackTrace();
        }

        return formattedDate;
    }

    public static Boolean isValidType(String type) {
        return type.equalsIgnoreCase("ppt")
                || type.equalsIgnoreCase("pdf")
                || type.equalsIgnoreCase("pptx")
                || type.equalsIgnoreCase("xls")
                || type.equalsIgnoreCase("xlsx")
                || type.equalsIgnoreCase("doc")
                || type.equalsIgnoreCase("docx");
    }

    public static void openPPT(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.ms-powerpoint");
        PackageManager packageManager = context.getPackageManager();
        List intentList = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        if (intentList.size() == 0) {
            try {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=cn.wps.moffice_eng")));
            } catch (android.content.ActivityNotFoundException anfe) {
            }
        } else {
            context.startActivity(intent);
        }
    }

    public static void openPDF(Context context, File file) {

        Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.adobe.reader");
        if (intent != null) {
            intent.setDataAndType(Uri.fromFile(file), "application/ppt");
            context.startActivity(intent);
        } else {
            try {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.adobe.reader")));
            } catch (android.content.ActivityNotFoundException anfe) {
            }
        }

    }

    public static void openDoc(Context context, File file) {

        String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
        String mimetype = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);

        if (extension.equals("pdf")) {
            openPDF(context, file);
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW);

            if (extension.equalsIgnoreCase("") || mimetype == null) {
                intent.setDataAndType(Uri.fromFile(file), "text/*");
            } else {
                intent.setDataAndType(Uri.fromFile(file), mimetype);
            }

            PackageManager packageManager = context.getPackageManager();
            List intentList = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

            if (intentList.size() == 0) {
                try {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=cn.wps.moffice_eng")));
                } catch (android.content.ActivityNotFoundException anfe) {
                }
            } else {
                context.startActivity(intent);
            }
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        try {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                return;
            }

            View listItem = null;
            int totalHeight = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                    View.MeasureSpec.AT_MOST);

            for (int i = 0; i < listAdapter.getCount(); i++) {
                listItem = listAdapter.getView(i, null, listView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
            listView.requestLayout();

        } catch (Exception e) {
        }
    }

    public static void addFragment(Activity mActivity, Fragment frag, boolean addToBackStack) {
        FragmentManager manager = mActivity.getFragmentManager();
        FragmentTransaction mTranscation = manager.beginTransaction();
        mTranscation.add(R.id.container, frag, frag.getClass().getName());
        mTranscation.commit();
    }

    public static void replaceFragment(Activity mActivity, Fragment frag, boolean addToBackStack) {
        FragmentManager manager = mActivity.getFragmentManager();
        FragmentTransaction mTranscation = manager.beginTransaction();
        mTranscation.replace(R.id.container, frag, frag.getClass().getName());
        mTranscation.commit();
    }

    /**
     * Check if color is dark or light
     *
     * @param color
     * @return
     */
    public boolean isColorDark(int color) {
        double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return darkness >= 0.5;
    }

}
