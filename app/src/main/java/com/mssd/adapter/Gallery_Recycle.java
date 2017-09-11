package com.mssd.adapter;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mssd.data.FoodBean;
import com.mssd.zl.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by DELL on 2017/8/30.
 */

public class Gallery_Recycle extends RecyclerView.Adapter {
    private List<Integer> list;
    private Activity activity;

    public Gallery_Recycle(List<Integer> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item_recycle, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.galleryrecycleimg.setImageResource(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView galleryrecycleimg;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            galleryrecycleimg = (ImageView) itemView.findViewById(R.id.gallery_item_img);
        }
    }
}
