package com.leeson.components.functionInterface;

/**
 *  *
 * Created by lisen on 2017/11/7.
 *
 * @author lisen < 4533548588@qq.com >
 */
public interface IBaseRequest<T> {
    /**
     * 正在请求
     */
    void onRequest();
    void onRequestSuccess(T t);
    void onRequestError(int errorCode, String errorMsg);
}
