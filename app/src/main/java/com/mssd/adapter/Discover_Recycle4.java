package com.mssd.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.mssd.data.DiscoverBean;
import com.mssd.html.WebActivity;
import com.mssd.utils.SingleModleUrl;
import com.mssd.zl.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by DELL on 2017/8/30.
 */

public class Discover_Recycle4 extends RecyclerView.Adapter {
    private List<DiscoverBean.DataBeanXXXX.T4Bean.DataBeanXXX> list;
    private Activity activity;

    public Discover_Recycle4(List<DiscoverBean.DataBeanXXXX.T4Bean.DataBeanXXX> list, Activity activity) {
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
        final DiscoverBean.DataBeanXXXX.T4Bean.DataBeanXXX info = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.discover4name.setText(info.getPname());
        ImageLoader.getInstance().displayImage(info.getUrl(),viewHolder.discover4img);
        AlphaAnimation animation= new AlphaAnimation(0.5f,1);
        animation.setDuration(500);
        viewHolder.discover4img.setAnimation(animation);
        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        viewHolder.discover4name.setTypeface(typeface);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WebActivity.class);
                intent.putExtra("url", SingleModleUrl.singleModleUrl().getTestUrl() + "Show/line/id/" + info.getId());
                v.getContext().startActivity(intent);
            }
        });
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
