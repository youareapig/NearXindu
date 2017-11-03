package com.mssd.html;

import android.app.Instrumentation;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.just.library.AgentWeb;
import com.lany.library.ProgressWebView;
import com.mssd.data.HtmlBean;
import com.mssd.utils.SingleModleUrl;
import com.mssd.zl.LoginActivity;
import com.mssd.zl.R;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class WebsActivity extends AutoLayoutActivity {

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
    @BindView(R.id.webview)
    ProgressWebView webview;
    private Unbinder unbinder;
    private String userID, cID, type;
    private int ischeck;
    private SharedPreferences sharedPreferences;
    private boolean isLogin;
    private WebSettings webSettings;
    private String urlType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_1);
        unbinder = ButterKnife.bind(this);
        changeFont();
        init();
        getBean();

    }

    private void getBean() {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + urlType);
        params.addBodyParameter("id", cID);
        params.addBodyParameter("uid", userID);
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                HtmlBean bean = gson.fromJson(result, HtmlBean.class);
                ischeck = bean.getData().getIscheck();
                if (ischeck == 0) {
                    shoucang.setImageResource(R.mipmap.shoucang);
                } else {
                    shoucang.setImageResource(R.mipmap.shoucang1);
                }
                Log.e("tag","地址------>"+bean.getData().getLink());
                webview.loadUrl(bean.getData().getLink());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
    }

    private void init() {
        sharedPreferences = getSharedPreferences("xindu", MODE_PRIVATE);
        userID = sharedPreferences.getString("userid", "0");
        isLogin = sharedPreferences.getBoolean("islogin", false);
        cID = sharedPreferences.getString("mmCid", "0");
        type = sharedPreferences.getString("mmType", "0");
        if (type.equals("1") || type.equals("2")) {
            urlType = "Show/roomboard";
        }
        if (type.equals("4")||type.equals("3")) {
            urlType = "Show/expere";
        }
        webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
// 让JavaScript可以自动打开windows
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
// 设置缓存
        webSettings.setAppCacheEnabled(true);
// 设置缓存模式,一共有四种模式
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
// 设置缓存路径
//        webSettings.setAppCachePath("");
// 支持缩放(适配到当前屏幕)
        webSettings.setSupportZoom(true);
// 将图片调整到合适的大小
        webSettings.setUseWideViewPort(true);
// 支持内容重新布局,一共有四种方式
// 默认的是NARROW_COLUMNS
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
// 设置可以被显示的屏幕控制
        webSettings.setDisplayZoomControls(true);
// 设置默认字体大小
        //webSettings.setDefaultFontSize(12);
    }


    private void changeFont() {
        AssetManager assetManager = getAssets();
        Typeface typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        talkTitle.setTypeface(typeface1);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
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
                if (isLogin == true) {
                    addCollect();
                } else {
                    Intent intent = new Intent(WebsActivity.this, LoginActivity.class);
                    intent.putExtra("intentTag", 8);
                    startActivity(intent);
                }
                break;
        }
    }

    private void addCollect() {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Member/addllect");
        params.addBodyParameter("uid", userID);
        params.addBodyParameter("type", type);
        params.addBodyParameter("tid", cID);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    if (json.getString("code").equals("3004")) {
                        AlphaAnimation animation = new AlphaAnimation(0, 1);
                        animation.setDuration(500);
                        shoucang.startAnimation(animation);
                        shoucang.setImageResource(R.mipmap.shoucang1);
                    } else if (json.getString("code").equals("-3000")) {
                        offCollect();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("tag", "收藏error");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void offCollect() {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Member/offllect");
        params.addBodyParameter("uid", userID);
        params.addBodyParameter("tid", cID);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    if (json.getString("code").equals("3006")) {
                        AlphaAnimation animation = new AlphaAnimation(0, 1);
                        animation.setDuration(500);
                        shoucang.startAnimation(animation);
                        shoucang.setImageResource(R.mipmap.shoucang);
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("tag", "取消收藏error");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webview.canGoBack()) {
                webview.goBack();//返回上一页面
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

}
