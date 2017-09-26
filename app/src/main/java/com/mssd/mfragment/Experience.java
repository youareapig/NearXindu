package com.mssd.mfragment;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import com.mssd.data.ExperienceBean;
import com.mssd.data.ExperienceNextBean;
import com.mssd.data.LocationBean;
import com.mssd.data.TestBean;
import com.mssd.utils.ListItemDecoration;
import com.mssd.utils.ObservableScrollView;
import com.mssd.utils.SingleModleUrl;
import com.mssd.utils.SpacesItemDecoration;
import com.mssd.zl.R;
import com.zhy.autolayout.AutoRelativeLayout;

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

public class Experience extends Fragment implements ObservableScrollView.ScrollViewListener {
    @BindView(R.id.experience_recycleTop)
    RecyclerView experienceRecycleTop;
    @BindView(R.id.experience_tx1)
    TextView experienceTx1;
    @BindView(R.id.experience_tx2)
    TextView experienceTx2;
    @BindView(R.id.experence_recycle)
    RecyclerView experenceRecycle;
    @BindView(R.id.experence_scroll)
    ObservableScrollView experenceScroll;
    @BindView(R.id.experence_titleName)
    TextView experenceTitleName;
    @BindView(R.id.experence_title)
    AutoRelativeLayout experenceTitle;
    @BindView(R.id.experence_pull)
    PullToRefreshLayout experencePull;
    private Unbinder unbinder;
    private List<LocationBean> list = new ArrayList<>();
    private List<ExperienceNextBean.DataBean> tlist;
    private LocationBean locationBean1, locationBean2, locationBean3, locationBean4, locationBean5;
    private Experience_Recycle adapter;
    private int heigh = 100;
    private int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.experience, container, false);
        unbinder = ButterKnife.bind(this, view);
        changeFont();
        changeTitle();
        initbean();
        getNetListBean();

        experencePull.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (tlist!=null){
                            tlist.clear();
                        }
                        page=1;
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
                        getNetListBean();
                        adapter.notifyDataSetChanged();
                        experencePull.finishLoadMore();
                    }
                }, 2000);
            }
        });
        addline();
        return view;
    }

    private void initbean() {
        locationBean1 = new LocationBean("户外活动", R.mipmap.test);
        locationBean2 = new LocationBean("艺术探究", R.mipmap.test);
        locationBean3 = new LocationBean("匠心手作", R.mipmap.test);
        locationBean4 = new LocationBean("茶会雅事", R.mipmap.test);
        locationBean5 = new LocationBean("生活美学", R.mipmap.test);
        list.add(locationBean1);
        list.add(locationBean2);
        list.add(locationBean3);
        list.add(locationBean4);
        list.add(locationBean5);

        experienceRecycleTop.setAdapter(new Experience_Recycle_Top(list, getActivity()));

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
        experenceTitleName.setTypeface(typeface1);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void changeTitle() {
        ViewTreeObserver observer = experienceRecycleTop.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                experienceRecycleTop.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                experenceScroll.setScrollViewListener(Experience.this);
            }
        });
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= heigh) {
            float scale = (float) y / heigh;
            float alpha = (255 * scale);
            experenceTitle.setVisibility(View.VISIBLE);
            experenceTitle.setAlpha(alpha);
            experenceTitle.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
        }
    }


    private void getNetListBean() {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Eatlive/pageList");
        params.addBodyParameter("type", "4");
        params.addBodyParameter("page", page + "");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("tag", "体验错误数据");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                Log.e("tag", "体验数据" + result);
                tlist = new ArrayList<ExperienceNextBean.DataBean>();
                Gson gson = new Gson();
                ExperienceNextBean bean = gson.fromJson(result, ExperienceNextBean.class);
                if (bean.getCode() == 2000) {
                    tlist.addAll(bean.getData());
                    adapter = new Experience_Recycle(tlist, getActivity());
                    experenceRecycle.setAdapter(adapter);
                }
                return false;
            }
        });
    }
}
