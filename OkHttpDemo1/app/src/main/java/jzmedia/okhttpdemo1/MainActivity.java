package jzmedia.okhttpdemo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.google.gson.Gson;
import jzmedia.okhttpdemo1.model.AndroidBean;
import jzmedia.okhttpdemo1.okhttputil.OkHttpManagerImpl;
import jzmedia.okhttpdemo1.okhttputil.Constant;
public class MainActivity extends AppCompatActivity implements OkHttpManagerImpl.OnResultBack {
    private String TAG = "MainActivity";
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);

        OkHttpManagerImpl impl = new OkHttpManagerImpl(Constant.ANDROID_URL,this);
        //如果有请求参数，调用该方法
//        RequestBodyUtil.getInstance().setParameters("123456","zhangshan");
//        impl.postHttpUtil();
        impl.getHttpUtil();
    }
    @Override
    public void onResultBack(String json) {
        Gson gson = new Gson();
        AndroidBean list = gson.fromJson(json,AndroidBean.class);
        //进行UI处理
        tv.setText(list.toString());
    }
}
