package com.mssd.zl;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AboutUsActivity extends AutoLayoutActivity {
    @BindView(R.id.aboutus_title)
    TextView aboutusTitle;
    @BindView(R.id.aboutus_content)
    TextView aboutusContent;
    @BindView(R.id.about_back)
    RelativeLayout aboutBack;
    private Unbinder unbinder;
    private Typeface typeface, typeface1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        unbinder = ButterKnife.bind(this);
        changeFont();
    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        aboutusContent.setTypeface(typeface);
        aboutusTitle.setTypeface(typeface1);
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
