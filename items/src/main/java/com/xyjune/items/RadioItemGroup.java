package com.xyjune.items;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;

public class RadioItemGroup extends ItemGroup {

    private static final String TAG = "XyRadioGroup";

    private int mCheckedId = -1;

    private CompoundButton.OnCheckedChangeListener mChildOnCheckedChangeListener;
    private boolean mProtectFromCheckedChange = false;
    private PassThroughHierarchyChangeListener mPassThroughListener;

    private OnCheckedChangeListener mOnCheckedChangeListener;

    public RadioItemGroup(Context context) {
        super(context);
        init();
    }

    public RadioItemGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mChildOnCheckedChangeListener = new CheckedStateTracker();
        mPassThroughListener = new PassThroughHierarchyChangeListener();
        super.setOnHierarchyChangeListener(mPassThroughListener);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // checks the appropriate radio button as requested in the XML file
        if (mCheckedId != -1) {
            mProtectFromCheckedChange = true;
            setCheckedStateForView(mCheckedId, true);
            mProtectFromCheckedChange = false;
            setCheckedId(mCheckedId);
        }
    }

    @Override
    public void addView(final View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof RadioItem) {
            ((RadioItem) child).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!((RadioItem) child).isChecked()) {
                        ((RadioItem) child).setChecked(true);
                        checkRadioItem((RadioItem) child);
                        if (mOnCheckedChangeListener != null)
                            mOnCheckedChangeListener.onCheckedChanged(RadioItemGroup.this, child.getId());
                    }
                }
            });
//            ((RadioItem) child).setOnTouchListener(new OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    if (event.getAction() == MotionEvent.ACTION_UP) {
//
//                        if (!((RadioItem) child).isChecked()) {
//                            ((RadioItem) child).setChecked(true);
//                            checkRadioItem((RadioItem) child);
//                            if (mOnCheckedChangeListener != null)
//                                mOnCheckedChangeListener.onCheckedChanged(RadioItemGroup.this, child.getId());
//                        }
//                    }
//                    return true;
//                }
//            });
        }
        super.addView(child, index, params);
    }

    private void checkRadioItem(RadioItem radioItem) {
        View child;
        int radioCount = getChildCount();
        for (int i = 0; i < radioCount; i++) {
            child = getChildAt(i);
            if (child instanceof RadioItem) {
                if (child != radioItem) {
                    ((RadioItem) child).setChecked(false);
                }
            }
        }
    }

    private void setCheckedId(int id) {
        mCheckedId = id;
    }

    private void setCheckedStateForView(int viewId, boolean checked) {
        View checkedView = findViewById(viewId);
        if (checkedView instanceof RadioItem) {
            ((RadioItem) checkedView).setChecked(checked);
        }
    }

    public int getCheckedRadioButtonId() {
        return mCheckedId;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListener = onCheckedChangeListener;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(RadioItemGroup radioGroup, int checkedId);
    }


    private class CheckedStateTracker implements CompoundButton.OnCheckedChangeListener {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // prevents from infinite recursion
            if (mProtectFromCheckedChange) {
                return;
            }

            mProtectFromCheckedChange = true;
            if (mCheckedId != -1) {
                setCheckedStateForView(mCheckedId, false);
            }
            mProtectFromCheckedChange = false;

            int id = buttonView.getId();
            setCheckedId(id);
        }
    }

    public void clearCheck() {
        check(-1);
    }

    public void check(int id) {
        // don't even bother
        if (id != -1 && (id == mCheckedId)) {
            return;
        }

        if (mCheckedId != -1) {
            setCheckedStateForView(mCheckedId, false);
        }

        if (id != -1) {
            setCheckedStateForView(id, true);
        }

        setCheckedId(id);
    }

    @Override
    public void setOnHierarchyChangeListener(OnHierarchyChangeListener listener) {
        // the user listener is delegated to our pass-through listener
        mPassThroughListener.mOnHierarchyChangeListener = listener;
    }

    private class PassThroughHierarchyChangeListener implements ViewGroup.OnHierarchyChangeListener {

        private ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;

        @Override
        public void onChildViewAdded(View parent, View child) {
            if (parent == RadioItemGroup.this && child instanceof RadioItem) {
                int id = child.getId();
                // generates an id if it's missing
                if (id == View.NO_ID) {
                    id = child.hashCode();
                    child.setId(id);
                }
                ((RadioItem) child).setOnCheckedChangeListener(mChildOnCheckedChangeListener);
            }

            if (mOnHierarchyChangeListener != null) {
                mOnHierarchyChangeListener.onChildViewAdded(parent, child);
            }
        }

        @Override
        public void onChildViewRemoved(View parent, View child) {
            if (parent == RadioItemGroup.this && child instanceof RadioItem) {
                ((RadioItem) child).setOnCheckedChangeListener(null);
            }

            if (mOnHierarchyChangeListener != null) {
                mOnHierarchyChangeListener.onChildViewRemoved(parent, child);
            }
        }
    }
}
