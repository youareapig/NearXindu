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
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.mssd.data.ExperienceNextBean;
import com.mssd.data.TiyanBean;
import com.mssd.html.WebsActivity;
import com.mssd.utils.SingleModleUrl;
import com.mssd.zl.LoginActivity;
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

public class TiyanAdapter extends RecyclerView.Adapter {
    private List<TiyanBean.DataBean> list;
    private Activity activity;
    private boolean isLogin;
    private String userID, tID;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public TiyanAdapter(List<TiyanBean.DataBean> list, Activity activity) {
        this.list = list;
        this.activity = activity;
        sharedPreferences = activity.getSharedPreferences("xindu", activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userID = sharedPreferences.getString("userid", "0");
        isLogin = sharedPreferences.getBoolean("islogin", false);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.experience_item_recycle, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final TiyanBean.DataBean info = list.get(position);
        final ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.experienceRecycletitle1.setText(info.getStitle());
        viewHolder.experienceRecycletitle2.setText(info.getSname());
        ImageLoader.getInstance().displayImage(info.getUrl(),viewHolder.experienceRecycleimg);
        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        viewHolder.experienceRecycletitle1.setTypeface(typeface);
        viewHolder.experienceRecycletitle2.setTypeface(typeface);
        if (info.getIscheck() == 0) {
            viewHolder.shoucang.setImageResource(R.mipmap.shoucang);
        } else {
            viewHolder.shoucang.setImageResource(R.mipmap.shoucang1);
        }
        viewHolder.shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin == true) {
                    tID = info.getId() + "";
                    addCollect(viewHolder);
                } else {
                    Intent intent = new Intent(v.getContext(), LoginActivity.class);
                    intent.putExtra("intentTag", 7);
                    v.getContext().startActivity(intent);
                }

            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tID = info.getId() + "";
                Intent intent = new Intent(v.getContext(), WebsActivity.class);
                editor.putString("mmCid", tID);
                editor.putString("mmType", "4");
                editor.commit();
                v.getContext().startActivity(intent);
            }
        });
    }
    private void addCollect(final ViewHolder hodler) {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Member/addllect");
        params.addBodyParameter("uid", userID);
        params.addBodyParameter("type", "4");
        params.addBodyParameter("tid", tID);
        Log.e("tag", "参数说明----->" + userID + "   " + tID);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag", "收藏" + result);
                try {
                    JSONObject json = new JSONObject(result);
                    if (json.getString("code").equals("3004")) {
                        AlphaAnimation animation= new AlphaAnimation(0,1);
                        animation.setDuration(500);
                        hodler.shoucang.startAnimation(animation);
                        hodler.shoucang.setImageResource(R.mipmap.shoucang1);
                    } else if (json.getString("code").equals("-3000")) {
                        offCollect(hodler);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("tag", "收藏error");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
    private void offCollect(final ViewHolder hodler) {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Member/offllect");
        params.addBodyParameter("uid", userID);
        params.addBodyParameter("tid", tID);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag", "取消收藏" + result);
                try {
                    JSONObject json = new JSONObject(result);
                    if (json.getString("code").equals("3006")) {
                        AlphaAnimation animation= new AlphaAnimation(0,1);
                        animation.setDuration(500);
                        hodler.shoucang.startAnimation(animation);
                        hodler.shoucang.setImageResource(R.mipmap.shoucang);
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
}
