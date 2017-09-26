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

import com.mssd.data.FoodDateBean;
import com.mssd.data.TripNeatBean;
import com.mssd.zl.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by DELL on 2017/8/30.
 */

public class FoodAdapter extends RecyclerView.Adapter {
    private List<FoodDateBean.DataBean.CanteenBean> list;
    private Activity activity;

    public FoodAdapter(List<FoodDateBean.DataBean.CanteenBean> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_item_list, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FoodDateBean.DataBean.CanteenBean info = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.trip1name.setText(info.getStitle());
        viewHolder.trip2name.setText(info.getSname());
        ImageLoader.getInstance().displayImage(info.getUrl(),viewHolder.tripimg);
        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        viewHolder.trip1name.setTypeface(typeface);
        viewHolder.trip2name.setTypeface(typeface);
    }

    @Override
    public int getItemCount() {
        if (list!=null){
            return list.size();
        }
        return 0;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView trip1name,trip2name;
        private ImageView tripimg;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            trip1name = (TextView) itemView.findViewById(R.id.trip_item1_list_text1);
            trip2name = (TextView) itemView.findViewById(R.id.trip_item1_list_text2);
            tripimg = (ImageView) itemView.findViewById(R.id.trip_item1_list_img);
        }
    }
}
