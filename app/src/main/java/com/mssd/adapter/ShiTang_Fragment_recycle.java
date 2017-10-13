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

import com.google.gson.Gson;
import com.mssd.data.HtmlBean;
import com.mssd.data.ShitangNextBean;
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

public class ShiTang_Fragment_recycle extends RecyclerView.Adapter {
    private List<ShitangNextBean.DataBean> list;
    private Activity activity;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String userID, tID;
    private boolean isLogin;
//    private double dangqianweidu, dangqianjingdu, latitude, longititude;
//    private SharedPreferences sharedPreferences;
//    private String location1, location2;
//    private static final double EARTH_RADIUS = 6378137.0;

    public ShiTang_Fragment_recycle(List<ShitangNextBean.DataBean> list, Activity activity) {
        this.list = list;
        this.activity = activity;
        sharedPreferences = activity.getSharedPreferences("xindu", activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userID = sharedPreferences.getString("userid", "0");
        isLogin = sharedPreferences.getBoolean("islogin", false);
//        sharedPreferences = activity.getSharedPreferences("xindu", activity.MODE_PRIVATE);
//        location1 = sharedPreferences.getString("latLng", "未知");
//        location2 = sharedPreferences.getString("longLng", "未知");
//        //TODO 当前位置纬度
//        dangqianweidu = Double.valueOf(location1).doubleValue();
//        //TODO 当前经度
//        dangqianjingdu = Double.valueOf(location2).doubleValue();


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shitang_fragment_item_recycle, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final ShitangNextBean.DataBean info = list.get(position);
        final ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.shitangtext1.setText(info.getStitle());
        viewHolder.shitangtext2.setText(info.getSname());
        ImageLoader.getInstance().displayImage(info.getUrl(), viewHolder.shitangimg);
        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        viewHolder.shitangtext1.setTypeface(typeface);
        viewHolder.shitangtext2.setTypeface(typeface);
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
                    intent.putExtra("intentTag", 4);
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
                editor.putString("mmType", "1");
                editor.commit();
                v.getContext().startActivity(intent);

            }
        });


//        GeocodeSearch geocodeSearch = new GeocodeSearch(activity);
//        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
//            @Override
//            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
//            }
//            @Override
//            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
//                if (i == 1000) {
//                    if (geocodeResult != null && geocodeResult.getGeocodeAddressList() != null &&
//                            geocodeResult.getGeocodeAddressList().size() > 0) {
//
//                        GeocodeAddress geocodeAddress = geocodeResult.getGeocodeAddressList().get(0);
//                        latitude = geocodeAddress.getLatLonPoint().getLatitude();//纬度
//                        longititude = geocodeAddress.getLatLonPoint().getLongitude();//经度
//
//
//                        double distance = getDistance(dangqianjingdu, dangqianweidu, longititude, latitude);
//                        //TODO 小数后保留一位小数
//                        BigDecimal bigDecimal = new BigDecimal(distance / 1000);
//                        double distance1 = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
//                        ViewHolder viewHolder = (ViewHolder) holder;
//                        viewHolder.shitangtext1.setText(info.getTitle1());
//                        viewHolder.shitangtext2.setText(info.getTitle2());
//                        viewHolder.shitangtext3.setText(distance1 + "km");
//                        viewHolder.shitangimg.setImageResource(info.getImg());
//                        AssetManager assetManager = activity.getAssets();
//                        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
//                        viewHolder.shitangtext1.setTypeface(typeface);
//                        viewHolder.shitangtext2.setTypeface(typeface);
//                        viewHolder.shitangtext3.setTypeface(typeface);
//
//                    } else {
//                        Toast.makeText(activity, "定位失败", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });

//        GeocodeQuery geocodeQuery = new GeocodeQuery(info.getTitle2().trim(), "29");
//        geocodeSearch.getFromLocationNameAsyn(geocodeQuery);


    }


    private void addCollect(final ViewHolder hodler) {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Member/addllect");
        params.addBodyParameter("uid", userID);
        params.addBodyParameter("type", "1");
        params.addBodyParameter("tid", tID);
        Log.e("tag", "参数说明----->" + userID + "   " + tID);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag", "收藏" + result);
                try {
                    JSONObject json = new JSONObject(result);
                    if (json.getString("code").equals("3004")) {
                        AlphaAnimation animation = new AlphaAnimation(0, 1);
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
                        AlphaAnimation animation = new AlphaAnimation(0, 1);
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

//    //TODO 经纬度计算距离
//    private static double getDistance(double longitude1, double latitude1,
//                                      double longitude2, double latitude2) {
//        double Lat1 = rad(latitude1);
//        double Lat2 = rad(latitude2);
//        double a = Lat1 - Lat2;
//        double b = rad(longitude1) - rad(longitude2);
//        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
//                + Math.cos(Lat1) * Math.cos(Lat2)
//                * Math.pow(Math.sin(b / 2), 2)));
//        s = s * EARTH_RADIUS;
//        s = Math.round(s * 10000) / 10000;
//        return s;
//    }

//    private static double rad(double d) {
//        return d * Math.PI / 180.0;
//    }

}
