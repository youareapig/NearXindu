package com.mssd.zl;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SettingActivity extends AutoLayoutActivity {
    @BindView(R.id.setting_title)
    TextView settingTitle;
    @BindView(R.id.setting_aboutus_text)
    TextView settingAboutusText;
    @BindView(R.id.setting_ideal_text)
    TextView settingIdealText;
    @BindView(R.id.setting_video_text)
    TextView settingVideoText;
    @BindView(R.id.setting_update_text)
    TextView settingUpdateText;
    @BindView(R.id.setting_update_text1)
    TextView settingUpdateText1;
    @BindView(R.id.setting_update_go)
    ImageView settingUpdateGo;
    @BindView(R.id.setting_aboutus)
    RelativeLayout settingAboutus;
    @BindView(R.id.setting_ideal)
    RelativeLayout settingIdeal;
    @BindView(R.id.setting_video)
    RelativeLayout settingVideo;
    @BindView(R.id.setting_update)
    RelativeLayout settingUpdate;
    @BindView(R.id.setting_loginout)
    TextView settingLoginout;
    private Unbinder unbinder;
    private Typeface typeface1, typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        unbinder = ButterKnife.bind(this);
        changeFont();
    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        typeface1 = Typeface.createFromAsset(assetManager, "fonts/sxsl.ttf");
        settingTitle.setTypeface(typeface1);
        settingAboutusText.setTypeface(typeface);
        settingIdealText.setTypeface(typeface);
        settingVideoText.setTypeface(typeface);
        settingUpdateText.setTypeface(typeface);
        settingUpdateText1.setTypeface(typeface);
        settingLoginout.setTypeface(typeface);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.setting_aboutus, R.id.setting_ideal, R.id.setting_video, R.id.setting_update, R.id.setting_loginout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting_aboutus:
                Intent intent=new Intent(SettingActivity.this,AboutUsActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_ideal:
                break;
            case R.id.setting_video:
                break;
            case R.id.setting_update:
                break;
            case R.id.setting_loginout:
                break;
        }
    }
}
