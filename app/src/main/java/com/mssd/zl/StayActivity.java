package com.mssd.zl;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.mssd.adapter.Stay_Gallery;
import com.mssd.adapter.Stay_Recycle;
import com.mssd.data.StayBean;
import com.mssd.data.StayNextBean;
import com.mssd.utils.ListItemDecoration;
import com.mssd.utils.ObservableScrollView;
import com.mssd.utils.SingleModleUrl;
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

public class StayActivity extends AutoLayoutActivity implements ObservableScrollView.ScrollViewListener {
    @BindView(R.id.stay_gallery)
    Gallery stayGallery;
    @BindView(R.id.stay_tag)
    RelativeLayout stayTag;
    @BindView(R.id.stay_tx1)
    TextView stayTx1;
    @BindView(R.id.stay_tx2)
    TextView stayTx2;
    @BindView(R.id.stay_recycle)
    RecyclerView stayRecycle;
    @BindView(R.id.stay_scroll)
    ObservableScrollView stayScroll;
    @BindView(R.id.stayText1)
    TextView stayText1;
    @BindView(R.id.stay_Title)
    RelativeLayout stayTitle;
    @BindView(R.id.stay_top_bg)
    ImageView stayTopBg;
    @BindView(R.id.stay_pull)
    PullToRefreshLayout stayPull;
    @BindView(R.id.isShow)
    TextView isShow;
    private Unbinder unbinder;
    private List<String> list;
    private Stay_Gallery gallery_adapter;
    private List<StayNextBean.DataBean> list_1 = new ArrayList<>();
    private int heigh = 100;
    private Stay_Recycle stay_recycle;
    private int page = 1;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stay);
        unbinder = ButterKnife.bind(this);
        changeFont();
        changeTitle();
        titleBean();


        stayPull.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        titleBean();
                        stayPull.finishRefresh();
                    }
                }, 2000);

            }

            @Override
            public void loadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        getLoadmore();
                        stay_recycle.notifyDataSetChanged();
                        stayPull.finishLoadMore();
                    }
                }, 2000);

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(StayActivity.this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        stayRecycle.addItemDecoration(new ListItemDecoration(120));
        stayRecycle.setLayoutManager(linearLayoutManager);
    }

    private void titleBean() {
        sharedPreferences = getSharedPreferences("xindu", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        list = new ArrayList<>();
        list.add("酒店");
        list.add("客栈");
        list.add("民宿");
        list.add("青旅");
        list.add("露营");
        gallery_adapter = new Stay_Gallery(list, StayActivity.this);
        stayGallery.setAdapter(gallery_adapter);
        stayGallery.setSpacing(160);
        stayGallery.setSelection(5 * 200);
        stayGallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gallery_adapter.setSelectItem(position % list.size());
                gallery_adapter.notifyDataSetChanged();
                Log.e("tag", "民宿id" + position % list.size());
                if (list_1 != null) {
                    list_1.clear();
                }
                page = 1;
                switch (position % list.size()) {
                    case 0:
                        editor.putString("cid", "8");
                        editor.commit();
                        getBean("8");
                        break;
                    case 1:
                        editor.putString("cid", "7");
                        editor.commit();
                        getBean("7");
                        break;
                    case 2:
                        editor.putString("cid", "6");
                        editor.commit();
                        getBean("6");
                        break;
                    case 3:
                        editor.putString("cid", "9");
                        editor.commit();
                        getBean("9");
                        break;
                    case 4:
                        editor.putString("cid", "10");
                        editor.commit();
                        getBean("10");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        stayTopBg.setImageResource(R.mipmap.test);

    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        Typeface typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        stayTx1.setTypeface(typeface1);
        stayTx2.setTypeface(typeface);
        stayText1.setTypeface(typeface1);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void changeTitle() {
        ViewTreeObserver observer = stayTag.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                stayTag.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                stayScroll.setScrollViewListener(StayActivity.this);
            }
        });
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= heigh) {
            float scale = (float) y / heigh;
            float alpha = (255 * scale);
            stayTitle.setVisibility(View.VISIBLE);
            stayTitle.setAlpha(alpha);
            stayTitle.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
        }
    }


    private void getBean(String cid) {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Eatlive/pageList");
        params.addBodyParameter("type", "2");
        params.addBodyParameter("cid", cid);
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag", "具体数据" + result);
                Gson gson = new Gson();
                StayNextBean bean = gson.fromJson(result, StayNextBean.class);
                if (bean.getCode() == 2000) {
                    list_1.addAll(bean.getData());
                    stayRecycle.setVisibility(View.VISIBLE);
                    isShow.setVisibility(View.GONE);
                    stay_recycle = new Stay_Recycle(list_1, StayActivity.this);
                    stayRecycle.setAdapter(stay_recycle);
                    stay_recycle.notifyDataSetChanged();
                } else if (bean.getCode() == -2000) {
                    Log.e("tag", "没有数据");
                    stayRecycle.setVisibility(View.GONE);
                    isShow.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("tag", "请求错误");
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

    private void getLoadmore() {
        String pullcid = sharedPreferences.getString("cid", "0");
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Eatlive/pageList");
        params.addBodyParameter("type", "2");
        params.addBodyParameter("cid", pullcid);
        params.addBodyParameter("page", page + "");
        Log.e("tag", "分页码" + page);
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag", "分页数据" + result);
                Gson gson = new Gson();
                StayNextBean bean = gson.fromJson(result, StayNextBean.class);
                if (bean.getCode() == 2000) {
                    list_1.addAll(bean.getData());
                } else {
                    ToastUtils.showShort(StayActivity.this, "没有更多数据");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("tag", "请求错误");
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
