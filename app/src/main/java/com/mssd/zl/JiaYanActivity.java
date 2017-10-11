package com.mssd.zl;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mssd.adapter.BannerAdapter;
import com.mssd.data.JiaYanBean;
import com.mssd.utils.SingleModleUrl;
import com.mssd.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.AutoLayoutActivity;
import com.zhy.autolayout.AutoLinearLayout;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class JiaYanActivity extends AutoLayoutActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.jiayan_title)
    TextView jiayanTitle;
    @BindView(R.id.jiayan_viewpager)
    ViewPager jiayanViewpager;
    @BindView(R.id.jiayan_name)
    TextView jiayanName;
    @BindView(R.id.jiayan_viewpager_group)
    LinearLayout jiayanViewpagerGroup;
    @BindView(R.id.jiayan_go)
    ImageView jiayanGo;
    @BindView(R.id.jiayan_scroll)
    ScrollView jiayanScroll;
    @BindView(R.id.jiayan_back)
    RelativeLayout jiayanBack;
    private Unbinder unbinder;
    private ImageView[] viewpagerTips, views;
    private Typeface typeface, typeface1;
    private List<JiaYanBean.DataBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jia_yan);
        unbinder = ButterKnife.bind(this);
        initbean();
        changeFont();
        getNetBean();
    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        jiayanTitle.setTypeface(typeface1);
    }

    private void initbean() {
        jiayanScroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    private void getViewpager() {
        viewpagerTips = new ImageView[list.size()];
        for (int i = 0; i < viewpagerTips.length; i++) {
            ImageView imageView = new ImageView(this);
            AutoLinearLayout.LayoutParams layoutParams = new AutoLinearLayout.LayoutParams(28, 28);
            layoutParams.leftMargin = 40;
            layoutParams.rightMargin = 40;
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            viewpagerTips[i] = imageView;
            jiayanName.setText(list.get(0).getFname());
            jiayanName.setTypeface(typeface1);
            if (i == 0) {
                viewpagerTips[i].setBackgroundResource(R.mipmap.jiayan_big);
                AutoLinearLayout.LayoutParams layoutParams1 = new AutoLinearLayout.LayoutParams(53, 86);
                layoutParams1.rightMargin = 40;
                layoutParams1.leftMargin = 40;
                imageView.setLayoutParams(layoutParams1);
            } else {
                viewpagerTips[i].setBackgroundResource(R.mipmap.jiayan_small);
                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(35, 56);
                layoutParams2.rightMargin = 40;
                layoutParams2.leftMargin = 40;
                imageView.setLayoutParams(layoutParams2);

            }
            jiayanViewpagerGroup.addView(imageView);
        }

        views = new ImageView[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

            views[i] = imageView;
            ImageLoader.getInstance().displayImage(list.get(i).getUrl(), imageView);

        }
        jiayanViewpager.setOnPageChangeListener(this);
        jiayanViewpager.setAdapter(new BannerAdapter(views));
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
                AutoLinearLayout.LayoutParams layoutParams3 = new AutoLinearLayout.LayoutParams(53, 86);
                layoutParams3.rightMargin = 40;
                layoutParams3.leftMargin = 40;
                viewpagerTips[i].setLayoutParams(layoutParams3);
                viewpagerTips[i].setBackgroundResource(R.mipmap.jiayan_big);
                jiayanName.setText(list.get(i).getFname());
                jiayanName.setTypeface(typeface1);
            } else {
                AutoLinearLayout.LayoutParams layoutParams4 = new AutoLinearLayout.LayoutParams(35, 56);
                layoutParams4.rightMargin = 40;
                layoutParams4.leftMargin = 40;
                viewpagerTips[i].setLayoutParams(layoutParams4);
                viewpagerTips[i].setBackgroundResource(R.mipmap.jiayan_small);
            }
        }
    }

    @OnClick({R.id.jiayan_go, R.id.jiayan_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.jiayan_go:
                jiayanScroll.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return false;
                    }
                });
                int[] coo = getScreenSize(this);
                jiayanScroll.smoothScrollTo(0, coo[1]);
                break;
            case R.id.jiayan_back:
                finish();
                break;
        }

    }

    //TODO 获取当前屏幕高度和宽度
    public static int[] getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return new int[]{outMetrics.widthPixels, outMetrics.heightPixels};
    }

    private void getNetBean() {
        jiayanScroll.setVisibility(View.GONE);
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Eatlive/feast");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                jiayanScroll.setVisibility(View.VISIBLE);
                Gson gson = new Gson();
                JiaYanBean bean = gson.fromJson(result, JiaYanBean.class);
                if (bean.getCode() == 2000) {
                    list = bean.getData();
                    getViewpager();
                }else {
                    ToastUtils.showShort(JiaYanActivity.this, R.string.nobean);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.showShort(JiaYanActivity.this, R.string.erroe);
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
