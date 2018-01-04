package com.leeson.components.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.leeson.components.R;
import com.leeson.components.utils.CommonUtils;


/**
 * Created by lisen on 2017/12/12.
 * 内部布局可回弹
 * @author lisen < 4533548588@qq.com >
 */

public class DragLayout extends FrameLayout {
    private ViewDragHelper mDragger;
    private int dragViewIndex;
    private View dragView;
    private Point point = new Point();//记录可拖动的view的原来的XY位置

    private DragCallBack callBack;

    public DragLayout(Context context) {
        this(context,null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DragLayout,defStyleAttr,0);
        dragViewIndex = typedArray.getInt(R.styleable.DragLayout_dragViewIndex,0);
        typedArray.recycle();
        callBack = new DragCallBack();
        mDragger = ViewDragHelper.create(this, 1f, callBack);
    }
    // 手指滑动距离与下拉头的滑动距离比，中间会随正切函数变化
    private float radio = 1f;
    public float touchTopY = 0;
    public float lastY = 0;

    private int height ;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (CommonUtils.isKeyboardShown(DragLayout.this)){
            CommonUtils.closeKeyBoard(getContext());
        }
        mDragger.processTouchEvent(event);

        return touchSlowDown(event);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        touchSlowDown(event);
        return mDragger.shouldInterceptTouchEvent(event);
    }

    private boolean touchSlowDown(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                touchTopY = touchTopY + (event.getY() - lastY) / radio;
                lastY = event.getY();
                // 根据下拉距离改变比例
                radio = (float) (1.5 + 2 * Math.tan(Math.PI / 2 / height
                        * (touchTopY + Math.abs(touchTopY))));
                if (lastY >= getMeasuredHeight() || lastY <= 0
                        || event.getX() > getMeasuredWidth()-30 || event.getX()-30 <= 0){
                    if (mDragger.smoothSlideViewTo(dragView,0,point.y)) {
                        touchTopY = point.y;
                        ViewCompat.postInvalidateOnAnimation(this);
                        postInvalidate();
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                touchTopY = point.y;
                break;
        }
        return true;
    }
    @Override
    public void computeScroll() {
        if (mDragger.continueSettling(true)){
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (getChildCount() > 0){
            dragView = getChildAt(dragViewIndex);
            point.x = dragView.getLeft();
            point.y = dragView.getTop();
            touchTopY = point.y;
            height = getMeasuredHeight();
        }
    }

    private class DragCallBack extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == dragView;
        }
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            if (listener != null){
                if (listener.isCanPullDown() && dy > 0 || listener.isCanPullUp() && dy < 0 ){
                    return (int) touchTopY;
                }else if(listener.isCanPullDown() && top > point.y || listener.isCanPullUp() && top < point.y){
                    return (int) touchTopY;
                } else{
                    return point.y;
                }
            }
            return (int) touchTopY;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return point.x;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (dragView == releasedChild){
                mDragger.settleCapturedViewAt(point.x, point.y);
                invalidate();
            }
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return 1;
        }
    }
    public interface DragListener {
        /**
         *
         * @return true:能下拉 false :不能
         */
        boolean isCanPullDown();

        /**
         *
         * @return true :能上拉 false :不能
         */
        boolean isCanPullUp();
    }
    private DragListener listener;

    public void setListener(DragListener listener) {
        this.listener = listener;
    }

}
