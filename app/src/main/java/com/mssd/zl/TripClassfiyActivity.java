package com.mssd.zl;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.mssd.adapter.Trip_Recycle3;
import com.mssd.data.TestBean;
import com.mssd.utils.ListItemDecoration;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TripClassfiyActivity extends AutoLayoutActivity {
    @BindView(R.id.tripclassfiyText1)
    TextView tripclassfiyText1;
    @BindView(R.id.tripclassfiyrecycle)
    RecyclerView tripclassfiyrecycle;
    private Unbinder unbinder;
    private List<TestBean> list;
    private TestBean testBean1, testBean2, testBean3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_classfiy);
        unbinder = ButterKnife.bind(this);
        initbean();
        changeFont();
        getBean();
    }

    private void initbean() {
        list = new ArrayList<>();
        testBean1 = new TestBean(R.mipmap.test, "黄河啊", "你好多水啊");
        testBean2 = new TestBean(R.mipmap.test, "黄河啊", "你好多水啊");
        testBean3 = new TestBean(R.mipmap.test, "黄河啊", "你好多水啊");
        list.add(testBean1);
        list.add(testBean2);
        list.add(testBean3);
    }
    private void changeFont() {
        AssetManager assetManager = getAssets();
        Typeface typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        tripclassfiyText1.setTypeface(typeface1);
    }
    private void getBean() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        tripclassfiyrecycle.addItemDecoration(new ListItemDecoration(20));
        tripclassfiyrecycle.setLayoutManager(linearLayoutManager);
        tripclassfiyrecycle.setAdapter(new Trip_Recycle3(list, this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
