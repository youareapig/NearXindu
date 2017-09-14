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

import com.mssd.data.FoodBean;
import com.mssd.zl.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by DELL on 2017/8/30.
 */

public class Search_Recycle extends RecyclerView.Adapter {
    private List<String> list;
    private Activity activity;
    public Search_Recycle(List<String> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_recycle_item, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.searchtext.setText(list.get(position));
        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        viewHolder.searchtext.setTypeface(typeface);
    }



    @Override
    public int getItemCount() {
        if (list!=null){
            return list.size();
        }else {
            return 0;
        }

    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView searchtext;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            searchtext = (TextView) itemView.findViewById(R.id.search_recycle_item_text);
        }
    }

}
