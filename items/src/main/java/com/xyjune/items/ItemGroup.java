package com.xyjune.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class ItemGroup extends LinearLayout {

    private static final String TAG = "ItemGroup";

    private Drawable mDivider;
    private int mDividerWidth;
    private int mDividerHeight;
    private int mShowDividers;
    private int mDividerPaddingLeft;
    private int mDividerPaddingRight = 0;

    public ItemGroup(Context context) {
        super(context);
        setOrientation(VERTICAL);
    }

    public ItemGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemGroup);
        Drawable drawable = typedArray.getDrawable(R.styleable.ItemGroup_xy_divider);
        mDividerPaddingLeft = typedArray.getDimensionPixelSize(R.styleable.ItemGroup_xy_divider_paddingLeft, 0);
        setDividerDrawable(drawable);
    }

    @Override
    public void setDividerDrawable(Drawable divider) {
        if (divider == mDivider) {
            return;
        }
        mDivider = divider;
        if (divider != null) {
            mDividerWidth = divider.getIntrinsicWidth();
            mDividerHeight = divider.getIntrinsicHeight();
            mShowDividers = SHOW_DIVIDER_MIDDLE;
        } else {
            mDividerWidth = 0;
            mDividerHeight = 0;
        }
        setWillNotDraw(divider == null);
        requestLayout();
    }

    @Override
    public void setDividerPadding(int padding) {
        mDividerPaddingLeft = padding;
        requestLayout();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //将分隔元素的宽高转化为对应的margin
        setChildrenDivider();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void setChildrenDivider() {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            //遍历每个子View
            View child = getChildAt(i);
            //拿到索引
            final int index = indexOfChild(child);
            //方向
            final int orientation = getOrientation();
            final LayoutParams params = (LayoutParams) child.getLayoutParams();
            //判断是否需要在子View左边绘制分隔
            if (hasDividerBeforeChildAt(index)) {
                if (orientation == VERTICAL) {
                    //如果需要，则设置topMargin为分隔元素的高度（垂直时）
                    params.topMargin = mDividerHeight;
                } else {
                    //如果需要，则设置leftMargin为分隔元素的宽度（水平时）
                    params.leftMargin = mDividerWidth;
                }
            }
        }
    }

    private boolean hasDividerBeforeChildAt(int childIndex) {
        if (childIndex == 0 || childIndex == getChildCount()) {
            return false;
        }
        if ((mShowDividers & SHOW_DIVIDER_MIDDLE) != 0) {
            boolean hasVisibleViewBefore = false;
            for (int i = childIndex - 1; i >= 0; i--) {
                //当前index的前一个元素不为GONE则认为需要
                if (getChildAt(i).getVisibility() != GONE) {
                    hasVisibleViewBefore = true;
                    break;
                }
            }
            return hasVisibleViewBefore;
        }
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: 绘制");
        if (mDivider != null) {
            if (getOrientation() == VERTICAL) {
                //绘制垂直方向的divider
                drawDividersVertical(canvas);
            } else {
                //绘制水平方向的divider
                drawDividersHorizontal(canvas);
            }
        }
        super.onDraw(canvas);
    }

    /**
     * 绘制水平方向的divider
     *
     * @param canvas
     */
    private void drawDividersHorizontal(Canvas canvas) {
        final int count = getChildCount();
        //遍历所有的子View
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child != null && child.getVisibility() != GONE) {
                //如果需要绘制divider
                if (hasDividerBeforeChildAt(i)) {
                    final LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();
                    //得到开始的位置，getLeft为当前View的左侧，而左侧有margin，所以之差为divider绘制的开始区域
                    final int left = child.getLeft() - lp.leftMargin;
                    //绘制divider
                    drawVerticalDivider(canvas, left);
                }
            }
        }
    }

    /**
     * 绘制divider，根据left，水平方向绘制
     *
     * @param canvas
     * @param left
     */
    public void drawVerticalDivider(Canvas canvas, int left) {
        //设置divider的范围
        mDivider.setBounds(left, getPaddingTop() + mDividerPaddingLeft, left + mDividerWidth, getHeight() - getPaddingBottom() - mDividerPaddingRight);
        //绘制
        mDivider.draw(canvas);
    }

    private void drawDividersVertical(Canvas canvas) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child != null && child.getVisibility() != GONE) {
                if (hasDividerBeforeChildAt(i)) {
                    final LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();
                    final int top = child.getTop() - lp.topMargin;
                    drawHorizontalDivider(canvas, top);
                }
            }
        }
    }

    private void drawHorizontalDivider(Canvas canvas, int top) {
        mDivider.setBounds(getPaddingLeft() + mDividerPaddingLeft, top, getWidth() - getPaddingRight() - mDividerPaddingRight, top + mDividerHeight);
        mDivider.draw(canvas);
    }
}
