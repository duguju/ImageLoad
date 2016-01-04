package com.example.j_joe.imageloadyzz.ImageUtil;

import android.graphics.Bitmap;

/**
 * Created by Yang on 2016/1/4.
 */
public interface ImageCache {
    public Bitmap get(String url);
    public void put(String url,Bitmap bmp);
}
