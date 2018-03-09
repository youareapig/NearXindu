package com.mssd.zl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mssd.adapter.Card_Recycle;
import com.mssd.data.CardBean;
import com.mssd.html.WebActivity;
import com.mssd.myview.CustomProgressDialog;
import com.mssd.utils.MyScrollView;
import com.mssd.utils.SingleModleUrl;
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

public class CardActivity extends AutoLayoutActivity {
    @BindView(R.id.card_back)
    RelativeLayout cardBack;
    @BindView(R.id.card_title)
    TextView cardTitle;
    @BindView(R.id.card_recycler)
    RecyclerView cardRecycler;
    @BindView(R.id.card_scroll)
    MyScrollView cardScroll;
    @BindView(R.id.isShow)
    RelativeLayout isShow;
    @BindView(R.id.nocard)
    TextView nocard;
    @BindView(R.id.get)
    TextView get;
    private Unbinder unbinder;
    private Typeface typeface, typeface1;
    private SharedPreferences sharedPreferences;
    private String userID;
    private List<CardBean.DataBean> list;
    private Card_Recycle card_recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        unbinder = ButterKnife.bind(this);
    }

    private void changFont() {
        sharedPreferences = getSharedPreferences("xindu", MODE_PRIVATE);
        userID = sharedPreferences.getString("userid", "0");
        AssetManager assetManager = getAssets();
        typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        nocard.setTypeface(typeface);
        get.setTypeface(typeface);
        cardTitle.setTypeface(typeface1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        changFont();
        getYouhui();
    }

    private void getYouhui() {
        final CustomProgressDialog customProgressDialog = new CustomProgressDialog(CardActivity.this, R.drawable.frame, R.style.dialog);
        customProgressDialog.setCanceledOnTouchOutside(false);
        customProgressDialog.show();
        cardScroll.setVisibility(View.GONE);
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Member/mycoupon");
        params.addBodyParameter("uid", userID);
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                cardScroll.setVisibility(View.VISIBLE);
                Gson gson = new Gson();
                CardBean bean = gson.fromJson(result, CardBean.class);
                if (bean.getCode() == 3000) {
                    list = bean.getData();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CardActivity.this, LinearLayoutManager.VERTICAL, false) {
                        @Override
                        public boolean canScrollVertically() {
                            return false;
                        }
                    };
                    card_recycle = new Card_Recycle(list, CardActivity.this);
                    cardRecycler.setLayoutManager(linearLayoutManager);
                    cardRecycler.setAdapter(card_recycle);
                } else {
                    cardScroll.setVisibility(View.GONE);
                    isShow.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.showShort(CardActivity.this, R.string.erroe);
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
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.card_back, R.id.get})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.card_back:
                finish();
                break;
            case R.id.get:
                Intent intent8=new Intent(CardActivity.this, WebActivity.class);
                intent8.putExtra("url",SingleModleUrl.singleModleUrl().getTestUrl()+"Show/active/uid/"+userID);
                intent8.putExtra("heigh",1);
                startActivity(intent8);
                break;
        }
    }
}
