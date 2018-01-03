package com.leeson.components.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.leeson.components.utils.CommonUtils;

/**
 * Created by lisen on 2017/12/29.
 *  水波纹
 * @author lisen < 4533548588@qq.com >
 */

public class WareView extends View {
    private int mOffset;
    private int mScreenWidth,mScreenHeight;

    private Path mPath;

    private Paint paint,bitPaint;
    private Bitmap mBitmap;
    private Canvas mCanvas;

    public WareView(Context context) {
        this(context,null);
    }

    public WareView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WareView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScreenWidth = CommonUtils.getScreenWidth(context);
        mScreenHeight = CommonUtils.getScreenHeight(context);
        mPath = new Path();
        paint = new Paint();
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);

        bitPaint = new Paint();
        bitPaint.setColor(Color.GRAY);
        bitPaint.setStrokeWidth(8);

        setViewanimator();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScreenWidth = getMeasuredWidth();
        mScreenHeight = getMeasuredHeight();
        mBitmap = Bitmap.createBitmap(mScreenWidth,mScreenHeight, Bitmap.Config.ARGB_8888); //生成一个bitmap
        mCanvas = new Canvas(mBitmap);//讲bitmp放在我们自己的画布上，实际上mCanvas.draw的时候 改变的是这个bitmap对象
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas.drawCircle(mScreenWidth/2,mScreenHeight/2,mScreenHeight/2,bitPaint);

        mPath.reset();
        //贝塞尔曲线
        mPath.moveTo(-mScreenWidth + mOffset, mScreenHeight / 2);

        //简化写法
        for (int i = 0; i < 2; i++) {
            mPath.quadTo(-mScreenWidth * 3 / 4 + (mScreenWidth * i) + mOffset, mScreenHeight / 2 - 50, -mScreenWidth / 2 + (mScreenWidth * i) + mOffset, mScreenHeight / 2);
            mPath.quadTo(-mScreenWidth / 4 + (mScreenWidth * i) + mOffset, mScreenHeight / 2 + 50, +(mScreenWidth * i) + mOffset, mScreenHeight / 2);
        }
        //空白部分填充
        mPath.lineTo(mScreenWidth, mScreenHeight);
        mPath.lineTo(0, mScreenHeight);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        mCanvas.drawPath(mPath, paint);




        canvas.drawBitmap(mBitmap,0,0,null);
    }
    /**
     * 设置动画效果
     */
    private void setViewanimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, mScreenWidth);
        valueAnimator.setDuration(3000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffset = (int) animation.getAnimatedValue();//当前平移的值
                invalidate();
            }
        });
        valueAnimator.start();
    }
}
