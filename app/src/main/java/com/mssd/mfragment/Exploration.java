package com.mssd.mfragment;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mssd.adapter.BannerAdapter;
import com.mssd.adapter.Exploration_Recycle_Food;
import com.mssd.adapter.Exploration_Recycle_House;
import com.mssd.adapter.Exploration_Recycle_Place;
import com.mssd.data.FoodBean;
import com.mssd.utils.SpacesItemDecoration;
import com.mssd.zl.R;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by DELL on 2017/8/30.
 */

public class Exploration extends Fragment implements ViewPager.OnPageChangeListener {
    @BindView(R.id.exploration_viewpager)
    ViewPager explorationViewpager;
    @BindView(R.id.exploration_viewpager_group)
    AutoLinearLayout explorationViewpagerGroup;
    @BindView(R.id.exploration_tx1)
    TextView explorationTx1;
    @BindView(R.id.exploration_tx2)
    TextView explorationTx2;
    @BindView(R.id.exploration_tx3)
    TextView explorationTx3;
    @BindView(R.id.exploration_recy_food)
    RecyclerView explorationRecyFood;
    @BindView(R.id.exploration_tx4)
    TextView explorationTx4;
    @BindView(R.id.exploration_recy_house)
    RecyclerView explorationRecyHouse;
    @BindView(R.id.exploration_tx5)
    TextView explorationTx5;
    @BindView(R.id.exploration_recy_place)
    RecyclerView explorationRecyPlace;
    private Unbinder unbinder;
    private ImageView[] viewpagerTips, viewpagerImage;
    private List<Integer> imgList;
    private Handler handler;
    private ViewPagerThread thread;
    private FoodBean foodBean1, foodBean2, foodBean3, foodBean4;
    private List<FoodBean> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exploration, container, false);
        unbinder = ButterKnife.bind(this, view);
        changeFont();
        initbean();
        banner();
        getFood();
        getHouse();
        getPlace();
        return view;
    }

    private void changeFont() {
        AssetManager assetManager = getActivity().getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        Typeface typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        explorationTx2.setTypeface(typeface);
        explorationTx1.setTypeface(typeface1);
        explorationTx3.setTypeface(typeface1);
        explorationTx4.setTypeface(typeface1);
        explorationTx5.setTypeface(typeface1);
    }

    private void banner() {
        thread = new ViewPagerThread();
        imgList = new ArrayList<>();
        imgList.add(R.mipmap.test);
        imgList.add(R.mipmap.test);
        viewpagerTips = new ImageView[imgList.size()];
        for (int i = 0; i < viewpagerTips.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(15, 15);
            layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 10;
            imageView.setLayoutParams(layoutParams);
            viewpagerTips[i] = imageView;
            if (i == 0) {
                viewpagerTips[i].setBackgroundResource(R.drawable.vpchecked);
            } else {
                viewpagerTips[i].setBackgroundResource(R.drawable.vpunchecked);

            }
            explorationViewpagerGroup.addView(imageView);
        }
        viewpagerImage = new ImageView[imgList.size()];
        for (int i = 0; i < viewpagerImage.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            viewpagerImage[i] = imageView;
            imageView.setImageResource(imgList.get(i));
        }
        explorationViewpager.setOnPageChangeListener(Exploration.this);
        explorationViewpager.setAdapter(new BannerAdapter(viewpagerImage));
        handler = new Handler() {
            int bannerNo = 0;

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (explorationViewpager.getCurrentItem() == viewpagerImage.length - 1) {
                    bannerNo = 0;
                } else {
                    bannerNo = explorationViewpager.getCurrentItem() + 1;
                }
                explorationViewpager.setCurrentItem(bannerNo, true);
            }
        };
        thread.start();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setImageBackground(position % viewpagerImage.length);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setImageBackground(int selectItems) {
        for (int i = 0; i < viewpagerTips.length; i++) {
            if (i == selectItems) {
                viewpagerTips[i].setBackgroundResource(R.drawable.vpchecked);
            } else {
                viewpagerTips[i].setBackgroundResource(R.drawable.vpunchecked);
            }
        }
    }

    private class ViewPagerThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (true) {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        }
    }

    private void initbean() {
        list = new ArrayList<>();
        foodBean1 = new FoodBean(R.mipmap.test, "蛋炒饭");
        foodBean2 = new FoodBean(R.mipmap.test, "黄焖鸡");
        foodBean3 = new FoodBean(R.mipmap.test, "翘脚牛肉");
        foodBean4 = new FoodBean(R.mipmap.test, "翘脚牛肉");
        list.add(foodBean1);
        list.add(foodBean2);
        list.add(foodBean3);
        list.add(foodBean4);
    }

    private void getFood() {
        explorationRecyFood.addItemDecoration(new SpacesItemDecoration(40));
        explorationRecyFood.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false));
        explorationRecyFood.setAdapter(new Exploration_Recycle_Food(list, getActivity()));
    }

    private void getHouse() {
        explorationRecyHouse.addItemDecoration(new SpacesItemDecoration(40));
        explorationRecyHouse.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        explorationRecyHouse.setAdapter(new Exploration_Recycle_House(list, getActivity()));
    }
    private void getPlace(){
        explorationRecyPlace.addItemDecoration(new SpacesItemDecoration(40));
        explorationRecyPlace.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        explorationRecyPlace.setAdapter(new Exploration_Recycle_Place(list, getActivity()));
    }
}
