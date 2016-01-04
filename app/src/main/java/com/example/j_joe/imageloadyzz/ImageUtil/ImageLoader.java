package com.example.j_joe.imageloadyzz.ImageUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Yang on 2016/1/4.
 * 自写图片加载框架（集成度高）
 */
public final class ImageLoader {
    private static ImageLoader instance = null;

    ImageCache mImageCache = new MemoryCache();
    ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public ImageLoader(){
        instance = this;
    }

    public static ImageLoader getInstance(){
        if (instance==null){
            synchronized (ImageLoader.class){
                if (instance==null){
                    instance = new ImageLoader();
                }
            }
        }
        return instance;
    }

    public void setImageCache(ImageCache cache){
        mImageCache = cache;
    }

    public void displayImage(String imageUrl, ImageView imageView){
        Bitmap bitmap = mImageCache.get(imageUrl);
        if (bitmap!=null){
            imageView.setImageBitmap(bitmap);
            return;
        }
        submitLoadRequest(imageUrl, imageView);
    }

    private void submitLoadRequest(final String imageUrl, final ImageView imageView){
        imageView.setTag(imageUrl);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = downloadImage(imageUrl);
                if (bitmap==null){
                    return;
                }
                if (imageView.getTag().equals(imageUrl)){
                    imageView.setImageBitmap(bitmap);
                    mImageCache.put(imageUrl,bitmap);
                }
            }
        });
    }

    private Bitmap downloadImage(String imageUrl){
        Bitmap bitmap = null;
        HttpURLConnection con = null;
        try {
            URL url = new URL(imageUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(5 * 1000);
            con.setReadTimeout(10 * 1000);
            bitmap = BitmapFactory.decodeStream(con.getInputStream());

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (con!=null){
                con.disconnect();
            }
        }

        return bitmap;
    }

}
