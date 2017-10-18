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

import com.mssd.data.TripNeatBean;
import com.mssd.data.XingBean;
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

public class XingAdapter extends RecyclerView.Adapter {
    private List<XingBean.DataBean> list;
    private Activity activity;
    private boolean isLogin;
    private String userID, tID;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public XingAdapter(List<XingBean.DataBean> list, Activity activity) {
        this.list = list;
        this.activity = activity;
        sharedPreferences = activity.getSharedPreferences("xindu", activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userID = sharedPreferences.getString("userid", "0");
        isLogin = sharedPreferences.getBoolean("islogin", false);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_item_list, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final XingBean.DataBean info = list.get(position);
        final ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.trip1name.setText(info.getStitle());
        viewHolder.trip2name.setText(info.getSname());
        ImageLoader.getInstance().displayImage(info.getUrl(),viewHolder.tripimg);
        AlphaAnimation animation= new AlphaAnimation(0.5f,1);
        animation.setDuration(500);
        viewHolder.tripimg.setAnimation(animation);
        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        viewHolder.trip1name.setTypeface(typeface);
        viewHolder.trip2name.setTypeface(typeface);
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
                    intent.putExtra("intentTag", 6);
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
                editor.putString("mmType", "3");
                editor.commit();
                v.getContext().startActivity(intent);
            }
        });
    }
    private void addCollect(final ViewHolder hodler) {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Member/addllect");
        params.addBodyParameter("uid", userID);
        params.addBodyParameter("type", "3");
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
        private TextView trip1name,trip2name;
        private ImageView tripimg,shoucang;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            trip1name = (TextView) itemView.findViewById(R.id.trip_item1_list_text1);
            trip2name = (TextView) itemView.findViewById(R.id.trip_item1_list_text2);
            tripimg = (ImageView) itemView.findViewById(R.id.trip_item1_list_img);
            shoucang= (ImageView) itemView.findViewById(R.id.trip_item1_list_shoucang);
        }
    }
}
