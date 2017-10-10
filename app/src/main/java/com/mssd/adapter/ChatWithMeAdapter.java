package com.mssd.adapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mssd.data.ChatBean;
import com.mssd.data.DiscoverBean;
import com.mssd.zl.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DELL on 2017/8/30.
 */

public class ChatWithMeAdapter extends RecyclerView.Adapter {
    private List<ChatBean.DataBean> list;
    private Activity activity;
    private SharedPreferences sharedPreferences;
    private String userhead, username;

    public ChatWithMeAdapter(List<ChatBean.DataBean> list, Activity activity) {
        this.list = list;
        this.activity = activity;
        sharedPreferences = activity.getSharedPreferences("xindu", activity.MODE_PRIVATE);
        userhead = sharedPreferences.getString("userHead", "0");
        username = sharedPreferences.getString("userName", "0");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatBean.DataBean info = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tv_right.setText(info.getMessage());
        if (info.getIsreplay() == 0) {
            viewHolder.left.setVisibility(View.GONE);
        } else {
            viewHolder.left.setVisibility(View.VISIBLE);
            viewHolder.tv_left.setText(info.getReplay());
        }
        viewHolder.tv_time_me.setText(info.getAddtime());
        viewHolder.tv_time_you.setText(info.getRepaytime());
        viewHolder.tv_name_me.setText(username);
        ImageLoader.getInstance().displayImage(userhead, viewHolder.head);
        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        viewHolder.tv_left.setTypeface(typeface);
        viewHolder.tv_right.setTypeface(typeface);
        viewHolder.tv_time_you.setTypeface(typeface);
        viewHolder.tv_time_me.setTypeface(typeface);
        viewHolder.tv_name_me.setTypeface(typeface);
        viewHolder.tv_name_you.setTypeface(typeface);
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;

    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private AutoRelativeLayout left, right;
        private TextView tv_left, tv_right, tv_time_me, tv_time_you, tv_name_me, tv_name_you;
        private CircleImageView head;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            left = (AutoRelativeLayout) itemView.findViewById(R.id.left);
            right = (AutoRelativeLayout) itemView.findViewById(R.id.right);
            tv_left = (TextView) itemView.findViewById(R.id.chat_item_content_left);
            tv_right = (TextView) itemView.findViewById(R.id.chat_item_content_right);
            tv_time_me = (TextView) itemView.findViewById(R.id.chat_item_date_wo);
            tv_time_you = (TextView) itemView.findViewById(R.id.chat_item_date_ni);
            tv_name_me = (TextView) itemView.findViewById(R.id.chat_item_text_right);
            tv_name_you = (TextView) itemView.findViewById(R.id.chat_item_text_left);
            head = (CircleImageView) itemView.findViewById(R.id.chat_item_head);
        }
    }
}
