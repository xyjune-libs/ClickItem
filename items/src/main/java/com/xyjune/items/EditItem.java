package com.xyjune.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.xyjune.items.utils.SoftKeyboardStateHelper;

public class EditItem extends BaseItem {

    private static final String TAG = "EditItem";

    private int editColor;
    private float editSize;
    private int editGravity;
    private String editHint;
    private String editText;
    private int editWidth;
    private int editHeight;

    private SoftKeyboardStateHelper mSoftKeyboardStateHelper;

    private EditText mEditText;

    public EditItem(Context context) {
        super(context);
        initEditItem();
    }

    public EditItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditItem);

        int defColor = ContextCompat.getColor(context, R.color.def_text);
        editColor = typedArray.getColor(R.styleable.EditItem_xy_edit_textColor, defColor);
        editSize = typedArray.getDimensionPixelSize(R.styleable.EditItem_xy_edit_textSize, dip2px(context, 16));
        editGravity = typedArray.getInt(R.styleable.EditItem_xy_edit_gravity, 1);
        editHint = typedArray.getString(R.styleable.EditItem_xy_edit_hint);
        editText = typedArray.getString(R.styleable.EditItem_xy_edit_text);

        editWidth = typedArray.getDimensionPixelSize(R.styleable.EditItem_xy_edit_width, 0);
        editHeight = typedArray.getDimensionPixelSize(R.styleable.EditItem_xy_edit_height, LayoutParams.MATCH_PARENT);

        typedArray.recycle();
        initEditItem();
    }

    private void initEditItem() {
        mEditText = new EditText(getContext());
        mEditText.setText(editText);
        mEditText.setHint(editHint);
        mEditText.setTextColor(editColor);
        mEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, editSize);
        switch (editGravity) {
            case 0:
                mEditText.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
                break;
            case 2:
                mEditText.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
                break;
            case 1:
            default:
                mEditText.setGravity(Gravity.CENTER);
                break;
        }
        mEditText.setBackground(null);
        LayoutParams params = new LayoutParams(editWidth, editHeight);
        if (editWidth == 0) {
            params.weight = 1;
        }
        params.setMarginEnd(dip2px(getContext(), 15));
        mEditText.setSingleLine();
        mEditText.setOnFocusChangeListener(mOnFocusChangeListener);
        mSoftKeyboardStateHelper = new SoftKeyboardStateHelper(this);
        mSoftKeyboardStateHelper.addSoftKeyboardStateListener(mSoftKeyboardStateListener);
        addView(mEditText, params);
    }

    private OnFocusChangeListener mOnFocusChangeListener = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            Log.d(TAG, "onFocusChange: " + hasFocus);
            if (!hasFocus) {
                mEditText.clearFocus();
            }
        }
    };

    @Override
    public void clearFocus() {
        super.clearFocus();
        mEditText.clearFocus();
    }

    @Override
    protected boolean isInitSubtext() {
        return false;
    }

    private SoftKeyboardStateHelper.SoftKeyboardStateListener mSoftKeyboardStateListener = new SoftKeyboardStateHelper.SoftKeyboardStateListener() {
        @Override
        public void onSoftKeyboardOpened(int keyboardHeightInPx) {

        }

        @Override
        public void onSoftKeyboardClosed() {
            if (mEditText.isFocused())
                mEditText.clearFocus();
        }
    };

    public void addTextChangedListener(TextWatcher watcher) {
        mEditText.addTextChangedListener(watcher);
    }

    public String getEditText() {
        return mEditText.getText().toString();
    }

    public void setEditText(CharSequence text) {
        mEditText.setText(text);
    }
}
