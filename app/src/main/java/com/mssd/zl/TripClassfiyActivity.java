package com.mssd.zl;

import android.content.SharedPreferences;
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
import com.mssd.adapter.XingAdapter;
import com.mssd.data.XingBean;
import com.mssd.myview.CustomProgressDialog;
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
import butterknife.OnClick;
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
    @BindView(R.id.tripclassfiy_back)
    RelativeLayout tripclassfiyBack;
    private Unbinder unbinder;
    private List<XingBean.DataBean> list = new ArrayList<>();
    private String cid, name;
    private XingAdapter adapter;
    private int page = 1;
    private SharedPreferences sharedPreferences;
    private String userID;

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
                        tripclassfiyPull.finishLoadMore();
                    }
                }, 2000);
            }
        });
        addline();
    }

    private void initbean() {
        sharedPreferences = getSharedPreferences("xindu", MODE_PRIVATE);
        userID = sharedPreferences.getString("userid", "0");
        name = sharedPreferences.getString("mName", "0");
        cid = sharedPreferences.getString("mID", "0");
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

    @Override
    protected void onRestart() {
        super.onRestart();
        userID = sharedPreferences.getString("userid", "0");
        if (list != null) {
            list.clear();
        }
        page = 1;
        getNetBean();
    }

    private void getNetBean() {
        final CustomProgressDialog customProgressDialog = new CustomProgressDialog(this, R.drawable.frame, R.style.dialog);
        customProgressDialog.setCanceledOnTouchOutside(false);
        customProgressDialog.show();
        tripclassfiyPull.setVisibility(View.GONE);
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Eatlive/pageList");
        params.addBodyParameter("type", "3");
        params.addBodyParameter("cid", cid);
        params.addBodyParameter("uid", userID);
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                tripclassfiyPull.setVisibility(View.VISIBLE);
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
                ToastUtils.showShort(TripClassfiyActivity.this, R.string.erroe);
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
        params.addBodyParameter("cid", cid);
        params.addBodyParameter("uid", userID);
        params.addBodyParameter("page", page + "");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                XingBean bean = gson.fromJson(result, XingBean.class);
                if (bean.getCode() == 2000) {
                    list.addAll(bean.getData());
                    adapter.notifyItemRangeChanged(0, bean.getData().size());
                } else {
                    ToastUtils.showShort(TripClassfiyActivity.this, R.string.end);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.showShort(TripClassfiyActivity.this, R.string.erroe);
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

    @OnClick(R.id.tripclassfiy_back)
    public void onViewClicked() {
        finish();
    }
}
