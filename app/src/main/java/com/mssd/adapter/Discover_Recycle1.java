package com.mssd.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.mssd.html.WebsActivity;
import com.mssd.utils.SingleModleUrl;
import com.mssd.zl.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by DELL on 2017/8/30.
 */

public class Discover_Recycle1 extends RecyclerView.Adapter {
    private List<DiscoverBean.DataBeanXXXX.T1Bean.DataBean> list;
    private Activity activity;

    public Discover_Recycle1(List<DiscoverBean.DataBeanXXXX.T1Bean.DataBean> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.discover_item_1, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final DiscoverBean.DataBeanXXXX.T1Bean.DataBean info = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.discover1name.setText(info.getHintro());
        ImageLoader.getInstance().displayImage(info.getUrl(), viewHolder.discover1img);
        AlphaAnimation animation= new AlphaAnimation(0.5f,1);
        animation.setDuration(500);
        viewHolder.discover1img.setAnimation(animation);
        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        viewHolder.discover1name.setTypeface(typeface);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WebActivity.class);
                intent.putExtra("url", SingleModleUrl.singleModleUrl().getTestUrl() + "Show/culture/id/" + info.getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView discover1name;
        private ImageView discover1img;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            discover1name = (TextView) itemView.findViewById(R.id.discover_item1_name);
            discover1img = (ImageView) itemView.findViewById(R.id.discover_item1_img);
        }
    }
}
