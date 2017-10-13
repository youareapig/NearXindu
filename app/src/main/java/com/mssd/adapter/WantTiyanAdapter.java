package com.mssd.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mssd.data.HtmlBean;
import com.mssd.data.TiyanBean;
import com.mssd.data.WantEatBean;
import com.mssd.html.WebsActivity;
import com.mssd.utils.SingleModleUrl;
import com.mssd.zl.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.utils.AutoUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by DELL on 2017/8/30.
 */

public class WantTiyanAdapter extends RecyclerView.Adapter {
    private List<WantEatBean.DataBean> list;
    private Activity activity;
    private String userID, tID;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private MyShow myShow;
    public WantTiyanAdapter(List<WantEatBean.DataBean> list, Activity activity) {
        this.list = list;
        this.activity = activity;
        sharedPreferences = activity.getSharedPreferences("xindu", activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userID = sharedPreferences.getString("userid", "0");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.experience_item_recycle, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final WantEatBean.DataBean info = list.get(position);
        final ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.experienceRecycletitle1.setText(info.getStitle());
        viewHolder.experienceRecycletitle2.setText(info.getSname());
        ImageLoader.getInstance().displayImage(info.getUrl(),viewHolder.experienceRecycleimg);
        viewHolder.shoucang.setImageResource(R.mipmap.shoucang1);
        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        viewHolder.experienceRecycletitle1.setTypeface(typeface);
        viewHolder.experienceRecycletitle2.setTypeface(typeface);
        viewHolder.shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tID = info.getTid()+"";
                offCollect(position,viewHolder);
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tID = info.getTid() + "";
            }
        });
    }

    private void offCollect(final int position, final ViewHolder holder) {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Member/offllect");
        params.addBodyParameter("uid", userID);
        params.addBodyParameter("tid", tID);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    if (json.getString("code").equals("3006")) {
                        list.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        if (list.size()==0){
                            myShow.mShow(true);
                        }else {
                            myShow.mShow(false);
                        }
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("tag", "取消收藏error");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

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
        private TextView experienceRecycletitle1,experienceRecycletitle2;
        private ImageView experienceRecycleimg,shoucang;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            experienceRecycletitle1 = (TextView) itemView.findViewById(R.id.experience_item_recycle_title1);
            experienceRecycletitle2 = (TextView) itemView.findViewById(R.id.experience_item_recycle_title2);
            experienceRecycleimg = (ImageView) itemView.findViewById(R.id.experience_item_recycle_img);
            shoucang= (ImageView) itemView.findViewById(R.id.experience_item_recycle_shoucang);
        }
    }
    public interface MyShow{
        void mShow(boolean b);
    }
    public void callBack(MyShow myShow){
        this.myShow=myShow;
    }
}
