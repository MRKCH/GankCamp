package com.cooke.gankcamp.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by kch on 2017/12/1.
 */

public class ScreenUtil {

    public static int getScreenWidth(Context context){
        DisplayMetrics metrics = new DisplayMetrics();
        if (context instanceof Activity) {
            ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int width = metrics.widthPixels;// 屏幕宽度（像素）
            return width;
        }else {
            return 0;
        }

    }

    public static int getScreenHeight(Context context){
        DisplayMetrics metrics = new DisplayMetrics();
        if (context instanceof Activity) {
            ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int height = metrics.heightPixels;// 屏幕宽度（像素）
            return height;
        }else {
            return 0;
        }

    }
}
