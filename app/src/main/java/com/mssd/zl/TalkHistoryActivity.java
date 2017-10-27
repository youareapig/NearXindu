package com.mssd.zl;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mssd.adapter.BannerAdapter;
import com.mssd.adapter.TalkBannerAdapter;
import com.mssd.data.TalkHistoryBean;
import com.mssd.myview.CustomProgressDialog;
import com.mssd.utils.SingleModleUrl;
import com.mssd.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.AutoLayoutActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TalkHistoryActivity extends AutoLayoutActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.talk_viewpager)
    ViewPager talkViewpager;
    @BindView(R.id.talk_viewpager_group)
    LinearLayout talkViewpagerGroup;
    @BindView(R.id.talk_viewpage_name)
    TextView talkViewpageName;
    @BindView(R.id.talk_title)
    TextView talkTitle;
    @BindView(R.id.talk_back)
    RelativeLayout talkBack;
    @BindView(R.id.talkhistort_main)
    RelativeLayout talkhistortMain;
    private Unbinder unbinder;
    private ImageView[] viewpagerTips, views;
    private Typeface typeface1, typeface;
    private List<TalkHistoryBean.DataBean> list;
    private int item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_history);
        unbinder = ButterKnife.bind(this);
        changeFont();
        Intent intent = getIntent();
        item = intent.getIntExtra("item", 0);
        init();
    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        talkTitle.setTypeface(typeface1);
    }


    private void getViewpager() {
        viewpagerTips = new ImageView[list.size()];
        for (int i = 0; i < viewpagerTips.length; i++) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(18, 18);
            layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 10;
            imageView.setLayoutParams(layoutParams);
            viewpagerTips[i] = imageView;
            talkViewpageName.setText(list.get(0).getHintro());
            talkViewpageName.setTypeface(typeface1);
            if (i == 0) {
                viewpagerTips[i].setBackgroundResource(R.drawable.vpchecked);
            } else {
                viewpagerTips[i].setBackgroundResource(R.drawable.vpunchecked);

            }
            talkViewpagerGroup.addView(imageView);
        }
        views = new ImageView[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            views[i] = imageView;
            Glide.with(this).load(list.get(i).getUrl()).centerCrop().placeholder(R.mipmap.hui).error(R.mipmap.hui).into(imageView);
        }

        talkViewpager.setOnPageChangeListener(this);
        talkViewpager.setAdapter(new TalkBannerAdapter(views,list));
        talkViewpager.setCurrentItem(item);//默认显示第几个
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void setImageBackground(int selectItems) {
        for (int i = 0; i < viewpagerTips.length; i++) {

            if (i == selectItems) {
                viewpagerTips[i].setBackgroundResource(R.drawable.vpchecked);
                talkViewpageName.setText(list.get(i).getHintro());
                talkViewpageName.setTypeface(typeface1);
            } else {
                viewpagerTips[i].setBackgroundResource(R.drawable.vpunchecked);
            }
        }
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

    private void init() {
        final CustomProgressDialog customProgressDialog = new CustomProgressDialog(this, R.drawable.frame, R.style.dialog);
        customProgressDialog.setCanceledOnTouchOutside(false);
        customProgressDialog.show();
        talkhistortMain.setVisibility(View.GONE);
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "History/culture");
        params.addBodyParameter("cid", "2");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                talkhistortMain.setVisibility(View.VISIBLE);
                Gson gson = new Gson();
                TalkHistoryBean bean = gson.fromJson(result, TalkHistoryBean.class);
                list = bean.getData();
                if (bean.getCode() == 1000) {
                    getViewpager();

                } else {
                    ToastUtils.showShort(TalkHistoryActivity.this, R.string.nobean);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.showShort(TalkHistoryActivity.this, R.string.erroe);
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

    @OnClick(R.id.talk_back)
    public void onViewClicked() {
        finish();
    }
}
