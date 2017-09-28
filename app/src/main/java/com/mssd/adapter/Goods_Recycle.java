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

import com.mssd.data.GoodsBean;
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
        GoodsBean.DataBean info = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.goodstext.setText(info.getPname());
        ImageLoader.getInstance().displayImage(info.getUrl(),viewHolder.goodsimg);
        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        viewHolder.goodstext.setTypeface(typeface);
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
