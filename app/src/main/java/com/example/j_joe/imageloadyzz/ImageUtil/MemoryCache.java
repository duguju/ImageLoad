package com.example.j_joe.imageloadyzz.ImageUtil;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

/**
 * Created by Yang on 2016/1/4.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
public class MemoryCache implements ImageCache{
    private LruCache<String,Bitmap> mMemoryCache;

    public MemoryCache(){
        initImageCache();
    }

    private void initImageCache(){
//        final int maxMemory = (int)(Runtime.getRuntime().maxMemory()/1024);
        final int maxMemory = (int)(Runtime.getRuntime().maxMemory());
//        final int cacheSize = maxMemory/4;
        final int cacheSize = maxMemory/8;
        mMemoryCache = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap){
//                return bitmap.getRowBytes() * bitmap.getHeight()/1024;
                return bitmap.getByteCount();
            }
        };
    }

    @Override
    public Bitmap get(String url) {
        return mMemoryCache.get(url);
    }

    @Override
    public void put(String url, Bitmap bmp) {
        mMemoryCache.put(url,bmp);
    }
}
