package com.mssd.mfragment;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mssd.adapter.BannerAdapter;
import com.mssd.adapter.Exploration_Recycle_Food;
import com.mssd.adapter.Exploration_Recycle_House;
import com.mssd.adapter.Exploration_Recycle_Place;
import com.mssd.adapter.TansuoBean;
import com.mssd.data.TBean;
import com.mssd.utils.ObservableScrollView;
import com.mssd.utils.SingleModleUrl;
import com.mssd.utils.SpacesItemDecoration;
import com.mssd.zl.FoodActivity;
import com.mssd.zl.HistoryActivity;
import com.mssd.zl.R;
import com.mssd.zl.StayActivity;
import com.mssd.zl.TripActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DELL on 2017/8/30.
 */

public class Exploration extends Fragment implements ViewPager.OnPageChangeListener, ObservableScrollView.ScrollViewListener {
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
    @BindView(R.id.exploration_scroll)
    ObservableScrollView explorationScroll;
    @BindView(R.id.exploration_titleName)
    TextView explorationTitleName;
    @BindView(R.id.exploration_title)
    AutoRelativeLayout explorationTitle;
    @BindView(R.id.exploration_classfiy1_name)
    TextView explorationClassfiy1Name;
    @BindView(R.id.exploration_classfiy1)
    AutoRelativeLayout explorationClassfiy1;
    @BindView(R.id.exploration_classfiy2_name)
    TextView explorationClassfiy2Name;
    @BindView(R.id.exploration_classfiy2)
    AutoRelativeLayout explorationClassfiy2;
    @BindView(R.id.exploration_classfiy3_name)
    TextView explorationClassfiy3Name;
    @BindView(R.id.exploration_classfiy3)
    AutoRelativeLayout explorationClassfiy3;
    @BindView(R.id.exploration_classfiy4_name)
    TextView explorationClassfiy4Name;
    @BindView(R.id.exploration_classfiy4)
    AutoRelativeLayout explorationClassfiy4;
    private Unbinder unbinder;
    private ImageView[] viewpagerTips, viewpagerImage;
    private Handler handler;
    private ViewPagerThread thread;
    private int heigh = 100;
    private List<TansuoBean.DataBean.BannerBean> bannerList;
    private List<TansuoBean.DataBean.MealBean> list1;
    private List<TansuoBean.DataBean.StayBean> list2;
    private List<TansuoBean.DataBean.LineBean> list3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exploration, container, false);
        unbinder = ButterKnife.bind(this, view);
        changeFont();
        changeTitle();
        getNetBean();
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
        explorationTitleName.setTypeface(typeface1);
        explorationClassfiy1Name.setTypeface(typeface1);
        explorationClassfiy2Name.setTypeface(typeface1);
        explorationClassfiy3Name.setTypeface(typeface1);
        explorationClassfiy4Name.setTypeface(typeface1);
    }

    private void banner() {
        thread = new ViewPagerThread();
        viewpagerTips = new ImageView[bannerList.size()];
        for (int i = 0; i < viewpagerTips.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            AutoLinearLayout.LayoutParams layoutParams = new AutoLinearLayout.LayoutParams(15, 15);
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
        viewpagerImage = new ImageView[bannerList.size()];
        for (int i = 0; i < viewpagerImage.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            viewpagerImage[i] = imageView;
            ImageLoader.getInstance().displayImage(bannerList.get(i).getUrl(), imageView);
        }
        explorationViewpager.setOnPageChangeListener(Exploration.this);
        explorationViewpager.setAdapter(new BannerAdapter(viewpagerImage));
        handler = new Handler() {
            int bannerNo = 0;

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                try {
                    if (explorationViewpager.getCurrentItem() == viewpagerImage.length - 1) {
                        bannerNo = 0;
                    } else {
                        bannerNo = explorationViewpager.getCurrentItem() + 1;
                    }
                    explorationViewpager.setCurrentItem(bannerNo, true);
                } catch (Exception e) {

                }

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

    private void changeTitle() {
        ViewTreeObserver observer = explorationViewpager.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                explorationViewpager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                explorationScroll.setScrollViewListener(Exploration.this);
            }
        });
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= heigh) {
            float scale = (float) y / heigh;
            float alpha = (255 * scale);
            explorationTitle.setVisibility(View.VISIBLE);
            explorationTitle.setAlpha(alpha);
            explorationTitle.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
        }
    }

    @OnClick({R.id.exploration_classfiy1, R.id.exploration_classfiy2, R.id.exploration_classfiy3, R.id.exploration_classfiy4})
    public void onViewClicked(View view) {
        Intent intent1, intent2, intent3, intent4;
        switch (view.getId()) {
            case R.id.exploration_classfiy1:
                intent1 = new Intent(getActivity(), HistoryActivity.class);
                startActivity(intent1);
                break;
            case R.id.exploration_classfiy2:
                intent2 = new Intent(getActivity(), FoodActivity.class);
                startActivity(intent2);
                break;
            case R.id.exploration_classfiy3:
                intent3 = new Intent(getActivity(), StayActivity.class);
                startActivity(intent3);
                break;
            case R.id.exploration_classfiy4:
                intent4 = new Intent(getActivity(), TripActivity.class);
                startActivity(intent4);
                break;
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

    private void getFood() {
        explorationRecyFood.addItemDecoration(new SpacesItemDecoration(20));
        explorationRecyFood.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false));
        explorationRecyFood.setAdapter(new Exploration_Recycle_Food(list1, getActivity()));
    }

    private void getHouse() {
        explorationRecyHouse.addItemDecoration(new SpacesItemDecoration(20));
        explorationRecyHouse.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        explorationRecyHouse.setAdapter(new Exploration_Recycle_House(list2, getActivity()));
    }

    private void getPlace() {
        explorationRecyPlace.addItemDecoration(new SpacesItemDecoration(20));
        explorationRecyPlace.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        explorationRecyPlace.setAdapter(new Exploration_Recycle_Place(list3, getActivity()));
    }

    private void getNetBean() {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Index/index");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                Log.e("tag", "探索" + result);
                Gson gson = new Gson();
                TansuoBean bean = gson.fromJson(result, TansuoBean.class);
                if (bean.getCode() == 1000) {
                    bannerList = bean.getData().getBanner();
                    list1 = bean.getData().getMeal();
                    list2 = bean.getData().getStay();
                    list3 = bean.getData().getLine();
                    banner();
                    getFood();
                    getHouse();
                    getPlace();
                }
                return false;
            }
        });
    }
}
