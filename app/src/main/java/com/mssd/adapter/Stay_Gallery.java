package com.mssd.adapter;

import android.app.Activity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mssd.zl.R;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by DELL on 2017/9/6.
 */

public class Stay_Gallery extends BaseAdapter{
    private List<String> list;
    private Activity activity;
    private LayoutInflater inflater;
    private int selectItem;
    public Stay_Gallery(List<String> list, Activity activity) {
        this.list = list;
        this.inflater = activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Object getItem(int position) {
        if (list!=null){
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.stay_item_gallery, parent,false);
        TextView textView= (TextView) convertView.findViewById(R.id.stay_item_text);
        AutoUtils.autoSize(convertView);
        textView.setText(list.get(position%list.size()));
        if (selectItem==position%list.size()){
//            ViewGroup.MarginLayoutParams params=new ViewGroup.MarginLayoutParams(textView.getLayoutParams());
//            AutoRelativeLayout.LayoutParams layoutParams=new AutoRelativeLayout.LayoutParams(params);
//            layoutParams.height=220;
//            textView.setLayoutParams(layoutParams);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,112);
            textView.setTextColor(android.graphics.Color.parseColor("#c69d39"));
        }
        return convertView;
    }
}
