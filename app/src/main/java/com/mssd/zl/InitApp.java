package com.mssd.zl;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.zhy.autolayout.config.AutoLayoutConifg;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by DELL on 2017/8/28.
 */

public class InitApp extends Application {
    public ImageLoaderConfiguration config;
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        AutoLayoutConifg.getInstance().useDeviceSize();
        initImageLoader(getApplicationContext());
        x.Ext.init(this);
    }
    public void initImageLoader(Context context) {
        config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
                .diskCacheExtraOptions(480, 800, null)
                .threadPoolSize(3) // default
                .threadPriority(Thread.NORM_PRIORITY - 2) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(1000)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(context)) // default
                .imageDecoder(new BaseImageDecoder(true)) // default
                .defaultDisplayImageOptions(new DisplayImageOptions.Builder()
                        .cacheInMemory(true)//开启内存缓存
                        .cacheOnDisk(true)//开启磁盘缓存
                        .showImageOnLoading(R.mipmap.ic_launcher)//加载过程中显示的图片
                        .showImageOnFail(R.mipmap.ic_launcher)//加载失败显示的图片
                        .build()) // default
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }
}
