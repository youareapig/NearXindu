package com.mssd.shitangfragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.mssd.adapter.ShiTang_Fragment_recycle;
import com.mssd.data.ShitangNextBean;
import com.mssd.utils.SingleModleUrl;
import com.mssd.utils.ToastUtils;
import com.mssd.zl.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by DELL on 2017/9/12.
 */

public class Mian extends Fragment {
    @BindView(R.id.shitang_fragment_recycle)
    RecyclerView shitangFragmentRecycle;
    @BindView(R.id.shitang_fragment_pull)
    PullToRefreshLayout shitangFragmentPull;
    @BindView(R.id.isShow)
    TextView isShow;
    private Unbinder unbinder;
    private List<ShitangNextBean.DataBean> list=new ArrayList<>();
    private int page = 1;
    private ShiTang_Fragment_recycle adapter;
    private SharedPreferences sharedPreferences;
    private String userID;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shitangfragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        sharedPreferences = getActivity().getSharedPreferences("xindu", getActivity().MODE_PRIVATE);
        userID= sharedPreferences.getString("userid", "0");
        firstBean();
        shitangFragmentRecycle.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        shitangFragmentPull.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (list != null) {
                            list.clear();
                        }
                        page = 1;
                        firstBean();
                        shitangFragmentPull.finishRefresh();
                    }
                }, 2000);
            }

            @Override
            public void loadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        loadBean();

                        shitangFragmentPull.finishLoadMore();
                    }
                }, 2000);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public void firstBean() {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Eatlive/pageList");
        params.addBodyParameter("type", "1");
        params.addBodyParameter("cid", "3");
        params.addBodyParameter("uid",userID);
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag", "食堂数据" + result);
                Gson gson = new Gson();
                ShitangNextBean bean = gson.fromJson(result, ShitangNextBean.class);
                if (bean.getCode() == 2000) {
                    shitangFragmentRecycle.setVisibility(View.VISIBLE);
                    isShow.setVisibility(View.GONE);
                    list.addAll(bean.getData());
                    adapter = new ShiTang_Fragment_recycle(list, getActivity());
                    shitangFragmentRecycle.setAdapter(adapter);
                    shitangFragmentPull.setCanLoadMore(true);
                } else {
                    shitangFragmentRecycle.setVisibility(View.GONE);
                    isShow.setVisibility(View.VISIBLE);
                    shitangFragmentPull.setCanLoadMore(false);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("tag", "食堂数据失败");
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
    public void loadBean() {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Eatlive/pageList");
        params.addBodyParameter("type", "1");
        params.addBodyParameter("cid", "3");
        params.addBodyParameter("uid",userID);
        params.addBodyParameter("page", page + "");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag", "食堂数据" + result);
                Gson gson = new Gson();
                ShitangNextBean bean = gson.fromJson(result, ShitangNextBean.class);
                if (bean.getCode() == 2000) {
                    list.addAll(bean.getData());
                    adapter.notifyItemRangeChanged(0,bean.getData().size());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("tag", "食堂数据失败");
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
