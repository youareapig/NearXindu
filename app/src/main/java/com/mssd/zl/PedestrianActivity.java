package com.mssd.zl;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.mssd.adapter.Goods_Recycle;
import com.mssd.adapter.Pedestrian_Recycle;
import com.mssd.utils.ListItemDecoration;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PedestrianActivity extends AutoLayoutActivity {
    @BindView(R.id.pedestrian_title)
    TextView pedestrianTitle;
    @BindView(R.id.pedestrian_recycle)
    RecyclerView pedestrianRecycle;
    private Unbinder unbinder;
    private List<Integer> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedestrian);
        unbinder = ButterKnife.bind(this);
        initbean();
        changeFont();
        getBean();
    }

    private void initbean() {
        list = new ArrayList<>();
        list.add(R.mipmap.test);
        list.add(R.mipmap.test);
        list.add(R.mipmap.test);
    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        pedestrianTitle.setTypeface(typeface);
    }

    private void getBean() {
        pedestrianRecycle.setAdapter(new Pedestrian_Recycle(list, this));
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        pedestrianRecycle.setLayoutManager(gridLayoutManager);
        pedestrianRecycle.addItemDecoration(new ListItemDecoration(90));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
