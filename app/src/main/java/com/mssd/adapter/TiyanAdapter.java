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

import com.mssd.data.ExperienceNextBean;
import com.mssd.data.TiyanBean;
import com.mssd.zl.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by DELL on 2017/8/30.
 */

public class TiyanAdapter extends RecyclerView.Adapter {
    private List<TiyanBean.DataBean> list;
    private Activity activity;

    public TiyanAdapter(List<TiyanBean.DataBean> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.experience_item_recycle, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TiyanBean.DataBean info = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.experienceRecycletitle1.setText(info.getStitle());
        viewHolder.experienceRecycletitle2.setText(info.getSname());
        ImageLoader.getInstance().displayImage(info.getUrl(),viewHolder.experienceRecycleimg);
        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        viewHolder.experienceRecycletitle1.setTypeface(typeface);
        viewHolder.experienceRecycletitle2.setTypeface(typeface);
    }

    @Override
    public int getItemCount() {
        if (list!=null){
            return list.size();
        }
        return 0;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView experienceRecycletitle1,experienceRecycletitle2;
        private ImageView experienceRecycleimg,shoucang;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            experienceRecycletitle1 = (TextView) itemView.findViewById(R.id.experience_item_recycle_title1);
            experienceRecycletitle2 = (TextView) itemView.findViewById(R.id.experience_item_recycle_title2);
            experienceRecycleimg = (ImageView) itemView.findViewById(R.id.experience_item_recycle_img);
            shoucang= (ImageView) itemView.findViewById(R.id.experience_item_recycle_shoucang);
        }
    }
}
