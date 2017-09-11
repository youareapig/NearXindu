package com.mssd.zl;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mssd.adapter.Stay_Gallery;
import com.mssd.adapter.Stay_Recycle;
import com.mssd.data.FoodBean;
import com.mssd.utils.ListItemDecoration;
import com.mssd.utils.ObservableScrollView;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class StayActivity extends AutoLayoutActivity implements ObservableScrollView.ScrollViewListener{
    @BindView(R.id.stay_gallery)
    Gallery stayGallery;
    @BindView(R.id.stay_tag)
    RelativeLayout stayTag;
    @BindView(R.id.stay_tx1)
    TextView stayTx1;
    @BindView(R.id.stay_tx2)
    TextView stayTx2;
    @BindView(R.id.stay_recycle)
    RecyclerView stayRecycle;
    @BindView(R.id.stay_scroll)
    ObservableScrollView stayScroll;
    @BindView(R.id.stayText1)
    TextView stayText1;
    @BindView(R.id.stay_Title)
    RelativeLayout stayTitle;
    private Unbinder unbinder;
    private List<String> list;
    private Stay_Gallery gallery_adapter;
    private List<FoodBean> list_1;
    private FoodBean foodBean1, foodBean2;
    private int heigh=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stay);
        unbinder = ButterKnife.bind(this);
        initbean();
        changeFont();
        changeTitle();
        getGallery();
        getRecycle();
    }

    private void initbean() {
        list = new ArrayList<>();
        list.add("酒店");
        list.add("客栈");
        list.add("民宿");
        list.add("青旅");
        list.add("露营");
        list_1 = new ArrayList<>();
        foodBean1 = new FoodBean(R.mipmap.test, "少林功夫好啊");
        foodBean2 = new FoodBean(R.mipmap.test, "少林功夫好啊");
        list_1.add(foodBean1);
        list_1.add(foodBean2);

    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        Typeface typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        stayTx1.setTypeface(typeface1);
        stayTx2.setTypeface(typeface);
        stayText1.setTypeface(typeface1);
    }

    private void getGallery() {
        gallery_adapter = new Stay_Gallery(list, this);
        stayGallery.setAdapter(gallery_adapter);
        stayGallery.setSpacing(160);
        stayGallery.setSelection(5 * 200);
        stayGallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gallery_adapter.setSelectItem(position % list.size());
                gallery_adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getRecycle() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        stayRecycle.addItemDecoration(new ListItemDecoration(120));
        stayRecycle.setLayoutManager(linearLayoutManager);
        stayRecycle.setAdapter(new Stay_Recycle(list_1, this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
    private void changeTitle() {
        ViewTreeObserver observer = stayTag.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                stayTag.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                stayScroll.setScrollViewListener(StayActivity.this);
            }
        });
    }
    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= heigh) {
            float scale = (float) y / heigh;
            float alpha = (255 * scale);
            stayTitle.setVisibility(View.VISIBLE);
            stayTitle.setAlpha(alpha);
            stayTitle.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
        }
    }
}
