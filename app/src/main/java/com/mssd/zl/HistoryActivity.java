package com.mssd.zl;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mssd.adapter.History_Recycle1;
import com.mssd.adapter.History_Recycle3;
import com.mssd.data.HistoryIndexBean;
import com.mssd.html.WebActivity;
import com.mssd.myview.CustomProgressDialog;
import com.mssd.utils.ObservableScrollView;
import com.mssd.utils.SingleModleUrl;
import com.mssd.utils.SpacesItemDecoration;
import com.mssd.utils.ToastUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

public class HistoryActivity extends AutoLayoutActivity implements ObservableScrollView.ScrollViewListener {
    @BindView(R.id.historyText1)
    TextView historyText1;
    @BindView(R.id.historyRecycleImg1)
    RelativeLayout historyRecycleImg1;
    @BindView(R.id.historyRecycle)
    RecyclerView historyRecycle;
    @BindView(R.id.historyRecycleEndImg1)
    TextView historyRecycleEndImg1;
    @BindView(R.id.history_text2)
    TextView historyText2;
    @BindView(R.id.history_text3)
    TextView historyText3;
    @BindView(R.id.historyRecycleImg2)
    RelativeLayout historyRecycleImg2;
    @BindView(R.id.historyRecycle1)
    RecyclerView historyRecycle1;
    @BindView(R.id.historyRecycleEndImg2)
    TextView historyRecycleEndImg2;
    @BindView(R.id.history_text4)
    TextView historyText4;
    @BindView(R.id.historyRecycleImg3)
    RelativeLayout historyRecycleImg3;
    @BindView(R.id.historyRecycle2)
    RecyclerView historyRecycle2;
    @BindView(R.id.historyRecycleEndImg3)
    TextView historyRecycleEndImg3;
    @BindView(R.id.history_Topimg)
    ImageView historyTopimg;
    @BindView(R.id.history_scroll)
    ObservableScrollView historyScroll;
    @BindView(R.id.history_Title)
    RelativeLayout historyTitle;
    @BindView(R.id.history_Toptext)
    TextView historyToptext;
    @BindView(R.id.history_Topimg1)
    ImageView historyTopimg1;
    @BindView(R.id.history_Toptext1)
    TextView historyToptext1;
    @BindView(R.id.history_Topimg2)
    ImageView historyTopimg2;
    @BindView(R.id.history_Toptext2)
    TextView historyToptext2;
    @BindView(R.id.history_back)
    RelativeLayout historyBack;
    @BindView(R.id.history_talk)
    RelativeLayout historyTalk;
    @BindView(R.id.xcmap)
    RelativeLayout xcmap;
    private Unbinder unbinder;
    private int heigh = 300;
    private List<HistoryIndexBean.DataBean.T2Bean> list1;
    private List<HistoryIndexBean.DataBean.T4Bean> list2;
    private List<HistoryIndexBean.DataBean.T6Bean> list3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        unbinder = ButterKnife.bind(this);
        changeFont();
        changeTitle();
        reqestNet();
    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        Typeface typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        historyText1.setTypeface(typeface1);
        historyText2.setTypeface(typeface1);

        historyText3.setTypeface(typeface1);
        historyText4.setTypeface(typeface1);
        historyToptext.setTypeface(typeface1);
        historyToptext1.setTypeface(typeface1);
        historyToptext2.setTypeface(typeface1);
        historyRecycleEndImg1.setTypeface(typeface);
        historyRecycleEndImg2.setTypeface(typeface);
        historyRecycleEndImg3.setTypeface(typeface);
    }


    private void getRecycle1() {
        historyRecycle.addItemDecoration(new SpacesItemDecoration(20));
        historyRecycle.setLayoutManager(new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false));
        historyRecycle.setAdapter(new History_Recycle1(list1, this));
    }

