package com.mssd.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mssd.data.FoodDateBean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/21 0021.
 */
public class BannerAdapter extends PagerAdapter {
    private ImageView[] imageViews;
    public BannerAdapter(ImageView[] imageViews) {
        this.imageViews=imageViews;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (imageViews==null){
            return 0;
        }
            return imageViews.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //TODO 如果图片是两张或者三张或出现bug,注释正常
        //container.removeView(imageViews[position % imageViews.length]);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        try {
            container.addView(imageViews[position % imageViews.length], 0);
        }catch (Exception e){

        }
        return imageViews[position % imageViews.length];
    }
}
