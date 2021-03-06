package com.mssd.place;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mssd.adapter.WantEatAdapter;
import com.mssd.data.WantEatBean;
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
 * Created by DELL on 2017/9/14.
 */

public class Eat extends Fragment {
    @BindView(R.id.want_eat_recycle)
    RecyclerView wantEatRecycle;
    @BindView(R.id.isShow)
    TextView isShow;
    private Unbinder unbinder;
    private List<WantEatBean.DataBean> list;
    private SharedPreferences sharedPreferences;
    private String userID;
    private WantEatAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wanteat, container, false);
        unbinder = ButterKnife.bind(this, view);
        initbean();

        return view;
    }

    private void initbean() {
        AssetManager assetManager = getActivity().getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        isShow.setTypeface(typeface);
        sharedPreferences = getActivity().getSharedPreferences("xindu", getActivity().MODE_PRIVATE);
        userID = sharedPreferences.getString("userid", "0");
    }

    @Override
    public void onStart() {
        super.onStart();
        getNetBean();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getNetBean() {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Member/want");
        params.addBodyParameter("type", "1");
        params.addBodyParameter("uid", userID);
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                WantEatBean bean = gson.fromJson(result, WantEatBean.class);
                if (bean.getCode() == 3000) {
                    list = bean.getData();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
                        @Override
                        public boolean canScrollVertically() {
                            return true;
                        }
                    };
                    wantEatRecycle.setLayoutManager(linearLayoutManager);
                    adapter = new WantEatAdapter(list, getActivity());
                    wantEatRecycle.setAdapter(adapter);
                    wantEatRecycle.setVisibility(View.VISIBLE);
                    isShow.setVisibility(View.GONE);
                    adapter.callBack(new WantEatAdapter.MyShow() {
                        @Override
                        public void mShow(boolean b) {
                            if (b == true) {
                                wantEatRecycle.setVisibility(View.GONE);
                                isShow.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                } else {
                    wantEatRecycle.setVisibility(View.GONE);
                    isShow.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.showShort(getActivity(),R.string.erroe);
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
