package com.mssd.html;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mssd.utils.NewWebView;
import com.mssd.utils.ToastUtils;
import com.mssd.zl.R;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class WebActivity extends AutoLayoutActivity {
    //    @BindView(R.id.layout)
//    RelativeLayout layout;
    @BindView(R.id.talk_back)
    RelativeLayout talkBack;
    @BindView(R.id.talk_title)
    TextView talkTitle;
    @BindView(R.id.web_share)
    ImageView webShare;
    @BindView(R.id.webview)
    NewWebView webview;
    @BindView(R.id.titleview)
    RelativeLayout titleview;
    @BindView(R.id.line)
    TextView line;
    @BindView(R.id.backicon)
    ImageView backicon;
    //private AgentWeb agentWeb;
    private Unbinder unbinder;
    private String url, Title, imgUrl, shareContent;
    private StringBuffer sb;
    private WebSettings webSettings;
    private int heigh,num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        unbinder = ButterKnife.bind(this);
        changeFont();
        init();
    }

    private void init() {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        num=intent.getIntExtra("heigh",0);
        if (num==1){
            heigh=3000;
        }else {
            heigh=800;
        }
        Log.e("tag", "address" + url);
//        agentWeb = AgentWeb.with(this)//传入Activity or Fragment
//                .setAgentWebParent(layout, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams ,第一个参数和第二个参数应该对应。
//                .useDefaultIndicator()// 使用默认进度条
//                .defaultProgressBarColor() // 使用默认进度条颜色
//                .setReceivedTitleCallback(mCallback) //设置 Web 页面的 title 回调
//                .createAgentWeb()//
//                .ready()
//                .go(url);
//        webView = agentWeb.getWebCreator().get();
        //       webView.setVerticalScrollBarEnabled(false);
        webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//        webSettings.setAppCachePath("");
        webSettings.setSupportZoom(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setDisplayZoomControls(true);
        webview.loadUrl(url);
        webview.setOnScrollChangeListener(new NewWebView.OnScrollChangeListener() {
            @Override
            public void onPageEnd(int l, int t, int oldl, int oldt) {

            }

            @Override
            public void onPageTop(int l, int t, int oldl, int oldt) {

            }

            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                if (t > heigh) {
                    titleview.setBackgroundColor(Color.parseColor("#ffffff"));
                    line.setVisibility(View.VISIBLE);
                    backicon.setImageResource(R.mipmap.back_hei_icon);
                    webShare.setImageResource(R.mipmap.share2);
                } else {
                    titleview.setBackgroundColor(Color.parseColor("#00000000"));
                    line.setVisibility(View.GONE);
                    backicon.setImageResource(R.mipmap.back_bai_icon);
                    webShare.setImageResource(R.mipmap.share);
                }
            }
        });
        webview.addJavascriptInterface(new InJavaScriptLocalObj(), "java_obj");
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Title = view.getTitle();
                view.loadUrl("javascript:window.java_obj.showSource(" + "document.getElementsByTagName('img')[0].src);");
                // view.loadUrl("javascript:window.java_obj.showDescription(document.documentElement.outerHTML);void(0)");
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

    private void changeFont() {
        AssetManager assetManager = getAssets();
        Typeface typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        talkTitle.setTypeface(typeface1);
    }

//    private ChromeClientCallbackManager.ReceivedTitleCallback mCallback = new ChromeClientCallbackManager.ReceivedTitleCallback() {
//
//        @Override
//
//        public void onReceivedTitle(WebView view, String title) {
//            //talkTitle.setText(title);
//            Title = title;
//        }
//
//    };

    public final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void showSource(String html) {
            imgUrl = html;
        }

        @JavascriptInterface
        public void showDescription(String str) {
//            Pattern p = Pattern.compile("<p.*?>(.*?)</p>");
//            Matcher m = p.matcher(str);
//            sb = new StringBuffer();
//            while (m.find()) {
//                sb.append(m.group(1));
//            }
            shareContent = str;
            Log.e("tag", "分享内容" + str);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (agentWeb.handleKeyEvent(keyCode, event)) {
//            return true;
//        }
        if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //agentWeb.getWebLifeCycle().onPause();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //agentWeb.getWebLifeCycle().onResume();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        //agentWeb.getWebLifeCycle().onDestroy();
    }

    @OnClick({R.id.talk_back, R.id.web_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.talk_back:
                finish();
                break;
            case R.id.web_share:
                try {
                    showShare();
                } catch (Exception e) {
                    ToastUtils.showShort(this, "正在加载请稍后");
                }
                break;
        }
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
//关闭sso授权
        oks.disableSSOWhenAuthorize();

// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        if (Title == "") {
            Title = "详情";
        }
        oks.setTitle(Title);
// titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(url);
// text是分享文本，所有平台都需要这个字段
        if (shareContent == null) {
            shareContent = "有远山而往，有近水则涉。寻境此心安处，不用千里外，推门出便是。";
        }
        oks.setText(shareContent);
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        //oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("惬成都");
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);
        //设置网络图片链接
        oks.setImageUrl(imgUrl);
// 启动分享GUI
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.e("tag", "分享失败" + throwable.getMessage());
            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        oks.show(this);

    }

}
