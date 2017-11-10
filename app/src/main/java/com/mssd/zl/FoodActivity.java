package com.mssd.zl;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mssd.adapter.BannerAdapter;
import com.mssd.adapter.FoodAdapter;
import com.mssd.adapter.FoodBannerAdapter;
import com.mssd.adapter.Food_Recycle1;
import com.mssd.adapter.Food_Recycle2;
import com.mssd.data.FoodDateBean;
import com.mssd.data.LocationBean;
import com.mssd.myview.CustomProgressDialog;
import com.mssd.utils.ListItemDecoration;
import com.mssd.utils.ObservableScrollView;
import com.mssd.utils.SingleModleUrl;
import com.mssd.utils.SpacesItemDecoration;
import com.mssd.utils.SpacesItemDecoration2;
import com.mssd.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.AutoLayoutActivity;
import com.zhy.autolayout.AutoLinearLayout;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

public class FoodActivity extends AutoLayoutActivity implements ViewPager.OnPageChangeListener, ObservableScrollView.ScrollViewListener {
    @BindView(R.id.food_recycleTop)
    RecyclerView foodRecycleTop;
    @BindView(R.id.food_tx1)
    TextView foodTx1;
    @BindView(R.id.food_tx2)
    TextView foodTx2;
    @BindView(R.id.food_viewpager)
    ViewPager foodViewpager;
    @BindView(R.id.food_viewpager_group)
    LinearLayout foodViewpagerGroup;
    @BindView(R.id.food_recycle2)
    RecyclerView foodRecycle2;
    @BindView(R.id.food_recycle3)
    RecyclerView foodRecycle3;
    @BindView(R.id.food_scroll)
    ObservableScrollView foodScroll;
    @BindView(R.id.food_titleName)
    TextView foodTitleName;
    @BindView(R.id.food_title)
    RelativeLayout foodTitle;
    @BindView(R.id.food_back)
    RelativeLayout foodBack;
    @BindView(R.id.food_search)
    ImageView foodSearch;
    @BindView(R.id.food_viewpage_title)
    TextView foodViewpageTitle;
    private Unbinder unbinder;
    private List<LocationBean> list;
    private LocationBean locationBean1, locationBean2, locationBean3;
    private List<FoodDateBean.DataBean.GastronomeBean> bannerList;
    private ImageView[] viewpagerTips, viewpagerImage;
    private Handler handler;
    private ViewPagerThread thread;
    private List<FoodDateBean.DataBean.FeastBean> list_2;
    private List<FoodDateBean.DataBean.CanteenBean> list_1;
    private int heigh = 300;
    private int root = 1;
    private SharedPreferences sharedPreferences;
    private String userID;
    private FoodAdapter adapter;
    private Typeface typeface,typeface1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        unbinder = ButterKnife.bind(this);
        initbean();
        changeTitle();
        changeFont();
        getRecycle_Top();
        getNetBean();

    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        foodTx1.setTypeface(typeface1);
        foodTx2.setTypeface(typeface);
        foodTitleName.setTypeface(typeface1);

    }

    private void initbean() {
        sharedPreferences = getSharedPreferences("xindu", MODE_PRIVATE);
        userID = sharedPreferences.getString("userid", "0");
        list = new ArrayList<>();
        locationBean1 = new LocationBean("家  .宴", R.mipmap.jiayan_icon);
        locationBean2 = new LocationBean("食  .堂", R.mipmap.shitang_icon);
        locationBean3 = new LocationBean("食  .家", R.mipmap.shijia_icon);
        list.add(locationBean1);
        list.add(locationBean2);
        list.add(locationBean3);
    }

    private void getRecycle_Top() {
        foodRecycleTop.addItemDecoration(new SpacesItemDecoration2(40));
        foodRecycleTop.setLayoutManager(new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false));
        foodRecycleTop.setAdapter(new Food_Recycle1(list, this));
    }


    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= heigh) {
            float scale = (float) y / heigh;
            float alpha = (255 * scale);
            foodTitle.setVisibility(View.VISIBLE);
            foodTitle.setAlpha(alpha);
            foodTitle.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
        }
        if (y > heigh) {
            foodTitle.setBackgroundColor(Color.WHITE);
        }

    }

    @OnClick({R.id.food_back, R.id.food_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.food_back:
                finish();
                break;
            case R.id.food_search:
                break;
        }
    }

    private class ViewPagerThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (root != 0) {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        }
    }


    private void changeTitle() {
        ViewTreeObserver observer = foodRecycleTop.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                foodRecycleTop.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                foodScroll.setScrollViewListener(FoodActivity.this);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        root = 0;
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
                viewpagerTips[i].setBackgroundResource(R.drawable.foodvpchecked);
                TranslateAnimation translateAnimation = new TranslateAnimation(0, 10, 0, 10);
                translateAnimation.setDuration(500);
                viewpagerTips[i].startAnimation(translateAnimation);
                foodViewpageTitle.setText(bannerList.get(i).getGname());
                foodViewpageTitle.setTypeface(typeface);
            } else {
                viewpagerTips[i].setBackgroundResource(R.drawable.foodvpunchecked);
            }
        }
    }

    private void getNetBean() {
        final CustomProgressDialog customProgressDialog = new CustomProgressDialog(this, R.drawable.frame, R.style.dialog);
        customProgressDialog.setCanceledOnTouchOutside(false);
        customProgressDialog.show();
        foodScroll.setVisibility(View.GONE);
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Eatlive/eat");
        params.addBodyParameter("uid", userID);
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                foodScroll.setVisibility(View.VISIBLE);
                Gson gson = new Gson();
                final FoodDateBean bean = gson.fromJson(result, FoodDateBean.class);
                if (bean.getCode() == 2000) {
                    bannerList = bean.getData().getGastronome();
                    list_1 = bean.getData().getCanteen();
                    list_2 = bean.getData().getFeast();
                    //TODO banner
                    thread = new ViewPagerThread();
                    viewpagerTips = new ImageView[bannerList.size()];
                    for (int i = 0; i < viewpagerTips.length; i++) {
                        ImageView imageView = new ImageView(FoodActivity.this);
                        AutoLinearLayout.LayoutParams layoutParams = new AutoLinearLayout.LayoutParams(50, 4);
                        layoutParams.leftMargin = 10;
                        layoutParams.rightMargin = 10;
                        imageView.setLayoutParams(layoutParams);
                        viewpagerTips[i] = imageView;
                        foodViewpageTitle.setText(bannerList.get(0).getGname());
                        foodViewpageTitle.setTypeface(typeface);
                        if (i == 0) {
                            viewpagerTips[i].setBackgroundResource(R.drawable.foodvpchecked);
                        } else {
                            viewpagerTips[i].setBackgroundResource(R.drawable.foodvpunchecked);

                        }
                        foodViewpagerGroup.addView(imageView);
                    }
                    viewpagerImage = new ImageView[bannerList.size()];
                    for (int i = 0; i < viewpagerImage.length; i++) {
                        ImageView imageView = new ImageView(FoodActivity.this);
                        viewpagerImage[i] = imageView;
                        Glide.with(FoodActivity.this).load(bannerList.get(i).getUrl()).centerCrop().placeholder(R.mipmap.hui).error(R.mipmap.hui).into(imageView);
                    }

                    foodViewpager.setOnPageChangeListener(FoodActivity.this);
                    foodViewpager.setAdapter(new FoodBannerAdapter(viewpagerImage,bannerList));
                    handler = new Handler() {
                        int bannerNo = 0;

                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            try {
                                if (foodViewpager.getCurrentItem() == viewpagerImage.length - 1) {
                                    bannerNo = 0;
                                } else {
                                    bannerNo = foodViewpager.getCurrentItem() + 1;
                                }
                                foodViewpager.setCurrentItem(bannerNo, true);
                            } catch (Exception e) {

                            }

                        }
                    };
                    thread.start();
                    //TODO 左右滑动列表
                    foodRecycle2.addItemDecoration(new SpacesItemDecoration(20));
                    foodRecycle2.setLayoutManager(new GridLayoutManager(FoodActivity.this, 1, LinearLayoutManager.HORIZONTAL, false));
                    foodRecycle2.setAdapter(new Food_Recycle2(list_2, FoodActivity.this));
                    //TODO 底部列表
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FoodActivity.this, LinearLayoutManager.VERTICAL, false) {
                        @Override
                        public boolean canScrollVertically() {
                            return false;
                        }
                    };
                    foodRecycle3.addItemDecoration(new ListItemDecoration(80));
                    foodRecycle3.setLayoutManager(linearLayoutManager);
                    adapter = new FoodAdapter(list_1, FoodActivity.this);
                    foodRecycle3.setAdapter(adapter);
                } else {
                    ToastUtils.showShort(FoodActivity.this, R.string.nobean);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.showShort(FoodActivity.this, R.string.erroe);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                customProgressDialog.cancel();
            }

            @Override
            public boolean onCache(String result) {

                return false;
            }
        });
    }

    private void reStar() {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Eatlive/eat");
        params.addBodyParameter("uid", userID);
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                FoodDateBean bean = gson.fromJson(result, FoodDateBean.class);
                if (bean.getCode() == 2000) {
                    bannerList = bean.getData().getGastronome();
                    list_1 = bean.getData().getCanteen();
                    //TODO 底部列表
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FoodActivity.this, LinearLayoutManager.VERTICAL, false) {
                        @Override
                        public boolean canScrollVertically() {
                            return false;
                        }
                    };
                    foodRecycle3.setLayoutManager(linearLayoutManager);
                    adapter = new FoodAdapter(list_1, FoodActivity.this);
                    foodRecycle3.setAdapter(adapter);
                } else {
                    ToastUtils.showShort(FoodActivity.this, R.string.nobean);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.showShort(FoodActivity.this, R.string.erroe);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
            }

            @Override
            public boolean onCache(String result) {

                return false;
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        userID = sharedPreferences.getString("userid", "0");
        reStar();
    }
    @Override
    protected void onResume() {
        super.onResume();
        JAnalyticsInterface.onPageStart(this,"食主页");
    }


    @Override
    protected void onPause() {
        super.onPause();
        JAnalyticsInterface.onPageEnd(this,"食主页");
    }
}
