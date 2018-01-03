package com.leeson.components.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.TextView;

import com.leeson.components.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisen on 2017/11/25.
 *
 * 当textview中有内容时可以点击
 * @author lisen < 4533548588@qq.com >
 */

public class ButtonObserver extends android.support.v7.widget.AppCompatButton implements TextWatcher{

    private int enableRes = 0;
    private int disableRes = 0;

    private boolean extraEnable = true;//额外的标记，当页面不只有输入框要填写时，比如还包括要选择图片

    public ButtonObserver(Context context) {
        this(context,null);
    }

    public ButtonObserver(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ButtonObserver(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setEnabled(false);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ButtonObserver, defStyleAttr, 0);
        enableRes = typedArray.getResourceId(R.styleable.ButtonObserver_enableDrawable,0);
        disableRes = typedArray.getResourceId(R.styleable.ButtonObserver_disableDrawable,0);
        typedArray.recycle();
        if (disableRes != 0){
            setBackgroundResource(disableRes);
        }
    }

    /**
     *
     * @param extraEnable false :不可用
     */
    public void setExtraEnable(boolean extraEnable) {
        this.extraEnable = extraEnable;
        checkEnable();
    }

    private List<TextView> list = new ArrayList<>();

    public void observers(TextView... tvs){
        for (TextView tv: tvs) {
            if (!list.contains(tv)){
                tv.addTextChangedListener(this);
                list.add(tv);
            }
        }
        checkEnable();
    }

    public void removeObservers(TextView... tvs){
        for (TextView tv: tvs) {
            if (list.contains(tv)){
                list.remove(tv);
            }
        }
        checkEnable();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        checkEnable();
    }

    private void checkEnable() {
        boolean empty = checkEmpty();
        if (empty || !extraEnable){
            setEnabled(false);
            setBackgroundResource(disableRes);
        }else{
            setBackgroundResource(enableRes);
            setEnabled(true);
        }
    }

    /**
     *
     * @return true : 空
     */
    private boolean checkEmpty(){
        for(TextView tv: list){
            if (TextUtils.isEmpty(tv.getText().toString().trim())){
                return true;
            }
        }
        return false;
    }
}
