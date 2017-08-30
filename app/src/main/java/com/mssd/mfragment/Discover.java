package com.mssd.mfragment;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mssd.adapter.Discover_Recycle1;
import com.mssd.adapter.Discover_Recycle2;
import com.mssd.adapter.Discover_Recycle3;
import com.mssd.adapter.Discover_Recycle4;
import com.mssd.data.FoodBean;
import com.mssd.utils.SpacesItemDecoration;
import com.mssd.zl.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by DELL on 2017/8/30.
 */

public class Discover extends Fragment {
    @BindView(R.id.discover_tx1)
    TextView discoverTx1;
    @BindView(R.id.discover_tx2)
    TextView discoverTx2;
    @BindView(R.id.discover_recycle1)
    RecyclerView discoverRecycle1;
    @BindView(R.id.discover_recycle2)
    RecyclerView discoverRecycle2;
    @BindView(R.id.discover_recycle3)
    RecyclerView discoverRecycle3;
    @BindView(R.id.discover_recycle4)
    RecyclerView discoverRecycle4;
    private Unbinder unbinder;
    private FoodBean foodBean1, foodBean2, foodBean3, foodBean4;
    private List<FoodBean> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.discover, container, false);
        unbinder = ButterKnife.bind(this, view);
        initbean();
        changeFont();
        getRecycler1();
        getRecycler2();
        getRecycler3();
        getRecycler4();
        return view;
    }

    private void initbean() {
        list = new ArrayList<>();
        foodBean1 = new FoodBean(R.mipmap.test, "蛋炒饭");
        foodBean2 = new FoodBean(R.mipmap.test, "黄焖鸡");
        foodBean3 = new FoodBean(R.mipmap.test, "翘脚牛肉");
        foodBean4 = new FoodBean(R.mipmap.test, "翘脚牛肉");
        list.add(foodBean1);
        list.add(foodBean2);
        list.add(foodBean3);
        list.add(foodBean4);
    }

    private void changeFont() {
        AssetManager assetManager = getActivity().getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        Typeface typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        discoverTx1.setTypeface(typeface1);
        discoverTx2.setTypeface(typeface);
    }

    private void getRecycler1() {
        discoverRecycle1.addItemDecoration(new SpacesItemDecoration(40));
        discoverRecycle1.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false));
        discoverRecycle1.setAdapter(new Discover_Recycle1(list, getActivity()));
    }
    private void getRecycler2() {
        discoverRecycle2.addItemDecoration(new SpacesItemDecoration(40));
        discoverRecycle2.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false));
        discoverRecycle2.setAdapter(new Discover_Recycle2(list, getActivity()));
    }
    private void getRecycler3() {
        discoverRecycle3.addItemDecoration(new SpacesItemDecoration(40));
        discoverRecycle3.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false));
        discoverRecycle3.setAdapter(new Discover_Recycle3(list, getActivity()));
    }
    private void getRecycler4() {
        discoverRecycle4.addItemDecoration(new SpacesItemDecoration(40));
        discoverRecycle4.setLayoutManager(new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false));
        discoverRecycle4.setAdapter(new Discover_Recycle4(list, getActivity()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
