package com.example.j_joe.imageloadyzz;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    }

    private static class ViewHolder{
        private ImageView imageView;
        private TextView textView;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null){
            view = inflater.inflate(android.R.layout.activity_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.icon);
            viewHolder.textView = (TextView) view.findViewById(android.R.id.text1);
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
                int width = dip2px(context,40);
                int height = dip2px(context,40);
                LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) viewHolder.imageView.getLayoutParams();
                p.width = width;
                p.height = height;
                viewHolder.imageView.setBackgroundResource(android.R.drawable.ic_dialog_info);
                viewHolder.imageView.setLayoutParams(p);


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
}
