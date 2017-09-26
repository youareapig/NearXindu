package com.mssd.adapter;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mssd.data.ShitangNextBean;
import com.mssd.data.TestBean;
import com.mssd.zl.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by DELL on 2017/8/30.
 */

public class Test1 extends RecyclerView.Adapter {
    private List<TestBean> list;
    private Activity activity;
//    private double dangqianweidu, dangqianjingdu, latitude, longititude;
//    private SharedPreferences sharedPreferences;
//    private String location1, location2;
//    private static final double EARTH_RADIUS = 6378137.0;

    public Test1(List<TestBean> list, Activity activity) {
        this.list = list;
        this.activity = activity;
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
        final TestBean info = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.shitangtext1.setText(info.getTitle2());
        viewHolder.shitangtext2.setText(info.getTitle1());
        AssetManager assetManager = activity.getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        viewHolder.shitangtext1.setTypeface(typeface);
        viewHolder.shitangtext2.setTypeface(typeface);
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

    @Override
    public int getItemCount() {
        if (list!=null){
            return list.size();
        }
        return 0;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView shitangtext1, shitangtext2, shitangtext3;
        private ImageView shitangimg;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            shitangtext1 = (TextView) itemView.findViewById(R.id.shitang_fragment_recycle_text1);
            shitangtext2 = (TextView) itemView.findViewById(R.id.shitang_fragment_recycle_text2);
            shitangtext3 = (TextView) itemView.findViewById(R.id.shitang_fragment_recycle_text3);
            shitangimg = (ImageView) itemView.findViewById(R.id.shitang_fragment_recycle_img);
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
