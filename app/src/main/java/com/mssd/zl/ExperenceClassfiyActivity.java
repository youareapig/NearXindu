package com.mssd.zl;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.mssd.adapter.Experience_Recycle;
import com.mssd.data.TestBean;
import com.mssd.utils.ListItemDecoration;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ExperenceClassfiyActivity extends AutoLayoutActivity {
    @BindView(R.id.experenceclassfiy_titleName)
    TextView experenceclassfiyTitleName;
    @BindView(R.id.experenceclassfiy_recycle)
    RecyclerView experenceclassfiyRecycle;
    private Unbinder unbinder;
    private List<TestBean> list;
    private TestBean testBean1, testBean2, testBean3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experence_classfiy);
        unbinder = ButterKnife.bind(this);
        initbean();
        changeFont();
        getBean();
    }

    private void initbean() {
        list = new ArrayList<>();
        testBean1 = new TestBean(R.mipmap.test, "故人西辞黄鹤楼", "来来来清仓大处理.99¥/人");
        testBean2 = new TestBean(R.mipmap.test, "抄词白帝彩云间", "来来来清仓大处理.19¥/人");
        testBean3 = new TestBean(R.mipmap.test, "莫道你在选择人", "来来来清仓大处理.29¥/人");
        list.add(testBean1);
        list.add(testBean2);
        list.add(testBean3);
    }
    private void changeFont() {
        AssetManager assetManager =getAssets();
        Typeface typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        experenceclassfiyTitleName.setTypeface(typeface1);
    }
    private void getBean(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        experenceclassfiyRecycle.addItemDecoration(new ListItemDecoration(80));
        experenceclassfiyRecycle.setLayoutManager(linearLayoutManager);
        experenceclassfiyRecycle.setAdapter(new Experience_Recycle(list, this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
