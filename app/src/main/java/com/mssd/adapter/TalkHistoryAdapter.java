package com.mssd.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/10/21 0021.
 */
public class TalkHistoryAdapter extends PagerAdapter {
    private View[] views;
    public TalkHistoryAdapter(View[] views) {
        this.views=views;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
            return views.length;
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
    public Object instantiateItem(ViewGroup container, int position) {
        try {
            container.addView(views[position % views.length], 0);
        }catch (Exception e){

        }
        return views[position % views.length];
    }

}
