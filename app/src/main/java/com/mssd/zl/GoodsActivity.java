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
import com.mssd.adapter.Goods_Recycle;
import com.mssd.data.GoodsBean;
import com.mssd.data.TBean;
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

public class GoodsActivity extends AutoLayoutActivity {
    @BindView(R.id.goods_title)
    TextView goodsTitle;
    @BindView(R.id.goods_recycle)
    RecyclerView goodsRecycle;
    @BindView(R.id.isShow)
    TextView isShow;
    @BindView(R.id.goods_pull)
    PullToRefreshLayout goodsPull;
    private Unbinder unbinder;
    private List<GoodsBean.DataBean> list = new ArrayList<>();
    private Goods_Recycle adapter;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        unbinder = ButterKnife.bind(this);
        changeFont();
        getNetBean();

        goodsPull.setRefreshListener(new BaseRefreshListener() {
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
                        goodsPull.finishRefresh();
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
                        goodsPull.finishLoadMore();
                    }
                }, 2000);
            }
        });
        addline();
    }


    private void changeFont() {
        AssetManager assetManager = getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        goodsTitle.setTypeface(typeface);
    }

    private void addline() {
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        goodsRecycle.setLayoutManager(gridLayoutManager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void getNetBean() {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Eatlive/traveler");
        params.addBodyParameter("lid", "2");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag", "好物" + result);
                Gson gson = new Gson();
                GoodsBean bean = gson.fromJson(result, GoodsBean.class);
                if (bean.getCode() == 2000) {
                    list.addAll(bean.getData());
                    adapter = new Goods_Recycle(list, GoodsActivity.this);
                    goodsRecycle.setAdapter(adapter);
                    goodsRecycle.setVisibility(View.VISIBLE);
                    isShow.setVisibility(View.GONE);
                    goodsPull.setCanLoadMore(true);
                } else if (bean.getCode() == -2000) {
                    goodsRecycle.setVisibility(View.GONE);
                    isShow.setVisibility(View.VISIBLE);
                    goodsPull.setCanLoadMore(false);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("tag", "eeor");
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
        params.addBodyParameter("lid", "2");
        params.addBodyParameter("page", page + "");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag", "好物" + result);
                Gson gson = new Gson();
                GoodsBean bean = gson.fromJson(result, GoodsBean.class);
                if (bean.getCode() == 2000) {
                    list.addAll(bean.getData());

                } else if (bean.getCode() == -2000) {
                    ToastUtils.showShort(GoodsActivity.this, "加载完成");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("tag", "eeor");
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
