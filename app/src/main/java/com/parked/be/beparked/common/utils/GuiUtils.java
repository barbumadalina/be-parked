package com.parked.be.beparked.common.utils;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GuiUtils {

    public static float dpToPx(Context ctx, int dp) {
        float density = ctx.getResources().getDisplayMetrics().density;
        return (dp * density);
    }

    public static float pxToDp(Context ctx, int px) {
        float density = ctx.getResources().getDisplayMetrics().density;
        return (px / density);
    }

    /**
     * todo: recreate this with a more complex parsing logic
     * - if event happened < 10 mins ago return "x mins ago"
     * - if event happened the day before return "yesterday + time"
     * - if event happened this week return "this week"
     * - else ...
     * @param date
     * @return
     */
    public static String lastUpdatedFromDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy hh:mm");
        return "Last update: " +  format.format(date);
    }
}