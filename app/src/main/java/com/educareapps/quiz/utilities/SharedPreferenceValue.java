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


}
