package com.mssd.zl;

import android.app.Application;
import com.zhy.autolayout.config.AutoLayoutConifg;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by DELL on 2017/8/28.
 */

public class InitApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        AutoLayoutConifg.getInstance().useDeviceSize();

    }
}
