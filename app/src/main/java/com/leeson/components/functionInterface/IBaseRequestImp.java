package com.leeson.components.functionInterface;

/**
 *
 * Created by lisen on 2017/11/7.
 *
 * @author lisen < 4533548588@qq.com >
 */

public abstract class IBaseRequestImp<T> implements IBaseRequest<T> {

    @Override
    public abstract void onRequestSuccess(T t);

    @Override
    public void onRequestError(int errorCode, String errorMsg) {

    }

    @Override
    public void onRequest() {

    }
}
