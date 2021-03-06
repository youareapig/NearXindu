package com.mssd.zl;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.mssd.html.WebActivity;
import com.mssd.mfragment.Discover;
import com.mssd.mfragment.Experience;
import com.mssd.mfragment.Exploration;
import com.mssd.mfragment.Mine;
import com.mssd.update.UpdateAppHttpUtil;
import com.mssd.utils.SingleModleUrl;
import com.mssd.utils.ToastUtils;
import com.vector.update_app.UpdateAppManager;
import com.zhy.autolayout.AutoLayoutActivity;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionGrant;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;

public class MainActivity extends AutoLayoutActivity {
    @BindView(R.id.main_Fragment)
    LinearLayout mainFragment;
    @BindView(R.id.main_exploration)
    RelativeLayout mainExploration;
    @BindView(R.id.main_discover)
    RelativeLayout mainDiscover;
    @BindView(R.id.main_experience)
    RelativeLayout mainExperience;
    @BindView(R.id.main_mine)
    RelativeLayout mainMine;
    @BindView(R.id.exploration_icon)
    ImageView explorationIcon;
    @BindView(R.id.exploration_name)
    TextView explorationName;
    @BindView(R.id.discover_icon)
    ImageView discoverIcon;
    @BindView(R.id.discover_name)
    TextView discoverName;
    @BindView(R.id.experience_icon)
    ImageView experienceIcon;
    @BindView(R.id.experience_name)
    TextView experienceName;
    @BindView(R.id.mine_icon)
    ImageView mineIcon;
    @BindView(R.id.mine_name)
    TextView mineName;
    private Unbinder unbinder;
    private static final String CURRENT_FRAGMENT = "STATE_FRAGMENT_SHOW";
    private Fragment fragment = new Fragment();
    private List<Fragment> list = new ArrayList<>();
    private int currentIndex = 0;
    private FragmentManager fragmentManager;
    public AMapLocationClient mLocationClient = null;
    public AMapLocationListener mLocationListener = new MyAMapLocationListener();
    public AMapLocationClientOption mLocationOption = null;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private long firstTime = 0;
    private int locationVersion = 0;
    private boolean b;
    private Typeface typeface;
    private int showTag;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("xindu", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        InitApp application = (InitApp) getApplication();
        locationVersion = application.location;
        b = sharedPreferences.getBoolean("islogin", false);
        userID = sharedPreferences.getString("userid", "0");
        getUpdate();
        youhuirukou();
        Intent intent = getIntent();
        showTag = intent.getIntExtra("showtag", 0);
        currentIndex = intent.getIntExtra("indextag", 0);
        if (currentIndex == 3) {
            explorationName.setTextColor(getResources().getColor(R.color.mainUnChecked));
            experienceName.setTextColor(getResources().getColor(R.color.mainUnChecked));
            discoverName.setTextColor(getResources().getColor(R.color.mainUnChecked));
            mineName.setTextColor(getResources().getColor(R.color.mainChecked));
            explorationIcon.setImageResource(R.mipmap.tansuo_icon);
            experienceIcon.setImageResource(R.mipmap.tiyan_icon);
            discoverIcon.setImageResource(R.mipmap.faxian_icon);
            mineIcon.setImageResource(R.mipmap.wode1_icon);
        } else if (currentIndex == 2) {
            explorationName.setTextColor(getResources().getColor(R.color.mainUnChecked));
            experienceName.setTextColor(getResources().getColor(R.color.mainChecked));
            discoverName.setTextColor(getResources().getColor(R.color.mainUnChecked));
            mineName.setTextColor(getResources().getColor(R.color.mainUnChecked));
            explorationIcon.setImageResource(R.mipmap.tansuo_icon);
            experienceIcon.setImageResource(R.mipmap.tiyan1_icon);
            discoverIcon.setImageResource(R.mipmap.faxian_icon);
            mineIcon.setImageResource(R.mipmap.wode_icon);

        } else if (currentIndex == 0) {
            explorationName.setTextColor(getResources().getColor(R.color.mainChecked));
            experienceName.setTextColor(getResources().getColor(R.color.mainUnChecked));
            discoverName.setTextColor(getResources().getColor(R.color.mainUnChecked));
            mineName.setTextColor(getResources().getColor(R.color.mainUnChecked));
            explorationIcon.setImageResource(R.mipmap.tansuo1_icon);
            experienceIcon.setImageResource(R.mipmap.tiyan_icon);
            discoverIcon.setImageResource(R.mipmap.faxian_icon);
            mineIcon.setImageResource(R.mipmap.wode_icon);
        }
        changeFont();
        MPermissions.requestPermissions(MainActivity.this, 55, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        mLocationClient = new AMapLocationClient(getApplicationContext());
        mLocationClient.setLocationListener(mLocationListener);
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(CURRENT_FRAGMENT, 0);
            list.removeAll(list);
            list.add(fragmentManager.findFragmentByTag(0 + ""));
            list.add(fragmentManager.findFragmentByTag(1 + ""));
            list.add(fragmentManager.findFragmentByTag(2 + ""));
            list.add(fragmentManager.findFragmentByTag(3 + ""));
            restoreFragment();
        } else {
            list.add(new Exploration());
            list.add(new Discover());
            list.add(new Experience());
            list.add(new Mine());

            showFragment();
        }


    }


