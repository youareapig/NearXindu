package com.mssd.place;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mssd.adapter.Stay_Recycle;
import com.mssd.data.FoodBean;
import com.mssd.utils.ListItemDecoration;
import com.mssd.zl.R;

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
    private Unbinder unbinder;
    private List<FoodBean> list;
    private FoodBean foodBean1, foodBean2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.placestayfragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initbean();
        getRecycle();
        return view;

    }

    private void initbean() {
        list = new ArrayList<>();
        foodBean1 = new FoodBean(R.mipmap.test, "少林功夫好啊");
        foodBean2 = new FoodBean(R.mipmap.test, "少林功夫好啊");
        list.add(foodBean1);
        list.add(foodBean2);

    }

    private void getRecycle() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        shitangFragmentRecycle.addItemDecoration(new ListItemDecoration(120));
        shitangFragmentRecycle.setLayoutManager(linearLayoutManager);
        shitangFragmentRecycle.setAdapter(new Stay_Recycle(list, getActivity()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
