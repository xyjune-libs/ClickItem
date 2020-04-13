package com.xyjune.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class EditItem extends BaseItem {

    private static final String TAG = "EditItem";

    private static final int START = 0;
    private static final int CENTER = 1;
    private static final int END = 2;

    private int editColor;
    private float editSize;
    private int editGravity;
    private String editHint;
    private String editText;
    private int editWidth;
    private int editHeight;

    protected EditText mEditText;

    public EditItem(Context context) {
        this(context, null);
    }

    public EditItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditItem);
        int defColor = ContextCompat.getColor(context, R.color.def_text);
        editColor = typedArray.getColor(R.styleable.EditItem_xy_edit_textColor, defColor);
        editSize = typedArray.getDimensionPixelSize(R.styleable.EditItem_xy_edit_textSize, dip2px(context, 16));
        editGravity = typedArray.getInt(R.styleable.EditItem_xy_edit_gravity, 0);
        editHint = typedArray.getString(R.styleable.EditItem_xy_edit_hint);
        editText = typedArray.getString(R.styleable.EditItem_xy_edit_text);
        editWidth = typedArray.getDimensionPixelSize(R.styleable.EditItem_xy_edit_width, -3);
        editHeight = typedArray.getDimensionPixelSize(R.styleable.EditItem_xy_edit_height, LayoutParams.MATCH_PARENT);
        typedArray.recycle();

        LayoutParams params = (LayoutParams) mText.getLayoutParams();
        params.weight = 0;
        params.width = LayoutParams.WRAP_CONTENT;
        mText.setMaxEms(5);
        initEditItem();
    }

    private void initEditItem() {
        mEditText = new EditText(getContext());
        mEditText.setText(editText);
        mEditText.setHint(editHint);
        mEditText.setTextColor(editColor);
        mEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, editSize);
        setEditGravity(editGravity);
        mEditText.setBackground(null);
        LayoutParams params = new LayoutParams(editWidth, editHeight);
        if (editWidth == -3) {
            params.weight = 1;
        }
        params.setMarginEnd(mDefMargin);
        mEditText.setSingleLine();
        mEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        mEditText.setPadding(0, 0, 0, 0);
        addView(mEditText, params);
    }

    /**
     * @param gravity Pass {@link #START} or {@link #CENTER} or {@link #END}. Default
     *                value is {@link #START}.
     */
    public void setEditGravity(int gravity) {
        switch (gravity) {
            case START:
            default:
                mEditText.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
                break;
            case CENTER:
                mEditText.setGravity(Gravity.CENTER);
                break;
            case END:
                mEditText.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
                break;
        }
    }

//    private OnFocusChangeListener mOnFocusChangeListener = new OnFocusChangeListener() {
//        @Override
//        public void onFocusChange(View v, boolean hasFocus) {
//            if (!hasFocus) {
//                mEditText.clearFocus();
//            }
//        }
//    };

    @Override
    public void clearFocus() {
        super.clearFocus();
        mEditText.clearFocus();
    }

//    private SoftKeyboardStateHelper.SoftKeyboardStateListener mSoftKeyboardStateListener = new SoftKeyboardStateHelper.SoftKeyboardStateListener() {
//        @Override
//        public void onSoftKeyboardOpened(int keyboardHeightInPx) {
//
//        }
//
//        @Override
//        public void onSoftKeyboardClosed() {
//            if (mEditText.isFocused())
//                mEditText.clearFocus();
//        }
//    };

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
