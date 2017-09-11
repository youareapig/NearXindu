package com.mssd.zl;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mssd.adapter.Trip_Recycle1;
import com.mssd.adapter.Trip_Recycle2;
import com.mssd.adapter.Trip_Recycle3;
import com.mssd.data.FoodBean;
import com.mssd.data.TestBean;
import com.mssd.utils.ListItemDecoration;
import com.mssd.utils.ObservableScrollView;
import com.mssd.utils.SpacesItemDecoration;
import com.mssd.utils.SpacesItemDecoration1;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TripActivity extends AutoLayoutActivity implements ObservableScrollView.ScrollViewListener {
    @BindView(R.id.trip_recycleTop)
    RecyclerView tripRecycleTop;
    @BindView(R.id.trip_scroll)
    ObservableScrollView tripScroll;
    @BindView(R.id.trip_tx1)
    TextView tripTx1;
    @BindView(R.id.trip_tx2)
    TextView tripTx2;
    @BindView(R.id.trip_show_img)
    ImageView tripShowImg;
    @BindView(R.id.trip_show_text1)
    TextView tripShowText1;
    @BindView(R.id.trip_show_text2)
    TextView tripShowText2;
    @BindView(R.id.trip_show1_img)
    ImageView tripShow1Img;
    @BindView(R.id.trip_show1_text1)
    TextView tripShow1Text1;
    @BindView(R.id.trip_show1_text2)
    TextView tripShow1Text2;
    @BindView(R.id.trip_recycleclassify)
    RecyclerView tripRecycleclassify;
    @BindView(R.id.trip_recycle_list)
    RecyclerView tripRecycleList;
    @BindView(R.id.tripText1)
    TextView tripText1;
    @BindView(R.id.trip_Title)
    RelativeLayout tripTitle;
    private Unbinder unbinder;
    private List<FoodBean> list;
    private FoodBean foodBean1, foodBean2, foodBean3;
    private List<String> list_1;
    private List<TestBean> list_2;
    private TestBean testBean1, testBean2;
    private int heigh=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        unbinder = ButterKnife.bind(this);
        initbean();
        changeFont();
        changeTitle();
        getRecycle_Top();
        getRecycle_Classfiy();
        getRecycle_List();
    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        Typeface typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        tripShowText1.setTypeface(typeface);
        tripShowText2.setTypeface(typeface);
        tripShow1Text1.setTypeface(typeface);
        tripShow1Text2.setTypeface(typeface);
        tripTx1.setTypeface(typeface1);
        tripTx2.setTypeface(typeface);
        tripText1.setTypeface(typeface1);
    }

    private void initbean() {
        list = new ArrayList<>();
        foodBean1 = new FoodBean(R.mipmap.test, "画廊");
        foodBean2 = new FoodBean(R.mipmap.test, "好物");
        foodBean3 = new FoodBean(R.mipmap.test, "行者");
        list.add(foodBean1);
        list.add(foodBean2);
        list.add(foodBean3);
        list_1 = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            list_1.add("第" + i + "条目");
        }
        list_2 = new ArrayList<>();
        testBean1 = new TestBean(R.mipmap.test, "黄河啊", "你好多水啊");
        testBean2 = new TestBean(R.mipmap.test, "黄河啊", "你好多水啊");
        list_2.add(testBean1);
        list_2.add(testBean2);
    }

    private void getRecycle_Top() {
        tripRecycleTop.addItemDecoration(new SpacesItemDecoration(20));
        tripRecycleTop.setLayoutManager(new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false));
        tripRecycleTop.setAdapter(new Trip_Recycle1(list, this));
    }

    private void getRecycle_Classfiy() {
        tripRecycleclassify.addItemDecoration(new SpacesItemDecoration1(20));
        tripRecycleclassify.setLayoutManager(new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false));
        tripRecycleclassify.setAdapter(new Trip_Recycle2(list_1, this));
    }

    private void getRecycle_List() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        tripRecycleList.addItemDecoration(new ListItemDecoration(20));
        tripRecycleList.setLayoutManager(linearLayoutManager);
        tripRecycleList.setAdapter(new Trip_Recycle3(list_2, this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void changeTitle() {
        ViewTreeObserver observer = tripRecycleTop.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tripRecycleTop.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                tripScroll.setScrollViewListener(TripActivity.this);
            }
        });
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= heigh) {
            float scale = (float) y / heigh;
            float alpha = (255 * scale);
            tripTitle.setVisibility(View.VISIBLE);
            tripTitle.setAlpha(alpha);
            tripTitle.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
        }
    }
}
