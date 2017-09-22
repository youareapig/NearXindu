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

import com.mssd.data.HistoryIndexBean;
import com.mssd.zl.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by DELL on 2017/8/30.
 */

public class History_Recycle3 extends RecyclerView.Adapter {
    private List<HistoryIndexBean.DataBean.T6Bean> list;
    private Activity activity;

    public History_Recycle3(List<HistoryIndexBean.DataBean.T6Bean> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item_2, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HistoryIndexBean.DataBean.T6Bean info = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.history1name.setText(info.getHname());
        ImageLoader.getInstance().displayImage(info.getUrl(),viewHolder.history1img);
        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        viewHolder.history1name.setTypeface(typeface);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView history1name;
        private ImageView history1img;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            history1name = (TextView) itemView.findViewById(R.id.histoty_item2_name);
            history1img = (ImageView) itemView.findViewById(R.id.histoty_item2_img);
        }
    }
}