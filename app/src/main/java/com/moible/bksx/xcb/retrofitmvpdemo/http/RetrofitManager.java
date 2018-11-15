package com.moible.bksx.xcb.retrofitmvpdemo.http;


import com.moible.bksx.xcb.retrofitmvpdemo.BuildConfig;
import com.moible.bksx.xcb.retrofitmvpdemo.config.Constant;
import com.moible.bksx.xcb.retrofitmvpdemo.api.RetrofitApiService;
import com.moible.bksx.xcb.retrofitmvpdemo.application.BaseApplication;
import com.moible.bksx.xcb.retrofitmvpdemo.config.URLConfig;
import com.moible.bksx.xcb.retrofitmvpdemo.http.cookieinterceptor.AddCookiesInterceptor;
import com.moible.bksx.xcb.retrofitmvpdemo.http.cookieinterceptor.ReceivedCookiesInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by qlf on 2017/9/1.
 */

public class RetrofitManager {
    private static RetrofitManager mRetrofitManager;
    private Retrofit mRetrofit;
    private RetrofitApiService mRetrofitApiService;
    private RetrofitManager(){
        init();
    }

    public static synchronized RetrofitManager getInstance(){
        if (mRetrofitManager == null){
            mRetrofitManager = new RetrofitManager();
        }
        return mRetrofitManager;
    }

    private void init() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor  loggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG){
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        builder.addInterceptor(loggingInterceptor);

        //TODO 缓存文件路径
        /**
         *设置缓存
         */
        File cacheFile = new File(Constant.PATH_CACHE);
        okhttp3.Cache cache = new okhttp3.Cache(cacheFile, 1024 * 1024 * 50);
        //设置网络请求拦截器
        NetCheckInterceptor netCheckInterceptor = new NetCheckInterceptor(BaseApplication.mContext);
        builder.addNetworkInterceptor(netCheckInterceptor);
        /**
         * 设置网络请求响应拦截器
         */
        //添加首次请求拦截器，获取登陆以后的sessionId，存到sharePreferences中
        builder.addInterceptor(new ReceivedCookiesInterceptor());
        //获取本地的cookie，携带到非首次的请求中。
        builder.addInterceptor(new AddCookiesInterceptor());
        //主机验证，SSL证书
        // .hostnameVerifier(new SafeHostnameVerifier())
        // .sslSocketFactory(CcsApplication.getSslSocket(),new SafeTrustManager())
        builder.cache(cache);
        //连接时间超时的时间阈值
        builder.connectTimeout( 15, TimeUnit.SECONDS);
        //数据获取时间阈值
        builder.readTimeout(20, TimeUnit.SECONDS);
        //写数据超时阈值
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //设置断网重连
        builder.retryOnConnectionFailure(true);
        OkHttpClient mOkHttpClient = builder.build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(URLConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(mOkHttpClient)
                .build();
    }

    public <T> T createReq(Class<T> reqServer){
        return mRetrofit.create(reqServer);
    }

    public <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io()) //在IO线程 产生数据
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//在UI线程 分发数据
                .subscribe(s);
    }

/**
 * 如果请求头 是统一的话，统一的请求头可以放在拦截器中
 */
//    public static OkHttpClient genericClient() {
//        OkHttpClient httpClient = new OkHttpClient.Builder()
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request()
//                                .newBuilder()
//                                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
//                                .addHeader("Accept-Encoding", "gzip, deflate")
//                                .addHeader("Connection", "keep-alive")
//                                .addHeader("Accept", "*/*")
//                                .addHeader("Cookie", "add cookies here")
//                                .build();
//                        return chain.proceed(request);
//                    }
//                }).build();
//        return httpClient;
//    }
}
