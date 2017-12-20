package jzmedia.okhttpdemo1;

import android.app.Application;

import jzmedia.okhttpdemo1.okhttputil.OkHttpManager;

/**
 * okhttp3的初始化
 * Created by fhuang on 2017/12/12.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpManager.getInstance().initOkHttp();
    }
}
