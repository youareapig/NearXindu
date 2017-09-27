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

import com.mssd.data.LocationBean;
import com.mssd.data.TiyanClassfiyBean;
import com.mssd.zl.ExperenceClassfiyActivity;
import com.mssd.zl.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by DELL on 2017/8/30.
 */

public class Experience_Recycle_Top extends RecyclerView.Adapter {
    private List<TiyanClassfiyBean> list;
    private Activity activity;

    public Experience_Recycle_Top(List<TiyanClassfiyBean> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.experience_item_top, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final TiyanClassfiyBean info = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.experienceTopname.setText(info.getName());
        viewHolder.experienceTopimg.setImageResource(info.getIcon());
        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        viewHolder.experienceTopname.setTypeface(typeface);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ExperenceClassfiyActivity.class);
                intent.putExtra("cid",info.getId());
                intent.putExtra("name",info.getName());
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
        private TextView experienceTopname;
        private ImageView experienceTopimg;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            experienceTopname = (TextView) itemView.findViewById(R.id.experience_item_name);
            experienceTopimg = (ImageView) itemView.findViewById(R.id.experience_item_img);
        }
    }
}
