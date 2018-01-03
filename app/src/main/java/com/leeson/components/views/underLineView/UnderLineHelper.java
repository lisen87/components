package com.leeson.components.views.underLineView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.leeson.components.R;


/**
 * Created by lisen on 2017/11/18.
 *
 * @author lisen < 4533548588@qq.com >
 */

public class UnderLineHelper {

    private int lineLeft;
    private int lineSize;

    private Paint paint;

    private View view;

    public UnderLineHelper(Context context,View view,@Nullable AttributeSet attrs,int defStyleAttr) {
        TypedArray typedArray = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.UnderLineLayout, defStyleAttr, 0);
        lineLeft = (int) typedArray.getDimension(R.styleable.UnderLineLayout_lineLeft,0);
        lineSize = (int) typedArray.getDimension(R.styleable.UnderLineLayout_lineSize,1);
        int lineColor = typedArray.getColor(R.styleable.UnderLineLayout_lineColor,
                ContextCompat.getColor(context,R.color.lineColor));


        typedArray.recycle();

        this.view = view;

        paint = new Paint();
        paint.setColor(lineColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(lineSize);
        paint.setAntiAlias(true);
        paint.setDither(true);
    }

    public void onDraw(Canvas canvas){
        canvas.drawLine(lineLeft,view.getHeight()-lineSize,view.getWidth(),view.getHeight()-lineSize,paint);
    }
}
