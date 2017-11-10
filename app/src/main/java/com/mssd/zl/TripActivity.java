package com.mssd.zl;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.mssd.adapter.Trip_Recycle1;
import com.mssd.adapter.Trip_Recycle2;
import com.mssd.adapter.Trip_Recycle3;
import com.mssd.data.LocationBean;
import com.mssd.data.TripClassfiyBean;
import com.mssd.data.TripNeatBean;
import com.mssd.html.WebActivity;
import com.mssd.html.WebsActivity;
import com.mssd.myview.CustomProgressDialog;
import com.mssd.utils.ListItemDecoration;
import com.mssd.utils.ObservableScrollView;
import com.mssd.utils.SingleModleUrl;
import com.mssd.utils.SpacesItemDecoration;
import com.mssd.utils.SpacesItemDecoration1;
import com.mssd.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

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
    @BindView(R.id.trip_show_shoucang1)
    ImageView tripShowShoucang1;
    @BindView(R.id.trip_show_shoucang2)
    ImageView tripShowShoucang2;
    @BindView(R.id.trip_back)
    RelativeLayout tripBack;
    @BindView(R.id.trip_search)
    ImageView tripSearch;
    private Unbinder unbinder;
    private List<LocationBean> mlist = new ArrayList<>();
    private LocationBean locationBean1, locationBean2, locationBean3;
    private List<TripClassfiyBean> clist = new ArrayList<>();
    private List<TripNeatBean.DataBean> list;
    private int heigh = 300;
    private Trip_Recycle3 adapter;
    private int page = 1;
    private TripClassfiyBean bean1, bean2, bean3, bean4, bean5, bean6, bean7, bean8;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String userID, tID;
    private boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        unbinder = ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("xindu", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userID = sharedPreferences.getString("userid", "0");
        isLogin = sharedPreferences.getBoolean("islogin", false);
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
                        loadmorer();
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
        bean1 = new TripClassfiyBean("茶空间", "11");
        bean2 = new TripClassfiyBean("山野风光", "12");
        bean3 = new TripClassfiyBean("城市小景", "13");
        bean4 = new TripClassfiyBean("艺术人文", "14");
        bean5 = new TripClassfiyBean("博物馆", "15");
        bean6 = new TripClassfiyBean("建筑群", "16");
        bean7 = new TripClassfiyBean("美学馆", "17");
        bean8 = new TripClassfiyBean("书院", "18");
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
        tripRecycleList.addItemDecoration(new ListItemDecoration(78));
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
        if (y > heigh) {
            tripTitle.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        userID = sharedPreferences.getString("userid", "0");
        if (list != null) {
            list.clear();
        }
        page = 1;
        getNetListBean();
    }

    private void getNetListBean() {
        final CustomProgressDialog customProgressDialog = new CustomProgressDialog(this, R.drawable.frame, R.style.dialog);
        customProgressDialog.setCanceledOnTouchOutside(false);
        customProgressDialog.show();
        tripRefresh.setVisibility(View.GONE);
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Eatlive/pageList");
        params.addBodyParameter("type", "3");
        params.addBodyParameter("uid", userID);
        Log.e("tag", "userid" + userID);
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                tripRefresh.setVisibility(View.VISIBLE);
                list = new ArrayList<TripNeatBean.DataBean>();
                Gson gson = new Gson();
                TripNeatBean bean = gson.fromJson(result, TripNeatBean.class);
                if (bean.getCode() == 2000) {
                    list.addAll(bean.getData());
                    adapter = new Trip_Recycle3(list, TripActivity.this);
                    tripRecycleList.setAdapter(adapter);
                    if (list.get(0).getIscheck() == 0) {
                        tripShowShoucang1.setImageResource(R.mipmap.shoucang);
                    } else {
                        tripShowShoucang1.setImageResource(R.mipmap.shoucang1);
                    }
                    if (list.get(1).getIscheck() == 0) {
                        tripShowShoucang2.setImageResource(R.mipmap.shoucang);
                    } else {
                        tripShowShoucang2.setImageResource(R.mipmap.shoucang1);
                    }
                    tripShowImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tID = list.get(0).getId() + "";
                            Intent intent = new Intent(v.getContext(), WebsActivity.class);
                            editor.putString("mmCid", tID);
                            editor.putString("mmType", "4");
                            editor.commit();
                            v.getContext().startActivity(intent);
                        }
                    });
                    tripShowShoucang1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (isLogin == true) {
                                tID = list.get(0).getId() + "";
                                addCollect(tripShowShoucang1);
                            } else {
                                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                                intent.putExtra("intentTag", 5);
                                v.getContext().startActivity(intent);
                            }
                        }
                    });
                    tripShow1Img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tID = list.get(1).getId() + "";
                            Intent intent = new Intent(v.getContext(), WebsActivity.class);
                            editor.putString("mmCid", tID);
                            editor.putString("mmType", "4");
                            editor.commit();
                            v.getContext().startActivity(intent);
                        }
                    });
                    tripShowShoucang2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (isLogin == true) {
                                tID = list.get(1).getId() + "";
                                addCollect(tripShowShoucang2);
                            } else {
                                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                                intent.putExtra("intentTag", 5);
                                v.getContext().startActivity(intent);
                            }
                        }
                    });
                    Glide.with(TripActivity.this).load(list.get(0).getUrl()).centerCrop().placeholder(R.mipmap.hui).error(R.mipmap.hui).into(tripShowImg);
                    tripShowText1.setText(list.get(0).getStitle());
                    tripShowText2.setText(list.get(0).getStitle());
                    Glide.with(TripActivity.this).load(list.get(1).getUrl()).centerCrop().placeholder(R.mipmap.hui).error(R.mipmap.hui).into(tripShow1Img);
                    tripShow1Text1.setText(list.get(1).getStitle());
                    tripShow1Text2.setText(list.get(1).getSname());
                    tripRefresh.setCanLoadMore(true);
                } else if (bean.getCode() == -2000) {
                    tripRefresh.setCanLoadMore(false);
                    ToastUtils.showShort(TripActivity.this, R.string.nobean);
                } else {
                    tripRefresh.setCanLoadMore(false);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.showShort(TripActivity.this, R.string.erroe);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                customProgressDialog.cancel();
            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
    }

    private void loadmorer() {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Eatlive/pageList");
        params.addBodyParameter("type", "3");
        params.addBodyParameter("page", page + "");
        params.addBodyParameter("uid", userID);
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                list = new ArrayList<TripNeatBean.DataBean>();
                Gson gson = new Gson();
                TripNeatBean bean = gson.fromJson(result, TripNeatBean.class);
                if (bean.getCode() == 2000) {
                    list.addAll(bean.getData());
                    adapter.notifyItemRangeChanged(0, bean.getData().size());
                } else {
                    ToastUtils.showShort(TripActivity.this, R.string.end);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.showShort(TripActivity.this, R.string.erroe);
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

    private void addCollect(final ImageView imageView) {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Member/addllect");
        params.addBodyParameter("uid", userID);
        params.addBodyParameter("type", "3");
        params.addBodyParameter("tid", tID);
        Log.e("tag", "参数说明----->" + userID + "   " + tID);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag", "收藏" + result);
                try {
                    JSONObject json = new JSONObject(result);
                    if (json.getString("code").equals("3004")) {
                        AlphaAnimation animation = new AlphaAnimation(0, 1);
                        animation.setDuration(500);
                        imageView.startAnimation(animation);
                        imageView.setImageResource(R.mipmap.shoucang1);
                    } else if (json.getString("code").equals("-3000")) {
                        offCollect(imageView);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("tag", "收藏error");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void offCollect(final ImageView imageView) {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Member/offllect");
        params.addBodyParameter("uid", userID);
        params.addBodyParameter("tid", tID);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag", "取消收藏" + result);
                try {
                    JSONObject json = new JSONObject(result);
                    if (json.getString("code").equals("3006")) {
                        AlphaAnimation animation = new AlphaAnimation(0, 1);
                        animation.setDuration(500);
                        imageView.startAnimation(animation);
                        imageView.setImageResource(R.mipmap.shoucang);
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("tag", "取消收藏error");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    @OnClick(R.id.trip_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        JAnalyticsInterface.onPageStart(this, "行");
    }


    @Override
    protected void onPause() {
        super.onPause();
        JAnalyticsInterface.onPageEnd(this, "行");
    }
}
