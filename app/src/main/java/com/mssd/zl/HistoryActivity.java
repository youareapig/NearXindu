package com.mssd.zl;

import android.content.Intent;
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

import com.mssd.adapter.History_Recycle1;
import com.mssd.adapter.History_Recycle2;
import com.mssd.data.FoodBean;
import com.mssd.utils.ObservableScrollView;
import com.mssd.utils.SpacesItemDecoration;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HistoryActivity extends AutoLayoutActivity implements ObservableScrollView.ScrollViewListener {
    @BindView(R.id.historyText1)
    TextView historyText1;
    @BindView(R.id.historyRecycleImg1)
    RelativeLayout historyRecycleImg1;
    @BindView(R.id.historyRecycle)
    RecyclerView historyRecycle;
    @BindView(R.id.historyRecycleEndImg1)
    ImageView historyRecycleEndImg1;
    @BindView(R.id.history_text2)
    TextView historyText2;
    @BindView(R.id.history_text3)
    TextView historyText3;
    @BindView(R.id.historyRecycleImg2)
    RelativeLayout historyRecycleImg2;
    @BindView(R.id.historyRecycle1)
    RecyclerView historyRecycle1;
    @BindView(R.id.historyRecycleEndImg2)
    ImageView historyRecycleEndImg2;
    @BindView(R.id.history_text4)
    TextView historyText4;
    @BindView(R.id.historyRecycleImg3)
    RelativeLayout historyRecycleImg3;
    @BindView(R.id.historyRecycle2)
    RecyclerView historyRecycle2;
    @BindView(R.id.historyRecycleEndImg3)
    ImageView historyRecycleEndImg3;
    @BindView(R.id.history_Topimg)
    ImageView historyTopimg;
    @BindView(R.id.history_scroll)
    ObservableScrollView historyScroll;
    @BindView(R.id.history_Title)
    RelativeLayout historyTitle;
    private Unbinder unbinder;
    private List<FoodBean> list;
    private FoodBean foodBean1, foodBean2, foodBean3;
    private int heigh = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        unbinder = ButterKnife.bind(this);
        changeFont();
        changeTitle();
        initbean();
        getRecycle1();
        getRecycle2();
        getRecycle3();
    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        Typeface typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        historyText1.setTypeface(typeface1);
        historyText2.setTypeface(typeface1);

        historyText3.setTypeface(typeface1);
        historyText4.setTypeface(typeface1);
    }

    private void initbean() {
        list = new ArrayList<>();
        foodBean1 = new FoodBean(R.mipmap.test, "九大碗");
        foodBean2 = new FoodBean(R.mipmap.test, "九大碗");
        foodBean3 = new FoodBean(R.mipmap.test, "九大碗");
        list.add(foodBean1);
        list.add(foodBean2);
        list.add(foodBean3);
    }

    private void getRecycle1() {
        historyRecycle.addItemDecoration(new SpacesItemDecoration(20));
        historyRecycle.setLayoutManager(new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false));
        historyRecycle.setAdapter(new History_Recycle1(list, this));
    }

    private void getRecycle2() {
        historyRecycle1.addItemDecoration(new SpacesItemDecoration(20));
        historyRecycle1.setLayoutManager(new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false));
        historyRecycle1.setAdapter(new History_Recycle1(list, this));
    }

    private void getRecycle3() {
        historyRecycle2.addItemDecoration(new SpacesItemDecoration(20));
        historyRecycle2.setLayoutManager(new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false));
        historyRecycle2.setAdapter(new History_Recycle2(list, this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void changeTitle() {
        ViewTreeObserver observer = historyTopimg.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                historyTopimg.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                historyScroll.setScrollViewListener(HistoryActivity.this);
            }
        });
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= heigh) {
            float scale = (float) y / heigh;
            float alpha = (255 * scale);
            historyTitle.setVisibility(View.VISIBLE);
            historyTitle.setAlpha(alpha);
            historyTitle.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
        }
    }

    @OnClick({R.id.historyRecycleImg1, R.id.historyRecycleEndImg1, R.id.historyRecycleImg2, R.id.historyRecycleEndImg2, R.id.historyRecycleImg3, R.id.historyRecycleEndImg3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.historyRecycleImg1:
                Intent intent = new Intent(this, TalkHistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.historyRecycleEndImg1:
                Intent intent1 = new Intent(this, TalkHistoryActivity.class);
                startActivity(intent1);
                break;
            case R.id.historyRecycleImg2:
                Intent intent5=new Intent(this,NatureActivity.class);
                startActivity(intent5);
                break;
            case R.id.historyRecycleEndImg2:
                Intent intent6=new Intent(this,NatureActivity.class);
                startActivity(intent6);
                break;
            case R.id.historyRecycleImg3:
                Intent intent2 = new Intent(this, HOFActivity.class);
                startActivity(intent2);
                break;
            case R.id.historyRecycleEndImg3:
                Intent intent3 = new Intent(this, HOFActivity.class);
                startActivity(intent3);
                break;
        }
    }
}
