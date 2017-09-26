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

import com.mssd.data.TBean;
import com.mssd.zl.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by DELL on 2017/8/30.
 */

public class Discover_Recycle4 extends RecyclerView.Adapter {
    private List<TBean> list;
    private Activity activity;

    public Discover_Recycle4(List<TBean> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.discover_item_4, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TBean info = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.discover4name.setText(info.getName());
        viewHolder.discover4img.setImageResource(info.getImg());
        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        viewHolder.discover4name.setTypeface(typeface);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView discover4name;
        private ImageView discover4img;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            discover4name = (TextView) itemView.findViewById(R.id.discover_item4_name);
            discover4img = (ImageView) itemView.findViewById(R.id.discover_item4_img);
        }
    }
}
