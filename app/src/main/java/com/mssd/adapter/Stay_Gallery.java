package com.mssd.adapter;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
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

public class Stay_Gallery extends BaseAdapter {
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
        if (list != null) {
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
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.stay_item_gallery, parent, false);
            holder.textView = (TextView) convertView.findViewById(R.id.stay_item_text);
            AutoUtils.autoSize(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(list.get(position % list.size()));
        AssetManager assetManager = convertView.getContext().getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        holder.textView.setTypeface(typeface);
        if (selectItem == position % list.size()) {
            holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 72);
            holder.textView.setTextColor(android.graphics.Color.parseColor("#c69d39"));
        } else {
            holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 62);
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView textView;
    }
}
