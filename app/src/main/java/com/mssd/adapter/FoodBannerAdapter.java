package com.mssd.adapter;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mssd.data.FoodDateBean;
import com.mssd.html.WebActivity;
import com.mssd.utils.SingleModleUrl;

import java.util.List;

/**
 * Created by Administrator on 2016/10/21 0021.
 */
public class FoodBannerAdapter extends PagerAdapter {
    private ImageView[] imageViews;
    private List<FoodDateBean.DataBean.GastronomeBean> bannerList;
    public FoodBannerAdapter(ImageView[] imageViews, List<FoodDateBean.DataBean.GastronomeBean> bannerList) {
        this.imageViews=imageViews;
        this.bannerList=bannerList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
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
            imageViews[position].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(v.getContext(), WebActivity.class);
                    intent.putExtra("url", SingleModleUrl.singleModleUrl().getTestUrl() + "Show/gourmet/id/" + bannerList.get(position).getId());
                    v.getContext().startActivity(intent);
                }
            });
        try {
            container.addView(imageViews[position % imageViews.length], 0);
        }catch (Exception e){

        }
        return imageViews[position % imageViews.length];
    }
}
