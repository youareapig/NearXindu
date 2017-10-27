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

import com.mssd.data.DiscoverBean;
import com.mssd.data.JpushBean;
import com.mssd.html.WebActivity;
import com.mssd.zl.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by DELL on 2017/8/30.
 */

public class NoticeAdapter extends RecyclerView.Adapter {
    private List<JpushBean> list;
    private Activity activity;

    public NoticeAdapter(List<JpushBean> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_item, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final JpushBean info = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.notice_date.setText(info.getDates());
        viewHolder.notice_content.setText(info.getContent());
        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        viewHolder.notice_date.setTypeface(typeface);
        viewHolder.notice_content.setTypeface(typeface);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info.getIsLink().equals("1")){
                    Intent intent=new Intent(v.getContext(), WebActivity.class);
                    intent.putExtra("url",info.getUrl());
                    v.getContext().startActivity(intent);
                }else {

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView notice_date,notice_content;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            notice_date = (TextView) itemView.findViewById(R.id.notice_item_date);
            notice_content = (TextView) itemView.findViewById(R.id.notice_item_content);
        }
    }
}
