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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.mssd.adapter.TiyanAdapter;
import com.mssd.data.TestBean;
import com.mssd.data.TiyanBean;
import com.mssd.utils.ListItemDecoration;
import com.mssd.utils.SingleModleUrl;
import com.mssd.utils.ToastUtils;
import com.zhy.autolayout.AutoLayoutActivity;
import com.zhy.autolayout.AutoRelativeLayout;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ExperenceClassfiyActivity extends AutoLayoutActivity {
    @BindView(R.id.experenceclassfiy_titleName)
    TextView experenceclassfiyTitleName;
    @BindView(R.id.experenceclassfiy_recycle)
    RecyclerView experenceclassfiyRecycle;
    @BindView(R.id.experenceclassfiy_back)
    RelativeLayout experenceclassfiyBack;
    @BindView(R.id.experence_title)
    AutoRelativeLayout experenceTitle;
    @BindView(R.id.isShow)
    TextView isShow;
    @BindView(R.id.experenceclassfiy_pull)
    PullToRefreshLayout experenceclassfiyPull;
    private Unbinder unbinder;
    private List<TiyanBean.DataBean> list = new ArrayList<>();
    private String cid;
    private int page = 1;
    private TiyanAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experence_classfiy);
        unbinder = ButterKnife.bind(this);
        initbean();
        changeFont();
        getNetBean();
        experenceclassfiyPull.setRefreshListener(new BaseRefreshListener() {
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
                        experenceclassfiyPull.finishRefresh();
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
                        experenceclassfiyPull.finishLoadMore();
                    }
                }, 2000);
            }
        });
        addline();
    }

    private void initbean() {
        Intent intent = getIntent();
        experenceclassfiyTitleName.setText(intent.getStringExtra("name"));
        cid = intent.getStringExtra("cid");
    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        Typeface typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        experenceclassfiyTitleName.setTypeface(typeface1);
    }

    private void addline() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        experenceclassfiyRecycle.addItemDecoration(new ListItemDecoration(80));
        experenceclassfiyRecycle.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void getNetBean() {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Eatlive/pageList");
        params.addBodyParameter("type", "4");
        params.addBodyParameter("cid", cid);
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag", "户外" + result);
                Gson gson = new Gson();
                TiyanBean bean = gson.fromJson(result, TiyanBean.class);
                if (bean.getCode() == 2000) {
                    list.addAll(bean.getData());
                    adapter = new TiyanAdapter(list, ExperenceClassfiyActivity.this);
                    experenceclassfiyRecycle.setAdapter(adapter);
                    experenceclassfiyRecycle.setVisibility(View.VISIBLE);
                    isShow.setVisibility(View.GONE);
                    experenceclassfiyPull.setCanLoadMore(true);
                } else {
                    experenceclassfiyRecycle.setVisibility(View.GONE);
                    isShow.setVisibility(View.VISIBLE);
                    experenceclassfiyPull.setCanLoadMore(false);
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
        params.addBodyParameter("type", "4");
        params.addBodyParameter("cid", cid);
        params.addBodyParameter("page", page + "");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                TiyanBean bean = gson.fromJson(result, TiyanBean.class);
                if (bean.getCode() == 2000) {
                    list.addAll(bean.getData());
                } else {
                    ToastUtils.showShort(ExperenceClassfiyActivity.this, "加载完成");
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
