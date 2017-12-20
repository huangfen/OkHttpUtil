package jzmedia.okhttpdemo1.okhttputil;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import jzmedia.okhttpdemo1.interfac.OnHttpResultListent;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * okhttp manager
 * Created by fhuang on 2017/12/12.
 */

public class OkHttpManager {
    private String TAG = "OkHttpManager";
    private OkHttpClient mOkHttpClient;
    private Request mRequest;
    private OnHttpResultListent mListen;
    private final static int SUCESS_MSG = 1;
    private final static int FAILED_MSG = 2;
    private final static class SingleInstance{
        private final static OkHttpManager MANAGER = new OkHttpManager();
    }
    public static OkHttpManager getInstance(){
        return SingleInstance.MANAGER;
    }
    public void initOkHttp(){
        //在这里进行okhttp的初始化配置
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new NetWorkInterceptor())
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3,TimeUnit.SECONDS)
                .writeTimeout(5,TimeUnit.SECONDS)
                .build();
    }

    /**
     * post
     * @param url
     * @param listen
     * @param formBody
     */
    public void postUrl(String url, RequestBody formBody,final OnHttpResultListent listen){
        this.mListen = listen;
        mRequest = new Request.Builder()
                .url(url)
                .post(formBody)
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build();
        Call call = mOkHttpClient.newCall(mRequest);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = new Message();
                msg.obj = e;
                msg.what = FAILED_MSG;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Log.d(TAG, "onResponse: "+response.body().string());
                Log.d(TAG, "onResponse: "+Thread.currentThread().getName());
                Message msg = new Message();
                msg.obj = response.body().string();
                msg.what = SUCESS_MSG;
                mHandler.sendMessage(msg);
            }
        });
    }

    /**
     * get
     * @param url
     * @param listen
     */
    public void getUrl(String url, final OnHttpResultListent listen){
        this.mListen = listen;
        mRequest = new Request.Builder()
                .url(url)
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build();
        Call call = mOkHttpClient.newCall(mRequest);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = new Message();
                msg.obj = e;
                msg.what = FAILED_MSG;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Log.d(TAG, "onResponse: "+response.body().string());
                Log.d(TAG, "onResponse: "+Thread.currentThread().getName());
                Message msg = new Message();
                msg.obj = response.body().string();
                msg.what = SUCESS_MSG;
                mHandler.sendMessage(msg);
            }
        });
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SUCESS_MSG:
                    mListen.onSucess((String) msg.obj);
                    break;
                case FAILED_MSG:
                    mListen.onFailure((IOException) msg.obj);
                    break;
            }
        }
    };
}
