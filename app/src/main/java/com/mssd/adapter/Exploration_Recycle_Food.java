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
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mssd.data.TansuoBean;
import com.mssd.html.WebActivity;
import com.mssd.utils.SingleModleUrl;
import com.mssd.zl.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by DELL on 2017/8/30.
 */

public class Exploration_Recycle_Food extends RecyclerView.Adapter {
    private List<TansuoBean.DataBean.MealBean> list;
    private Activity activity;

    public Exploration_Recycle_Food(List<TansuoBean.DataBean.MealBean> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.exploration_item_food, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final TansuoBean.DataBean.MealBean info = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.foodname.setText(info.getFname());
        Glide.with(activity).load(info.getUrl()).centerCrop().placeholder(R.mipmap.hui).error(R.mipmap.hui).into(viewHolder.foodimg);
        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        viewHolder.foodname.setTypeface(typeface);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), WebActivity.class);
                intent.putExtra("url", SingleModleUrl.singleModleUrl().getTestUrl()+"Show/feast/id/"+info.getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list!=null){
            return list.size();
        }
        return 0;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView foodname;
        private ImageView foodimg;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            foodname = (TextView) itemView.findViewById(R.id.exploration_item_name);
            foodimg = (ImageView) itemView.findViewById(R.id.exploration_item_img);
        }
    }
}
