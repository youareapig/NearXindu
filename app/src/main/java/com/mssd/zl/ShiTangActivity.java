package com.mssd.zl;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mssd.adapter.ShiTangTabAdapter;
import com.mssd.shitangfragment.All;
import com.mssd.shitangfragment.Cha;
import com.mssd.shitangfragment.Fan;
import com.mssd.shitangfragment.Jiu;
import com.mssd.shitangfragment.Mian;
import com.mssd.shitangfragment.Su;
import com.mssd.xtab.XTabLayout;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

public class ShiTangActivity extends AutoLayoutActivity {
    @BindView(R.id.shitang_title)
    TextView shitangTitle;
    @BindView(R.id.shitang_tab)
    XTabLayout shitangTab;
    @BindView(R.id.shitang_viewpager)
    ViewPager shitangViewpager;
    @BindView(R.id.shitang_back)
    RelativeLayout shitangBack;
    private Unbinder unbinder;
    private List<Fragment> list1;
    private List<String> titleList;
    private ShiTangTabAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shi_tang);
        unbinder = ButterKnife.bind(this);

        changeFont();
        initview();
    }


    private void initview() {
        list1 = new ArrayList<>();
        titleList = new ArrayList<>();
        list1.add(new All());
        list1.add(new Jiu());
        list1.add(new Fan());
        list1.add(new Mian());
        list1.add(new Cha());
        list1.add(new Su());
        titleList.add("全部");
        titleList.add("酒馆");
        titleList.add("饭馆");
        titleList.add("面馆");
        titleList.add("茶馆");
        titleList.add("素食");
        adapter=new ShiTangTabAdapter(getSupportFragmentManager(), titleList, list1);
        shitangViewpager.setAdapter(adapter);
        shitangViewpager.setOffscreenPageLimit(6);
        shitangTab.setupWithViewPager(shitangViewpager);
        shitangTab.getTabAt(0).select();
        shitangTab.getTabAt(1).select();
        shitangViewpager.setCurrentItem(0);
    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        Typeface typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        shitangTitle.setTypeface(typeface1);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    @OnClick(R.id.shitang_back)
    public void onViewClicked() {
        finish();
    }
    @Override
    protected void onResume() {
        super.onResume();
        JAnalyticsInterface.onPageStart(this,"食堂");
    }


    @Override
    protected void onPause() {
        super.onPause();
        JAnalyticsInterface.onPageEnd(this,"食堂");
    }
}
