package com.moible.bksx.xcb.retrofitmvpdemo.http;

import com.moible.bksx.xcb.retrofitmvpdemo.bean.BaseResultBean;

/*Subscriber回调，统一归结为next（）【成功】 error（）【失败】
 */
public interface SubscribeOnNextListener<T> {
    void onNext(T t);
    void onError(BaseResultBean t);
}
