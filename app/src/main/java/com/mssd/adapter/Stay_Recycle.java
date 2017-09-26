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

import com.mssd.data.StayNextBean;
import com.mssd.zl.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by DELL on 2017/8/30.
 */

public class Stay_Recycle extends RecyclerView.Adapter {
    private List<StayNextBean.DataBean> list;
    private Activity activity;

    public Stay_Recycle(List<StayNextBean.DataBean> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.stay_item_recycle, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        StayNextBean.DataBean info = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.stayname.setText(info.getStitle());
        ImageLoader.getInstance().displayImage(info.getUrl(),viewHolder.stayimg);
        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        viewHolder.stayname.setTypeface(typeface);
    }

    @Override
    public int getItemCount() {
        if (list!=null){
            return list.size();
        }
        return 0;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView stayname;
        private ImageView stayimg;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            stayname = (TextView) itemView.findViewById(R.id.stay_item_recycle_text);
            stayimg = (ImageView) itemView.findViewById(R.id.stay_item_recycle_img);
        }
    }
}
