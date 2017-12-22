package com.mssd.zl;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.mssd.data.XCmapBean;
import com.mssd.myview.CustomProgressDialog;
import com.mssd.utils.SingleModleUrl;
import com.mssd.utils.ToastUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import uk.co.senab.photoview.PhotoView;

public class XCMapActivity extends AutoLayoutActivity {
    @BindView(R.id.map_img)
    PhotoView mapImg;
    @BindView(R.id.map_back)
    RelativeLayout mapBack;
    @BindView(R.id.map_title)
    TextView mapTitle;
    private Unbinder unbinder;
    private Typeface typeface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xcmap);
        unbinder = ButterKnife.bind(this);
        AssetManager assetManager = getAssets();
        typeface = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        getImage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.map_back)
    public void onViewClicked() {
        finish();
    }

    private void getImage() {
        final CustomProgressDialog customProgressDialog = new CustomProgressDialog(this, R.drawable.frame, R.style.dialog);
        customProgressDialog.setCanceledOnTouchOutside(false);
        customProgressDialog.show();
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "History/geography");
        params.addBodyParameter("cid", "3");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                XCmapBean bean=gson.fromJson(result,XCmapBean.class);
                if (bean.getCode()==1000){
                    mapTitle.setText(bean.getData().getTitle());
                    mapTitle.setTypeface(typeface);
                    Glide.with(XCMapActivity.this)
                            .load(bean.getData().getImgs().get(0))
                            .placeholder(R.mipmap.hui)
                            .error(R.mipmap.hui)
                            .into(mapImg);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.showShort(XCMapActivity.this, R.string.erroe);
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
}
