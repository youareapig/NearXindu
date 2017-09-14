package com.mssd.zl;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import android.widget.Toast;

import com.androidkun.xtablayout.XTabLayout;
import com.mssd.adapter.ShiTangTab;
import com.mssd.mfragment.ShiTang;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ShiTangActivity extends AutoLayoutActivity {
    @BindView(R.id.shitang_title)
    TextView shitangTitle;
    @BindView(R.id.shitang_tab)
    XTabLayout shitangTab;
    @BindView(R.id.shitang_viewpager)
    ViewPager shitangViewpager;
    private Unbinder unbinder;
    private List<String> list;
    private List<Fragment> list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shi_tang);
        unbinder = ButterKnife.bind(this);
        initbean();
        changeFont();
    }

    private void initbean() {
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        list.add("全部");
        list.add("酒馆");
        list.add("饭馆");
        list.add("面馆");
        list.add("茶馆");
        list.add("牌馆");
        for (int i = 0; i <= 5; i++) {
            list1.add(new ShiTang());
        }
        shitangViewpager.setAdapter(new ShiTangTab(getSupportFragmentManager(), list, list1));
        shitangViewpager.setOffscreenPageLimit(5);
        shitangTab.setupWithViewPager(shitangViewpager);
        shitangViewpager.setCurrentItem(0);
        shitangTab.setOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(XTabLayout.Tab tab) {
                Toast.makeText(ShiTangActivity.this,"选中"+tab.getPosition(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTabUnselected(XTabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(XTabLayout.Tab tab) {
            }
        });
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
}
