package com.img.demo.base;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.img.demo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
public class BaseApplication extends Application {
    private static BaseApplication instance;
    public static int screen_height, screen_width;

    public static synchronized BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        initImageLoader();


        //获取屏幕高度和宽度
        getHeightAndWidth();

    }

    /**
     * 获取屏幕的高度和宽度
     */
    private void getHeightAndWidth() {
        // 要获取屏幕的宽和高等参数，首先需要声明一个DisplayMetrics对象，屏幕的宽高等属性存放在这个对象中
        DisplayMetrics DM = new DisplayMetrics();
        // 获取窗口管理器,获取当前的窗口,调用getDefaultDisplay()后，其将关于屏幕的一些信息写进DM对象中,最后通过getMetrics(DM)获取
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(DM);
        screen_width = manager.getDefaultDisplay().getWidth();
        screen_height = manager.getDefaultDisplay().getHeight();
    }

    /**
     * 初始化图片加载库
     */
    private void initImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder() //
                .showImageForEmptyUri(R.drawable.default_ti_img) //
                .showImageOnFail(R.drawable.default_ti_img) //
                .cacheInMemory(true) //
                .cacheOnDisk(true) //
                .build();//
        ImageLoaderConfiguration config = new ImageLoaderConfiguration//
                .Builder(getApplicationContext())//
                .defaultDisplayImageOptions(defaultOptions)//
                .discCacheSize(50 * 1024 * 1024)//
                .discCacheFileCount(100)// 缓存一百张图片
                .writeDebugLogs()//
                .build();//
        ImageLoader.getInstance().init(config);
    }

}
