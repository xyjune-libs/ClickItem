package com.xyjune.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class BaseItem extends LinearLayout {

    protected ImageView mIcon;
    protected TextView mText;
    protected TextView mSubtext;

    private int icon;
    private String textStr;
    private int textColor;
    private float textSize;
    private String subtextStr;
    private int subtextColor;
    private float subtextSize;

    public BaseItem(Context context) {
        super(context);
        init();
    }

    public BaseItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseItem);
        icon = typedArray.getResourceId(R.styleable.BaseItem_xy_icon, 0);
        textStr = typedArray.getString(R.styleable.BaseItem_xy_text);
        textColor = typedArray.getColor(R.styleable.BaseItem_xy_textColor,
                ContextCompat.getColor(context, R.color.def_text));
        textSize = typedArray.getDimensionPixelSize(R.styleable.BaseItem_xy_textSize, dip2px(context, 16));

        subtextStr = typedArray.getString(R.styleable.BaseItem_xy_subtext);
        subtextColor = typedArray.getColor(R.styleable.BaseItem_xy_subtextColor,
                ContextCompat.getColor(context, R.color.def_subtext));
        subtextSize = typedArray.getDimensionPixelSize(R.styleable.BaseItem_xy_subtextSize, dip2px(context, 14));

        typedArray.recycle();
        init();
    }

    private void init() {
        setGravity(Gravity.CENTER_VERTICAL);
        setOrientation(HORIZONTAL);
        initIcon();
        initText();
        if (isInitSubtext()) {
            initSubtext();
        }
        setClickable(false);
    }

    protected boolean isInitSubtext() {
        return true;
    }

    private void initIcon() {
        mIcon = new ImageView(this.getContext());
        if (icon != 0) {
            mIcon.setImageResource(icon);
        } else {
            mIcon.setVisibility(GONE);
        }
        int width = dip2px(this.getContext(), 20);
        int height = dip2px(this.getContext(), 20);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        params.leftMargin = dip2px(this.getContext(), 15);
        this.addView(mIcon, params);
    }

    private void initText() {
        mText = new TextView(this.getContext());
        mText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        mText.setText(textStr);
        mText.setTextColor(textColor);
        mText.setSingleLine();
        mText.setEllipsize(TextUtils.TruncateAt.END);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        params.setMarginStart(dip2px(getContext(), 15));
        params.setMarginEnd(dip2px(getContext(), 15));
        this.addView(mText, params);
    }

    private void initSubtext() {
        mSubtext = new TextView(this.getContext());
        mSubtext.setTextSize(TypedValue.COMPLEX_UNIT_PX, subtextSize);
        if (subtextStr != null) {
            mSubtext.setText(subtextStr);
        } else {
            mSubtext.setVisibility(GONE);
        }
        mSubtext.setTextColor(subtextColor);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMarginEnd(dip2px(getContext(), 15));
        this.addView(mSubtext, params);
    }

    public void setText(CharSequence charSequence) {
        textStr = charSequence.toString();
        mText.setText(charSequence);
    }

    public void setSubtext(CharSequence charSequence) {
        mSubtext.setText(charSequence);
        if (charSequence.length() > 0) {
            mSubtext.setVisibility(VISIBLE);
        } else {
            mSubtext.setVisibility(GONE);
        }
    }

    public void setTextColor(int color) {
        mText.setTextColor(color);
    }

    public void setIcon(int icon) {
        mIcon.setImageResource(icon);
        mIcon.setVisibility(VISIBLE);
    }

    public void setIcon(Bitmap bitmap) {
        mIcon.setImageBitmap(bitmap);
        mIcon.setVisibility(VISIBLE);
    }

    public void setIcon(Drawable drawable) {
        mIcon.setImageDrawable(drawable);
        mIcon.setVisibility(VISIBLE);
    }

    protected int dip2px(Context context, int dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    public String getText() {
        return mText.getText().toString();
    }

    public String getSubtextStr() {
        return mSubtext.getText().toString();
    }
}
