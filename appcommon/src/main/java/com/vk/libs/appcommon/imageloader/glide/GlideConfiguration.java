package com.vk.libs.appcommon.imageloader.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;
import com.vk.libs.appcommon.imageloader.ConfigConstants;

/**
 * Created by jess on 16/4/15.
 */
public class GlideConfiguration implements GlideModule {

    @Override
    public void applyOptions(final Context context, GlideBuilder builder) {
        builder.setDiskCache(new DiskCache.Factory() {
            @Override
            public DiskCache build() {
                // Careful: the external cache directory doesn't enforce permissions
                return DiskLruCacheWrapper.get(Glide.getPhotoCacheDir(context), ConfigConstants.MAX_DISK_CACHE_SIZE);
            }
        });

        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
