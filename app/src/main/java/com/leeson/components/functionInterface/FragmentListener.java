package com.leeson.components.functionInterface;

/**
 * Created by Administrator on 2017/6/19.
 * lisen
 * Fragment中请求权限时，Fragment与activity通信接口
 */

public interface FragmentListener {
    void onFragmentRequestPermission(String[] permissions, int requestCode);
}
