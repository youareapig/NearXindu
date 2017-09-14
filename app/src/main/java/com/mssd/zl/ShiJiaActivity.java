package com.mssd.zl;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.mssd.adapter.ShiJia_Recycle;
import com.mssd.adapter.Stay_Recycle;
import com.mssd.data.FoodBean;
import com.mssd.utils.ListItemDecoration;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ShiJiaActivity extends AutoLayoutActivity {
    @BindView(R.id.shijia_title)
    TextView shijiaTitle;
    @BindView(R.id.shijia_recycle)
    RecyclerView shijiaRecycle;
    private Unbinder unbinder;
    private Typeface typeface, typeface1;
    private List<FoodBean> list;
    private FoodBean foodBean1, foodBean2, foodBean3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shi_jia);
        unbinder = ButterKnife.bind(this);
        initbean();
        changeFont();
        getBean();
    }

    private void initbean() {
        list = new ArrayList<>();
        foodBean1 = new FoodBean(R.mipmap.test, "我有一头小毛驴");
        foodBean2 = new FoodBean(R.mipmap.test, "我有一头小毛驴");
        foodBean3 = new FoodBean(R.mipmap.test, "我有一头小毛驴");
        list.add(foodBean1);
        list.add(foodBean2);
        list.add(foodBean3);
    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        shijiaTitle.setTypeface(typeface1);
    }

    private void getBean() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        shijiaRecycle.addItemDecoration(new ListItemDecoration(40));
        shijiaRecycle.setLayoutManager(linearLayoutManager);
        shijiaRecycle.setAdapter(new ShiJia_Recycle(list, this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
