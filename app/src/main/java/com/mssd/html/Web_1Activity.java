package com.mssd.html;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;
import com.mssd.zl.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Web_1Activity extends AutoLayoutActivity {
    @BindView(R.id.layout)
    RelativeLayout layout;
    @BindView(R.id.talk_back)
    RelativeLayout talkBack;
    @BindView(R.id.talk_title)
    TextView talkTitle;
    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.shoucang)
    ImageView shoucang;
    private AgentWeb agentWeb;
    private Unbinder unbinder;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_1);
        unbinder = ButterKnife.bind(this);
        changeFont();
        init();
    }

    private void init() {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        Log.e("tag", "地址---->" + url);
        agentWeb = AgentWeb.with(this)//传入Activity or Fragment
                .setAgentWebParent(layout, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams ,第一个参数和第二个参数应该对应。
                .useDefaultIndicator()// 使用默认进度条
                .defaultProgressBarColor() // 使用默认进度条颜色
                .setReceivedTitleCallback(mCallback) //设置 Web 页面的 title 回调
                .createAgentWeb()//
                .ready()
                .go(url);
    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        Typeface typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        talkTitle.setTypeface(typeface1);
    }

    private ChromeClientCallbackManager.ReceivedTitleCallback mCallback = new ChromeClientCallbackManager.ReceivedTitleCallback() {

        @Override

        public void onReceivedTitle(WebView view, String title) {
            talkTitle.setText(title);
        }

    };

    @Override
    protected void onPause() {
        super.onPause();
        agentWeb.getWebLifeCycle().onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        agentWeb.getWebLifeCycle().onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        agentWeb.getWebLifeCycle().onDestroy();
    }

    @OnClick(R.id.talk_back)
    public void onViewClicked() {
        finish();
    }

    @OnClick({R.id.share, R.id.shoucang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.share:
                break;
            case R.id.shoucang:
                break;
        }
    }
}
