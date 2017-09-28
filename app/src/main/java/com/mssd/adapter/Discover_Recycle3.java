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

import com.mssd.data.DiscoverBean;
import com.mssd.zl.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by DELL on 2017/8/30.
 */

public class Discover_Recycle3 extends RecyclerView.Adapter {
    private List<DiscoverBean.DataBeanXXXX.T3Bean.DataBeanXX> list;
    private Activity activity;

    public Discover_Recycle3(List<DiscoverBean.DataBeanXXXX.T3Bean.DataBeanXX> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.discover_item_3, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DiscoverBean.DataBeanXXXX.T3Bean.DataBeanXX info = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.discover3name.setText(info.getPname());
        ImageLoader.getInstance().displayImage(info.getUrl(),viewHolder.discover3img);
        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        viewHolder.discover3name.setTypeface(typeface);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView discover3name;
        private ImageView discover3img;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            discover3name = (TextView) itemView.findViewById(R.id.discover_item3_name);
            discover3img = (ImageView) itemView.findViewById(R.id.discover_item3_img);
        }
    }
}
