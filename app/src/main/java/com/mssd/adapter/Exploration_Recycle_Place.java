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

import com.mssd.data.TansuoBean;
import com.mssd.zl.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by DELL on 2017/8/30.
 */

public class Exploration_Recycle_Place extends RecyclerView.Adapter {
    private List<TansuoBean.DataBean.LineBean> list;
    private Activity activity;

    public Exploration_Recycle_Place(List<TansuoBean.DataBean.LineBean> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.exploration_item_place, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TansuoBean.DataBean.LineBean info = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.placename.setText(info.getSname());
        ImageLoader.getInstance().displayImage(info.getUrl(),viewHolder.placeimg);
        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        viewHolder.placename.setTypeface(typeface);
    }

    @Override
    public int getItemCount() {
        if (list!=null){
            return list.size();
        }
        return 0;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView placename;
        private ImageView placeimg;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            placename = (TextView) itemView.findViewById(R.id.explorationplace_item_name);
            placeimg = (ImageView) itemView.findViewById(R.id.explorationplace_item_img);
        }
    }
}
