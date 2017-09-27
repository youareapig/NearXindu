package com.mssd.zl;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.mssd.adapter.Trip_Recycle3;
import com.mssd.adapter.XingAdapter;
import com.mssd.data.TestBean;
import com.mssd.data.XingBean;
import com.mssd.utils.ListItemDecoration;
import com.mssd.utils.SingleModleUrl;
import com.mssd.utils.ToastUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TripClassfiyActivity extends AutoLayoutActivity {
    @BindView(R.id.tripclassfiyText1)
    TextView tripclassfiyText1;
    @BindView(R.id.tripclassfiyrecycle)
    RecyclerView tripclassfiyrecycle;
    @BindView(R.id.isShow)
    TextView isShow;
    @BindView(R.id.tripclassfiy_pull)
    PullToRefreshLayout tripclassfiyPull;
    private Unbinder unbinder;
    private List<XingBean.DataBean> list = new ArrayList<>();
    private String cid, name;
    private XingAdapter adapter;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_classfiy);
        unbinder = ButterKnife.bind(this);
        initbean();
        changeFont();

        getNetBean();
        tripclassfiyPull.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (list != null) {
                            list.clear();
                        }
                        page = 1;
                        getNetBean();
                        tripclassfiyPull.finishRefresh();
                    }
                }, 2000);
            }

            @Override
            public void loadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        loadmorer();
                        adapter.notifyDataSetChanged();
                        tripclassfiyPull.finishLoadMore();
                    }
                }, 2000);
            }
        });
        addline();
    }

    private void initbean() {
        Intent intent = getIntent();
        cid = intent.getStringExtra("cid");
        name = intent.getStringExtra("name");
        tripclassfiyText1.setText(name);

    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        Typeface typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        tripclassfiyText1.setTypeface(typeface1);
    }

    private void addline() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        tripclassfiyrecycle.addItemDecoration(new ListItemDecoration(20));
        tripclassfiyrecycle.setLayoutManager(linearLayoutManager);
    }

    private void getNetBean() {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Eatlive/pageList");
        params.addBodyParameter("type", "3");
        params.addBodyParameter("cid", cid);
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag", "山野风光" + result);
                Gson gson = new Gson();
                XingBean bean = gson.fromJson(result, XingBean.class);
                if (bean.getCode() == 2000) {
                    list.addAll(bean.getData());
                    adapter = new XingAdapter(list, TripClassfiyActivity.this);
                    tripclassfiyrecycle.setAdapter(adapter);
                    tripclassfiyrecycle.setVisibility(View.VISIBLE);
                    isShow.setVisibility(View.GONE);
                    tripclassfiyPull.setCanLoadMore(true);
                } else {
                    tripclassfiyrecycle.setVisibility(View.GONE);
                    isShow.setVisibility(View.VISIBLE);
                    tripclassfiyPull.setCanLoadMore(false);//禁止加载
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

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

    private void loadmorer() {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Eatlive/pageList");
        params.addBodyParameter("type", "3");
        params.addBodyParameter("cid", cid);
        params.addBodyParameter("page", page + "");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag", "山野风光" + result);
                Gson gson = new Gson();
                XingBean bean = gson.fromJson(result, XingBean.class);
                if (bean.getCode() == 2000) {
                    list.addAll(bean.getData());
                } else {
                    ToastUtils.showShort(TripClassfiyActivity.this, "加载完成");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
