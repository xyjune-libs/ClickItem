package com.xyjune.items;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.Nullable;

public final class RadioItem extends BaseItem {

    private RadioButton mRadioButton;

    public RadioItem(Context context) {
        super(context);
        initRadioButtonItem();
    }

    public RadioItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initRadioButtonItem();
    }

    private void initRadioButtonItem() {
        mRadioButton = new RadioButton(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMarginEnd(dip2px(getContext(), 15));
        this.addView(mRadioButton, params);
        mRadioButton.setClickable(false);
        setClickable(true);
        setOnClickListener(mOnClickListener);
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener checkedChangeListener) {
        mRadioButton.setOnCheckedChangeListener(checkedChangeListener);
    }

    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            mRadioButton.setChecked(true);
        }
    };

    public void setChecked(boolean checked) {
        mRadioButton.setChecked(checked);
    }

    public boolean isChecked() {
        return mRadioButton.isChecked();
    }
}
