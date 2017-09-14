package com.mssd.mfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.mssd.adapter.ShiTang_Fragment_recycle;
import com.mssd.data.TestBean;
import com.mssd.zl.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by DELL on 2017/9/12.
 */

public class ShiTang extends Fragment {
    @BindView(R.id.shitang_fragment_recycle)
    RecyclerView shitangFragmentRecycle;
    private Unbinder unbinder;
    private List<TestBean> list;
    private TestBean testBean1, testBean2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shitangfragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initbean();
        getBean();
        return view;
    }


    private void initbean() {
        list = new ArrayList<>();
        testBean1 = new TestBean(R.mipmap.test, "一个神奇的地方", "洪雅县");
        testBean2 = new TestBean(R.mipmap.test, "一个很神奇的地方", "九寨沟");
        list.add(testBean1);
        list.add(testBean2);
    }

    private void getBean() {
        shitangFragmentRecycle.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        shitangFragmentRecycle.setAdapter(new ShiTang_Fragment_recycle(list, getActivity()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
