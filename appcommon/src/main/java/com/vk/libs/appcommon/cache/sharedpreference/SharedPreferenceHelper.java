package com.vk.libs.appcommon.cache.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by VK on 2017/2/7.
 */

public class SharedPreferenceHelper {

    public static final String TAG = "SharedPreferenceHelper";

    public static String getStringInDefault(Context context,String key){
        return getString(context,"default",key);
    }

    public static boolean putStringInDefault(Context context,String key,String value){
        return putString(context,"default",key,value);
    }

    public static String getString(Context context,String name,String key){
        String val ;

        SharedPreferences sp = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        val = sp.getString(key,null);
        return val;
    }

    public static boolean putString(Context context,String name,String key,String value){
        SharedPreferences sp = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        return sp.edit().putString(key,value).commit();
    }


}
