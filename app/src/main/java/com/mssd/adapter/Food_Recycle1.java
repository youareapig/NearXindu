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

public class Food_Recycle1 extends RecyclerView.Adapter {
    private List<FoodBean> list;
    private Activity activity;

    public Food_Recycle1(List<FoodBean> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item_top, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FoodBean info = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.foodtopname.setText(info.getName());
        viewHolder.foodtopimg.setImageResource(info.getImg());

        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        viewHolder.foodtopname.setTypeface(typeface);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView foodtopname;
        private ImageView foodtopimg;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            foodtopname = (TextView) itemView.findViewById(R.id.food_itemtop_name);
            foodtopimg = (ImageView) itemView.findViewById(R.id.food_itemtop_img);
        }
    }
}
