package com.mssd.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mssd.data.FoodBean;
import com.mssd.zl.JiaYanActivity;
import com.mssd.zl.R;
import com.mssd.zl.ShiJiaActivity;
import com.mssd.zl.ShiTangActivity;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by DELL on 2017/8/30.
 */

public class ShiJia_Recycle extends RecyclerView.Adapter {
    private List<FoodBean> list;
    private Activity activity;

    public ShiJia_Recycle(List<FoodBean> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shijia_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        FoodBean info = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.shijianame.setText(info.getName());
        viewHolder.shijiaimg.setImageResource(info.getImg());

        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        viewHolder.shijianame.setTypeface(typeface);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView shijianame;
        private ImageView shijiaimg;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            shijianame = (TextView) itemView.findViewById(R.id.shijia_item_text);
            shijiaimg = (ImageView) itemView.findViewById(R.id.shijia_item_img);
        }
    }
}
