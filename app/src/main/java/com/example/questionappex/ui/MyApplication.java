package com.example.questionappex.ui;

import android.app.Application;
import android.content.Context;

/**
 * Created by qiaojiange on 2017/2/18.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
