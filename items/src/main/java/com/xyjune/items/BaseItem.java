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

    protected int mDefMargin; // 默认间距

    private int icon;
    private String textStr;
    private int textColor;
    private float textSize;
    private int iconWidth;
    private int iconHeight;
    private int paddingStart;
    private int paddingEnd;


    public BaseItem(Context context) {
        this(context, null);
    }

    public BaseItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseItem);
        icon = typedArray.getResourceId(R.styleable.BaseItem_xy_icon, 0);
        textStr = typedArray.getString(R.styleable.BaseItem_xy_text);
        textColor = typedArray.getColor(R.styleable.BaseItem_xy_textColor,
                ContextCompat.getColor(context, R.color.def_text));
        textSize = typedArray.getDimensionPixelSize(R.styleable.BaseItem_xy_textSize, dip2px(context, 16));
        iconWidth = typedArray.getDimensionPixelSize(R.styleable.BaseItem_xy_icon_width, dip2px(context, 20));
        iconHeight = typedArray.getDimensionPixelSize(R.styleable.BaseItem_xy_icon_height, dip2px(context, 20));
        paddingStart = typedArray.getDimensionPixelSize(R.styleable.BaseItem_xy_paddingStart, 15);
        paddingEnd = typedArray.getDimensionPixelSize(R.styleable.BaseItem_xy_paddingEnd, 15);
        mDefMargin = typedArray.getDimensionPixelSize(R.styleable.BaseItem_xy_margin, dip2px(context, 10));
        typedArray.recycle();

        init();
    }

    private void init() {
        setGravity(Gravity.CENTER_VERTICAL);
        setOrientation(HORIZONTAL);
        setPadding(paddingStart, 0, paddingEnd, 0);
        setClickable(false);
        initIcon();
        initText();
    }

    private void initIcon() {
        mIcon = new ImageView(this.getContext());
        if (icon != 0) {
            mIcon.setImageResource(icon);
        } else {
            mIcon.setVisibility(GONE);
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(iconWidth, iconHeight);
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
        params.setMarginStart(mDefMargin);
        this.addView(mText, params);
    }

    public void setText(CharSequence charSequence) {
        textStr = charSequence.toString();
        mText.setText(charSequence);
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
}
