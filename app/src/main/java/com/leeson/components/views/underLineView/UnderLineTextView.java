package com.leeson.components.views.underLineView;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;


/**
 * Created by Administrator on 2017/9/26.
 * lisen
 */

public class UnderLineTextView extends android.support.v7.widget.AppCompatTextView {

    private UnderLineHelper helper;
    public UnderLineTextView(Context context) {
        this(context,null);
    }

    public UnderLineTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public UnderLineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        helper = new UnderLineHelper(context,this,attrs,defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        helper.onDraw(canvas);
    }
}
