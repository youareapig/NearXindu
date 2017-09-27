package com.mssd.zl;

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
import com.mssd.adapter.Pedestrian_Recycle;
import com.mssd.data.PedestrianBean;
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

public class PedestrianActivity extends AutoLayoutActivity {
    @BindView(R.id.pedestrian_title)
    TextView pedestrianTitle;
    @BindView(R.id.pedestrian_recycle)
    RecyclerView pedestrianRecycle;
    @BindView(R.id.isShow)
    TextView isShow;
    @BindView(R.id.pedestrian_pull)
    PullToRefreshLayout pedestrianPull;
    private Unbinder unbinder;
    private List<PedestrianBean.DataBean> list = new ArrayList<>();
    private int page = 1;
    private Pedestrian_Recycle adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedestrian);
        unbinder = ButterKnife.bind(this);
        changeFont();
        getNetBean();
        pedestrianPull.setRefreshListener(new BaseRefreshListener() {
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
                        pedestrianPull.finishRefresh();
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
                        pedestrianPull.finishLoadMore();
                    }
                }, 2000);
            }
        });
        addline();
    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        pedestrianTitle.setTypeface(typeface);
    }

    private void addline() {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        pedestrianRecycle.setLayoutManager(gridLayoutManager);
        pedestrianRecycle.addItemDecoration(new ListItemDecoration(90));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void getNetBean() {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Eatlive/traveler");
        params.addBodyParameter("lid", "3");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag", "行者" + result);
                Gson gson = new Gson();
                PedestrianBean bean = gson.fromJson(result, PedestrianBean.class);
                if (bean.getCode() == 2000) {
                    list.addAll(bean.getData());
                    pedestrianRecycle.setVisibility(View.VISIBLE);
                    isShow.setVisibility(View.GONE);
                    adapter = new Pedestrian_Recycle(list, PedestrianActivity.this);
                    pedestrianRecycle.setAdapter(adapter);
                    pedestrianPull.setCanLoadMore(true);
                } else if (bean.getCode() == -2000) {
                    pedestrianRecycle.setVisibility(View.GONE);
                    isShow.setVisibility(View.VISIBLE);
                    pedestrianPull.setCanLoadMore(false);
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
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Eatlive/traveler");
        params.addBodyParameter("lid", "3");
        params.addBodyParameter("page", page + "");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag", "行者" + result);
                Gson gson = new Gson();
                PedestrianBean bean = gson.fromJson(result, PedestrianBean.class);
                if (bean.getCode() == 2000) {
                    list.addAll(bean.getData());
                } else if (bean.getCode() == -2000) {
                    ToastUtils.showShort(PedestrianActivity.this, "加载完成");
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
}
