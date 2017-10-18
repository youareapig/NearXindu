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

import com.mssd.data.FoodDateBean;
import com.mssd.html.WebActivity;
import com.mssd.utils.SingleModleUrl;
import com.mssd.zl.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by DELL on 2017/8/30.
 */

public class Food_Recycle2 extends RecyclerView.Adapter {
    private List<FoodDateBean.DataBean.FeastBean> list;
    private Activity activity;

    public Food_Recycle2(List<FoodDateBean.DataBean.FeastBean> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item_2, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final FoodDateBean.DataBean.FeastBean info = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.food2name.setText(info.getFname());
        ImageLoader.getInstance().displayImage(info.getUrl(),viewHolder.food2img);
        AlphaAnimation animation= new AlphaAnimation(0.5f,1);
        animation.setDuration(500);
        viewHolder.food2img.setAnimation(animation);
        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        viewHolder.food2name.setTypeface(typeface);
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
        private TextView food2name;
        private ImageView food2img;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            food2name = (TextView) itemView.findViewById(R.id.food_item2_name);
            food2img = (ImageView) itemView.findViewById(R.id.food_item2_img);
        }
    }
}
