package com.xyjune.items.edititems;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.xyjune.items.EditItem;
import com.xyjune.items.R;

public class PhoneEditItem extends EditItem {
    ImageView mImageView;

    public PhoneEditItem(Context context) {
        this(context, null);
    }

    public PhoneEditItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mEditText.setInputType(InputType.TYPE_CLASS_PHONE);
        mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        initDel();
        addTextChangedListener(mTextWatcher);
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            showDel();
        }
    };

    private void initDel() {
        mImageView = new ImageView(getContext());
        mImageView.setImageResource(R.drawable.ic_del);
        int width = dip2px(getContext(), 30);
        int height = dip2px(getContext(), 30);
        int padding = dip2px(getContext(), 5);
        mImageView.setPadding(padding, padding, padding, padding);
        LayoutParams layoutParams = new LayoutParams(width, height);
        layoutParams.setMarginStart(mDefMargin);
        addView(mImageView, layoutParams);
        mImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.setText("");
            }
        });
        showDel();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            showDel();
        } else {
            mImageView.setVisibility(GONE);
        }
    }

    private void showDel() {
        if (TextUtils.isEmpty(mEditText.getText())) {
            mImageView.setVisibility(GONE);
        } else {
            mImageView.setVisibility(VISIBLE);
        }
    }
}
