package com.vk.libs.appcommon.cache;

/**
 * Created by VK on 2016/12/12.
 * 数据缓存统一管理类
 */

public final class CacheManager {
    private static CacheManager cacheManager;

    /**
     * 单例对象
     * @return
     */
    public static CacheManager getInstance(){
        if(cacheManager == null){
            synchronized (CacheManager.class){
                if(cacheManager == null)
                    cacheManager = new CacheManager();
            }
        }
        return cacheManager;
    }


}
