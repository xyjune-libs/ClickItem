package com.xyjune.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.annotation.Nullable;

public final class SwitchItem extends SubtextItem {

    private Switch mSwitch;

    private boolean isChecked;

    public SwitchItem(Context context) {
        super(context);
        initSwitchItem();
    }

    public SwitchItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchItem);
        isChecked = typedArray.getBoolean(R.styleable.SwitchItem_xy_checked, false);
        typedArray.recycle();
        initSwitchItem();
    }

    private void initSwitchItem() {
        mSwitch = new Switch(getContext());
        mSwitch.setChecked(isChecked);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMarginStart(mDefMargin);
        this.addView(mSwitch, params);
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener checkedChangeListener) {
        mSwitch.setOnCheckedChangeListener(checkedChangeListener);
    }

    public void setChecked(boolean checked) {
        mSwitch.setChecked(checked);
    }

    public boolean isChecked() {
        return mSwitch.isChecked();
    }
}
