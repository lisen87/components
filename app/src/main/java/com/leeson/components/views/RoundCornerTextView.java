package com.leeson.components.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;

import com.leeson.components.R;


/**
 * 带圆角边框的textview
 * Created by Administrator on 2017/9/6.
 * lisen
 */

public class RoundCornerTextView extends android.support.v7.widget.AppCompatTextView {

    private float[] radii = new float[8];   // top-left, top-right, bottom-right, bottom-left
    private int stroke;//外边框（dp）
    private Path path;
    private Paint paint,strokePaint;

    private int pressColor = 0;//按下时的颜色
    private int solidColor = 0;//内填充的颜色

    public RoundCornerTextView(Context context) {
        this(context,null);
    }

    public RoundCornerTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoundCornerTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RoundCornerTextView, defStyleAttr, 0);
        //外边框的颜色
        int strokeColor = typedArray.getColor(R.styleable.RoundCornerTextView_strokeColor,
                ContextCompat.getColor(context,android.R.color.transparent));
        //内填充的颜色
        solidColor = typedArray.getColor(R.styleable.RoundCornerTextView_solidColor,
                ContextCompat.getColor(context,android.R.color.transparent));
        pressColor = typedArray.getColor(R.styleable.RoundCornerTextView_pressColor,
                ContextCompat.getColor(context,android.R.color.transparent));
        //外边框的圆角（dp）
        int corner = typedArray.getDimensionPixelSize(R.styleable.RoundCornerTextView_corner, 2);
        stroke = typedArray.getDimensionPixelSize(R.styleable.RoundCornerTextView_stroke,0);
        int roundCornerTopLeft = typedArray.getDimensionPixelSize(
                R.styleable.RoundCornerTextView_cornerLeftTop, corner);
        int roundCornerTopRight = typedArray.getDimensionPixelSize(
                R.styleable.RoundCornerTextView_cornerRightTop, corner);
        int roundCornerBottomLeft = typedArray.getDimensionPixelSize(
                R.styleable.RoundCornerTextView_cornerLeftBottom, corner);
        int roundCornerBottomRight = typedArray.getDimensionPixelSize(
                R.styleable.RoundCornerTextView_cornerRightBottom, corner);
        typedArray.recycle();

        radii[0] = roundCornerTopLeft;
        radii[1] = roundCornerTopLeft;

        radii[2] = roundCornerTopRight;
        radii[3] = roundCornerTopRight;

        radii[4] = roundCornerBottomRight;
        radii[5] = roundCornerBottomRight;

        radii[6] = roundCornerBottomLeft;
        radii[7] = roundCornerBottomLeft;


        paint = new Paint();
        paint.setColor(solidColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        strokePaint = new Paint();
        strokePaint.setColor(strokeColor);
        strokePaint.setStrokeWidth(stroke);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setAntiAlias(true);

        path = new Path();
    }

    public void setStrokeColor(@ColorInt int strokeColor) {
        strokePaint.setColor(strokeColor);
        invalidate();
    }

    public void setSolidColor(@ColorInt int solidColor) {
        paint.setColor(solidColor);
        invalidate();
    }

    /**
     *  单位dp
     * @param corner
     */
    public void setCorner(int corner) {
        corner = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,corner,getContext().getResources().getDisplayMetrics());
        radii[0] = corner;
        radii[1] = corner;

        radii[2] = corner;
        radii[3] = corner;

        radii[4] = corner;
        radii[5] = corner;

        radii[6] = corner;
        radii[7] = corner;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        RectF areas = new RectF();
        areas.left = stroke;
        areas.top = stroke;
        areas.right = w - stroke;
        areas.bottom = h - stroke;
        path.reset();
        path.addRoundRect(areas,radii,Path.Direction.CW);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
        if (stroke > 0 ){
            canvas.drawPath(path, strokePaint);
        }
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (pressColor != 0){
                    paint.setColor(pressColor);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (pressColor != 0){
                    paint.setColor(solidColor);
                    invalidate();
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
