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
import com.mssd.adapter.ShiJia_Recycle;
import com.mssd.data.ShijiaBean;
import com.mssd.data.TBean;
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

public class ShiJiaActivity extends AutoLayoutActivity {
    @BindView(R.id.shijia_title)
    TextView shijiaTitle;
    @BindView(R.id.shijia_recycle)
    RecyclerView shijiaRecycle;
    @BindView(R.id.shijia_pull)
    PullToRefreshLayout shijiaPull;
    @BindView(R.id.isShow)
    TextView isShow;
    private Unbinder unbinder;
    private Typeface typeface, typeface1;
    private List<ShijiaBean.DataBean> list = new ArrayList<>();
    private int page = 1;
    private ShiJia_Recycle adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shi_jia);
        unbinder = ButterKnife.bind(this);
        changeFont();
        getNetBean();
        shijiaPull.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        if (list != null) {
                            list.clear();
                        }
                        getNetBean();
                        shijiaPull.finishRefresh();
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
                        shijiaPull.finishLoadMore();
                    }
                }, 2000);

            }
        });
        addline();
    }


    private void changeFont() {
        AssetManager assetManager = getAssets();
        typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        shijiaTitle.setTypeface(typeface1);
    }

    private void addline() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        shijiaRecycle.addItemDecoration(new ListItemDecoration(40));
        shijiaRecycle.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void getNetBean() {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Eatlive/gastronome");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {

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
                Log.e("tag", "食家" + result);
                Gson gson = new Gson();
                ShijiaBean bean = gson.fromJson(result, ShijiaBean.class);
                if (bean.getCode() == 2000) {
                    shijiaRecycle.setVisibility(View.VISIBLE);
                    isShow.setVisibility(View.GONE);
                    list.addAll(bean.getData());
                    adapter = new ShiJia_Recycle(list, ShiJiaActivity.this);
                    shijiaRecycle.setAdapter(adapter);
                } else if (bean.getCode() == -2000) {
                    shijiaRecycle.setVisibility(View.GONE);
                    isShow.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
    }

    private void loadmorer() {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Eatlive/gastronome");
        params.addBodyParameter("page", page + "");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {

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
                Log.e("tag", "食家" + result);
                Gson gson = new Gson();
                ShijiaBean bean = gson.fromJson(result, ShijiaBean.class);
                if (bean.getCode() == 2000) {
                    list.addAll(bean.getData());
                } else if (bean.getCode() == -2000) {
                    ToastUtils.showShort(ShiJiaActivity.this, "加载完成");
                }
                return false;
            }
        });
    }
}
