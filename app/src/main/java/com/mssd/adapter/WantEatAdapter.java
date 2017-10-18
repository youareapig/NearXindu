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
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mssd.data.HtmlBean;
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

public class WantEatAdapter extends RecyclerView.Adapter {
    private List<WantEatBean.DataBean> list;
    private Activity activity;
    private String userID, tID;
    private SharedPreferences sharedPreferences;
    private MyShow myShow;
    private SharedPreferences.Editor editor;
    public WantEatAdapter(List<WantEatBean.DataBean> list, Activity activity) {
        this.list = list;
        this.activity = activity;
        sharedPreferences = activity.getSharedPreferences("xindu", activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userID = sharedPreferences.getString("userid", "0");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shitang_fragment_item_recycle, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final WantEatBean.DataBean info = list.get(position);
        final ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.shitangtext1.setText(info.getStitle());
        viewHolder.shitangtext2.setText(info.getSname());
        ImageLoader.getInstance().displayImage(info.getUrl(), viewHolder.shitangimg);
        AlphaAnimation animation= new AlphaAnimation(0.5f,1);
        animation.setDuration(500);
        viewHolder.shitangimg.setAnimation(animation);
        viewHolder.shoucang.setImageResource(R.mipmap.shoucang1);
        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        viewHolder.shitangtext1.setTypeface(typeface);
        viewHolder.shitangtext2.setTypeface(typeface);
        viewHolder.shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tID = info.getTid() + "";
                offCollect(position, viewHolder);
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tID = info.getTid() + "";
                Intent intent = new Intent(v.getContext(), WebsActivity.class);
                editor.putString("mmCid", tID);
                editor.putString("mmType", "1");
                editor.commit();
                v.getContext().startActivity(intent);
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
                        if (list.size() == 0) {
                            myShow.mShow(true);
                        } else {
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
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView shitangtext1, shitangtext2, shitangtext3;
        private ImageView shitangimg, shoucang;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            shitangtext1 = (TextView) itemView.findViewById(R.id.shitang_fragment_recycle_text1);
            shitangtext2 = (TextView) itemView.findViewById(R.id.shitang_fragment_recycle_text2);
            shitangtext3 = (TextView) itemView.findViewById(R.id.shitang_fragment_recycle_text3);
            shitangimg = (ImageView) itemView.findViewById(R.id.shitang_fragment_recycle_img);
            shoucang = (ImageView) itemView.findViewById(R.id.shitang_fragment_recycle_shoucang);
        }
    }

    public interface MyShow {
        void mShow(boolean b);
    }

    public void callBack(MyShow myShow) {
        this.myShow = myShow;
    }


}
