package com.xyjune.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public final class ClickItem extends BaseItem {

    private static final String TAG = "ClickItem";

    private ImageView mClickIcon;

    private int clickIcon;
    private int clickIconWidth;
    private int clickIconHeight;

    public ClickItem(Context context) {
        super(context);
        initClickItem();
    }

    public ClickItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClickItem);
        clickIcon = typedArray.getResourceId(R.styleable.ClickItem_xy_clickIcon, -1);
        int defWidth = dip2px(this.getContext(), 15);
        int defHeight = dip2px(this.getContext(), 15);
        clickIconWidth = typedArray.getDimensionPixelSize(R.styleable.ClickItem_xy_clickIcon_width, defWidth);
        clickIconHeight = typedArray.getDimensionPixelSize(R.styleable.ClickItem_xy_clickIcon_height, defHeight);
        typedArray.recycle();
        initClickItem();
    }

    private void initClickItem() {
        mClickIcon = new ImageView(getContext());
        setClickable(true);
        Log.d(TAG, "initClickItem: " + clickIcon);
        if (clickIcon != -1) {
            mClickIcon.setImageResource(clickIcon);
        } else {
            mClickIcon.setVisibility(GONE);
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(clickIconWidth, clickIconHeight);
        params.setMarginEnd(dip2px(this.getContext(), 15));
        this.addView(mClickIcon, params);
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
