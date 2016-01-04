package com.example.j_joe.imageloadyzz;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.LruCache;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.j_joe.imageloadyzz.ImageUtil.DoubleCache;
import com.example.j_joe.imageloadyzz.ImageUtil.ImageLoader;
import com.example.j_joe.imageloadyzz.ImageUtil.MemoryCache;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by j-joe on 16/1/3.
 */
public class mainAdapter extends BaseAdapter{

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<String> arrayList;

    public mainAdapter(Context context,LayoutInflater inflater,ArrayList<String> arrayList){
        this.context = context;
        this.inflater = inflater;
        this.arrayList = arrayList;
        init();
    }

    private static class ViewHolder{
        private ImageView imageView;
        private TextView textView;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null){
            view = inflater.inflate(R.layout.adapter_main,null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.adapter_main_img);
            viewHolder.textView = (TextView) view.findViewById(R.id.adapter_main_tv);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }

        try{
            if (arrayList.size()>0){
                String url = arrayList.get(position);
                viewHolder.textView.setText(url);
                //TODO
//                int width = dip2px(context,70);
//                int height = dip2px(context,70);
//                LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) viewHolder.imageView.getLayoutParams();
//                p.width = width;
//                p.height = height;
//                viewHolder.imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), android.R.drawable.ic_dialog_info));
//                viewHolder.imageView.setLayoutParams(p);

                ImageLoader imageLoader = ImageLoader.getInstance();//单例模式（推荐使用）
//                ImageLoader imageLoader = new ImageLoader();
                imageLoader.setImageCache(new DoubleCache());
                imageLoader.displayImage(url,viewHolder.imageView);

                //使用下面自带图片加载框架（集成度低）
//                BitmapDrawable drawable = getBitmapFromMemoryCache(url);
//                if (drawable != null) {
//                    viewHolder.imageView.setImageDrawable(drawable);
//                } else {
//                    BitmapWorkerTask task = new BitmapWorkerTask(viewHolder.imageView);
//                    task.execute(url);
//                }


            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }

    private int dip2px(Context context,float dipValue){
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dipValue,r.getDisplayMetrics());
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    //自带图片加载框架（集成度低）

    private LruCache<String, BitmapDrawable> mMemoryCache;

    private void init() {
        // 获取应用程序最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, BitmapDrawable>(cacheSize) {
            @Override
            protected int sizeOf(String key, BitmapDrawable drawable) {
                return drawable.getBitmap().getByteCount();
            }
        };
    }

    /**
     * 将一张图片存储到LruCache中。
     *
     * @param key LruCache的键，这里传入图片的URL地址。
     * @param drawable LruCache的值，这里传入从网络上下载的BitmapDrawable对象。
     */
    public void addBitmapToMemoryCache(String key, BitmapDrawable drawable) {
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, drawable);
        }
    }

    /**
     * 从LruCache中获取一张图片，如果不存在就返回null。
     *
     * @param key LruCache的键，这里传入图片的URL地址。
     * @return 对应传入键的BitmapDrawable对象，或者null。
     */
    public BitmapDrawable getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

    /**
     * 异步下载图片的任务。
     * @author guolin
     */
    class BitmapWorkerTask extends AsyncTask<String, Void, BitmapDrawable> {

        private ImageView mImageView;

        public BitmapWorkerTask(ImageView imageView) {
            mImageView = imageView;
        }

        @Override
        protected BitmapDrawable doInBackground(String... params) {
            String imageUrl = params[0];
            // 在后台开始下载图片
            Bitmap bitmap = downloadBitmap(imageUrl);
            BitmapDrawable drawable = new BitmapDrawable(context.getResources(), bitmap);
            addBitmapToMemoryCache(imageUrl, drawable);
            return drawable;
        }

        @Override
        protected void onPostExecute(BitmapDrawable drawable) {
            if (mImageView != null && drawable != null) {
                mImageView.setImageDrawable(drawable);
            }
        }

        /**
         * 建立HTTP请求，并获取Bitmap对象。
         *
         * @param imageUrl 图片的URL地址
         * @return 解析后的Bitmap对象
         */
        private Bitmap downloadBitmap(String imageUrl) {
            Bitmap bitmap = null;
            HttpURLConnection con = null;
            try {
                URL url = new URL(imageUrl);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(10 * 1000);
                bitmap = BitmapFactory.decodeStream(con.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (con != null) {
                    con.disconnect();
                }
            }
            return bitmap;
        }

    }
}
