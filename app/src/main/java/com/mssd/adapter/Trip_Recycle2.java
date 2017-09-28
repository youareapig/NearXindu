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
import android.widget.TextView;

import com.mssd.data.TripClassfiyBean;
import com.mssd.zl.R;
import com.mssd.zl.TripClassfiyActivity;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by DELL on 2017/8/30.
 */

public class Trip_Recycle2 extends RecyclerView.Adapter {
    private List<TripClassfiyBean> list;
    private Activity activity;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public Trip_Recycle2(List<TripClassfiyBean> list, Activity activity) {
        this.list = list;
        this.activity = activity;
        sharedPreferences = activity.getSharedPreferences("xindu", activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_item_classfiy, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final TripClassfiyBean bean=list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.trip1name.setText(bean.getName());
        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        viewHolder.trip1name.setTypeface(typeface);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),TripClassfiyActivity.class);
                editor.putString("mName",bean.getName());
                editor.putString("mID",bean.getCid());
                editor.commit();
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
        private TextView trip1name;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            trip1name = (TextView) itemView.findViewById(R.id.trip_item1_classfiy_name);
        }
    }
}
