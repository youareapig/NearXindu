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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mssd.data.CardBean;
import com.mssd.data.DiscoverBean;
import com.mssd.html.WebActivity;
import com.mssd.html.WebsActivity;
import com.mssd.utils.SingleModleUrl;
import com.mssd.zl.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by DELL on 2017/8/30.
 */

public class Card_Recycle extends RecyclerView.Adapter {
    private List<CardBean.DataBean> list;
    private Activity activity;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public Card_Recycle(List<CardBean.DataBean> list, Activity activity) {
        sharedPreferences = activity.getSharedPreferences("xindu", activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        this.list = list;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final CardBean.DataBean info = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.cardID.setText(info.getCoupon_code() + "");
        switch (info.getState()) {
            case 1:
                viewHolder.carduseTag.setText("未使用");
                break;
            case 2:
                viewHolder.carduseTag.setText("已使用");
                viewHolder.itemView.setAlpha(0.5f);
                break;
            case 3:
                viewHolder.carduseTag.setText("已过期");
                viewHolder.itemView.setAlpha(0.5f);
                break;

        }
        Glide.with(activity).load(info.getUrl()).centerCrop().placeholder(R.mipmap.hui).error(R.mipmap.hui).into(viewHolder.cardimg);
        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        viewHolder.cardbianma.setTypeface(typeface);
        viewHolder.cardID.setTypeface(typeface);
        viewHolder.carduseTag.setTypeface(typeface);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 微信公众号
                if (info.getIswei() == 1) {
                    Intent intent = new Intent(v.getContext(), WebActivity.class);
                    intent.putExtra("url", info.getWeiurl());
                    v.getContext().startActivity(intent);
                    //TODO h5详情
                } else {
                    Intent intent1 = new Intent(v.getContext(), WebsActivity.class);
                    switch (info.getType()) {
                        case 1:
                            editor.putString("mmCid", info.getShop_id() + "");
                            editor.putString("mmType", "1");
                            editor.commit();
                            v.getContext().startActivity(intent1);
                            break;
                        case 2:
                            editor.putString("mmCid", info.getShop_id() + "");
                            editor.putString("mmType", "2");
                            editor.commit();
                            v.getContext().startActivity(intent1);
                            break;
                        case 3:
                            editor.putString("mmCid", info.getShop_id() + "");
                            editor.putString("mmType", "3");
                            editor.commit();
                            v.getContext().startActivity(intent1);
                            break;
                        case 4:
                            editor.putString("mmCid", info.getShop_id() + "");
                            editor.putString("mmType", "4");
                            editor.commit();
                            v.getContext().startActivity(intent1);
                            break;
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView cardbianma, cardID, carduseTag;
        private ImageView cardimg;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            cardbianma = (TextView) itemView.findViewById(R.id.bianma);
            cardID = (TextView) itemView.findViewById(R.id.cardid);
            carduseTag = (TextView) itemView.findViewById(R.id.useTag);
            cardimg = (ImageView) itemView.findViewById(R.id.card_item_img);
        }
    }
}
