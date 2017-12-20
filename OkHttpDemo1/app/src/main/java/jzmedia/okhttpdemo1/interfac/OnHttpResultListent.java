package jzmedia.okhttpdemo1.interfac;

import java.io.IOException;

/**
 * Created by pc on 2017/12/13.
 */

public interface OnHttpResultListent {
    void onFailure(IOException e);
    void onSucess(String json);
}
