package com.moible.bksx.xcb.retrofitmvpdemo.http.cookieinterceptor;

import android.util.Log;

import com.google.gson.Gson;
import com.moible.bksx.xcb.retrofitmvpdemo.config.Constant;
import com.moible.bksx.xcb.retrofitmvpdemo.util.SPUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookiesInterceptor  implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();
        String cookieStr = SPUtil.getData(Constant.SP.SP,Constant.SP.SESSION_ID,String.class,null);
        Log.i("TAG", "===intercept: " + cookieStr);
        List<String> cookies = new Gson().fromJson(cookieStr,List.class);
        if (cookies != null){
            for (String cookie: cookies){
                requestBuilder.addHeader("Cookie",cookie);
                Log.i("TAG", "===intercept: " + cookie);
            }
        }
        return chain.proceed(requestBuilder.build());
    }
}
