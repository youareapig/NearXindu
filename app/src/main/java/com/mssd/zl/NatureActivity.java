package com.mssd.zl;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mssd.adapter.BannerAdapter;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NatureActivity extends AutoLayoutActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.nature_viewpager)
    ViewPager natureViewpager;
    @BindView(R.id.nature_viewpager_group)
    LinearLayout natureViewpagerGroup;
    @BindView(R.id.nature_viewpager_grouptext)
    LinearLayout natureViewpagerGrouptext;
    private Unbinder unbinder;
    private List<Integer> list;
    private List<String> list1;
    private ImageView[] viewpagerTips, views;
    private TextView[] textTips;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nature);
        unbinder = ButterKnife.bind(this);
        initbean();
        getViewpager();
    }

    private void initbean() {
        list = new ArrayList<>();
        list.add(R.mipmap.test);
        list.add(R.mipmap.ic_launcher);
        list.add(R.mipmap.test);
        list1 = new ArrayList<>();
        list1.add("农作物");
        list1.add("矿物");
        list1.add("家具");
    }

    private void getViewpager() {
        viewpagerTips = new ImageView[list.size()];
        textTips = new TextView[list.size()];
        for (int i = 0; i < viewpagerTips.length; i++) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(18, 18);
            layoutParams.leftMargin = 40;
            layoutParams.rightMargin = 40;
            imageView.setLayoutParams(layoutParams);
            viewpagerTips[i] = imageView;

            textView = new TextView(this);
            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(100, 50);
            layoutParams1.leftMargin = 40;
            layoutParams1.rightMargin = 40;
            textView.setLayoutParams(layoutParams1);
            textView.setTextColor(Color.parseColor("#ffffff"));
            textTips[i] = textView;
            textTips[i].setText(list1.get(i));

            if (i == 0) {
                viewpagerTips[i].setBackgroundResource(R.drawable.vpchecked);
                textView.setAlpha(1);
            } else {
                viewpagerTips[i].setBackgroundResource(R.drawable.vpunchecked);
                textView.setAlpha((float) 0.8);
            }
            natureViewpagerGroup.addView(imageView);
            natureViewpagerGrouptext.addView(textView);


        }
        views = new ImageView[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            views[i] = imageView;
            imageView.setImageResource(list.get(i));


        }

        natureViewpager.setOnPageChangeListener(this);
        natureViewpager.setAdapter(new BannerAdapter(views));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setImageBackground(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setImageBackground(int selectItems) {
        for (int i = 0; i < viewpagerTips.length; i++) {

            if (i == selectItems) {
                viewpagerTips[i].setBackgroundResource(R.drawable.vpchecked);
                textView.setAlpha(1);
            } else {
                viewpagerTips[i].setBackgroundResource(R.drawable.vpunchecked);
                textView.setAlpha((float) 0.8);
            }
        }
    }
}
