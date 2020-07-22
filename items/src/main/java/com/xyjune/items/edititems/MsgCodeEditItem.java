package com.xyjune.items.edititems;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.xyjune.items.EditItem;
import com.xyjune.items.R;
import com.xyjune.items.utils.VerificationCountDownTimer;

public class MsgCodeEditItem extends EditItem {

    private static final String TAG = "MsgCodeEditItem";

    private TextView mTextView;

    private TimerListener mTimerListener;

    private int textColor;
    private int textDisColor;
    private int textSize;

    private VerificationCountDownTimer verificationCountDownTimer;

    public MsgCodeEditItem(Context context) {
        this(context, null);
    }

    public MsgCodeEditItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MsgCodeEditItem);

        textColor = typedArray.getColor(R.styleable.MsgCodeEditItem_xy_msg_click_text_color, ContextCompat.getColor(context, R.color.def_text));
        textDisColor = typedArray.getColor(R.styleable.MsgCodeEditItem_xy_msg_click_text_discolor, ContextCompat.getColor(context, R.color.def_text));

        textSize = typedArray.getDimensionPixelSize(R.styleable.MsgCodeEditItem_xy_msg_click_text_size, dip2px(context, 14));

        typedArray.recycle();

        initText();
    }

    private void initText() {
        mTextView = new TextView(getContext());
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setTextColor(textColor);
        mTextView.setText("发送验证码");
        int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        mTextView.measure(spec, spec);
        int measuredWidth = mTextView.getMeasuredWidth();

        LayoutParams layoutParams = new LayoutParams(measuredWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMarginStart(mDefMargin);
        addView(mTextView, layoutParams);
        mTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerListener != null) {
                    mTimerListener.onTimerClick();
                }
            }
        });
    }

    public void setTimerListener(TimerListener timerListener) {
        mTimerListener = timerListener;
    }

    public void checkTimer() {
        if (!VerificationCountDownTimer.isTimeEnd())
            startTimer(VerificationCountDownTimer.getEndTime() - System.currentTimeMillis());
    }

    public void startTimer(final long mills) {
        if (verificationCountDownTimer != null && verificationCountDownTimer.isRunning()) {
            return;
        }
        verificationCountDownTimer = new VerificationCountDownTimer(mills, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                mTextView.setClickable(false);
                mTextView.setTextColor(textDisColor);
                mTextView.setText((millisUntilFinished / 1000) + " s");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mTextView.setText("重新发送");
                mTextView.setTextColor(textColor);
                mTextView.setClickable(true);
                if (mTimerListener != null) {
                    mTimerListener.onTimeEnd();
                }
            }
        };
        verificationCountDownTimer.timerStart();
    }

    public interface TimerListener {
        void onTimerClick();

        void onTimeEnd();
    }
}
