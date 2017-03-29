package com.vk.libs.appcommon.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Pair;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import retrofit2.Retrofit;

/**
 * Created by VK on 2016/12/12.
 * 网络辅助类,相关权限：permission:android.permission.READ_PHONE_STATE,android.permission.ACCESS_NETWORK_STATE
 */

public final class NetworkUtil {

    /**
     * Unknown network class.
     */
    public static final int NETWORK_CLASS_UNKNOWN = 0;
    /**
     * Class of broadly defined "2G" networks.
     */
    public static final int NETWORK_CLASS_2_G = 1;
    /**
     * Class of broadly defined "3G" networks.
     */
    public static final int NETWORK_CLASS_3_G = 2;
    /**
     * Class of broadly defined "4G" networks.
     */
    public static final int NETWORK_CLASS_4_G = 3;
    /**
     * "WIFI" networks
     */
    public static final int NETWORK_CLASS_WIFI = 4;

    /**
     * 当前网络是否有效
     *
     * @param context
     * @return true:已连接;false:未连接
     */
    public static boolean isConnected(Context context) {
        NetworkInfo networkInfo = getActiveNetworkInfo(context);
        if (networkInfo != null)
            return networkInfo.isConnected();
        return false;
    }

    /**
     * 获取当前活动的网络信息
     *
     * @param context
     * @return 当前活动的网络信息对象
     */
    private static NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        Retrofit.Builder s;
        return networkInfo;
    }

    /**
     * 获取当前网络类型
     *
     * @param context
     * @return unknown, 2G, 3G, 4G, WIFI
     */
    public static Pair<Integer, String> getConnectType(Context context) {
        String clsName = "unknown";
        int networkClass = NETWORK_CLASS_UNKNOWN;

        Pair<Integer, String> ret = null;

        NetworkInfo networkInfo = getActiveNetworkInfo(context);
        if (networkInfo == null)
            return new Pair<>(NETWORK_CLASS_UNKNOWN, "unknown");
        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return new Pair<>(NETWORK_CLASS_WIFI, "WIFI");
        }
        else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            int networkType = telephonyManager.getNetworkType();
            try {
                Method getNetworkClassMethod = TelephonyManager.class.getMethod("getNetworkClass", int.class);
                networkClass = (Integer) getNetworkClassMethod.invoke(null, networkType);
                switch (networkClass) {
                    case NETWORK_CLASS_UNKNOWN:
                        clsName = "unknown";
                        break;
                    case NETWORK_CLASS_2_G:
                        clsName = "2G";
                        break;
                    case NETWORK_CLASS_3_G:
                        clsName = "3G";
                        break;
                    case NETWORK_CLASS_4_G:
                        clsName = "4G";
                        break;
                    default:
                        break;
                }
                ret = new Pair<>(networkClass, clsName);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }
}
