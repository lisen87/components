package com.leeson.components.views.underLineView;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;


/**
 * Created by Administrator on 2017/9/26.
 * lisen
 */

public class UnderLineLayout extends LinearLayout {

    private UnderLineHelper helper;

    public UnderLineLayout(Context context) {
        this(context,null);
    }

    public UnderLineLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public UnderLineLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        helper = new UnderLineHelper(context,this,attrs,defStyleAttr);
    }
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        helper.onDraw(canvas);
    }
}
