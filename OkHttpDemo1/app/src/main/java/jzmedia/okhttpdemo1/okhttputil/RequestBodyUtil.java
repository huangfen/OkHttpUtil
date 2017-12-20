package jzmedia.okhttpdemo1.okhttputil;

import java.io.File;
import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * Created by fhuang on 2017/12/14.
 */

public class RequestBodyUtil {
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");
    public static final class SingleInstance{
        private static final RequestBodyUtil REQUEST_BODY_UTIL = new RequestBodyUtil();
    }
    public static RequestBodyUtil getInstance(){
        return SingleInstance.REQUEST_BODY_UTIL;
    }
    /**
     * Posting form parameters
     */
    public RequestBody setParameters(String pswd,String name ){
        RequestBody body = new FormBody.Builder()
                .add("password",pswd)
                .add("name",name)
                .build();
        return body;
    }
    /**
     * Posting a String
     */
    public RequestBody postString(){
        String postBody = ""
                + "Releases\n"
                + "--------\n"
                + "\n"
                + " * _1.0_ May 6, 2013\n"
                + " * _1.1_ June 15, 2013\n"
                + " * _1.2_ August 11, 2013\n";

        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody);
        return body;
    }
    /**
     * Post Streaming
     */
    public RequestBody postStream(){
        RequestBody requestBody = new RequestBody() {
            @Override public MediaType contentType() {
                return MEDIA_TYPE_MARKDOWN;
            }
            @Override public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8("Numbers\n");
                sink.writeUtf8("-------\n");
                for (int i = 2; i <= 997; i++) {
                    sink.writeUtf8(String.format(" * %s = %s\n", i, factor(i)));
                }
            }
            private String factor(int n) {
                for (int i = 2; i < n; i++) {
                    int x = n / i;
                    if (x * i == n) return factor(x) + " × " + i;
                }
                return Integer.toString(n);
            }
        };
        return requestBody;
    }
    /**
     * Posting a File
     */
    public RequestBody postFile(){
        File file = new File("README.md");
        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN, file);
        return body;
    }
}
