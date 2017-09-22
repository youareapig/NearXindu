package com.mssd.zl;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mssd.adapter.BannerAdapter;
import com.mssd.adapter.BannerOnClick_Adapter;
import com.mssd.data.TalkHistoryBean;
import com.mssd.utils.SingleModleUrl;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.AutoLayoutActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NatureActivity extends AutoLayoutActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.nature_viewpager)
    ViewPager natureViewpager;
    @BindView(R.id.nature_viewpager_group)
    LinearLayout natureViewpagerGroup;
    @BindView(R.id.nature_title)
    TextView natureTitle;
    private Unbinder unbinder;
    private List<TalkHistoryBean.DataBean> list;
    private ImageView[] viewpagerTips, views;
    private TextView[] textTips;
    private TextView textView;
    private Typeface typeface, typeface1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nature);
        unbinder = ButterKnife.bind(this);
        changeFont();
        init();
    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        natureTitle.setTypeface(typeface1);
    }

    private void getViewpager() {
        viewpagerTips = new ImageView[list.size()];
        textTips = new TextView[list.size()];
        for (int i = 0; i < viewpagerTips.length; i++) {

            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(28, 28);
            layoutParams.topMargin = 40;
            imageView.setLayoutParams(layoutParams);
            viewpagerTips[i] = imageView;


            textView = new TextView(this);
            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(layoutParams1);
            textView.setTextColor(Color.parseColor("#ffffff"));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 52);
            textView.setTypeface(typeface1);
            textTips[i] = textView;
            textTips[i].setText(list.get(i).getHname());

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 80;
            params.rightMargin = 80;

            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(params);
            layout.setGravity(Gravity.CENTER_HORIZONTAL);
            layout.addView(textView);
            layout.addView(imageView);


            if (i == 0) {
                viewpagerTips[i].setBackgroundResource(R.drawable.vpchecked);
                textView.setAlpha(1);
            } else {
                viewpagerTips[i].setBackgroundResource(0);
                textView.setAlpha((float) 0.8);
            }

            natureViewpagerGroup.addView(layout);

        }
        views = new ImageView[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            views[i] = imageView;
            ImageLoader.getInstance().displayImage(list.get(i).getUrl(), imageView);
        }

        natureViewpager.setOnPageChangeListener(this);
        natureViewpager.setAdapter(new BannerOnClick_Adapter(views, list));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setImageBackground(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setImageBackground(int selectItems) {
        for (int i = 0; i < viewpagerTips.length; i++) {

            if (i == selectItems) {
                viewpagerTips[i].setBackgroundResource(R.drawable.vpchecked);
                textView.setAlpha(1);
            } else {
                viewpagerTips[i].setBackgroundResource(0);
                textView.setAlpha((float) 0.8);
            }
        }
    }

    private void init() {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "History/culture");
        params.addBodyParameter("cid", "4");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag", "自然资源" + result);
                Gson gson = new Gson();
                TalkHistoryBean bean = gson.fromJson(result, TalkHistoryBean.class);
                list = bean.getData();
                if (bean.getCode() == 1000) {
                    getViewpager();
                } else {
                    Toast.makeText(NatureActivity.this, "请求错误", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("tag", "自然资源访问出错");
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
}
