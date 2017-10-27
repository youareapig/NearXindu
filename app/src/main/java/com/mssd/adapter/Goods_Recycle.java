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

import com.bumptech.glide.Glide;
import com.mssd.data.GoodsBean;
import com.mssd.html.WebActivity;
import com.mssd.utils.SingleModleUrl;
import com.mssd.zl.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by DELL on 2017/8/30.
 */

public class Goods_Recycle extends RecyclerView.Adapter {
    private List<GoodsBean.DataBean> list;
    private Activity activity;

    public Goods_Recycle(List<GoodsBean.DataBean> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_item, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final GoodsBean.DataBean info = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.goodstext.setText(info.getPname());
        Glide.with(activity).load(info.getUrl()).centerCrop().placeholder(R.mipmap.hui).error(R.mipmap.hui).into(viewHolder.goodsimg);
        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        viewHolder.goodstext.setTypeface(typeface);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), WebActivity.class);
                intent.putExtra("url", SingleModleUrl.singleModleUrl().getTestUrl()+"Show/line/id/"+info.getId());
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
        private TextView goodstext;
        private ImageView goodsimg;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            goodsimg = (ImageView) itemView.findViewById(R.id.goods_item_img);
            goodstext = (TextView) itemView.findViewById(R.id.goods_item_text);
        }
    }
}
