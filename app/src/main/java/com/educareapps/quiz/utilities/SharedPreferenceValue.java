package com.educareapps.quiz.utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by RAFI on 9/18/2016.
 */

public class SharedPreferenceValue {

    public static long getUserID(Context context) {
        SharedPreferences settings = context.getSharedPreferences("USER_INFO", 0);
        long mFlag = settings.getLong("user_id", -1);
        return mFlag;
    }

    public static void setUserID(Context context, long user_id) {
        SharedPreferences settings = context.getSharedPreferences("USER_INFO", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong("user_id", user_id);
        editor.commit();
    }

    public static boolean getDownloadSuccess(Context context) {
        SharedPreferences settings = context.getSharedPreferences("SPLASH_DOWNLOAD", 0);
        boolean mFlag = settings.getBoolean("is_download_complete", false);
        return mFlag;
    }

    public static void setDownloadSuccess(Context context, boolean isDownloadSuccess) {
        SharedPreferences settings = context.getSharedPreferences("SPLASH_DOWNLOAD", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("is_download_complete", isDownloadSuccess);
        editor.commit();
    }
}
