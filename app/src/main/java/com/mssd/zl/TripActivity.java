package com.mssd.zl;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.mssd.adapter.Trip_Recycle1;
import com.mssd.adapter.Trip_Recycle2;
import com.mssd.adapter.Trip_Recycle3;
import com.mssd.data.LocationBean;
import com.mssd.data.TripClassfiyBean;
import com.mssd.data.TripNeatBean;
import com.mssd.utils.ListItemDecoration;
import com.mssd.utils.ObservableScrollView;
import com.mssd.utils.SingleModleUrl;
import com.mssd.utils.SpacesItemDecoration;
import com.mssd.utils.SpacesItemDecoration1;
import com.mssd.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.AutoLayoutActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TripActivity extends AutoLayoutActivity implements ObservableScrollView.ScrollViewListener {
    @BindView(R.id.trip_recycleTop)
    RecyclerView tripRecycleTop;
    @BindView(R.id.trip_scroll)
    ObservableScrollView tripScroll;
    @BindView(R.id.trip_tx1)
    TextView tripTx1;
    @BindView(R.id.trip_tx2)
    TextView tripTx2;
    @BindView(R.id.trip_show_img)
    ImageView tripShowImg;
    @BindView(R.id.trip_show_text1)
    TextView tripShowText1;
    @BindView(R.id.trip_show_text2)
    TextView tripShowText2;
    @BindView(R.id.trip_show1_img)
    ImageView tripShow1Img;
    @BindView(R.id.trip_show1_text1)
    TextView tripShow1Text1;
    @BindView(R.id.trip_show1_text2)
    TextView tripShow1Text2;
    @BindView(R.id.trip_recycleclassify)
    RecyclerView tripRecycleclassify;
    @BindView(R.id.trip_recycle_list)
    RecyclerView tripRecycleList;
    @BindView(R.id.tripText1)
    TextView tripText1;
    @BindView(R.id.trip_Title)
    RelativeLayout tripTitle;
    @BindView(R.id.trip_refresh)
    PullToRefreshLayout tripRefresh;
    private Unbinder unbinder;
    private List<LocationBean> mlist = new ArrayList<>();
    private LocationBean locationBean1, locationBean2, locationBean3;
    private List<TripClassfiyBean> clist = new ArrayList<>();
    private List<TripNeatBean.DataBean> list;
    private int heigh = 100;
    private Trip_Recycle3 adapter;
    private int page = 1;
    private TripClassfiyBean bean1,bean2,bean3,bean4,bean5,bean6,bean7,bean8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        unbinder = ButterKnife.bind(this);
        changeFont();
        changeTitle();
        initbean();

        getNetListBean();


        tripRefresh.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (list != null) {
                            list.clear();
                        }
                        page = 1;
                        getNetListBean();
                        tripRefresh.finishRefresh();
                    }
                }, 2000);

            }

            @Override
            public void loadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 结束加载更多
                        page++;
                        getNetListBean();
                        adapter.notifyDataSetChanged();
                        tripRefresh.finishLoadMore();
                    }
                }, 2000);

            }
        });
        addline();
    }

    private void initbean() {
        locationBean1 = new LocationBean("画廊", R.mipmap.test);
        locationBean2 = new LocationBean("好物", R.mipmap.test);
        locationBean3 = new LocationBean("行者", R.mipmap.test);
        mlist.add(locationBean1);
        mlist.add(locationBean2);
        mlist.add(locationBean3);
        bean1=new TripClassfiyBean("茶空间","11");
        bean2=new TripClassfiyBean("山野风光","12");
        bean3=new TripClassfiyBean("城市小景","13");
        bean4=new TripClassfiyBean("艺术人文","14");
        bean5=new TripClassfiyBean("博物馆","15");
        bean6=new TripClassfiyBean("建筑群","16");
        bean7=new TripClassfiyBean("美学馆","17");
        bean8=new TripClassfiyBean("书院","18");
        clist.add(bean1);
        clist.add(bean2);
        clist.add(bean3);
        clist.add(bean4);
        clist.add(bean5);
        clist.add(bean6);
        clist.add(bean7);
        clist.add(bean8);
        tripRecycleTop.setAdapter(new Trip_Recycle1(mlist, TripActivity.this));

        tripRecycleclassify.setAdapter(new Trip_Recycle2(clist, TripActivity.this));
    }

    private void addline() {
        tripRecycleTop.addItemDecoration(new SpacesItemDecoration(20));
        tripRecycleTop.setLayoutManager(new GridLayoutManager(TripActivity.this, 1, LinearLayoutManager.HORIZONTAL, false));
        tripRecycleclassify.addItemDecoration(new SpacesItemDecoration1(20));
        tripRecycleclassify.setLayoutManager(new GridLayoutManager(TripActivity.this, 4, LinearLayoutManager.VERTICAL, false));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        tripRecycleList.addItemDecoration(new ListItemDecoration(20));
        tripRecycleList.setLayoutManager(linearLayoutManager);
    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        Typeface typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        tripShowText1.setTypeface(typeface);
        tripShowText2.setTypeface(typeface);
        tripShow1Text1.setTypeface(typeface);
        tripShow1Text2.setTypeface(typeface);
        tripTx1.setTypeface(typeface1);
        tripTx2.setTypeface(typeface);
        tripText1.setTypeface(typeface1);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void changeTitle() {
        ViewTreeObserver observer = tripRecycleTop.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tripRecycleTop.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                tripScroll.setScrollViewListener(TripActivity.this);
            }
        });
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= heigh) {
            float scale = (float) y / heigh;
            float alpha = (255 * scale);
            tripTitle.setVisibility(View.VISIBLE);
            tripTitle.setAlpha(alpha);
            tripTitle.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
        }
    }


    private void getNetListBean() {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Eatlive/pageList");
        params.addBodyParameter("type", "3");
        params.addBodyParameter("page", page + "");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag", "秘境数据" + result);
                list = new ArrayList<TripNeatBean.DataBean>();
                Gson gson = new Gson();
                TripNeatBean bean = gson.fromJson(result, TripNeatBean.class);
                if (bean.getCode() == 2000) {
                    list.addAll(bean.getData());
                    adapter = new Trip_Recycle3(list, TripActivity.this);
                    tripRecycleList.setAdapter(adapter);
                    ImageLoader.getInstance().displayImage(list.get(0).getUrl(), tripShowImg);
                    tripShowText1.setText(list.get(0).getStitle());
                    tripShowText2.setText(list.get(0).getStitle());
                    ImageLoader.getInstance().displayImage(list.get(1).getUrl(), tripShow1Img);
                    tripShow1Text1.setText(list.get(1).getStitle());
                    tripShow1Text2.setText(list.get(1).getSname());
                    tripRefresh.setCanLoadMore(true);
                } else if (bean.getCode() == -2000) {
                    tripRefresh.setCanLoadMore(false);
                    ToastUtils.showShort(TripActivity.this, "没有更多数据");
                } else {
                    tripRefresh.setCanLoadMore(false);
                    ToastUtils.showShort(TripActivity.this, "数据异常");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("tag", "秘境数据请求错误");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {

                return false;
            }
        });
    }
}
