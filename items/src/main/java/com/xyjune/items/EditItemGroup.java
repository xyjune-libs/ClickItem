package com.xyjune.items;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.Nullable;

public class EditItemGroup extends ItemGroup {
    public EditItemGroup(Context context) {
        this(context, null);
    }

    public EditItemGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        alignChildText();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void alignChildText() {
        int maxWidth = 0;
        final int count = getChildCount();
        final DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child instanceof EditItem) {
                View text = ((EditItem) child).getChildAt(1);
                text.measure(View.MeasureSpec.makeMeasureSpec(dm.widthPixels, MeasureSpec.AT_MOST),
                        View.MeasureSpec.makeMeasureSpec(dm.heightPixels, MeasureSpec.AT_MOST));
                if (maxWidth < text.getMeasuredWidth()) {
                    maxWidth = text.getMeasuredWidth();
                }
            }
        }
        setChildTextWidth(maxWidth);
    }

    private void setChildTextWidth(int width) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child instanceof EditItem) {
                View text = ((EditItem) child).getChildAt(1);
                LayoutParams params = (LayoutParams) text.getLayoutParams();
                params.width = width;
                text.setLayoutParams(params);
            }
        }
    }
}
