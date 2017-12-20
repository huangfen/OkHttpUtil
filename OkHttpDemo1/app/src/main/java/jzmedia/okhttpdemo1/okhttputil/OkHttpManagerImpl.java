package jzmedia.okhttpdemo1.okhttputil;
import java.io.IOException;
import jzmedia.okhttpdemo1.interfac.OnHttpResultListent;
import okhttp3.RequestBody;
/**
 * Created by fhuang on 2017/12/14.
 */

public class OkHttpManagerImpl {
    private String mUrl;
    private String TAG = "OkHttpManagerImpl";
    private OnResultBack mBack;
    public OkHttpManagerImpl(String url,OnResultBack back) {
        this.mUrl = url;
        this.mBack = back;
    }
    public void getHttpUtil(){
        OkHttpManager.getInstance().getUrl(mUrl, new OnHttpResultListent() {
            @Override
            public void onFailure(IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onSucess(String json) {
                mBack.onResultBack(json);
            }
        });
    }
    public void postHttpUtil(RequestBody body){
        OkHttpManager.getInstance().postUrl(mUrl,body, new OnHttpResultListent() {
            @Override
            public void onFailure(IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onSucess(String json) {
                mBack.onResultBack(json);
            }
        });
    }
    public interface OnResultBack{
        void onResultBack(String json);
    }
}
