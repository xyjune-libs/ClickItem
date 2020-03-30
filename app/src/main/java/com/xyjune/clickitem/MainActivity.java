package com.xyjune.clickitem;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.xyjune.items.ItemGroup;

public class MainActivity extends AppCompatActivity {

    ItemGroup mItemGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mItemGroup = findViewById(R.id.item_group);
        mItemGroup.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        mItemGroup.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
    }
}
