package com.xyjune.items.utils;

import android.content.Context;

/**
 * Created By June.
 * on 2020/3/14
 */
public class Utils {

    /**
     * dip转换px
     *
     * @param dip
     * @return
     */
    public static int dip2px(Context context, int dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }
}
