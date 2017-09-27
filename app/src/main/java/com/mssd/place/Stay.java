package com.mssd.place;

import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.mssd.adapter.WantStayAdapter;
import com.mssd.data.TBean;
import com.mssd.data.WantEatBean;
import com.mssd.utils.ListItemDecoration;
import com.mssd.utils.SingleModleUrl;
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

public class Stay extends Fragment {
    @BindView(R.id.shitang_fragment_recycle)
    RecyclerView shitangFragmentRecycle;
    @BindView(R.id.isShow)
    TextView isShow;
    private Unbinder unbinder;
    private List<WantEatBean.DataBean> list;
    private SharedPreferences sharedPreferences;
    private String userID;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.placestayfragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initbean();
        getNetBean();
        return view;

    }

    private void initbean() {
        sharedPreferences = getActivity().getSharedPreferences("xindu", getActivity().MODE_PRIVATE);
        userID = sharedPreferences.getString("userid", "0");

    }


    private void getNetBean() {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Member/want");
        params.addBodyParameter("type", "2");
        params.addBodyParameter("uid", "1");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag", "想去住" + result);
                Gson gson=new Gson();
                WantEatBean bean=gson.fromJson(result,WantEatBean.class);
                if (bean.getCode()==3000){
                    list=bean.getData();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
                        @Override
                        public boolean canScrollVertically() {
                            return true;
                        }
                    };
                    shitangFragmentRecycle.addItemDecoration(new ListItemDecoration(120));
                    shitangFragmentRecycle.setLayoutManager(linearLayoutManager);
                    shitangFragmentRecycle.setAdapter(new WantStayAdapter(list, getActivity()));
                    shitangFragmentRecycle.setVisibility(View.VISIBLE);
                    isShow.setVisibility(View.GONE);
                }else {
                    shitangFragmentRecycle.setVisibility(View.GONE);
                    isShow.setVisibility(View.VISIBLE);
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
