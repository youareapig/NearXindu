package com.mssd.zl;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mssd.adapter.Gallery_Recycle;
import com.mssd.data.GalleryBean;
import com.mssd.utils.MyScrollView;
import com.mssd.utils.SingleModleUrl;
import com.mssd.utils.SpacesItemDecoration3;
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

public class GalleryActivity extends AutoLayoutActivity {
    @BindView(R.id.gallery_title)
    TextView galleryTitle;
    @BindView(R.id.gallery_recycle)
    RecyclerView galleryRecycle;
    @BindView(R.id.gallery_text1)
    TextView galleryText1;
    @BindView(R.id.gallery_text2)
    TextView galleryText2;
    @BindView(R.id.galler_scroll)
    MyScrollView gallerScroll;
    @BindView(R.id.gallery_back)
    RelativeLayout galleryBack;
    private Unbinder unbinder;
    private List<GalleryBean.DataBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        unbinder = ButterKnife.bind(this);
        changeFont();
        getNetBean();
    }


    private void changeFont() {
        AssetManager assetManager = getAssets();
        Typeface typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        galleryTitle.setTypeface(typeface1);
        galleryText1.setTypeface(typeface1);
        galleryText2.setTypeface(typeface1);
    }

    private void getBean() {
        galleryRecycle.setAdapter(new Gallery_Recycle(list, this));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        galleryRecycle.setLayoutManager(gridLayoutManager);
        galleryRecycle.addItemDecoration(new SpacesItemDecoration3(30));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void getNetBean() {
        gallerScroll.setVisibility(View.GONE);
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Eatlive/gallery");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                gallerScroll.setVisibility(View.VISIBLE);
                Gson gson = new Gson();
                GalleryBean bean = gson.fromJson(result, GalleryBean.class);
                if (bean.getCode() == 2000) {
                    list = bean.getData();
                    getBean();
                }else {
                    ToastUtils.showShort(GalleryActivity.this,R.string.nobean);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.showShort(GalleryActivity.this,R.string.erroe);
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

    @OnClick(R.id.gallery_back)
    public void onViewClicked() {
        finish();
    }
}
