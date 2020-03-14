package com.xyjune.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class ClickBar extends LinearLayout {

    private Context mContext;

    private ImageView icon;
    private ImageView rightIcon;
    private TextView text;
    private TextView subText;
    private View divider;

    private int textColor;
    private int subtextColor;
    private int disableTextColor;

    private boolean hadIcon = false;

    public ClickBar(Context context) {
        super(context);
    }

    public ClickBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.item_click, this);

        icon = findViewById(R.id.cb_img_icon);
        rightIcon = findViewById(R.id.cb_img_right_icon);
        text = findViewById(R.id.cb_tv_text);
        subText = findViewById(R.id.cb_tv_subtext);
        divider = findViewById(R.id.cb_view_divider);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClickBar);

        int left_icon_id = typedArray.getResourceId(R.styleable.ClickBar_cb_icon_left, 0);
        int right_icon_id = typedArray.getResourceId(R.styleable.ClickBar_cb_icon_right, 0);
        String textStr = typedArray.getString(R.styleable.ClickBar_cb_text);
        String subtextStr = typedArray.getString(R.styleable.ClickBar_cb_subtext);
        boolean showDivider = typedArray.getBoolean(R.styleable.ClickBar_cb_show_divider, false);

        textColor = typedArray.getColor(R.styleable.ClickBar_cb_text_color,
                ContextCompat.getColor(context, R.color.def_text));
        subtextColor = typedArray.getColor(R.styleable.ClickBar_cb_subtext_color,
                ContextCompat.getColor(context, R.color.def_subtext));
        disableTextColor = typedArray.getColor(R.styleable.ClickBar_cb_text_color_disable_click,
                ContextCompat.getColor(context, R.color.def_text_disable));

        text.setTextColor(textColor);
        subText.setTextColor(subtextColor);

        setLeftIcon(left_icon_id);
        setRightIcon(right_icon_id);
        setText(textStr);
        setSubText(subtextStr);
        showDivider(showDivider);

        typedArray.recycle();
    }

    public void setLeftIcon(int rid) {
        if (rid == 0) {
            hadIcon = false;
            icon.setVisibility(GONE);
        } else {
            hadIcon = true;
            icon.setImageResource(rid);
            icon.setVisibility(VISIBLE);
        }
        fixDividerMargin();
    }

    public void setRightIcon(int rid) {
        if (rid == 0) {
            rightIcon.setVisibility(GONE);
        } else {
            rightIcon.setImageResource(rid);
            rightIcon.setVisibility(VISIBLE);
        }
    }

    public void setText(String str) {
        text.setText(str);
    }

    public void setSubText(String str) {
        if (str == null) {
            subText.setVisibility(GONE);
        } else {
            subText.setText(str);
            subText.setVisibility(VISIBLE);
        }
    }

    public void showDivider(boolean isShow) {
        divider.setVisibility(isShow ? VISIBLE : GONE);
    }

    private void fixDividerMargin() {
        LayoutParams layoutParams = (LayoutParams) divider.getLayoutParams();
        int marginStart = layoutParams.getMarginStart();
        if (hadIcon) {
            marginStart = Utils.dip2px(mContext, 50);
        } else {
            marginStart = Utils.dip2px(mContext, 15);
        }
        layoutParams.setMarginStart(marginStart);
    }

    @Override
    public void setClickable(boolean clickable) {
        super.setClickable(clickable);
        if (clickable) {
            text.setTextColor(textColor);
            subText.setTextColor(subtextColor);
            rightIcon.setVisibility(View.VISIBLE);
        } else {
            text.setTextColor(disableTextColor);
            subText.setTextColor(disableTextColor);
            rightIcon.setVisibility(View.GONE);
        }
    }
}
