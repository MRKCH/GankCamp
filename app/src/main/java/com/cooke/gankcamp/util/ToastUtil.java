package com.cooke.gankcamp.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by kch on 2017/11/29.
 */

public class ToastUtil {
    public static void showToast(Context context,String content){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }
}
