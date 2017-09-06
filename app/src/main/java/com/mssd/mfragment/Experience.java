package com.mssd.mfragment;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.mssd.adapter.Experience_Recycle;
import com.mssd.adapter.Experience_Recycle_Top;
import com.mssd.data.FoodBean;
import com.mssd.data.TestBean;
import com.mssd.utils.ListItemDecoration;
import com.mssd.utils.ObservableScrollView;
import com.mssd.utils.SpacesItemDecoration;
import com.mssd.zl.R;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by DELL on 2017/8/30.
 */

public class Experience extends Fragment implements ObservableScrollView.ScrollViewListener{
    @BindView(R.id.experience_recycleTop)
    RecyclerView experienceRecycleTop;
    @BindView(R.id.experience_tx1)
    TextView experienceTx1;
    @BindView(R.id.experience_tx2)
    TextView experienceTx2;
    @BindView(R.id.experence_recycle)
    RecyclerView experenceRecycle;
    @BindView(R.id.experence_scroll)
    ObservableScrollView experenceScroll;
    @BindView(R.id.experence_titleName)
    TextView experenceTitleName;
    @BindView(R.id.experence_title)
    AutoRelativeLayout experenceTitle;
    private Unbinder unbinder;
    private List<FoodBean> list;
    private List<TestBean> tlist;
    private FoodBean foodBean1, foodBean2, foodBean3, foodBean4, foodBean5;
    private TestBean testBean1, testBean2, testBean3;
    private int heigh=100;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.experience, container, false);
        unbinder = ButterKnife.bind(this, view);
        initdata();
        changeFont();
        changeTitle();
        getRecycleTop();
        getRecycle();
        return view;
    }

    private void initdata() {
        list = new ArrayList<>();
        tlist = new ArrayList<>();
        foodBean1 = new FoodBean(R.mipmap.test, "户外活动");
        foodBean2 = new FoodBean(R.mipmap.test, "艺术探究");
        foodBean3 = new FoodBean(R.mipmap.test, "匠心手作");
        foodBean4 = new FoodBean(R.mipmap.test, "茶会雅事");
        foodBean5 = new FoodBean(R.mipmap.test, "吃翔翔");
        list.add(foodBean1);
        list.add(foodBean2);
        list.add(foodBean3);
        list.add(foodBean4);
        list.add(foodBean5);
        testBean1 = new TestBean(R.mipmap.test, "故人西辞黄鹤楼", "来来来清仓大处理.99¥/人");
        testBean2 = new TestBean(R.mipmap.test, "抄词白帝彩云间", "来来来清仓大处理.19¥/人");
        testBean3 = new TestBean(R.mipmap.test, "莫道你在选择人", "来来来清仓大处理.29¥/人");
        tlist.add(testBean1);
        tlist.add(testBean2);
        tlist.add(testBean3);
    }

    private void changeFont() {
        AssetManager assetManager = getActivity().getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        Typeface typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        experienceTx1.setTypeface(typeface1);
        experienceTx2.setTypeface(typeface);
        experenceTitleName.setTypeface(typeface1);
    }

    private void getRecycleTop() {
        experienceRecycleTop.addItemDecoration(new SpacesItemDecoration(20));
        experienceRecycleTop.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false));
        experienceRecycleTop.setAdapter(new Experience_Recycle_Top(list, getActivity()));
    }

    private void getRecycle() {
        experenceRecycle.addItemDecoration(new ListItemDecoration(80));
        experenceRecycle.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        experenceRecycle.setAdapter(new Experience_Recycle(tlist, getActivity()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    private void changeTitle() {
        ViewTreeObserver observer = experienceRecycleTop.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                experienceRecycleTop.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                experenceScroll.setScrollViewListener(Experience.this);
            }
        });
    }
    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= heigh) {
            float scale = (float) y / heigh ;
            float alpha = (255 * scale);
            experenceTitle.setVisibility(View.VISIBLE);
            experenceTitle.setAlpha(alpha);
            experenceTitle.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
        }
    }
}
