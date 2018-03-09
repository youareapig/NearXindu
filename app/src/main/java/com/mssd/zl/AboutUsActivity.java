package com.mssd.zl;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mssd.myview.CustomProgressDialog;
import com.mssd.utils.SingleModleUrl;
import com.mssd.utils.ToastUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jiguang.analytics.android.api.BrowseEvent;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

public class AboutUsActivity extends AutoLayoutActivity {
    @BindView(R.id.aboutus_title)
    TextView aboutusTitle;
    @BindView(R.id.aboutus_content)
    WebView aboutusContent;
    @BindView(R.id.about_back)
    RelativeLayout aboutBack;
    private Unbinder unbinder;
    private Typeface typeface, typeface1;
    private WebSettings webSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        unbinder = ButterKnife.bind(this);
        changeFont();
        BrowseEvent browseEvent = new BrowseEvent("test_browseID",//设置浏览内容id
                "深圳热点新闻",//设置浏览的内容的名称
                "news", //设置浏览的内容类型
                30);
//        测试git
        browseEvent.addKeyValue("key_browse_event_extra3", "")
                .addKeyValue("key_browse_event_extra4", "浏览11111");
        JAnalyticsInterface.onEvent(this, browseEvent);
        getBean();
    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        aboutusTitle.setTypeface(typeface1);
    }

    private void getBean() {
        final CustomProgressDialog customProgressDialog = new CustomProgressDialog(this, R.drawable.frame, R.style.dialog);
        customProgressDialog.setCanceledOnTouchOutside(false);
        customProgressDialog.show();
        aboutusContent.setVisibility(View.GONE);
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Index/about");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    aboutusContent.setVisibility(View.VISIBLE);
                    JSONObject jsonObject = new JSONObject(result);
                    webSettings=aboutusContent.getSettings();
                    webSettings.setJavaScriptEnabled(true);
                    webSettings.setBlockNetworkImage(false);
                    webSettings.setLoadWithOverviewMode(true);
                    webSettings.setUseWideViewPort(true);
                    webSettings.setTextZoom(250);
                    aboutusContent.loadDataWithBaseURL(null, getNewContent(jsonObject.getString("data")), "text/html", "utf-8", null);
                    aboutusContent.setWebViewClient(new WebViewClient());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.showShort(AboutUsActivity.this, R.string.erroe);
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
    private String getNewContent(String htmltext) {
        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }
        return doc.toString();
    }
    @Override
    protected void onResume() {
        super.onResume();
        JAnalyticsInterface.onPageStart(this,"关于我们");
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }


    @Override
    protected void onPause() {
        super.onPause();
        JAnalyticsInterface.onPageEnd(this,"关于我们");
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.about_back)
    public void onViewClicked() {
        finish();
    }
}
