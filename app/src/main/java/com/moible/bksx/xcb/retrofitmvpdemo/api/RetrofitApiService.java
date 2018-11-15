package com.moible.bksx.xcb.retrofitmvpdemo.api;

import com.moible.bksx.xcb.retrofitmvpdemo.bean.BaseResponse;
import com.moible.bksx.xcb.retrofitmvpdemo.bean.ReturnDataBean;

import java.util.HashMap;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface RetrofitApiService {

    //表单提交
    @FormUrlEncoded
    @POST("login/login")
    Observable<BaseResponse<ReturnDataBean>> getMovieListData(@FieldMap HashMap<String,String> map);

    @POST("dlzc/dlzc/grdlCx")
    Observable<ResponseBody> getLoginData(@Body RequestBody requestBody);
    //招聘会列表查询
    @GET("zph/zph/zphlbCx")
    Observable<ResponseBody> getZPHData(@Query("pageNum") String pageNum);
}
