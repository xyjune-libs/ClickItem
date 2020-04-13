package com.xyjune.items;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.content.ContextCompat;

public class JCheckBox extends AppCompatCheckBox {

    private int right;
    private int bottom;
    private int left;
    private int top;

    public JCheckBox(Context context) {
        this(context, null);
    }

    public JCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //一定要在设置 Drawable 之前设置 否者不生效
    public void setDrawableBounds(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public void setButtonDrawable(int resId) {
        setButtonDrawable(ContextCompat.getDrawable(getContext(), resId));
    }

    @Override
    public void setButtonDrawable(Drawable buttonDrawable) {
        if (buttonDrawable != null) {
            buttonDrawable.setBounds(left, top, right, bottom);
            setCompoundDrawables(buttonDrawable, null, null, null);
        }
    }
}
