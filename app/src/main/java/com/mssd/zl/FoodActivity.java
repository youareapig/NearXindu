package com.mssd.zl;

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
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mssd.adapter.BannerAdapter;
import com.mssd.adapter.Food_Recycle1;
import com.mssd.adapter.Food_Recycle2;
import com.mssd.adapter.Trip_Recycle3;
import com.mssd.data.FoodBean;
import com.mssd.data.TestBean;
import com.mssd.utils.ListItemDecoration;
import com.mssd.utils.ObservableScrollView;
import com.mssd.utils.SpacesItemDecoration;
import com.mssd.utils.SpacesItemDecoration2;
import com.zhy.autolayout.AutoLayoutActivity;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FoodActivity extends AutoLayoutActivity implements ViewPager.OnPageChangeListener,ObservableScrollView.ScrollViewListener {
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
    private Unbinder unbinder;
    private List<FoodBean> list;
    private FoodBean foodBean1, foodBean2, foodBean3;
    private List<Integer> list_1;
    private ImageView[] viewpagerTips, viewpagerImage;
    private Handler handler;
    private ViewPagerThread thread;
    private List<TestBean> list_2;
    private TestBean testBean1, testBean2;
    private int heigh=100;
    private int root=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        unbinder = ButterKnife.bind(this);
        initbean();
        changeTitle();
        changeFont();
        getRecycle_Top();
        banner();
        getRecycle_2();
        getRecycle_3();

    }
    private void changeFont() {
        AssetManager assetManager = getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        Typeface typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        foodTx1.setTypeface(typeface1);
        foodTx2.setTypeface(typeface);
        foodTitleName.setTypeface(typeface1);

    }

    private void initbean() {
        list = new ArrayList<>();
        foodBean1 = new FoodBean(R.mipmap.test, "家  .宴");
        foodBean2 = new FoodBean(R.mipmap.test, "食  .堂");
        foodBean3 = new FoodBean(R.mipmap.test, "食  .家");
        list.add(foodBean1);
        list.add(foodBean2);
        list.add(foodBean3);
        list_1 = new ArrayList<>();
        list_1.add(R.mipmap.test);
        list_1.add(R.mipmap.test);
        list_1.add(R.mipmap.test);
        list_2 = new ArrayList<>();
        testBean1 = new TestBean(R.mipmap.test, "黄河啊", "你好多水啊");
        testBean2 = new TestBean(R.mipmap.test, "黄河啊", "你好多水啊");
        list_2.add(testBean1);
        list_2.add(testBean2);
    }

    private void getRecycle_Top() {
        foodRecycleTop.addItemDecoration(new SpacesItemDecoration2(40));
        foodRecycleTop.setLayoutManager(new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false));
        foodRecycleTop.setAdapter(new Food_Recycle1(list, this));
    }

    private void banner() {
        thread = new ViewPagerThread();
        viewpagerTips = new ImageView[list_1.size()];
        for (int i = 0; i < viewpagerTips.length; i++) {
            ImageView imageView = new ImageView(this);
            AutoLinearLayout.LayoutParams layoutParams = new AutoLinearLayout.LayoutParams(50, 4);
            layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 10;
            imageView.setLayoutParams(layoutParams);
            viewpagerTips[i] = imageView;
            if (i == 0) {
                viewpagerTips[i].setBackgroundResource(R.drawable.foodvpchecked);
            } else {
                viewpagerTips[i].setBackgroundResource(R.drawable.foodvpunchecked);

            }
            foodViewpagerGroup.addView(imageView);
        }
        viewpagerImage = new ImageView[list_1.size()];
        for (int i = 0; i < viewpagerImage.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            viewpagerImage[i] = imageView;
            imageView.setImageResource(list_1.get(i));
        }
        foodViewpager.setOnPageChangeListener(FoodActivity.this);
        foodViewpager.setAdapter(new BannerAdapter(viewpagerImage));
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
                }catch (Exception e){

                }

            }
        };
        thread.start();
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

    }

    private class ViewPagerThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (root!=0) {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        }
    }

    private void getRecycle_2() {
        foodRecycle2.addItemDecoration(new SpacesItemDecoration(20));
        foodRecycle2.setLayoutManager(new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false));
        foodRecycle2.setAdapter(new Food_Recycle2(list, this));
    }

    private void getRecycle_3() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        foodRecycle3.addItemDecoration(new ListItemDecoration(80));
        foodRecycle3.setLayoutManager(linearLayoutManager);
        foodRecycle3.setAdapter(new Trip_Recycle3(list_2, this));
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
        root=0;
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
            } else {
                viewpagerTips[i].setBackgroundResource(R.drawable.foodvpunchecked);
            }
        }
    }
}
