package com.xyjune.items;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class BaseItem extends LinearLayout {
    public BaseItem(Context context) {
        super(context);
    }

    public BaseItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