//    private void getRecycle2() {
//        historyRecycle1.addItemDecoration(new SpacesItemDecoration(20));
//        historyRecycle1.setLayoutManager(new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false));
//        historyRecycle1.setAdapter(new History_Recycle2(list2, this));
//    }

    private void getRecycle3() {
        historyRecycle2.addItemDecoration(new SpacesItemDecoration(20));
        historyRecycle2.setLayoutManager(new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false));
        historyRecycle2.setAdapter(new History_Recycle3(list3, this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void changeTitle() {
        ViewTreeObserver observer = historyTopimg.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                historyTopimg.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //heigh=historyTopimg.getHeight();
                historyScroll.setScrollViewListener(HistoryActivity.this);
            }
        });
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= heigh) {
            float scale = (float) y / heigh;
            float alpha = (255 * scale);
            historyTitle.setVisibility(View.VISIBLE);
            historyTitle.setAlpha(alpha);
            historyTitle.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
        }
        if (y > heigh) {
            historyTitle.setBackgroundColor(Color.WHITE);
        }
    }

    @OnClick({R.id.historyRecycleImg1, R.id.historyRecycleEndImg1, R.id.historyRecycleImg2, R.id.historyRecycleEndImg2, R.id.historyRecycleImg3, R.id.historyRecycleEndImg3, R.id.history_back, R.id.history_talk,R.id.xcmap})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.historyRecycleImg1:
                Intent intent = new Intent(this, TalkHistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.historyRecycleEndImg1:
                Intent intent1 = new Intent(this, TalkHistoryActivity.class);
                startActivity(intent1);
                break;
            case R.id.historyRecycleImg2:
                Intent intent5 = new Intent(this, NatureActivity.class);
                startActivity(intent5);
                break;
            case R.id.historyRecycleEndImg2:
                Intent intent6 = new Intent(this, NatureActivity.class);
                startActivity(intent6);
                break;
            case R.id.historyRecycleImg3:
                Intent intent2 = new Intent(this, HOFActivity.class);
                startActivity(intent2);
                break;
            case R.id.historyRecycleEndImg3:
                Intent intent3 = new Intent(this, HOFActivity.class);
                startActivity(intent3);
                break;
            case R.id.history_back:
                finish();
                break;
            case R.id.history_talk:
                Intent intent4 = new Intent(HistoryActivity.this, WebActivity.class);
                intent4.putExtra("url", SingleModleUrl.singleModleUrl().getTestUrl() + "show/playvideo");
                startActivity(intent4);
                break;
            case R.id.xcmap:
                Intent intent7=new Intent(HistoryActivity.this,XCMapActivity.class);
                startActivity(intent7);
                break;
        }
    }

    private void reqestNet() {
        final CustomProgressDialog customProgressDialog = new CustomProgressDialog(this, R.drawable.frame, R.style.dialog);
        customProgressDialog.setCanceledOnTouchOutside(false);
        customProgressDialog.show();
        historyScroll.setVisibility(View.GONE);
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "History/index");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                historyScroll.setVisibility(View.VISIBLE);
                Gson gson = new Gson();
                HistoryIndexBean bean = gson.fromJson(result, HistoryIndexBean.class);
                if (bean.getCode() == 1000) {
                    historyToptext.setText(bean.getData().getT1().getTitle());
                    historyToptext1.setText(bean.getData().getT3().getTitle());
                    historyToptext2.setText(bean.getData().getT5().getTitle());
                    Glide.with(HistoryActivity.this).load(bean.getData().getT1().getUrl()).centerCrop().placeholder(R.mipmap.hui).error(R.mipmap.hui).into(historyTopimg);
                    Glide.with(HistoryActivity.this).load(bean.getData().getT3().getUrl()).centerCrop().placeholder(R.mipmap.hui).error(R.mipmap.hui).into(historyTopimg1);
                    Glide.with(HistoryActivity.this).load(bean.getData().getT5().getUrl()).centerCrop().placeholder(R.mipmap.hui).error(R.mipmap.hui).into(historyTopimg2);
                    list1 = bean.getData().getT2();
                    list2 = bean.getData().getT4();
                    list3 = bean.getData().getT6();
                    getRecycle1();
                    //getRecycle2();
                    getRecycle3();

                } else {
                    ToastUtils.showShort(HistoryActivity.this, R.string.nobean);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.showShort(HistoryActivity.this, R.string.erroe);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                customProgressDialog.cancel();
            }

            @Override
            public boolean onCache(String result) {

                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        JAnalyticsInterface.onPageStart(this, "历史主页");
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    @Override
    protected void onPause() {
        super.onPause();
        JAnalyticsInterface.onPageEnd(this, "历史主页");
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
