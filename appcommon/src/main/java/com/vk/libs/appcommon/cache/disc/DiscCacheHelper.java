package com.vk.libs.appcommon.cache.disc;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.vk.libs.appcommon.base.BaseApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by VK on 2016/12/13.
 * 磁盘缓存帮助类
 */

public final class DiscCacheHelper {

    public static final String TAG = "DiscCacheHelper";

    public static boolean saveWithEncrypt(String identity, Serializable obj, boolean inSDCard) {
        Log.d(TAG, "saveWithEncrypt: not implement ");
        return false;
    }

    public static <T extends Serializable> T getCacheWithEncrypt(Class<T> cls, @NonNull String identity, boolean inSDCard) {
        Log.d(TAG, "getCacheWithEncrypt: not implement ");
        return null;
    }

    /**
     * 对象序列化到本地
     *
     * @param identity 对象唯一标识
     * @param obj      即将被序列化的对象
     * @param inSDCard true: 存到SDCard/Android/data/你的应用包名/cache/，false: 存到data/data/packagename/cache目录下
     * @return true:成功;false:失败
     */
    public static boolean save(@NonNull String identity, @NonNull Serializable obj, boolean inSDCard) {

        //检查sdcard
        if (inSDCard) {
            //1.检查权限

            //2.检查sdcard是否挂载
           if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
               Log.e(TAG,"external sdcard is unmounted,stop saving cache ");
               return false;
           }

        }

        ObjectOutputStream oos;
        File file;
        boolean ret;
        file = new File(inSDCard ? BaseApp.getInstance().getExternalCacheDir().getPath() : BaseApp.getInstance().getCacheDir().getPath(), identity);
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(obj);
            oos.close();
            ret = true;
        } catch (IOException e) {
            e.printStackTrace();
            ret = false;
        }
        return ret;
    }

    /**
     * 本地反序列化到对象
     *
     * @param cls      目标对象Class
     * @param identity 对象唯一标识
     * @param inSDCard true: 存到SDCard/Android/data/你的应用包名/cache/，false: 存到data/data/packagename/cache目录下
     * @param <T>      目标对象实例
     * @return
     */
    public static <T extends Serializable> T getCache(Class<T> cls, @NonNull String identity, boolean inSDCard) {

        //检查sdcard
        if (inSDCard) {
            //1.检查权限

            //2.检查sdcard是否挂载

        }

        T obj = null;
        ObjectInputStream ois;
        File file = new File(inSDCard ? BaseApp.getInstance().getExternalCacheDir().getPath() : BaseApp.getInstance().getCacheDir().getPath(), identity);
        if (file.exists() && file.isFile()) {
            try {
                ois = new ObjectInputStream(new FileInputStream(file));
                obj = (T) ois.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return obj;
    }


}
