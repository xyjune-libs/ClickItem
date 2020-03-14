package com.xyjune.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class SwitchBar extends LinearLayout {

    private Context mContext;

    private ImageView icon;
    private TextView text;
    private TextView subText;
    private View divider;
    private Switch mSwitch;

    private boolean hadIcon = false;

    public SwitchBar(Context context) {
        super(context);
    }

    public SwitchBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.item_switch_bar, this);

        icon = findViewById(R.id.sb_img_icon);
        text = findViewById(R.id.sb_tv_text);
        subText = findViewById(R.id.sb_tv_subtext);
        divider = findViewById(R.id.sb_view_divider);
        mSwitch = findViewById(R.id.sb_switch);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchBar);

        int rid = typedArray.getResourceId(R.styleable.SwitchBar_sb_img, 0);
        String textStr = typedArray.getString(R.styleable.SwitchBar_sb_text);
        String subtextStr = typedArray.getString(R.styleable.SwitchBar_sb_subtext);
        boolean showDivider = typedArray.getBoolean(R.styleable.SwitchBar_sb_show_divider, false);
        boolean isChecked = typedArray.getBoolean(R.styleable.SwitchBar_sb_check, false);

        setIcon(rid);
        setText(textStr);
        setSubText(subtextStr);
        showDivider(showDivider);
        mSwitch.setChecked(isChecked);

        typedArray.recycle();
    }

    public void setIcon(int rid) {
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
