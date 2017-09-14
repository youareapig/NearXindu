package com.mssd.zl;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.mssd.adapter.Gallery_Recycle;
import com.mssd.adapter.Goods_Recycle;
import com.mssd.data.FoodBean;
import com.mssd.utils.SpacesItemDecoration3;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class GoodsActivity extends AutoLayoutActivity {
    @BindView(R.id.goods_title)
    TextView goodsTitle;
    @BindView(R.id.goods_recycle)
    RecyclerView goodsRecycle;
    private Unbinder unbinder;
    private List<FoodBean> list;
    private FoodBean foodBean1, foodBean2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        unbinder = ButterKnife.bind(this);
        initbean();
        changeFont();
        getBean();
    }

    private void initbean() {
        list = new ArrayList<>();
        foodBean1 = new FoodBean(R.mipmap.test, "新繁 .棕编");
        foodBean2 = new FoodBean(R.mipmap.test, "铜器 .手作");
        list.add(foodBean1);
        list.add(foodBean2);
    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        goodsTitle.setTypeface(typeface);
    }

    private void getBean() {
        goodsRecycle.setAdapter(new Goods_Recycle(list, this));
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        goodsRecycle.setLayoutManager(gridLayoutManager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
