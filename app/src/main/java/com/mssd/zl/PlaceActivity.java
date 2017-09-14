package com.mssd.zl;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.mssd.adapter.ShiTangTab;
import com.mssd.mfragment.ShiTang;
import com.mssd.place.Eat;
import com.mssd.place.Stay;
import com.mssd.place.Trip;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PlaceActivity extends AutoLayoutActivity {
    @BindView(R.id.place_title)
    TextView placeTitle;
    @BindView(R.id.place_tab)
    XTabLayout placeTab;
    @BindView(R.id.place_viewpager)
    ViewPager placeViewpager;
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
        list1.add(new Eat());
        list1.add(new Stay());
        list1.add(new Trip());
        placeViewpager.setAdapter(new ShiTangTab(getSupportFragmentManager(), list, list1));
        placeViewpager.setOffscreenPageLimit(3);
        placeTab.setupWithViewPager(placeViewpager);
        placeViewpager.setCurrentItem(0);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
