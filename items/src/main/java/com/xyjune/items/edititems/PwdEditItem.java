package com.xyjune.items.edititems;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;

import com.xyjune.items.EditItem;
import com.xyjune.items.JCheckBox;
import com.xyjune.items.R;

public class PwdEditItem extends EditItem {

    private boolean visible;

    public PwdEditItem(Context context) {
        this(context, null);
    }

    public PwdEditItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PwdEditItem);
        visible = typedArray.getBoolean(R.styleable.PwdEditItem_xy_pwd_visible, false);
        typedArray.recycle();
        mEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        initCheckBox();
    }

    private void initCheckBox() {
        JCheckBox checkBox = new JCheckBox(getContext());
        checkBox.setChecked(visible);

//        checkBox.setButtonDrawable(ContextCompat.getDrawable(getContext(), R.drawable.pwd_visible_box));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setVisible(isChecked);
            }
        });
        int width = dip2px(getContext(), 30);
        int height = dip2px(getContext(), 30);
        int drawableW = dip2px(getContext(), 20);
        int drawableH = dip2px(getContext(), 15);
        int left = (width - drawableW) / 2;
        int right = (width - drawableW) / 2 + drawableW;

        checkBox.setDrawableBounds(left, 0, right, drawableH);
        checkBox.setButtonDrawable(R.drawable.pwd_visible_box);
        LayoutParams params = new LayoutParams(width, height);
        params.setMarginStart(mDefMargin);
        setVisible(visible);
        addView(checkBox, params);
    }

    public void setVisible(boolean isVisible) {
        if (isVisible) {
            mEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            mEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        mEditText.setSelection(mEditText.length());
    }
}