    private void initLocation() {
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(false);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();


    }

    private class MyAMapLocationListener implements AMapLocationListener {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    Log.e("位置：", aMapLocation.getAddress());
                    editor.putString("latLng", aMapLocation.getLatitude() + "");
                    editor.putString("longLng", aMapLocation.getLongitude() + "");
                    editor.commit();
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    }

    private void changeFont() {
        AssetManager assetManager = getAssets();
        typeface = Typeface.createFromAsset(assetManager, "fonts/ltqh.ttf");
        experienceName.setTypeface(typeface);
        explorationName.setTypeface(typeface);
        discoverName.setTypeface(typeface);
        mineName.setTypeface(typeface);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENT_FRAGMENT, currentIndex);
        super.onSaveInstanceState(outState);
    }

    private void showFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!list.get(currentIndex).isAdded()) {
            transaction
                    .hide(fragment)
                    .add(R.id.main_Fragment, list.get(currentIndex), "" + currentIndex);
        } else {
            transaction
                    .hide(fragment)
                    .show(list.get(currentIndex));
        }
        fragment = list.get(currentIndex);
        transaction.commit();
    }

    private void restoreFragment() {
        FragmentTransaction mBeginTreansaction = fragmentManager.beginTransaction();
        for (int i = 0; i < list.size(); i++) {

            if (i == currentIndex) {
                mBeginTreansaction.show(list.get(i));
            } else {
                mBeginTreansaction.hide(list.get(i));
            }
        }
        mBeginTreansaction.commit();
        fragment = list.get(currentIndex);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mLocationClient.stopLocation();
    }

    @OnClick({R.id.main_exploration, R.id.main_discover, R.id.main_experience, R.id.main_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_exploration:
                currentIndex = 0;
                showFragment();
                explorationName.setTextColor(getResources().getColor(R.color.mainChecked));
                experienceName.setTextColor(getResources().getColor(R.color.mainUnChecked));
                discoverName.setTextColor(getResources().getColor(R.color.mainUnChecked));
                mineName.setTextColor(getResources().getColor(R.color.mainUnChecked));

                explorationIcon.setImageResource(R.mipmap.tansuo1_icon);
                experienceIcon.setImageResource(R.mipmap.tiyan_icon);
                discoverIcon.setImageResource(R.mipmap.faxian_icon);
                mineIcon.setImageResource(R.mipmap.wode_icon);
                break;
            case R.id.main_discover:
                currentIndex = 1;
                showFragment();
                explorationName.setTextColor(getResources().getColor(R.color.mainUnChecked));
                experienceName.setTextColor(getResources().getColor(R.color.mainUnChecked));
                discoverName.setTextColor(getResources().getColor(R.color.mainChecked));
                mineName.setTextColor(getResources().getColor(R.color.mainUnChecked));

                explorationIcon.setImageResource(R.mipmap.tansuo_icon);
                experienceIcon.setImageResource(R.mipmap.tiyan_icon);
                discoverIcon.setImageResource(R.mipmap.faxian1_icon);
                mineIcon.setImageResource(R.mipmap.wode_icon);
                break;
            case R.id.main_experience:
                currentIndex = 2;
                showFragment();
                explorationName.setTextColor(getResources().getColor(R.color.mainUnChecked));
                experienceName.setTextColor(getResources().getColor(R.color.mainChecked));
                discoverName.setTextColor(getResources().getColor(R.color.mainUnChecked));
                mineName.setTextColor(getResources().getColor(R.color.mainUnChecked));
                explorationIcon.setImageResource(R.mipmap.tansuo_icon);
                experienceIcon.setImageResource(R.mipmap.tiyan1_icon);
                discoverIcon.setImageResource(R.mipmap.faxian_icon);
                mineIcon.setImageResource(R.mipmap.wode_icon);
                break;
            case R.id.main_mine:
                if (b == false) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.putExtra("intentTag", 1);
                    startActivity(intent);
                } else {
                    currentIndex = 3;
                    showFragment();
                    explorationName.setTextColor(getResources().getColor(R.color.mainUnChecked));
                    experienceName.setTextColor(getResources().getColor(R.color.mainUnChecked));
                    discoverName.setTextColor(getResources().getColor(R.color.mainUnChecked));
                    mineName.setTextColor(getResources().getColor(R.color.mainChecked));
                    explorationIcon.setImageResource(R.mipmap.tansuo_icon);
                    experienceIcon.setImageResource(R.mipmap.tiyan_icon);
                    discoverIcon.setImageResource(R.mipmap.faxian_icon);
                    mineIcon.setImageResource(R.mipmap.wode1_icon);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionGrant(55)
    public void requestReadSuccess() {
        initLocation();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    Toast.makeText(MainActivity.this, "再按就要退出去啦!", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;
                    return true;
                } else {
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);

    }


    private void getUpdate() {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Index/initial");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    int nowVersion = Integer.valueOf(json.getString("version"));
                    if (locationVersion < nowVersion && showTag == 1) {
                        new UpdateAppManager
                                .Builder()
                                //当前Activity
                                .setActivity(MainActivity.this)
                                //更新地址
                                .setUpdateUrl(SingleModleUrl.singleModleUrl().getTestUrl() + "Index/updateInfo")
                                //实现httpManager接口的对象
                                .setHttpManager(new UpdateAppHttpUtil())
                                .build()
                                .update();
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

    private void showYouhui() {
        final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this, R.style.FullScreenDialog).create();
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_youhui, null);
        v.findViewById(R.id.dialog_off).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        TextView textView = (TextView) v.findViewById(R.id.get);
        textView.setTypeface(typeface);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b == false) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.putExtra("intentTag", 9);
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(MainActivity.this, WebActivity.class);
                    intent.putExtra("url",SingleModleUrl.singleModleUrl().getTestUrl()+"Show/active/uid/"+userID);
                    intent.putExtra("heigh",1);
                    startActivity(intent);
                    dialog.cancel();
                }

            }
        });
        dialog.setView(v);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(AutoRelativeLayout.LayoutParams.MATCH_PARENT, AutoRelativeLayout.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = 0.9f;
        window.setAttributes(layoutParams);
    }

    private void youhuirukou() {
        RequestParams params = new RequestParams(SingleModleUrl.singleModleUrl().getTestUrl() + "Index/ishow");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("data").equals("1") && showTag == 1) {
                        showYouhui();
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

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        JAnalyticsInterface.onPageStart(this, "首页");
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    @Override
    protected void onPause() {
        super.onPause();
        JAnalyticsInterface.onPageEnd(this, "首页");
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
