package com.moible.bksx.xcb.retrofitmvpdemo.http.cookieinterceptor;

import android.util.Log;

import com.google.gson.Gson;
import com.moible.bksx.xcb.retrofitmvpdemo.config.Constant;
import com.moible.bksx.xcb.retrofitmvpdemo.util.SPUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 首次请求处理
 */
public class ReceivedCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (!originalResponse.headers("Set-Cookie").isEmpty()){
            List<String> cookies = new ArrayList<>();
            Log.i("TAG", "===+++intercept: " +originalResponse.headers("Set-Cookie"));
            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }
            String cookieStr = new Gson().toJson(cookies);
            SPUtil.putData(Constant.SP.SP,Constant.SP.SESSION_ID,cookieStr);
        }
        return originalResponse;
    }
}
