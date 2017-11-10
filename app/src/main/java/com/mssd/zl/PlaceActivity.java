package com.mssd.zl;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mssd.adapter.TestAdapter;
import com.mssd.place.Eat;
import com.mssd.place.Stay;
import com.mssd.place.Tiyan;
import com.mssd.place.Trip;
import com.mssd.xtab.XTabLayout;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

public class PlaceActivity extends AutoLayoutActivity {
    @BindView(R.id.place_title)
    TextView placeTitle;
    @BindView(R.id.place_tab)
    XTabLayout placeTab;
    @BindView(R.id.place_viewpager)
    ViewPager placeViewpager;
    @BindView(R.id.place_back)
    RelativeLayout placeBack;
    private Unbinder unbinder;
    private List<String> list;
    private List<Fragment> list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        unbinder = ButterKnife.bind(this);
        initbean();
        changeFont();
    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        Typeface typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        placeTitle.setTypeface(typeface1);

    }

    private void initbean() {
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        list.add("食");
        list.add("宿");
        list.add("行");
        list.add("体验");
        list1.add(new Eat());
        list1.add(new Stay());
        list1.add(new Trip());
        list1.add(new Tiyan());
        placeViewpager.setAdapter(new TestAdapter(getSupportFragmentManager(), list, list1));
        placeViewpager.setOffscreenPageLimit(4);
        placeTab.setupWithViewPager(placeViewpager);
        //TODO 避免默认第一个tab加粗
        placeTab.getTabAt(0).select();
        placeTab.getTabAt(1).select();
        placeViewpager.setCurrentItem(0);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.place_back)
    public void onViewClicked() {
        finish();
    }
    @Override
    protected void onResume() {
        super.onResume();
        JAnalyticsInterface.onPageStart(this,"想去");
    }


    @Override
    protected void onPause() {
        super.onPause();
        JAnalyticsInterface.onPageEnd(this,"想去");
    }
}
