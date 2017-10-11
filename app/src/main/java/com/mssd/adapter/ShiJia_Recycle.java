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

import com.mssd.data.ShijiaBean;
import com.mssd.html.WebActivity;
import com.mssd.utils.SingleModleUrl;
import com.mssd.zl.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by DELL on 2017/8/30.
 */

public class ShiJia_Recycle extends RecyclerView.Adapter {
    private List<ShijiaBean.DataBean> list;
    private Activity activity;

    public ShiJia_Recycle(List<ShijiaBean.DataBean> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shijia_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ShijiaBean.DataBean info = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.shijianame.setText(info.getGname());
        ImageLoader.getInstance().displayImage(info.getUrl(), viewHolder.shijiaimg);
        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        viewHolder.shijianame.setTypeface(typeface);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WebActivity.class);
                intent.putExtra("url", SingleModleUrl.singleModleUrl().getTestUrl() + "Show/gourmet/id/" + info.getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView shijianame;
        private ImageView shijiaimg;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            shijianame = (TextView) itemView.findViewById(R.id.shijia_item_text);
            shijiaimg = (ImageView) itemView.findViewById(R.id.shijia_item_img);
        }
    }
}
