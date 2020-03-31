package com.xyjune.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public final class ClickItem extends BaseItem {

    private static final String TAG = "ClickItem";

    private ImageView mClickIcon;

    private int clickIcon;

    public ClickItem(Context context) {
        super(context);
        initClickItem();
    }

    public ClickItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClickItem);
        clickIcon = typedArray.getResourceId(R.styleable.ClickItem_xy_click_icon, 0);
        typedArray.recycle();
        initClickItem();
    }

    private void initClickItem() {
        mClickIcon = new ImageView(getContext());
        if (clickIcon != 0) {
            mClickIcon.setImageResource(clickIcon);
        } else {
            mClickIcon.setVisibility(GONE);
        }
        int width = dip2px(this.getContext(), 15);
        int height = dip2px(this.getContext(), 15);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        params.setMarginEnd(dip2px(this.getContext(), 15));
        this.addView(mClickIcon, params);
        setClickable(true);
    }

    public void setClickIcon(int icon) {
        mClickIcon.setImageResource(icon);
        mClickIcon.setVisibility(VISIBLE);
    }

    public void setClickIcon(Bitmap bitmap) {
        mClickIcon.setImageBitmap(bitmap);
        mClickIcon.setVisibility(VISIBLE);
    }

    public void setClickIcon(Drawable drawable) {
        mClickIcon.setImageDrawable(drawable);
        mClickIcon.setVisibility(VISIBLE);
    }

    @Override
    public void setClickable(boolean clickable) {
        super.setClickable(clickable);
        if (mClickIcon != null) {
            if (clickable) {
                mClickIcon.setVisibility(View.VISIBLE);
            } else {
                mClickIcon.setVisibility(View.GONE);
            }
        }
    }
}
