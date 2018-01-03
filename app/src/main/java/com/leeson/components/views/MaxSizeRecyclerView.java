package com.leeson.components.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.leeson.components.R;


/**
 * Created by Administrator on 2017/9/9.
 * lisen
 */

class MaxSizeRecyclerView extends RecyclerView {
    private int maxHeight = -1;
    private int maxWidth = -1;
    public MaxSizeRecyclerView(Context context) {
        this(context,null);
    }

    public MaxSizeRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MaxSizeRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MaxSizeRecyclerView,defStyle,0);
        maxHeight = (int) typedArray.getDimension(R.styleable.MaxSizeRecyclerView_maxHeight,-1);
        maxWidth = (int) typedArray.getDimension(R.styleable.MaxSizeRecyclerView_maxWidth,-1);
        typedArray.recycle();
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        requestLayout();
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        requestLayout();
    }


    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        if (maxHeight > -1) {
            heightSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
        }
        if (maxWidth > -1){
            widthSpec = MeasureSpec.makeMeasureSpec(maxWidth,MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthSpec, heightSpec);
    }
}
