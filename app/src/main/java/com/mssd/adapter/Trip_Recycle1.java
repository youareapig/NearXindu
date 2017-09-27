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
import com.mssd.zl.GalleryActivity;
import com.mssd.zl.GoodsActivity;
import com.mssd.zl.PedestrianActivity;
import com.mssd.zl.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by DELL on 2017/8/30.
 */

public class Trip_Recycle1 extends RecyclerView.Adapter {
    private List<LocationBean> list;
    private Activity activity;

    public Trip_Recycle1(List<LocationBean> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_item_1, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        LocationBean info = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.trip1name.setText(info.getName());
        viewHolder.trip1img.setImageResource(info.getImg());
        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        viewHolder.trip1name.setTypeface(typeface);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){
                    case 0:
                        Intent intent=new Intent(v.getContext(), GalleryActivity.class);
                        v.getContext().startActivity(intent);
                        break;
                    case 1:
                        Intent intent1=new Intent(v.getContext(), GoodsActivity.class);
                        v.getContext().startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2=new Intent(v.getContext(), PedestrianActivity.class);
                        v.getContext().startActivity(intent2);
                        break;
                }
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
        private TextView trip1name;
        private ImageView trip1img;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            trip1name = (TextView) itemView.findViewById(R.id.trip_item1_name);
            trip1img = (ImageView) itemView.findViewById(R.id.trip_item1_img);
        }
    }
}
