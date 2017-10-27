package com.mssd.zl;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mssd.update.UpdateAppHttpUtil;
import com.mssd.utils.CacheDataManager;
import com.mssd.utils.SingleModleUrl;
import com.mssd.utils.ToastUtils;
import com.vector.update_app.UpdateAppManager;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

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
    @BindView(R.id.setting_cache_text)
    TextView settingCacheText;
    @BindView(R.id.setting_cache_size)
    TextView settingCacheSize;
    @BindView(R.id.setting_cache)
    RelativeLayout settingCache;
    @BindView(R.id.setting_back)
    RelativeLayout settingBack;
    @BindView(R.id.update_name)
    TextView updateName;
    private Unbinder unbinder;
    private Typeface typeface1, typeface;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String isUpdate;
    private int locationVersion = 0;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(SettingActivity.this, "清理完成", Toast.LENGTH_SHORT).show();
                    try {
                        settingCacheSize.setText(CacheDataManager.getTotalCacheSize(SettingActivity.this));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        unbinder = ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("xindu", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        changeFont();
        getUpdate();
    }

    private void changeFont() {
        InitApp application = (InitApp) getApplication();
        locationVersion = application.location;
        Log.e("tag","当前版本号"+locationVersion);
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
        settingCacheText.setTypeface(typeface);
        updateName.setTypeface(typeface);
        settingCacheSize.setTypeface(typeface);
        try {
            settingCacheSize.setText(CacheDataManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.setting_aboutus, R.id.setting_ideal, R.id.setting_video, R.id.setting_update, R.id.setting_loginout, R.id.setting_cache, R.id.setting_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting_aboutus:
                Intent intent = new Intent(SettingActivity.this, AboutUsActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_ideal:
                Intent intent2 = new Intent(SettingActivity.this, ChatWithMeActivity.class);
                startActivity(intent2);
                break;
            case R.id.setting_video:
                break;
            case R.id.setting_update:
                if (isUpdate.equals("Yes")) {
                    new UpdateAppManager
                            .Builder()
                            //当前Activity
                            .setActivity(SettingActivity.this)
                            //更新地址
                            .setUpdateUrl(SingleModleUrl.singleModleUrl().getTestUrl() + "Index/updateInfo")
                            //实现httpManager接口的对象
                            .setHttpManager(new UpdateAppHttpUtil())
                            .build()
                            .update();
                } else {
                    final AlertDialog dialog = new AlertDialog.Builder(SettingActivity.this).create();
                    LayoutInflater inflater = getLayoutInflater();
                    View v = inflater.inflate(R.layout.bestversion, null);
                    dialog.setView(v);
                    dialog.show();
                    Window window = dialog.getWindow();
                    WindowManager.LayoutParams layoutParams = window.getAttributes();
                    layoutParams.alpha = 0.6f;
                    layoutParams.width = 650;
                    window.setAttributes(layoutParams);
                    dialog.setCanceledOnTouchOutside(true);
                }
                break;
            case R.id.setting_loginout:
                TextView tv_off, tv_sure, tv_text;
                final AlertDialog dialog = new AlertDialog.Builder(this).create();
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.sureloginout, null);
                dialog.setView(v);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                tv_off = (TextView) v.findViewById(R.id.off);
                tv_sure = (TextView) v.findViewById(R.id.sure);
                tv_text = (TextView) v.findViewById(R.id.sure_text);
                tv_off.setTypeface(typeface);
                tv_sure.setTypeface(typeface);
                tv_text.setTypeface(typeface);
                tv_off.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
                tv_sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editor.putString("userid", "0");
                        editor.putBoolean("islogin", false);
                        editor.commit();
                        Intent intent1 = new Intent(SettingActivity.this, MainActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent1);
                        dialog.cancel();
                    }
                });

                break;
            case R.id.setting_cache:
                new Thread(new clearCache()).start();
                break;
            case R.id.setting_back:
                finish();
                break;
        }
    }

    class clearCache implements Runnable {
        @Override
        public void run() {
            try {
                CacheDataManager.clearAllCache(SettingActivity.this);
                Thread.sleep(2000);
                if (CacheDataManager.getTotalCacheSize(SettingActivity.this).startsWith("0")) {
                    handler.sendEmptyMessage(0);
                }
            } catch (Exception e) {
                return;
            }
        }
    }


    private void getUpdate(){
        RequestParams params=new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl()+"Index/initial");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag",result);
                try {
                    JSONObject json=new JSONObject(result);
                    int nowVersion=Integer.valueOf(json.getString("version"));
                    if (locationVersion<nowVersion){
                        isUpdate = "Yes";
                        settingUpdateText1.setVisibility(View.VISIBLE);
                    }else {
                        settingUpdateText1.setVisibility(View.GONE);
                        isUpdate = "No";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


}
