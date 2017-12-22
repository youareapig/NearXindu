package com.mssd.html;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;
import com.mssd.data.HtmlBean;
import com.mssd.utils.SingleModleUrl;
import com.mssd.utils.ToastUtils;
import com.mssd.zl.LoginActivity;
import com.mssd.zl.R;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.sharesdk.onekeyshare.OnekeyShare;

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
    private Unbinder unbinder;
    private String userID, cID, type;
    private int ischeck;
    private SharedPreferences sharedPreferences;
    private boolean isLogin;
    private String urlType, mUrl, title1, imgUrl,shareContent;
    private StringBuffer sb;
    private AgentWeb agentWeb;
    private WebView webView;

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
                mUrl = bean.getData().getLink();
                webView.loadUrl(mUrl);
                webView.addJavascriptInterface(new InJavaScriptLocalObj(), "java_obj");

                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        view.loadUrl("javascript:window.java_obj.showSource("
                                + "document.getElementsByTagName('img')[0].src);");

                        //view.loadUrl("javascript:window.java_obj.showDescription(document.documentElement.outerHTML);void(0)");
                        view.loadUrl("javascript:window.java_obj.showDescription(document.getElementById('share').innerHTML);");
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        if (url.startsWith("http:") || url.startsWith("https:")) {
                            view.loadUrl(url);
                            return false;
                        } else {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                            return true;
                        }
                    }
                });
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

    private ChromeClientCallbackManager.ReceivedTitleCallback mCallback = new ChromeClientCallbackManager.ReceivedTitleCallback() {

        @Override

        public void onReceivedTitle(WebView view, String title) {
            //talkTitle.setText(title);
            title1 = title;
            Log.e("mm", "标题" + title);
        }

    };

    private void init() {
        agentWeb = AgentWeb.with(WebsActivity.this)
                .setAgentWebParent(layout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .defaultProgressBarColor()
                .setReceivedTitleCallback(mCallback) //设置 Web 页面的 title 回调
                .createAgentWeb()//
                .ready()
                .go(mUrl);
        webView = agentWeb.getWebCreator().get();
        webView.setVerticalScrollBarEnabled(false);
        sharedPreferences = getSharedPreferences("xindu", MODE_PRIVATE);
        userID = sharedPreferences.getString("userid", "0");
        isLogin = sharedPreferences.getBoolean("islogin", false);
        cID = sharedPreferences.getString("mmCid", "0");
        type = sharedPreferences.getString("mmType", "0");
        if (type.equals("1") || type.equals("2")) {
            urlType = "Show/roomboard";
        }
        if (type.equals("4") || type.equals("3")) {
            urlType = "Show/expere";
        }
//        webSettings = webview.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//// 让JavaScript可以自动打开windows
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//// 设置缓存
//        webSettings.setAppCacheEnabled(true);
//// 设置缓存模式,一共有四种模式
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//// 设置缓存路径
////        webSettings.setAppCachePath("");
//// 支持缩放(适配到当前屏幕)
//        webSettings.setSupportZoom(true);
//// 将图片调整到合适的大小
//        webSettings.setUseWideViewPort(true);
//// 支持内容重新布局,一共有四种方式
//// 默认的是NARROW_COLUMNS
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//// 设置可以被显示的屏幕控制
//        webSettings.setDisplayZoomControls(true);
//// 设置默认字体大小
//        //webSettings.setDefaultFontSize(12);
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
                try {
                    showShare();
                } catch (Exception e) {
                    ToastUtils.showShort(this,"正在加载请稍后");
                }
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

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
//关闭sso授权
        oks.disableSSOWhenAuthorize();

// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        if (title1 == null) {
            title1 = "详情";
        }
        oks.setTitle(title1);
// titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(mUrl);
// text是分享文本，所有平台都需要这个字段
        if (shareContent == null) {
            shareContent="有远山而往，有近水则涉。寻境此心安处，不用千里外，推门出便是。";
        }
        oks.setText(shareContent);
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(mUrl);
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        //oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("惬成都");
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(mUrl);
        //设置网络图片链接
        oks.setImageUrl(imgUrl);
        oks.show(this);

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

    public final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void showSource(String html) {
            imgUrl = html;
            Log.e("mm", "tupian" + html);
        }

        @JavascriptInterface
        public void showDescription(String str) {
//            Pattern p = Pattern.compile("<p.*?>(.*?)</p>");
//            Matcher m = p.matcher(str);
//            sb = new StringBuffer();
//            while (m.find()) {
//                sb.append(m.group(1));
//            }
            shareContent=str;
            Log.e("tag","分享内容"+str);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (agentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
