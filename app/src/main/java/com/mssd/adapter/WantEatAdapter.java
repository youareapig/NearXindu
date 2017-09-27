package com.mssd.adapter;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mssd.data.ShitangNextBean;
import com.mssd.data.TestBean;
import com.mssd.data.WantEatBean;
import com.mssd.zl.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by DELL on 2017/8/30.
 */

public class WantEatAdapter extends RecyclerView.Adapter {
    private List<WantEatBean.DataBean> list;
    private Activity activity;

    public WantEatAdapter(List<WantEatBean.DataBean> list, Activity activity) {
        this.list = list;
        this.activity = activity;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shitang_fragment_item_recycle, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final WantEatBean.DataBean info = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.shitangtext1.setText(info.getStitle());
        viewHolder.shitangtext2.setText(info.getSname());
        ImageLoader.getInstance().displayImage(info.getUrl(),viewHolder.shitangimg);
        viewHolder.shoucang.setImageResource(R.mipmap.shoucang1);
        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        viewHolder.shitangtext1.setTypeface(typeface);
        viewHolder.shitangtext2.setTypeface(typeface);


    }

    @Override
    public int getItemCount() {
        if (list!=null){
            return list.size();
        }
        return 0;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView shitangtext1, shitangtext2, shitangtext3;
        private ImageView shitangimg,shoucang;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            shitangtext1 = (TextView) itemView.findViewById(R.id.shitang_fragment_recycle_text1);
            shitangtext2 = (TextView) itemView.findViewById(R.id.shitang_fragment_recycle_text2);
            shitangtext3 = (TextView) itemView.findViewById(R.id.shitang_fragment_recycle_text3);
            shitangimg = (ImageView) itemView.findViewById(R.id.shitang_fragment_recycle_img);
            shoucang= (ImageView) itemView.findViewById(R.id.shitang_fragment_recycle_shoucang);
        }
    }


}
