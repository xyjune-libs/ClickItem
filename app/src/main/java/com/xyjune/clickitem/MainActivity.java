package com.xyjune.clickitem;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.xyjune.items.ClickItem;
import com.xyjune.items.RadioItem;
import com.xyjune.items.RadioItemGroup;
import com.xyjune.items.SwitchItem;
import com.xyjune.items.edititems.MsgCodeEditItem;
import com.xyjune.items.edititems.PhoneEditItem;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    ClickItem mClickItem;
    SwitchItem mSwitchItem;
    RadioItemGroup mRadioItemGroup;
    MsgCodeEditItem mMsgCodeEditItem;
    PhoneEditItem mPhoneEditItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("ClickItem v" + BuildConfig.VERSION_NAME);

        mClickItem = findViewById(R.id.click_item);
        mSwitchItem = findViewById(R.id.switch_item);
        mRadioItemGroup = findViewById(R.id.radio_group);
        mMsgCodeEditItem = findViewById(R.id.msgEdit);
        mPhoneEditItem = findViewById(R.id.phoneEdit);

        mPhoneEditItem.setEditText("137****5030");
        mPhoneEditItem.setEnabled(false);

        mClickItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, mClickItem.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        mSwitchItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String str = "SwitchItem: " + (isChecked ? "On" : "Off");
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });

        mRadioItemGroup.setOnCheckedChangeListener(new RadioItemGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioItemGroup radioGroup, int checkedId) {
                RadioItem item = findViewById(checkedId);
                Toast.makeText(MainActivity.this, item.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        mMsgCodeEditItem.checkTimer();
        Log.d(TAG, "onCreate: " + mMsgCodeEditItem.getChildAt(1).getLeft());
        mMsgCodeEditItem.setTimerListener(new MsgCodeEditItem.TimerListener() {
            @Override
            public void onTimerClick() {
                Toast.makeText(MainActivity.this, "onTimerClick", Toast.LENGTH_SHORT).show();
                mMsgCodeEditItem.startTimer(5000);
            }

            @Override
            public void onTimeEnd() {
                Toast.makeText(MainActivity.this, "onTimeEnd", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
