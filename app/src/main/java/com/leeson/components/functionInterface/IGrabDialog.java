package com.leeson.components.functionInterface;

/**
 * Created by Administrator on 2017/6/6.
 * lisen
 */

public interface IGrabDialog {
    /**
     * 确定
     */
    void onConfirmListener(String msg);

    /**
     * 取消
     */
    void onCancelListener();
}
