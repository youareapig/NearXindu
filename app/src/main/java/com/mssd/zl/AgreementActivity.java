package com.mssd.zl;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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

public class AgreementActivity extends AutoLayoutActivity {
    private WebView webView;
    private WebSettings webSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        webView= (WebView) findViewById(R.id.agreement_webview);
        init();
    }
    private void init(){

        RequestParams params=new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl()+"Member/userAree");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    if (jsonObject.getString("code").equals("3000")){
                        webSettings=webView.getSettings();
                        webSettings.setJavaScriptEnabled(true);
                        webSettings.setBlockNetworkImage(false);
                        webSettings.setLoadWithOverviewMode(true);
                        webSettings.setUseWideViewPort(true);
                        webSettings.setTextZoom(250);
                        webView.loadDataWithBaseURL(null, getNewContent(jsonObject.getString("data")), "text/html", "utf-8", null);
                        webView.setWebViewClient(new WebViewClient());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.showShort(AgreementActivity.this,R.string.erroe);
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
    private String getNewContent(String htmltext) {
        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }
        return doc.toString();
    }
}
