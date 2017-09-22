package com.mssd.zl;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mssd.adapter.BannerAdapter;
import com.mssd.data.FoodBean;
import com.zhy.autolayout.AutoLayoutActivity;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class JiaYanActivity extends AutoLayoutActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.jiayan_title)
    TextView jiayanTitle;
    @BindView(R.id.jiayan_viewpager)
    ViewPager jiayanViewpager;
    @BindView(R.id.jiayan_name)
    TextView jiayanName;
    @BindView(R.id.jiayan_viewpager_group)
    LinearLayout jiayanViewpagerGroup;
    @BindView(R.id.jiayan_go)
    ImageView jiayanGo;
    @BindView(R.id.jiayan_scroll)
    ScrollView jiayanScroll;
    private Unbinder unbinder;
    private ImageView[] viewpagerTips, views;
    private List<FoodBean> list;
    private FoodBean foodBean1, foodBean2, foodBean3;
    private Typeface typeface, typeface1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jia_yan);
        unbinder = ButterKnife.bind(this);
        initbean();
        changeFont();
        getViewpager();
    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        jiayanTitle.setTypeface(typeface1);
    }

    private void initbean() {
        list = new ArrayList<>();
        foodBean1 = new FoodBean(R.mipmap.test1, "红烧排骨");
        foodBean2 = new FoodBean(R.mipmap.test1, "宫保鸡丁");
        foodBean3 = new FoodBean(R.mipmap.test1, "白宰鸡");
        list.add(foodBean1);
        list.add(foodBean2);
        list.add(foodBean3);
        jiayanScroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    private void getViewpager() {
        viewpagerTips = new ImageView[list.size()];
        for (int i = 0; i < viewpagerTips.length; i++) {
            ImageView imageView = new ImageView(this);
            AutoLinearLayout.LayoutParams layoutParams = new AutoLinearLayout.LayoutParams(28, 28);
            layoutParams.leftMargin = 40;
            layoutParams.rightMargin = 40;
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            viewpagerTips[i] = imageView;
            jiayanName.setText(list.get(0).getName());
            jiayanName.setTypeface(typeface1);
            if (i == 0) {
                viewpagerTips[i].setBackgroundResource(R.mipmap.ic_launcher);
                AutoLinearLayout.LayoutParams layoutParams1 = new AutoLinearLayout.LayoutParams(38, 38);
                imageView.setLayoutParams(layoutParams1);
            } else {
                viewpagerTips[i].setBackgroundResource(R.mipmap.ic_launcher);
                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(28, 28);
                imageView.setLayoutParams(layoutParams2);

            }
            jiayanViewpagerGroup.addView(imageView);
        }

        views = new ImageView[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

            views[i] = imageView;
            imageView.setImageResource(list.get(i).getImg());

        }
        jiayanViewpager.setOnPageChangeListener(this);
        jiayanViewpager.setAdapter(new BannerAdapter(views));
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
                AutoLinearLayout.LayoutParams layoutParams3 = new AutoLinearLayout.LayoutParams(38, 38);
                viewpagerTips[i].setLayoutParams(layoutParams3);
                viewpagerTips[i].setBackgroundResource(R.mipmap.ic_launcher);
                jiayanName.setText(list.get(i).getName());
                jiayanName.setTypeface(typeface1);
            } else {
                AutoLinearLayout.LayoutParams layoutParams4 = new AutoLinearLayout.LayoutParams(28, 28);
                viewpagerTips[i].setLayoutParams(layoutParams4);
                viewpagerTips[i].setBackgroundResource(R.mipmap.ic_launcher);
            }
        }
    }

    @OnClick(R.id.jiayan_go)
    public void onViewClicked() {
        jiayanScroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        int[] coo = getScreenSize(this);
        jiayanScroll.smoothScrollTo(0, coo[1]);
    }

    //TODO 获取当前屏幕高度和宽度
    public static int[] getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return new int[]{outMetrics.widthPixels, outMetrics.heightPixels};
    }
}