package com.moible.bksx.xcb.retrofitmvpdemo.http;

import android.content.Context;

import com.moible.bksx.xcb.retrofitmvpdemo.util.LogUtils;
import com.moible.bksx.xcb.retrofitmvpdemo.util.SystemUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static android.support.constraint.Constraints.TAG;

public class NetCheckInterceptor implements Interceptor {
    private Context mContext = null;
    public NetCheckInterceptor(Context context){
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!SystemUtil.isNetworkConnected(mContext)) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            LogUtils.i(TAG, "===intercept: " + request.url());
        }
        LogUtils.i(TAG, "===intercept: " + request.url());
        Response response = chain.proceed(request);
        LogUtils.i("===TAG",response.headers().toString());
        if (SystemUtil.isNetworkConnected(mContext)) {
            int maxAge = 0;
            // 有网络时, 不缓存, 最大保存时长为0
            response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Pragma")
                    .build();
        } else {
            // 无网络时，设置超时为4周
            int maxStale = 60 * 60 * 24 * 28;
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build();
        }
        return response;
    }
}
