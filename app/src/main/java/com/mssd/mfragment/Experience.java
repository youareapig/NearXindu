package com.mssd.mfragment;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.mssd.adapter.Experience_Recycle;
import com.mssd.adapter.Experience_Recycle_Top;
import com.mssd.data.ExperienceNextBean;
import com.mssd.data.TiyanClassfiyBean;
import com.mssd.myview.CustomProgressDialog;
import com.mssd.utils.ListItemDecoration;
import com.mssd.utils.MyScrollView;
import com.mssd.utils.SingleModleUrl;
import com.mssd.utils.SpacesItemDecoration;
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
 * Created by DELL on 2017/8/30.
 */

public class Experience extends Fragment {
    @BindView(R.id.experience_recycleTop)
    RecyclerView experienceRecycleTop;
    @BindView(R.id.experience_tx1)
    TextView experienceTx1;
    @BindView(R.id.experience_tx2)
    TextView experienceTx2;
    @BindView(R.id.experence_recycle)
    RecyclerView experenceRecycle;
    @BindView(R.id.experence_scroll)
    MyScrollView experenceScroll;
    @BindView(R.id.experence_pull)
    PullToRefreshLayout experencePull;
    private Unbinder unbinder;
    private List<ExperienceNextBean.DataBean> tlist;
    private Experience_Recycle adapter;
    private int page = 1;
    private List<TiyanClassfiyBean> mlist;
    private TiyanClassfiyBean bean1, bean2, bean3, bean4, bean5;
    private SharedPreferences sharedPreferences;
    private String userID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.experience, container, false);
        unbinder = ButterKnife.bind(this, view);
        changeFont();
        initbean();
        getNetListBean();

        experencePull.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (tlist != null) {
                            tlist.clear();
                        }
                        page = 1;
                        getNetListBean();
                        experencePull.finishRefresh();
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
                        experencePull.finishLoadMore();
                    }
                }, 2000);
            }
        });
        addline();
        return view;
    }

    private void initbean() {
        sharedPreferences = getActivity().getSharedPreferences("xindu", getActivity().MODE_PRIVATE);
        userID = sharedPreferences.getString("userid", "0");
        mlist = new ArrayList<>();
        bean1 = new TiyanClassfiyBean("户外活动", R.mipmap.shi_icon, "19");
        bean2 = new TiyanClassfiyBean("艺术探究", R.mipmap.shi_icon, "20");
        bean3 = new TiyanClassfiyBean("匠心手作", R.mipmap.shi_icon, "21");
        bean4 = new TiyanClassfiyBean("茶会雅事", R.mipmap.shi_icon, "22");
        bean5 = new TiyanClassfiyBean("生活美学", R.mipmap.shi_icon, "23");
        mlist.add(bean1);
        mlist.add(bean2);
        mlist.add(bean3);
        mlist.add(bean4);
        mlist.add(bean5);

        experienceRecycleTop.setAdapter(new Experience_Recycle_Top(mlist, getActivity()));

    }

    private void addline() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        experenceRecycle.addItemDecoration(new ListItemDecoration(80));
        experenceRecycle.setLayoutManager(linearLayoutManager);
        experienceRecycleTop.addItemDecoration(new SpacesItemDecoration(20));
        experienceRecycleTop.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false));
    }

    private void changeFont() {
        AssetManager assetManager = getActivity().getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        Typeface typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        experienceTx1.setTypeface(typeface1);
        experienceTx2.setTypeface(typeface);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();
        userID = sharedPreferences.getString("userid", "0");
        if (tlist != null) {
            tlist.clear();
        }
        page = 1;
        getNetListBean();
    }

    private void getNetListBean() {
        final CustomProgressDialog customProgressDialog = new CustomProgressDialog(getActivity(), R.drawable.frame, R.style.dialog);
        customProgressDialog.setCanceledOnTouchOutside(false);
        customProgressDialog.show();
        experenceScroll.setVisibility(View.GONE);
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Eatlive/pageList");
        params.addBodyParameter("type", "4");
        params.addBodyParameter("uid", userID);
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                experenceScroll.setVisibility(View.VISIBLE);
                tlist = new ArrayList<ExperienceNextBean.DataBean>();
                Gson gson = new Gson();
                ExperienceNextBean bean = gson.fromJson(result, ExperienceNextBean.class);
                if (bean.getCode() == 2000) {
                    tlist.addAll(bean.getData());
                    adapter = new Experience_Recycle(tlist, getActivity());
                    experenceRecycle.setAdapter(adapter);
                    experencePull.setCanLoadMore(true);
                } else {
                    experencePull.setCanLoadMore(false);
                    ToastUtils.showShort(getActivity(), R.string.nobean);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.showShort(getActivity(), R.string.erroe);
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
        params.addBodyParameter("type", "4");
        params.addBodyParameter("uid", userID);
        params.addBodyParameter("page", page + "");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                tlist = new ArrayList<ExperienceNextBean.DataBean>();
                Gson gson = new Gson();
                ExperienceNextBean bean = gson.fromJson(result, ExperienceNextBean.class);
                if (bean.getCode() == 2000) {
                    tlist.addAll(bean.getData());
                    adapter.notifyItemRangeChanged(0, bean.getData().size());
                } else {
                    ToastUtils.showShort(getActivity(), R.string.end);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.showShort(getActivity(), R.string.erroe);
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
