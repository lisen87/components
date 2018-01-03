package com.leeson.components.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

import com.leeson.components.R;


/**
 * Created by lisen on 2017/12/19.
 *
 * @author lisen < 4533548588@qq.com >
 */

public class MaxScorllView extends NestedScrollView {
    private int maxHeight = -1;
    private int maxWidth = -1;
    public MaxScorllView(Context context) {
        this(context,null);
    }

    public MaxScorllView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MaxScorllView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MaxScorllView,defStyleAttr,0);
        maxHeight = (int) typedArray.getDimension(R.styleable.MaxScorllView_maxH,-1);
        maxWidth = (int) typedArray.getDimension(R.styleable.MaxScorllView_maxW,-1);
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
