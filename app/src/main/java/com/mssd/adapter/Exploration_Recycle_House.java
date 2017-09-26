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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by DELL on 2017/8/30.
 */

public class Exploration_Recycle_House extends RecyclerView.Adapter {
    private List<TansuoBean.DataBean.StayBean> list;
    private Activity activity;

    public Exploration_Recycle_House(List<TansuoBean.DataBean.StayBean> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.exploration_item_house, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TansuoBean.DataBean.StayBean info = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.housename.setText(info.getSname());
        ImageLoader.getInstance().displayImage(info.getUrl(),viewHolder.houseimg);
        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        viewHolder.housename.setTypeface(typeface);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView housename;
        private ImageView houseimg;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            housename = (TextView) itemView.findViewById(R.id.explorationhouse_item_name);
            houseimg = (ImageView) itemView.findViewById(R.id.explorationhouse_item_img);
        }
    }
}
