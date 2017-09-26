package com.mssd.zl;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mssd.adapter.BannerAdapter;
import com.mssd.adapter.FoodAdapter;
import com.mssd.adapter.Food_Recycle1;
import com.mssd.adapter.Food_Recycle2;
import com.mssd.adapter.Trip_Recycle3;
import com.mssd.data.FoodDateBean;
import com.mssd.data.LocationBean;
import com.mssd.data.TBean;
import com.mssd.data.TestBean;
import com.mssd.utils.ListItemDecoration;
import com.mssd.utils.ObservableScrollView;
import com.mssd.utils.SingleModleUrl;
import com.mssd.utils.SpacesItemDecoration;
import com.mssd.utils.SpacesItemDecoration2;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.AutoLayoutActivity;
import com.zhy.autolayout.AutoLinearLayout;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FoodActivity extends AutoLayoutActivity implements ViewPager.OnPageChangeListener,ObservableScrollView.ScrollViewListener {
    @BindView(R.id.food_recycleTop)
    RecyclerView foodRecycleTop;
    @BindView(R.id.food_tx1)
    TextView foodTx1;
    @BindView(R.id.food_tx2)
    TextView foodTx2;
    @BindView(R.id.food_viewpager)
    ViewPager foodViewpager;
    @BindView(R.id.food_viewpager_group)
    LinearLayout foodViewpagerGroup;
    @BindView(R.id.food_recycle2)
    RecyclerView foodRecycle2;
    @BindView(R.id.food_recycle3)
    RecyclerView foodRecycle3;
    @BindView(R.id.food_scroll)
    ObservableScrollView foodScroll;
    @BindView(R.id.food_titleName)
    TextView foodTitleName;
    @BindView(R.id.food_title)
    RelativeLayout foodTitle;
    private Unbinder unbinder;
    private List<LocationBean> list;
    private LocationBean locationBean1, locationBean2, locationBean3;
    private List<FoodDateBean.DataBean.GastronomeBean> bannerList;
    private ImageView[] viewpagerTips, viewpagerImage;
    private Handler handler;
    private ViewPagerThread thread;
    private List<FoodDateBean.DataBean.FeastBean> list_2;
    private List<FoodDateBean.DataBean.CanteenBean> list_1;
    private int heigh=100;
    private int root=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        unbinder = ButterKnife.bind(this);
        initbean();
        changeTitle();
        changeFont();
        getRecycle_Top();
        getNetBean();

    }
    private void changeFont() {
        AssetManager assetManager = getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        Typeface typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        foodTx1.setTypeface(typeface1);
        foodTx2.setTypeface(typeface);
        foodTitleName.setTypeface(typeface1);

    }

    private void initbean() {
        list = new ArrayList<>();
        locationBean1 = new LocationBean("家  .宴",R.mipmap.test );
        locationBean2 = new LocationBean("食  .堂",R.mipmap.test );
        locationBean3 = new LocationBean("食  .家",R.mipmap.test );
        list.add(locationBean1);
        list.add(locationBean2);
        list.add(locationBean3);
    }

    private void getRecycle_Top() {
        foodRecycleTop.addItemDecoration(new SpacesItemDecoration2(40));
        foodRecycleTop.setLayoutManager(new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false));
        foodRecycleTop.setAdapter(new Food_Recycle1(list, this));
    }


    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= heigh) {
            float scale = (float) y / heigh;
            float alpha = (255 * scale);
            foodTitle.setVisibility(View.VISIBLE);
            foodTitle.setAlpha(alpha);
            foodTitle.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
        }

    }

    private class ViewPagerThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (root!=0) {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        }
    }


    private void changeTitle() {
        ViewTreeObserver observer = foodRecycleTop.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                foodRecycleTop.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                foodScroll.setScrollViewListener(FoodActivity.this);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        root=0;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setImageBackground(position % viewpagerImage.length);


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setImageBackground(int selectItems) {
        for (int i = 0; i < viewpagerTips.length; i++) {

            if (i == selectItems) {
                viewpagerTips[i].setBackgroundResource(R.drawable.foodvpchecked);
                TranslateAnimation translateAnimation = new TranslateAnimation(0, 10, 0, 10);
                translateAnimation.setDuration(500);
                viewpagerTips[i].startAnimation(translateAnimation);
            } else {
                viewpagerTips[i].setBackgroundResource(R.drawable.foodvpunchecked);
            }
        }
    }
    private void getNetBean(){
        RequestParams params=new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl()+"Eatlive/eat");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("tag","食主页错误");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                Log.e("tag","食主页"+result);
                Gson gson=new Gson();
                FoodDateBean bean=gson.fromJson(result,FoodDateBean.class);
                if (bean.getCode()==2000){
                    bannerList=bean.getData().getGastronome();
                    list_1=bean.getData().getCanteen();
                    list_2=bean.getData().getFeast();
                    //TODO banner
                    thread = new ViewPagerThread();
                    viewpagerTips = new ImageView[bannerList.size()];
                    for (int i = 0; i < viewpagerTips.length; i++) {
                        ImageView imageView = new ImageView(FoodActivity.this);
                        AutoLinearLayout.LayoutParams layoutParams = new AutoLinearLayout.LayoutParams(50, 4);
                        layoutParams.leftMargin = 10;
                        layoutParams.rightMargin = 10;
                        imageView.setLayoutParams(layoutParams);
                        viewpagerTips[i] = imageView;
                        if (i == 0) {
                            viewpagerTips[i].setBackgroundResource(R.drawable.foodvpchecked);
                        } else {
                            viewpagerTips[i].setBackgroundResource(R.drawable.foodvpunchecked);

                        }
                        foodViewpagerGroup.addView(imageView);
                    }
                    viewpagerImage = new ImageView[bannerList.size()];
                    for (int i = 0; i < viewpagerImage.length; i++) {
                        ImageView imageView = new ImageView(FoodActivity.this);
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        viewpagerImage[i] = imageView;
                        ImageLoader.getInstance().displayImage(bannerList.get(i).getUrl(),imageView);
                    }
                    foodViewpager.setOnPageChangeListener(FoodActivity.this);
                    foodViewpager.setAdapter(new BannerAdapter(viewpagerImage));
                    handler = new Handler() {
                        int bannerNo = 0;

                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            try {
                                if (foodViewpager.getCurrentItem() == viewpagerImage.length - 1) {
                                    bannerNo = 0;
                                } else {
                                    bannerNo = foodViewpager.getCurrentItem() + 1;
                                }
                                foodViewpager.setCurrentItem(bannerNo, true);
                            }catch (Exception e){

                            }

                        }
                    };
                    thread.start();
                }
                //TODO 左右滑动列表
                foodRecycle2.addItemDecoration(new SpacesItemDecoration(20));
                foodRecycle2.setLayoutManager(new GridLayoutManager(FoodActivity.this, 1, LinearLayoutManager.HORIZONTAL, false));
                foodRecycle2.setAdapter(new Food_Recycle2(list_2,FoodActivity.this));
                //TODO 底部列表
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(FoodActivity.this,LinearLayoutManager.VERTICAL, false){
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };
                foodRecycle3.addItemDecoration(new ListItemDecoration(80));
                foodRecycle3.setLayoutManager(linearLayoutManager);
                foodRecycle3.setAdapter(new FoodAdapter(list_1,FoodActivity.this));
                return false;
            }
        });
    }
}
