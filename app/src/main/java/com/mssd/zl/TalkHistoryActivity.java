package com.mssd.zl;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mssd.adapter.BannerAdapter;
import com.mssd.data.FoodBean;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TalkHistoryActivity extends AutoLayoutActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.talk_viewpager)
    ViewPager talkViewpager;
    @BindView(R.id.talk_viewpager_group)
    LinearLayout talkViewpagerGroup;
    @BindView(R.id.talk_viewpage_name)
    TextView talkViewpageName;
    @BindView(R.id.talk_title)
    TextView talkTitle;
    private Unbinder unbinder;
    private ImageView[] viewpagerTips, views;
    private List<FoodBean> list;
    private FoodBean foodBean1, foodBean2, foodBean3;
    private Typeface typeface1, typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_history);
        unbinder = ButterKnife.bind(this);
        initbean();
        changeFont();
        getViewpager();
    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        talkTitle.setTypeface(typeface1);
    }

    private void initbean() {
        list = new ArrayList<>();
        foodBean1 = new FoodBean(R.mipmap.test, "在成都的街头走一走");
        foodBean2 = new FoodBean(R.mipmap.ic_launcher, "哈哈哈哈哈");
        foodBean3 = new FoodBean(R.mipmap.test, "杀戮空间发牢骚家乐福就是杀戮空间发牢骚家乐福就是垃杀戮空间发牢骚家乐福就是垃");
        list.add(foodBean1);
        list.add(foodBean2);
        list.add(foodBean3);
    }

    private void getViewpager() {
        viewpagerTips = new ImageView[list.size()];
        for (int i = 0; i < viewpagerTips.length; i++) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(18, 18);
            layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 10;
            imageView.setLayoutParams(layoutParams);
            viewpagerTips[i] = imageView;
            talkViewpageName.setText(list.get(0).getName());
            talkViewpageName.setTypeface(typeface1);
            if (i == 0) {
                viewpagerTips[i].setBackgroundResource(R.drawable.vpchecked);
            } else {
                viewpagerTips[i].setBackgroundResource(R.drawable.vpunchecked);

            }
            talkViewpagerGroup.addView(imageView);
        }
        views = new ImageView[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            views[i] = imageView;
            imageView.setImageResource(list.get(i).getImg());


        }
        talkViewpager.setOnPageChangeListener(this);
        talkViewpager.setAdapter(new BannerAdapter(views));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void setImageBackground(int selectItems) {
        for (int i = 0; i < viewpagerTips.length; i++) {

            if (i == selectItems) {
                viewpagerTips[i].setBackgroundResource(R.drawable.vpchecked);
                talkViewpageName.setText(list.get(i).getName());
                talkViewpageName.setTypeface(typeface1);
            } else {
                viewpagerTips[i].setBackgroundResource(R.drawable.vpunchecked);
            }
        }
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
}
